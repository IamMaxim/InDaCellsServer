package ru.iammaxim.InDaCellsServer.Creatures.Mobs;

import ru.iammaxim.InDaCellsServer.Creatures.Creature;
import ru.iammaxim.InDaCellsServer.World.World;

public class Hedgehog extends Creature {
    public Hedgehog(World world) {
        super(world, "Ёжик", 2, 1);
    }
}
