package ru.iammaxim.InDaCellsServer;

import ru.iammaxim.InDaCellsServer.Creatures.Creature;
import ru.iammaxim.InDaCellsServer.Creatures.Player;
import ru.iammaxim.InDaCellsServer.NetBus.NetBus;
import ru.iammaxim.InDaCellsServer.NetBus.NetBusHandler;
import ru.iammaxim.InDaCellsServer.Packets.PacketCell;
import ru.iammaxim.InDaCellsServer.Packets.PacketMove;
import ru.iammaxim.InDaCellsServer.Packets.PacketStats;
import ru.iammaxim.InDaCellsServer.World.World;
import ru.iammaxim.InDaCellsServer.World.WorldCell;
import ru.iammaxim.NetLib.Client;
import ru.iammaxim.NetLib.NetLib;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class Server {
    public World world;
    public static final long tickTimeNanos = 10000000; // 0.01sec
    public static final int startX = 0, startY = 0;

    public Server() {
        world = new World("World");

        for (int x = -10; x <= 10; x++)
            for (int y = -10; y <= 10; y++) {
                WorldCell cell = new WorldCell();
                world.addCell(x, y, cell);
            }

        world.getCell(0, 0).addCreature(new Creature(world, "A very dangerous one"));

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
}
