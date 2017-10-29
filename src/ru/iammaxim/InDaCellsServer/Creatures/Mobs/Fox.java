package ru.iammaxim.InDaCellsServer.Creatures.Mobs;

import ru.iammaxim.InDaCellsServer.Creatures.Creature;
import ru.iammaxim.InDaCellsServer.World.World;

public class Fox extends Creature {
    public Fox() {
        super(new World("World"), "Лиса", 5, 2);
    }
}