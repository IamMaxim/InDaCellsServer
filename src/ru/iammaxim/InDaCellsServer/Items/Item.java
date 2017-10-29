package ru.iammaxim.InDaCellsServer.Items;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;

public class Item {
    public static HashMap<Integer, Item> items = new HashMap<>();
    public static HashMap<Integer, ItemWeapon> weapons = new HashMap<>();
    public static HashMap<Integer, ItemArmor> armors = new HashMap<>();

    public final int itemID;
    public final String name;
    public final Type type;
    private String description = "";
    public int id;

    public Item setDescription(String s) {
        this.description = s;
        return this;
    }

    public static void registerItem(Item item) {
        items.put(item.itemID, item);
    }

    public static void registerItems() {
        registerItem(new Item(0, Type.MISC, "Доска"));
        registerItem(new Item(0, Type.MISC, "Железный прут"));
        registerItem(new Item(0, Type.MISC, "Железная пластина"));
        registerItem(new Item(0, Type.MISC, "Скотч"));
        registerItem(new Item(0, Type.MISC, "Гвозди"));
        registerItem(new Item(0, Type.MISC, "Верёвка"));
    }

    public static void registerWeapon(ItemWeapon weapon) {
        weapons.put(weapon.itemID, weapon);
    }

    public static void registerWeapons() {
        registerWeapon(new ItemWeapon(0, "Дрын", 1));
        registerWeapon(new ItemWeapon(1, "Дубина с гвоздями", 2));
        registerWeapon(new ItemWeapon(2, "Лук", 2));
        registerWeapon(new ItemWeapon(3, "Полицейская дубина", 2));
        registerWeapon(new ItemWeapon(4, "Полицейский пистолет", 3));
        registerWeapon(new ItemWeapon(5, "Полицейское ружьё", 8));
        registerWeapon(new ItemWeapon(6, "М14", 4));
        registerWeapon(new ItemWeapon(7, "Пистолет из мусора", 2));
        registerWeapon(new ItemWeapon(8, "Охотночье ружьё с мусора", 3));
        registerWeapon(new ItemWeapon(9, "Кухонный нож", 1));
        registerWeapon(new ItemWeapon(10, "Боевой нож", 2));
        registerWeapon(new ItemWeapon(11, "Меч из музея", 3));
    }

    public static void registerArmor(ItemArmor armor) {
        armors.put(armor.itemID, armor);
    }

    public static void registerArmors() {
        registerArmor(new ItemArmor(0, "Тяжёлая мусорная броня", 12));
        registerArmor(new ItemArmor(1, "Тяжёлая полицейская броня", 20));
        registerArmor(new ItemArmor(2, "Лёгкая мусорная броня", 5));
        registerArmor(new ItemArmor(3, "Лёгкая полицейская броня", 8));
        registerArmor(new ItemArmor(4, "Тряпочная мусорная броня", 2));
        registerArmor(new ItemArmor(5, "Тряпочная полицейская броня", 3));
    }

    public static void registerAll() {
        registerArmors();
        registerItems();
        registerWeapons();
    }

    public Item(int itemID, Type type, String name) {
        this.itemID = itemID;
        this.type = type;
        this.name = name;
        this.id = (int) (Math.random() * Integer.MAX_VALUE);
    }

    public Item clone() {
        return new Item(this.itemID, this.type, this.name).setDescription(this.description);
    }

    public void write(DataOutputStream dos) throws IOException {
        dos.writeInt(itemID);
        dos.writeInt(type.ordinal());
        dos.writeUTF(name);
        dos.writeInt(id);
    }

    public static Item read(DataInputStream dis) throws IOException {
        Item item = new Item(
                dis.readInt(),
                Type.values()[dis.readInt()],
                dis.readUTF()
        );
        item.id = dis.readInt();

        return item;
    }

    public int getID() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Item setID(int id) {
        this.id = id;
        return this;
    }

    public enum Type {
        WEAPON,
        ARMOR,
        FOOD,
        MISC
    }
}
