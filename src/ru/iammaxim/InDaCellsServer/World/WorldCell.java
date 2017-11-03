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
    private int id;
    private int x = 0, y = 0;
    private String name = "Wastelands";
    private String description = "";
    private boolean isInterior;
    private HashMap<Integer, Creature> creatures = new HashMap();
    private HashMap<Integer, Activator> activators = new HashMap();
    private HashMap<Integer, Item> items = new HashMap();
    private HashMap<Integer, Entrance> entrances = new HashMap<>();

    private WorldCell() {

    }

    public WorldCell(boolean isInterior) {
        this.isInterior = isInterior;
        id = (int) (Math.random() * Integer.MAX_VALUE);
    }

    public WorldCell setIsInterior(boolean isInterior) {
        this.isInterior = isInterior;
        return this;
    }

    public boolean isInterior() {
        return isInterior;
    }

    public WorldCell setID(int id) {
        this.id = id;
        return this;
    }

    public int getID() {
        return id;
    }

    public static WorldCell read(World world, DataInputStream dis) throws IOException {
        WorldCell cell = new WorldCell();

        cell.x = dis.readInt();
        cell.y = dis.readInt();
        cell.name = dis.readUTF();
        cell.description = dis.readUTF();
        cell.isInterior = dis.readBoolean();
        cell.id = dis.readInt();

        int activatorsCount = dis.readInt();
        for (int i = 0; i < activatorsCount; i++) {
            Activator a = Activator.read(dis);
            cell.addActivator(a);
        }

        int itemsCount = dis.readInt();
        for (int i = 0; i < itemsCount; i++) {
            Item item = Item.read(dis);
            cell.addItem(item);
        }

        int entrancesCount = dis.readInt();
        for (int i = 0; i < entrancesCount; i++) {
            Entrance e = Entrance.read(world, dis);
            cell.addEntrance(e);
        }

        int creaturesCount = dis.readInt();
        for (int i = 0; i < creaturesCount; i++) {
            Creature c = Creature.read(world, dis);
            cell.addCreature(c);
        }

        cell.getPlayers().forEach(world::addPlayer);

        return cell;
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

    public WorldCell addCreature(Creature creature) {
        creatures.put(creature.getID(), creature);
        creature.setX(getX());
        creature.setY(getY());
        creature.setCellID(id);
        update();
        return this;
    }

    public void removeCreature(Creature creature) {
//        System.out.println("[" + x + ", " + y + "] Removing " + creature.getName());
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
        dos.writeUTF(description);
        dos.writeBoolean(isInterior);
        dos.writeInt(id);

        dos.writeInt(activators.size());
        for (Activator a : activators.values()) {
            a.write(dos);
        }

        dos.writeInt(items.size());
        for (Item i : items.values()) {
            i.write(dos);
        }

        dos.writeInt(entrances.size());
        for (Entrance e : entrances.values()) {
            e.write(dos);
        }

        dos.writeInt(creatures.size());
        for (Creature c : creatures.values()) {
            c.write(dos);
        }
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void removeItem(Item i) {
        items.remove(i.getID());
        update();
    }

    public void addItem(Item i) {
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

    public String getName() {
        return name;
    }

    public WorldCell setName(String name) {
        this.name = name;
        return this;
    }

    public Collection<Entrance> getEntrances() {
        return entrances.values();
    }

    public WorldCell addEntrance(Entrance entrance) {
        entrances.put(entrance.getID(), entrance);
        return this;
    }

    public Entrance getEntrance(int id) {
        return entrances.get(id);
    }
}
