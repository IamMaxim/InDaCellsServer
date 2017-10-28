package ru.iammaxim.InDaCellsServer.Items;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;

public class Item {
    public static HashMap<Integer, Item> items = new HashMap<>();
    public static HashMap<Integer, ItemWeapon> weapons = new HashMap<>();
    public static HashMap<Integer, ItemArmor> armors = new HashMap<>();

    public final int id;
    public final String name;
    public final Type type;
    public final float weight;

    public static void registerItem(Item item){
        items.put(item.id, item);
    }

    public static void registerItems(){
        registerItem(new Item(0, Type.MISC, "Доска", 2));
        registerItem(new Item(0, Type.MISC, "Железный прут", 2));
        registerItem(new Item(0, Type.MISC, "Железная пластина", 3));
        registerItem(new Item(0, Type.MISC, "Скотч", 0.5f));
        registerItem(new Item(0, Type.MISC, "Гвозди", 0.5f));
        registerItem(new Item(0, Type.MISC, "Верёвка", 1));
    }

    public static void registerWeapon(ItemWeapon weapon){
        weapons.put(weapon.id, weapon);
    }

    public static void registerWeapons(){
        registerWeapon(new ItemWeapon(0, Type.WEAPON, "Дрын", 1, 3));
        registerWeapon(new ItemWeapon(1, Type.WEAPON, "Дубина с гвоздями", 2, 4));
        registerWeapon(new ItemWeapon(2, Type.WEAPON, "Лук", 2, 3));
        registerWeapon(new ItemWeapon(3, Type.WEAPON, "Полицейская дубина", 2, 1).addStat(ItemWeapon.AdditionalStats.ACCURACY, 5));
        registerWeapon(new ItemWeapon(4, Type.WEAPON, "Полицейский пистолет", 3, 2).addStat(ItemWeapon.AdditionalStats.ACCURACY, 5));
        registerWeapon(new ItemWeapon(5, Type.WEAPON, "Полицейское ружьё", 8, 8).addStat(ItemWeapon.AdditionalStats.ACCURACY, 5));
        registerWeapon(new ItemWeapon(6, Type.WEAPON, "М14", 4, 5));
        registerWeapon(new ItemWeapon(7, Type.WEAPON, "Пистолет из мусора", 2, 3));
        registerWeapon(new ItemWeapon(8, Type.WEAPON, "Охотночье ружьё с мусора", 3, 6));
        registerWeapon(new ItemWeapon(9, Type.WEAPON, "Кухонный нож", 1, 1));
        registerWeapon(new ItemWeapon(10, Type.WEAPON, "Боевой нож", 2, 2));
        registerWeapon(new ItemWeapon(11, Type.WEAPON, "Меч из музея", 3, 2));
    }

    public static void registerArmor(ItemArmor armor){
        armors.put(armor.id, armor);
    }

    public static void registerArmors() {
        registerArmor(new ItemArmor(0, "Тяжёлая мусорная броня", 12, 6));
        registerArmor(new ItemArmor(1, "Тяжёлая полицейская броня", 20, 10));
        registerArmor(new ItemArmor(2, "Лёгкая мусорная броня", 5, 3));
        registerArmor(new ItemArmor(3, "Лёгкая полицейская броня", 8, 5));
        registerArmor(new ItemArmor(4, "Тряпочная мусорная броня", 2, 1));
        registerArmor(new ItemArmor(5, "Тряпочная полицейская броня", 3, 2));
    }

    public Item(int id, Type type, String name, float weight) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.weight = weight;
    }

    public void write(DataOutputStream dos) throws IOException {
        dos.writeInt(id);
        dos.writeUTF(name);
        dos.writeInt(type.ordinal());
        dos.writeFloat(weight);
    }

    public static Item read(DataInputStream dis) throws IOException {
        Item item = new Item(
                dis.readInt(),
                Type.values()[dis.readInt()],
                dis.readUTF(),
                dis.readFloat()
        );

        return item;
    }

    public enum Type {
        WEAPON,
        ARMOR,
        FOOD,
        MISC
    }

}