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
    public String description = "";

    public static Item craft(Type type, String name, String description, float value) {
        switch (type) {
            case WEAPON:
                return new ItemWeapon(-1, name, description, value);
            case ARMOR:
                return new ItemArmor(-1, name, description, value);
            case FOOD:
                return new ItemFood(-1, name, description, value);
            case MISC:
                return new Item(-1, Type.MISC, name, description);
        }
        return null;
    }

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
        registerItem(new Item(0, Type.MISC, ItemNames.item_plank, ItemsDescriptions.item_plank));
        registerItem(new Item(1, Type.MISC, ItemNames.item_iron_rod, ItemsDescriptions.item_iron_rod));
        registerItem(new Item(2, Type.MISC, ItemNames.item_iron_plate, ItemsDescriptions.item_iron_plate));
        registerItem(new Item(3, Type.MISC, ItemNames.item_scotch_tape, ItemsDescriptions.item_scotch_tape));
        registerItem(new Item(4, Type.MISC, ItemNames.item_nails, ItemsDescriptions.item_nails));
        registerItem(new Item(5, Type.MISC, ItemNames.item_rope, ItemsDescriptions.item_rope));
    }

    public static void registerWeapon(ItemWeapon weapon) {
        weapons.put(weapon.itemID, weapon);
    }

    public static void registerWeapons() {
        registerWeapon(new ItemWeapon(0, ItemNames.weapon_drin, ItemsDescriptions.weapon_drin, 1));
        registerWeapon(new ItemWeapon(1, ItemNames.weapon_drin_with_nails, ItemsDescriptions.weapon_drin_with_nails, 2));
        registerWeapon(new ItemWeapon(2, ItemNames.weapon_bow, ItemsDescriptions.weapon_bow, 2));
        registerWeapon(new ItemWeapon(3, ItemNames.weapon_police_club, ItemsDescriptions.weapon_police_club, 2));
        registerWeapon(new ItemWeapon(4, ItemNames.weapon_police_pistol, ItemsDescriptions.weapon_police_pistol, 3));
        registerWeapon(new ItemWeapon(5, ItemNames.weapon_police_gun, ItemsDescriptions.weapon_police_gun, 8));
        registerWeapon(new ItemWeapon(6, ItemNames.weapon_M14, ItemsDescriptions.weapon_M14, 4));
        registerWeapon(new ItemWeapon(7, ItemNames.weapon_trash_pistol, ItemsDescriptions.weapon_trash_pistol, 2));
        registerWeapon(new ItemWeapon(8, ItemNames.weapon_trash_gun, ItemsDescriptions.weapon_trash_gun, 3));
        registerWeapon(new ItemWeapon(9, ItemNames.weapon_kitchen_knife, ItemsDescriptions.weapon_kitchen_knife, 1));
        registerWeapon(new ItemWeapon(10, ItemNames.weapon_knife, ItemsDescriptions.weapon_knife, 2));
        registerWeapon(new ItemWeapon(11, ItemNames.weapon_museum_sword, ItemsDescriptions.weapon_museum_sword, 3));
    }

    public static void registerArmor(ItemArmor armor) {
        armors.put(armor.itemID, armor);
    }

    public static void registerArmors() {
        registerArmor(new ItemArmor(0, ItemNames.armor_heavy_trash, ItemsDescriptions.armor_heavy_trash, 12));
        registerArmor(new ItemArmor(1, ItemNames.armor_heavy_police, ItemsDescriptions.armor_heavy_police, 20));
        registerArmor(new ItemArmor(2, ItemNames.armor_light_trash, ItemsDescriptions.armor_light_trash, 5));
        registerArmor(new ItemArmor(3, ItemNames.armor_light_police, ItemsDescriptions.armor_light_police, 8));
        registerArmor(new ItemArmor(4, ItemNames.armor_robe_trash, ItemsDescriptions.armor_robe_trash, 2));
        registerArmor(new ItemArmor(5, ItemNames.armor_robe_police, ItemsDescriptions.armor_robe_police, 3));
    }

    public static void registerAll() {
        registerArmors();
        registerItems();
        registerWeapons();
    }

    @Override
    public String toString() {
        return type + " " + getName();
    }

    public static Item read(DataInputStream dis) throws IOException {
        int itemID = dis.readInt();
        Type type = Type.values()[dis.readInt()];
        String name = dis.readUTF();
        String description = dis.readUTF();
        int id = dis.readInt();
        float value = 0;

        if (type == Type.WEAPON || type == Type.ARMOR || type == Type.FOOD) {
            value = dis.readFloat();
        }

        switch (type) {
            case WEAPON:
                return new ItemWeapon(itemID, name, description, value).setID(id);
            case ARMOR:
                return new ItemArmor(itemID, name, description, value).setID(id);
            case FOOD:
                return new ItemFood(itemID, name, description, value).setID(id);
            case MISC:
                return new Item(itemID, type, name, description).setID(id);
        }

        return null;
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
