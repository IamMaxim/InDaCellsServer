package ru.iammaxim.InDaCellsServer;

import ru.iammaxim.InDaCellsServer.Creatures.Creature;
import ru.iammaxim.InDaCellsServer.Creatures.Player;
import ru.iammaxim.InDaCellsServer.World.World;
import ru.iammaxim.InDaCellsServer.World.WorldCell;

public class Server {
    public World world;
    public static final long tickTimeNanos = 10000000;

    public Server() {
        world = new World("World");
        WorldCell cell = new WorldCell();
        world.addCell(0, 0, cell);
        Player p = new Player(world, "Player1");
        cell.addCreature(p);
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
