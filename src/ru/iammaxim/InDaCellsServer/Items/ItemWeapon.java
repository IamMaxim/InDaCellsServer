package ru.iammaxim.InDaCellsServer.Items;

import java.io.DataOutputStream;
import java.io.IOException;

public class ItemWeapon extends Item {
    private float damage;

    public ItemWeapon(int itemID, String name, String desc, float damage) {
        super(itemID, Type.WEAPON, name, desc);
        this.damage = damage;
    }

    @Override
    public void write(DataOutputStream dos) throws IOException {
        super.write(dos);
        dos.writeFloat(damage);
    }

    @Override
    public Item clone() {
        return new ItemWeapon(id, name, description, damage);
    }

    public float getDamage() {
        return damage;
    }
}
