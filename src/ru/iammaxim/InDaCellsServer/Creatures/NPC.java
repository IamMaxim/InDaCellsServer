package ru.iammaxim.InDaCellsServer.Creatures;

import ru.iammaxim.InDaCellsServer.World.World;

public class NPC extends Human {
    public NPC(World world, String name) {
        super(world, name);
    }

    public void speak(String text){
        System.out.println(name + ": " + text);
    }
}
