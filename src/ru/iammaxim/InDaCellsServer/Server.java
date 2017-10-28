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
    public static final long tickTimeNanos = 10000000;

    public Server() {
        world = new World("World");

        WorldCell cell = new WorldCell();
        world.addCell(0, 0, cell);
        Player p = new Player(world, "Player1");
        cell.addCreature(p);
        p.setMaxHP(10);
        p.setMaxHunger(10);
        p.setMaxSP(10);

        NetLib.setOnClientConnect((client -> {
            try {
                NetLib.send(client.name, new PacketStats(p));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }));
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
