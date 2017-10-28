package ru.iammaxim.InDaCellsServer.Creatures.Mobs;

import ru.iammaxim.InDaCellsServer.Creatures.Creature;
import ru.iammaxim.InDaCellsServer.World.World;

public class Dog extends Creature {
    public Dog(){
        super(new World("World"), "Пёс", 7, 3);
    }
}
