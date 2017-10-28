package ru.iammaxim.InDaCellsServer.Items;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;

public class Item {
    public static HashMap<Integer, ItemArmor> armors = new HashMap<>();
    public static HashMap<Integer, ItemWeapon> weapons = new HashMap<>();
    public static HashMap<Integer, ItemFood> foods = new HashMap<>();

    public final int id;
    public final String name;
    public final Type type;
    private int ID;

    public static void registerArmor(ItemArmor armor) {
        armors.put(armor.id, armor);
    }

    public static void registerWeapon(ItemWeapon weapon) {
        weapons.put(weapon.id, weapon);
    }

    public static void registerFood(ItemFood food) {
        foods.put(food.id, food);
    }

    public static void registerArmors() {
        registerArmor(new ItemArmor(0, "TestArmor", 1));
    }

    public static void registerWeapons() {
        registerWeapon(new ItemWeapon(1, "TestWeapon", 1));
    }

    public static void registerFoods() {
        registerFood(new ItemFood(2, "TestFood", 1));
    }

    public Item(int id, Type type, String name) {
        this.id = id;
        this.type = type;
        this.name = name;
    }

    public void write(DataOutputStream dos) throws IOException {
        dos.writeInt(id);
        dos.writeUTF(name);
        dos.writeInt(type.ordinal());
    }

    public static Item read(DataInputStream dis) throws IOException {
        Item item = new Item(
                dis.readInt(),
                Type.values()[dis.readInt()],
                dis.readUTF()
        );

        return item;
    }

    public int getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public enum Type {
        WEAPON,
        ARMOR,
        FOOD,
        MISC
    }
}
