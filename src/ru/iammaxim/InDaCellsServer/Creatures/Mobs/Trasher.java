package ru.iammaxim.InDaCellsServer.Creatures.Mobs;

import ru.iammaxim.InDaCellsServer.Creatures.Creature;
import ru.iammaxim.InDaCellsServer.World.World;

public class Trasher extends Creature {
    public Trasher(World world) {
        super(world, "Мусорщик", 10, 4);
    }
}
