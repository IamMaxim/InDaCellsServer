package ru.iammaxim.InDaCellsServer.Creatures.Mobs;

import ru.iammaxim.InDaCellsServer.Creatures.Creature;
import ru.iammaxim.InDaCellsServer.World.World;

public class Raider extends Creature {
    public Raider(World world) {
        super(world, "Рейдер", 5, 2);
    }
}