package ru.iammaxim.InDaCellsServer;

import ru.iammaxim.InDaCellsServer.Activators.Activator;
import ru.iammaxim.InDaCellsServer.Creatures.Creature;
import ru.iammaxim.InDaCellsServer.Creatures.NPC;
import ru.iammaxim.InDaCellsServer.Creatures.Player;
import ru.iammaxim.InDaCellsServer.Dialogs.DialogTopic;
import ru.iammaxim.InDaCellsServer.Items.Item;
import ru.iammaxim.InDaCellsServer.NetBus.NetBus;
import ru.iammaxim.InDaCellsServer.Packets.*;
import ru.iammaxim.InDaCellsServer.Quests.Quest;
import ru.iammaxim.InDaCellsServer.World.Entrance;
import ru.iammaxim.InDaCellsServer.World.World;
import ru.iammaxim.InDaCellsServer.World.WorldCell;
import ru.iammaxim.NetLib.Client;
import ru.iammaxim.NetLib.NetLib;
import ru.iammaxim.NetLib.Packet;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Server {
    public static final long tickTimeNanos = 10000000; // 0.01sec
    public static final int startX = 0, startY = 0;
    public World world;

    public Server() {
        world = new World("World");
        initHandlers();

        Quest.registerQuests(world);

        boolean loaded = false;
//        try {
//            loaded = load("world.sav");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        // generate starting world
        if (!loaded) {
            System.out.println("No save found. Generating new world");

            for (int x = -20; x <= 20; x++) {
                for (int y = -20; y <= 20; y++) {
                    WorldCell cell = new WorldCell(false);
                    world.addCell(x, y, cell);
                }
            }

            world.getCell(0, 0).addCreature(
                    new NPC(world, "Test NPC")
                            .attachQuest(Quest.quests.get(0))
                            .addTopic(new DialogTopic("Start TestQuest1", "Yay, hello there!") {
                                @Override
                                public void onSay(Player p) {
                                    p.startQuest(0);
                                }
                            }));

            WorldCell testEntrance1 = new WorldCell(true);
            world.addCell(testEntrance1);
            world.getCell(0, 1).addEntrance(new Entrance(world, testEntrance1.getID(), "Test door"));
            testEntrance1.addEntrance(new Entrance(world, world.getCell(0, 1).getID(), "Test door to outside"));

            // debug things
            world.getCell(0, 1).addActivator(new Activator(2, "Item generator"));
            world.getCell(0, 1).addActivator(new Activator(3, "Weapon generator"));
            world.getCell(0, 1).addActivator(new Activator(4, "Armor generator"));
        }
    }

    public void tick() {
        ArrayList<Creature> creatures = new ArrayList<>();
        synchronized (world.getCells()) {
            for (WorldCell c : world.getCells().values()) {
                creatures.addAll(c.getCreatures());
            }
        }
        creatures.forEach(Creature::tick);
    }

    private void initHandlers() {
        NetLib.setOnClientConnect(c -> {
            Player p = world.getPlayers().get(c.name);
            // player is connecting first time
            if (p == null) {
                p = new Player(world, c.name);
                p.setX(startX);
                p.setY(startY);
                p.setDamage(10);

                p.setMaxHP(10);
                p.maxHP();

                p.setMaxSP(10);
//                p.maxSP();

                p.setMaxHunger(10);
                p.maxHunger();

                world.addPlayer(p);
                world.getCell(startX, startY).addCreature(p);
            }
            try {
                NetLib.send(c.name, new PacketStats(p));
                NetLib.send(c.name, new PacketCell(p.getCurrentCell()));
                NetLib.send(c.name, new PacketInventory(p.getInventory()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        NetLib.setOnPacketReceive(NetBus::handle);

        NetBus.register(PacketDoAction.class, (Client c, Packet p) -> {
            PacketDoAction packet = (PacketDoAction) p;
//            System.out.println("Gonna do action " + packet.type + " on " + packet.targetID);

            try {
                switch (((PacketDoAction) p).type) {
                    case ATTACK:
                        world.getPlayer(c.name).attack(((PacketDoAction) p).targetID, 0);
                        NetLib.send(c.name, new PacketStartAction("Attacking...", 1));
                        break;
                    case DEFEND:
                        world.getPlayer(c.name).defend();
                        NetLib.send(c.name, new PacketStartAction("Defending...", 2));
                        break;
                    case ACTIVATE:
                        world.getPlayer(c.name).activate(((PacketDoAction) p).targetID);
                        NetLib.send(c.name, new PacketStartAction("Activating...", 1));
                        break;
                    case PICKUP_ITEM:
                        world.getPlayer(c.name).pickup(((PacketDoAction) p).targetID);
                        NetLib.send(c.name, new PacketStartAction("Picking up...", 0.5f));
                        break;
                    case TALK:
                        world.getPlayer(c.name).talk(((PacketDoAction) p).targetID, ((PacketDoAction) p).additionalString);
                        break;
                    case DO_ACTION:
                        world.getPlayer(c.name).addMessage("This feature is not yet implemented");
                        break;
                    case GO_TO:
                        Player player = world.getPlayer(c.name);
                        Entrance e = player.getCurrentCell().getEntrance(packet.targetID);
                        if (e == null) {
                            player.addMessage("No such entrance found.");
                            break;
                        }
                        e.transit(player);
                        break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        NetBus.register(PacketMove.class, (c, packet) -> {
            PacketMove p = (PacketMove) packet;
            Player player = world.getPlayer(c.name);

            switch (p.dir) {
                case LEFT:
                    player.move(player.getX() - 1, player.getY());
                    break;
                case RIGHT:
                    player.move(player.getX() + 1, player.getY());
                    break;
                case UP:
                    player.move(player.getX(), player.getY() + 1);
                    break;
                case DOWN:
                    player.move(player.getX(), player.getY() - 1);
                    break;
            }
        });

        NetBus.register(PacketSendMessage.class, (client, packet) -> {
            PacketSendMessage message = (PacketSendMessage) packet;
            Player player = world.getPlayer(client.name);

            for (int x = player.getX() - 1; x <= player.getX() + 1; x++) {
                for (int y = player.getY() - 1; y <= player.getY() + 1; y++) {
                    WorldCell cell = world.getCell(x, y);
                    if (cell != null) {
                        for (Player p : cell.getPlayers()) {
                            p.addMessage(player.getName(), message.message);
                        }
                    }
                }
            }
        });

        NetBus.register(PacketRequestQuestList.class, (c, p) -> {
            try {
                NetLib.send(c.name, new PacketQuestList(world.getPlayer(c.name).getQuests()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        NetBus.register(PacketCraft.class, (c, p) -> {
            Player player = world.getPlayer(c.name);
            PacketCraft packet = (PacketCraft) p;
            boolean hasAll = true;
            for (Integer id : packet.ingredients) {
                if (player.getItem(id) == null) {
                    hasAll = false;
                    player.addMessage("Couldn't craft because item with id " + id + " was not found in your inventory");
                    break;
                }
            }

            if (hasAll) {
                packet.ingredients.forEach(player::removeItemByID);
            }

            Item item = Item.craft(packet.type, packet.name, packet.description, 1);
            player.addItem(item);
        });
    }

    public void run() {
        while (true) {
            long startTime = System.nanoTime();
            tick();
            long elapsed = System.nanoTime() - startTime;
            if (elapsed < tickTimeNanos) {
                try {
                    long diff = tickTimeNanos - elapsed;
                    int seconds = (int) (diff / 1000000);
                    Thread.sleep(seconds, (int) (diff - seconds * 1000000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void startSaveThread() {
        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(60000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                    save("world.sav");
                    System.out.println("World saved.");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void save(String filename) throws IOException {
        File f = new File(filename);
        if (!f.exists())
            f.createNewFile();
        else
            f.renameTo(new File(filename + ".old"));

        DataOutputStream dos = new DataOutputStream(new FileOutputStream(f));
        world.save(dos);
        dos.close();
    }

    public boolean load(String filename) throws IOException {
        File f = new File(filename);
        if (!f.exists())
            return false;

        DataInputStream dis = new DataInputStream(new FileInputStream(f));
        world.load(dis);
        dis.close();
        return true;
    }
}
