package ru.iammaxim.InDaCellsServer.Items;

public class ItemFood extends Item {
    private float nutrition;

    public ItemFood(int id, String name, float nutrition) {
        super(id, Type.FOOD, name);
        this.nutrition = nutrition;
    }

    public float getNutrition() {
        return nutrition;
    }
}
