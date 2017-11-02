package ru.iammaxim.InDaCellsServer.Items;

import java.io.DataOutputStream;
import java.io.IOException;

public class ItemFood extends Item {
    private float nutrition;

    public ItemFood(int itemID, String name, String description, float nutrition) {
        super(itemID, Type.FOOD, name, description);
        this.nutrition = nutrition;
    }

    @Override
    public void write(DataOutputStream dos) throws IOException {
        super.write(dos);
        dos.writeFloat(nutrition);
    }

    @Override
    public Item clone() {
        return new ItemFood(itemID, name, description, nutrition);
    }

    public float getNutrition() {
        return nutrition;
    }
}
