package ru.iammaxim.InDaCellsServer.Items;

import java.io.DataOutputStream;
import java.io.IOException;

public class ItemArmor extends Item {
    private float armorValue;

    public ItemArmor(int itemID, String name, String desc, float armorValue) {
        super(itemID, Type.ARMOR, name, desc);
        this.armorValue = armorValue;
    }

    @Override
    public void write(DataOutputStream dos) throws IOException {
        super.write(dos);
        dos.writeFloat(armorValue);
    }

    @Override
    public Item clone() {
        return new ItemArmor(itemID, name, description, armorValue);
    }

    public float getArmorValue() {
        return armorValue;
    }
}
