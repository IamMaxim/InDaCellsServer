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
    public int id;
    private String description = "";

    public Item(int itemID, Type type, String name, String description) {
        this.itemID = itemID;
        this.type = type;
        this.name = name;
        this.description = description;
        this.id = (int) (Math.random() * Integer.MAX_VALUE);
    }

    public Item(int itemID, Type type, String name) {
        this.itemID = itemID;
        this.type = type;
        this.name = name;
        this.id = (int) (Math.random() * Integer.MAX_VALUE);
    }

    public static void registerItem(Item item) {
        items.put(item.itemID, item);
    }

    public static void registerItems() {
        registerItem(new Item(0, Type.MISC, "Доска", ItemsDescription.item_plank));
        registerItem(new Item(1, Type.MISC, "Железный прут", ItemsDescription.item_iron_rod));
        registerItem(new Item(2, Type.MISC, "Железная пластина", ItemsDescription.item_iron_plate));
        registerItem(new Item(3, Type.MISC, "Скотч", ItemsDescription.item_scotch_tape));
        registerItem(new Item(4, Type.MISC, "Гвозди", ItemsDescription.item_nails));
        registerItem(new Item(5, Type.MISC, "Верёвка", ItemsDescription.item_rope));
    }

    public static void registerWeapon(ItemWeapon weapon) {
        weapons.put(weapon.itemID, weapon);
    }

    public static void registerWeapons() {
        registerWeapon(new ItemWeapon(0, "Дрын", ItemsDescription.weapon_drin, 1));
        registerWeapon(new ItemWeapon(1, "Дубина с гвоздями", ItemsDescription.weapon_drin_with_nails, 2));
        registerWeapon(new ItemWeapon(2, "Лук", ItemsDescription.weapon_bow, 2));
        registerWeapon(new ItemWeapon(3, "Полицейская дубина", ItemsDescription.weapon_police_club, 2));
        registerWeapon(new ItemWeapon(4, "Полицейский пистолет", ItemsDescription.weapon_police_pistol, 3));
        registerWeapon(new ItemWeapon(5, "Полицейское ружьё", ItemsDescription.weapon_police_gun, 8));
        registerWeapon(new ItemWeapon(6, "М14", ItemsDescription.weapon_M14, 4));
        registerWeapon(new ItemWeapon(7, "Пистолет из мусора", ItemsDescription.weapon_trash_pistol, 2));
        registerWeapon(new ItemWeapon(8, "Охотночье ружьё с мусора", ItemsDescription.weapon_trash_gun, 3));
        registerWeapon(new ItemWeapon(9, "Кухонный нож", ItemsDescription.weapon_kitchen_knife, 1));
        registerWeapon(new ItemWeapon(10, "Боевой нож", ItemsDescription.weapon_knife, 2));
        registerWeapon(new ItemWeapon(11, "Меч из музея", ItemsDescription.weapon_museum_sword, 3));
    }

    public static void registerArmor(ItemArmor armor) {
        armors.put(armor.itemID, armor);
    }

    public static void registerArmors() {
        registerArmor(new ItemArmor(0, "Тяжёлая мусорная броня", ItemsDescription.armor_heavy_trash, 12));
        registerArmor(new ItemArmor(1, "Тяжёлая полицейская броня", ItemsDescription.armor_heavy_police, 20));
        registerArmor(new ItemArmor(2, "Лёгкая мусорная броня", ItemsDescription.armor_light_trash, 5));
        registerArmor(new ItemArmor(3, "Лёгкая полицейская броня", ItemsDescription.armor_light_police, 8));
        registerArmor(new ItemArmor(4, "Тряпочная мусорная броня", ItemsDescription.armor_robe_trash, 2));
        registerArmor(new ItemArmor(5, "Тряпочная полицейская броня", ItemsDescription.armor_robe_police, 3));
    }

    public static void registerAll() {
        registerArmors();
        registerItems();
        registerWeapons();
    }

    public static Item read(DataInputStream dis) throws IOException {
        Item item = new Item(
                dis.readInt(),
                Type.values()[dis.readInt()],
                dis.readUTF(),
                dis.readUTF()
        );
        item.id = dis.readInt();

        return item;
    }

    public Item clone() {
        return new Item(this.itemID, this.type, this.name, this.description);
    }

    public void write(DataOutputStream dos) throws IOException {
        dos.writeInt(itemID);
        dos.writeInt(type.ordinal());
        dos.writeUTF(name);
        dos.writeUTF(description);
        dos.writeInt(id);
    }

    public int getID() {
        return id;
    }

    public Item setID(int id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Item setDescription(String description) {
        this.description = description;
        return this;
    }

    public enum Type {
        WEAPON,
        ARMOR,
        FOOD,
        MISC
    }
}
