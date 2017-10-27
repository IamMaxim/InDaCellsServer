package ru.iammaxim.InDaCellsServer.Creatures;

import ru.iammaxim.InDaCellsServer.Items.Item;
import ru.iammaxim.InDaCellsServer.World.World;

import java.util.ArrayList;
import java.util.HashMap;

public class Human extends Creature implements Attacker {
    protected float SP;
    protected HashMap<Item.Slot, Item> equippedItems = new HashMap<>();
    protected ArrayList<Item> inventory = new ArrayList<>();


    public Human(World world, String name) {
        super(world, name);
    }

    @Override
    public void tick() {
        super.tick();

        SP += 1;
    }

    public HashMap<Item.Slot, Item> getEquippedItems() {
        return equippedItems;
    }

    public ArrayList<Item> getInventory() {
        return inventory;
    }

    public float getSP() {
        return SP;
    }

    @Override
    public void attack(Creature victim) {
        // TODO: change to real value
        victim.damage(1);
    }
}
