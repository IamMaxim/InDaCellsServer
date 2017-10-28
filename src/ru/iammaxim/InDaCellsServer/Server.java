package ru.iammaxim.InDaCellsServer;

import ru.iammaxim.InDaCellsServer.Creatures.Creature;
import ru.iammaxim.InDaCellsServer.Creatures.Player;
import ru.iammaxim.InDaCellsServer.Packets.PacketStats;
import ru.iammaxim.InDaCellsServer.World.World;
import ru.iammaxim.InDaCellsServer.World.WorldCell;
import ru.iammaxim.NetLib.NetLib;

import java.io.IOException;

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

        NetLib.setOnClientConnect(c -> {
            Player p = world.getPlayers().get(c.name);
            // player is connecting first time
            if (p == null) {
                p = new Player(world, c.name);
                p.setX(startX);
                p.setY(startY);
            }
            p.maxHP();
            p.maxSP();
            p.maxHunger();
            try {
                NetLib.send(c.name, new PacketStats(p));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public void tick() {
        world.getCells().forEach((x, row) -> row.forEach((y, cell) -> {
            cell.getCreatures().forEach(Creature::tick);
        }));
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
