package ru.iammaxim.InDaCellsServer.Creatures.Mobs;

import ru.iammaxim.InDaCellsServer.Creatures.Creature;
import ru.iammaxim.InDaCellsServer.World.World;

public class Beggar extends Creature {
    public Beggar(World world) {
        super(world, "Агрессивный бомж", 15, 5);
    }
}
