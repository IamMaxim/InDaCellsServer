package ru.iammaxim.InDaCellsServer.Creatures;

import ru.iammaxim.InDaCellsServer.Items.Item;
import ru.iammaxim.InDaCellsServer.Quests.Quest;
import ru.iammaxim.InDaCellsServer.World.World;

import java.util.ArrayList;
import java.util.HashMap;

public class Human extends Creature implements Attacker {
    protected float SP;
    protected HashMap<Item.Type, Item> equippedItems = new HashMap<>();
    protected ArrayList<Item> inventory = new ArrayList<>();
    protected ArrayList<Quest> attachedQuests = new ArrayList<>();
    protected float hunger;
    protected float maxSP;

    public Human(World world, String name) {
        super(world, name);
    }

    @Override
    public void tick() {
        super.tick();

        SP += 1;
    }

    public HashMap<Item.Type, Item> getEquippedItems() {
        return equippedItems;
    }

    public ArrayList<Item> getInventory() {
        return inventory;
    }

    public float getSP() {
        return SP;
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

    }

    public void setMaxSP(float maxSP) {
        this.maxSP = maxSP;
    }
}
