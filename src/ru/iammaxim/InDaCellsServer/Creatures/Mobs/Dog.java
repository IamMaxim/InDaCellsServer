package ru.iammaxim.InDaCellsServer.Creatures.Mobs;

import ru.iammaxim.InDaCellsServer.Creatures.Creature;
import ru.iammaxim.InDaCellsServer.World.World;

public class Dog extends Creature {
    public Dog(World world) {
        super(world, "Пёс", 7, 3);
    }
}
