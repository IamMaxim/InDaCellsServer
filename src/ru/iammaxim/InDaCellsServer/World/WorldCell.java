package ru.iammaxim.InDaCellsServer.World;


import ru.iammaxim.InDaCellsServer.Activators.Activator;
import ru.iammaxim.InDaCellsServer.Creatures.Creature;
import ru.iammaxim.InDaCellsServer.Items.Item;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class WorldCell {
    private int x, y;
    private String name = "Unnamed cell";
    private ArrayList<Creature> creatures = new ArrayList<>();
    private ArrayList<Activator> activators = new ArrayList<>();
    private ArrayList<Item> items = new ArrayList<>();

    public WorldCell() {
    }

    public WorldCell(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public WorldCell setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public String toString() {
        if (name != null)
            return name;
        else return "Cell";
    }

    public void addCreature(Creature creature) {
        creatures.add(creature);
    }

    public void removeCreature(Creature creature) {
        creatures.remove(creature);
    }

    public ArrayList<Creature> getCreatures() {
        return creatures;
    }

    public void write(DataOutputStream dos) throws IOException {
        dos.writeInt(x);
        dos.writeInt(y);
        dos.writeUTF(name);

        dos.writeInt(creatures.size());
        for (Creature c : creatures) {
            c.write(dos);
        }

        dos.writeInt(activators.size());
        for (Activator a : activators) {
            a.write(dos);
        }

        dos.writeInt(items.size());
        for (Item i : items) {
            i.write(dos);
        }
    }

    public static WorldCell read(DataInputStream dis) throws IOException {
        WorldCell cell = new WorldCell();

        cell.x = dis.readInt();
        cell.y = dis.readInt();
        cell.name = dis.readUTF();


        // TODO: CHECK THIS!
        for (int i = 0; i < dis.readInt(); i++) {
            cell.creatures.add(Creature.read(dis));
        }

        for (int i = 0; i < dis.readInt(); i++) {
            cell.activators.add(Activator.read(dis));
        }

        for (int i = 0; i < dis.readInt(); i++) {
            cell.items.add(Item.read(dis));
        }

        return cell;
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }
}
