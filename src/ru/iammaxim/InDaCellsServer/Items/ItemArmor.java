package ru.iammaxim.InDaCellsServer.Items;

public class ItemArmor extends Item {
    private float armorValue;

    public ItemArmor(int id, String name, String desc, float armorValue) {
        super(id, Type.ARMOR, desc, name);
        this.armorValue = armorValue;
    }

    public float getArmorValue() {
        return armorValue;
    }
}
