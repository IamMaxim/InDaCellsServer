package ru.iammaxim.InDaCellsServer.World;


import ru.iammaxim.InDaCellsServer.Activators.Activator;
import ru.iammaxim.InDaCellsServer.Creatures.Creature;
import ru.iammaxim.InDaCellsServer.Creatures.NPC;
import ru.iammaxim.InDaCellsServer.Creatures.Player;
import ru.iammaxim.InDaCellsServer.Items.Item;
import ru.iammaxim.InDaCellsServer.Packets.PacketCell;
import ru.iammaxim.NetLib.NetLib;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
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

    public void update() {
        getPlayers().forEach(p -> new Thread(() -> {
            try {
                NetLib.send(p.getName(), new PacketCell(this));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start());
    }

    public void addCreature(Creature creature) {
        creatures.put(creature.getID(), creature);
        update();
    }

    public void removeCreature(Creature creature) {
        creatures.remove(creature.getID());
        update();
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

    public static WorldCell read(World world, DataInputStream dis) throws IOException {
        WorldCell cell = new WorldCell();

        cell.x = dis.readInt();
        cell.y = dis.readInt();
        cell.name = dis.readUTF();

        int creaturesCount = dis.readInt();
        for (int i = 0; i < creaturesCount; i++) {
            Creature c = Creature.read(world, dis);
            cell.creatures.put(c.getID(), c);
        }

        int activatorsCount = dis.readInt();
        for (int i = 0; i < activatorsCount; i++) {
            Activator a = Activator.read(dis);
            cell.activators.put(a.getID(), a);
        }

        int itemsCount = dis.readInt();
        for (int i = 0; i < itemsCount; i++) {
            Item item = Item.read(dis);
            System.out.println("Putting item with ID: " + item.getID());
            cell.items.put(item.getID(), item);
        }

        cell.getPlayers().forEach(p -> {
            world.addPlayer(p.getName(), p);
        });

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

    public ArrayList<Player> getPlayers() {
        ArrayList<Player> players = new ArrayList<>();
        creatures.values().forEach(c -> {
            if (c instanceof Player)
                players.add((Player) c);
        });
        return players;
    }

    public Collection<Item> getItems() {
        return items.values();
    }

    public Collection<Activator> getActivators() {
        return activators.values();
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void removeItem(Item i) {
        items.remove(i.getID());
        update();
    }

    public void addItem(Item i) {
        System.out.println("Adding item with id " + i.getID());
        items.put(i.getID(), i);
        update();
    }

    public void addActivator(Activator a) {
        activators.put(a.getID(), a);
        update();
    }

    public void removeActivator(Activator a) {
        activators.remove(a.getID());
        update();
    }

    public NPC getNPC(int targetID) {
        return (NPC) creatures.get(targetID);
    }
}
