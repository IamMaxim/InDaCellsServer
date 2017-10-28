package ru.iammaxim.InDaCellsServer.Creatures;

import ru.iammaxim.InDaCellsServer.Items.Item;
import ru.iammaxim.InDaCellsServer.Packets.PacketStats;
import ru.iammaxim.InDaCellsServer.Quests.Quest;
import ru.iammaxim.InDaCellsServer.World.World;
import ru.iammaxim.NetLib.NetLib;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Human extends Creature implements Attacker {
    protected HashMap<Item.Type, Item> equippedItems = new HashMap<>();
    protected ArrayList<Item> inventory = new ArrayList<>();
    protected ArrayList<Quest> attachedQuests = new ArrayList<>();
    protected float hunger;
    protected float maxHunger;
    protected float sp;
    protected float maxSP;
    protected int statsUpdateTimer = 0;
    protected int spTimer = 0;

    public Human(World world, String name) {
        super(world, name);
    }

    @Override
    public void tick() {
        super.tick();

        hp += 0.01;
        hp = Math.min(hp, maxHP);

        sp += 0.01;
        sp = Math.min(hp, maxHP);

        spTimer++;
        if (spTimer == 500) {
            spTimer = 0;
            sp = 0;
        }


        statsUpdateTimer++;
        if (statsUpdateTimer == 100) {
            statsUpdateTimer = 0;
            try {
                NetLib.send(name, new PacketStats(this));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public HashMap<Item.Type, Item> getEquippedItems() {
        return equippedItems;
    }

    public ArrayList<Item> getInventory() {
        return inventory;
    }

    public float getSP() {
        return sp;
    }

    public void addItem(Item item) {
        inventory.add(item);
        attachedQuests.forEach(q -> q.onItemAdd(item));
    }

    @Override
    public void attack(Creature victim) {
        // TODO: change to real value
        victim.damage(1);
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
}
