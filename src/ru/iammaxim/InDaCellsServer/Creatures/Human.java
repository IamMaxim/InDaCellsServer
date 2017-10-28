package ru.iammaxim.InDaCellsServer.Creatures;

import ru.iammaxim.InDaCellsServer.Items.Item;
import ru.iammaxim.InDaCellsServer.Quests.Quest;
import ru.iammaxim.InDaCellsServer.World.World;

import java.util.ArrayList;
import java.util.HashMap;

public class Human extends Creature {
    protected HashMap<Item.Type, Item> equippedItems = new HashMap<>();
    protected ArrayList<Item> inventory = new ArrayList<>();
    protected ArrayList<Quest> attachedQuests = new ArrayList<>();
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
}
