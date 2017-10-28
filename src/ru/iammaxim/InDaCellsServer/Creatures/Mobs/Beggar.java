package ru.iammaxim.InDaCellsServer.Creatures.Mobs;

import ru.iammaxim.InDaCellsServer.Creatures.Creature;
import ru.iammaxim.InDaCellsServer.World.World;

public class Beggar extends Creature {
    public Beggar(){
        super(new World("World"), "Агрессивный бомж", 15, 5);
    }
}
