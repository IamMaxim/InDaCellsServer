package ru.iammaxim.InDaCellsServer.Creatures;

import ru.iammaxim.InDaCellsServer.Items.Item;
import ru.iammaxim.InDaCellsServer.Packets.PacketInventoryChange;
import ru.iammaxim.InDaCellsServer.Quests.Quest;
import ru.iammaxim.InDaCellsServer.World.World;
import ru.iammaxim.NetLib.NetLib;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class Human extends Creature {
    protected HashMap<Item.Type, Integer> equippedItems = new HashMap<>();
    protected ArrayList<Item> inventory = new ArrayList<>();
    protected ArrayList<Quest> attachedQuests = new ArrayList<>();
    protected HashMap<Attribute, Float> attributes = new HashMap<>();
    protected float hunger;
    protected float maxHunger;
    protected float sp;
    protected float maxSP;

    public Human(World world, String name) {
        super(world, name);
    }

    @Override
    public void tick() {
        super.tick();

        hp += 0.01;
        hp = Math.min(hp, maxHP);

        sp += 0.01;
        sp = Math.min(sp, maxSP);
    }

    public HashMap<Item.Type, Integer> getEquippedItems() {
        return equippedItems;
    }

    public ArrayList<Item> getInventory() {
        return inventory;
    }

    @Override
    public void write(DataOutputStream dos) throws IOException {
        super.write(dos);

        dos.writeFloat(hunger);
        dos.writeFloat(maxHunger);
        dos.writeFloat(sp);
        dos.writeFloat(maxSP);

        dos.writeInt(inventory.size());
        inventory.forEach(i -> {
            try {
                i.write(dos);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public float getSP() {
        return sp;
    }

    public void addItem(Item item) {
        inventory.add(item);
        attachedQuests.forEach(q -> q.getCurrentStage().onItemAdd(this, item));
    }

    public void removeItem(Item item) {
        removeItem(inventory.indexOf(item));
    }

    public void removeItem(int index) {
        Iterator<Item.Type> it = equippedItems.keySet().iterator();

        while (it.hasNext()) {
            Item.Type slot = it.next();
            int i = equippedItems.get(slot);
            if (index == i) {
                equippedItems.remove(slot);

                if (this instanceof Player) {
                    try {
                        NetLib.send(name, new PacketInventoryChange(PacketInventoryChange.Type.UNEQUIP, slot));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        inventory.remove(index);
    }

    public float getHunger() {
        return hunger;
    }

    public void setMaxHunger(float hunger) {
        maxHunger = hunger;
    }

    public void setMaxSP(float maxSP) {
        this.maxSP = maxSP;
    }

    public void maxSP() {
        sp = maxSP;
    }

    public void maxHunger() {
        hunger = maxHunger;
    }

    public float getMaxHP() {
        return maxHP;
    }

    public float getMaxHunger() {
        return maxHunger;
    }

    public float getMaxSP() {
        return maxSP;
    }

    public void pickup(int targetID) {
        setState(State.PICKING_UP, 50);
        actionTargetID = targetID;
    }
}