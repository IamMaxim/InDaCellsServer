package ru.iammaxim.InDaCellsServer;

import ru.iammaxim.InDaCellsServer.Activators.Activator;
import ru.iammaxim.InDaCellsServer.Creatures.Creature;
import ru.iammaxim.InDaCellsServer.Creatures.Player;
import ru.iammaxim.InDaCellsServer.NetBus.NetBus;
import ru.iammaxim.InDaCellsServer.NetBus.NetBusHandler;
import ru.iammaxim.InDaCellsServer.Packets.*;
import ru.iammaxim.InDaCellsServer.World.World;
import ru.iammaxim.InDaCellsServer.World.WorldCell;
import ru.iammaxim.NetLib.Client;
import ru.iammaxim.NetLib.NetLib;
import ru.iammaxim.NetLib.Packet;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class Server {
    public World world;
    public static final long tickTimeNanos = 10000000; // 0.01sec
    public static final int startX = 0, startY = 0;

    public Server() {
        world = new World("World");
        initHandlers();

        boolean loaded = false;
        try {
            loaded = load("world.sav");
        } catch (IOException e) {
            e.printStackTrace();
        }

        // generate starting world
        if (!loaded) {
            System.out.println("No save found. Generating new world");

            for (int x = -10; x <= 10; x++)
                for (int y = -10; y <= 10; y++) {
                    WorldCell cell = new WorldCell();
                    world.addCell(x, y, cell);
                }

            world.getCell(0, 0).addCreature(new Creature(world, "A very dangerous one"));
            world.getCell(0, 1).addActivator(new Activator("Push me!") {
                @Override
                public void activate(Creature c) {
                    super.activate(c);

                    c.damage(5);
                }
            }.setDescription("Push me! Harder, harder!"));
        }


    }

    public void tick() {
//        System.out.println("Tick()");

        ArrayList<Creature> creatures = new ArrayList<>();
        synchronized (world.getCells()) {
            for (Integer integer1 : world.getCells().keySet()) {
                HashMap<Integer, WorldCell> row = world.getCells().get(integer1);
                for (Integer integer : row.keySet()) {
                    WorldCell cell = row.get(integer);
                    creatures.addAll(cell.getCreatures());
                }
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

                p.setMaxHP(10);
                p.maxHP();

                p.setMaxSP(10);
//                p.maxSP();

                p.setMaxHunger(10);
                p.maxHunger();

                world.addPlayer(c.name, p);
                world.getCell(startX, startY).addCreature(p);
            }
            try {
                NetLib.send(c.name, new PacketStats(p));
                NetLib.send(c.name, new PacketCell(p.getCurrentCell()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        NetLib.setOnPacketReceive(NetBus::handle);

        NetBus.register(PacketDoAction.class, (Client c, Packet p) -> {
            PacketDoAction packet = (PacketDoAction) p;
            System.out.println("Gonna do action " + packet.type + " on " + packet.targetID);

            try {
                switch (((PacketDoAction) p).type) {
                    case ATTACK:
                        world.getPlayer(c.name).attack(((PacketDoAction) p).targetID);
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
                        try {
                            for (Player player1 : cell.getPlayers()) {
                                NetLib.send(player1.getName(), new PacketAddToLog(
                                        new LogElement(LogElement.Type.MESSAGE, message.message, player1.getName())));
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
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
