package ru.iammaxim.InDaCellsServer.World;

import ru.iammaxim.InDaCellsServer.Creatures.Mobs.*;
import ru.iammaxim.InDaCellsServer.Creatures.NPCs.*;

public class WorldCreator {
    public static void create(World world) {
        //Town
        world.getCell(0, 0).addCreature(new RoyFirstMarshal(world)).addCreature(new JoCaptain(world));
        world.getCell(0, 1).addCreature(new Dog(world));
        world.getCell(-1, 0).addCreature(new Hedgehog(world));
        world.getCell(-1, 1).addCreature(new Beggar(world)).addCreature(new Beggar(world));
        world.getCell(1, -1).addCreature(new Beggar(world));
        world.getCell(2, 0).addCreature(new Dog(world)).addCreature(new Dog(world)).addCreature(new Dog(world));
        world.getCell(0, 2).addCreature(new Trasher(world)).addCreature(new Trasher(world)).addCreature(new Trasher(world));
        world.getCell(-2, 0).addCreature(new Trasher(world));

        //Tod's house
        world.getCell(-2, -7).addCreature(new TodOldFarmer(world)).addCreature(new Raider(world)).addCreature(new Raider(world)).addCreature(new Raider(world));

        //Raider's base.
        world.getCell(-1, -5).addCreature(new GeorgRaidersLeader(world)).addCreature(new RammCrusher(world)).addCreature(new Raider(world));
        world.getCell(-1,-4).addCreature(new Raider(world));
        world.getCell(0, -5).addCreature(new Raider(world)).addCreature(new Raider(world));

        //Ruins
        world.getCell(1, 3).addCreature(new Hedgehog(world)).addCreature(new Hedgehog(world));
        world.getCell(2, 1).addCreature(new Dog(world));
        world.getCell(2, 4).addCreature(new Fox(world));
        world.getCell(2, 5).addCreature(new Hedgehog(world)).addCreature(new Hedgehog(world)).addCreature(new Hedgehog(world));

        //Mountains
        world.getCell(-18, 18).addCreature(new Beggar(world));
        world.getCell(16, -18).addCreature(new Beggar(world));
        world.getCell(13, -19).addCreature(new Trasher(world)).addCreature(new Trasher(world));
        world.getCell(-13, -18).addCreature(new Beggar(world));

        //Top Trasher's way
        world.getCell(16, 19).addCreature(new Beggar(world));
        world.getCell(16, 15).addCreature(new Trasher(world)).addCreature(new Trasher(world));
        world.getCell(16, 14).addCreature(new Trasher(world));
        world.getCell(16, 9).addCreature(new Trasher(world));
        world.getCell(16, 5).addCreature(new Dog(world)).addCreature(new Dog(world));
        world.getCell(16, 1).addCreature(new Dog(world));

        //Bottom Trasher's way
        world.getCell(16, -2).addCreature(new Dog(world));
        world.getCell(16, -4).addCreature(new Dog(world)).addCreature(new Dog(world));
        world.getCell(16, -7).addCreature(new Trasher(world));
        world.getCell(16, -10).addCreature(new Trasher(world));
        world.getCell(16, -16).addCreature(new Trasher(world)).addCreature(new Trasher(world));
        world.getCell(16, -18).addCreature(new Beggar(world));
    }
}
