package ru.iammaxim.InDaCellsServer.World;

import ru.iammaxim.InDaCellsServer.Creatures.Creature;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Entrance {
    private int id;
    private int destCellID;
    private String name;
    private World world;

    public Entrance(World world, int destCellID, String name) {
        this.destCellID = destCellID;
        this.name = name;
        this.world = world;
        id = (int) (Math.random() * Integer.MAX_VALUE);
    }

    public void transit(Creature c) {
        WorldCell dest = world.getCell(destCellID);
        if (dest == null) {
            System.out.println("Can't transit " + c.getName() + ", because destination is null");
            return;
        }
        c.move(destCellID);
    }

    public Entrance setID(int id) {
        this.id = id;
        return this;
    }

    public int getID() {
        return id;
    }

    public void write(DataOutputStream dos) throws IOException {
        dos.writeInt(id);
        dos.writeInt(destCellID);
        dos.writeUTF(name);
    }

    public static Entrance read(World world, DataInputStream dis) throws IOException {
        int id = dis.readInt();
        int destCellID = dis.readInt();
        String name = dis.readUTF();
        return new Entrance(world, destCellID, name).setID(id);
    }

    public String getName() {
        return name;
    }
}
