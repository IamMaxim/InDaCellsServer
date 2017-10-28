package ru.iammaxim.InDaCellsServer.Creatures.Mobs;

import ru.iammaxim.InDaCellsServer.Creatures.Creature;
import ru.iammaxim.InDaCellsServer.World.World;

public class Trasher extends Creature {
    public Trasher(){
        super(new World("World"), "Мусорщик", 10, 4);
    }
}
