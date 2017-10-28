package ru.iammaxim.InDaCellsServer.World;


import ru.iammaxim.InDaCellsServer.Activators.Activator;
import ru.iammaxim.InDaCellsServer.Creatures.Creature;
import ru.iammaxim.InDaCellsServer.Items.Item;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;

public class WorldCell {
    private int x, y;
    private String name = "Unnamed cell";
    private HashMap<Integer, Creature> creatures = new HashMap();
    private HashMap<Integer, Activator> activators = new HashMap();
    private HashMap<Integer, Item> items = new HashMap();

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
        creatures.put(creature.getID(), creature);
    }

    public void removeCreature(Creature creature) {
        creatures.remove(creature.getID());
    }

    public Collection<Creature> getCreatures() {
        return creatures.values();
    }

    public void write(DataOutputStream dos) throws IOException {
        dos.writeInt(x);
        dos.writeInt(y);
        dos.writeUTF(name);

        dos.writeInt(creatures.size());
        for (Creature c : creatures.values()) {
            c.write(dos);
        }

        dos.writeInt(activators.size());
        for (Activator a : activators.values()) {
            a.write(dos);
        }

        dos.writeInt(items.size());
        for (Item i : items.values()) {
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
            Creature c = Creature.read(dis);
            cell.creatures.put(c.getID(), c);
        }

        for (int i = 0; i < dis.readInt(); i++) {
            Activator a = Activator.read(dis);
            cell.activators.put(a.getID(), a);
        }

        for (int i = 0; i < dis.readInt(); i++) {
            Item item = Item.read(dis);
            cell.items.put(item.getID(), item);
        }

        return cell;
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    public Creature getCreature(int id) {
        return creatures.get(id);
    }

    public Activator getActivator(int id) {
        return activators.get(id);
    }
}
