package ru.iammaxim.InDaCellsServer.Items;

public class ItemWeapon extends Item {
    private float damage;

    public ItemWeapon(int id, String name, String desc, float damage) {
        super(id, Type.WEAPON, name, desc);
        this.damage = damage;
    }

    public float getDamage() {
        return damage;
    }
}
