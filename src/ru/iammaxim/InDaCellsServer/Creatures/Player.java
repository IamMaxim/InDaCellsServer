package ru.iammaxim.InDaCellsServer.Creatures;


import ru.iammaxim.InDaCellsServer.World.World;

public class Player extends Human {

    public Player(World world, String name) {
        super(world, name);
    }

    @Override
    public void tick() {
        super.tick();

//        System.out.println("ticking player " + name);
    }
}
