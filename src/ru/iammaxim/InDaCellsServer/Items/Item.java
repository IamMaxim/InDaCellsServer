package ru.iammaxim.InDaCellsServer.Items;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;

public class Item {
    public static HashMap<Integer, ItemArmor> armors = new HashMap<>();

    public final int id;
    public final String name;
    public final Type type;


    public static void registerArmors() {
        armors.put(0, new ItemArmor(0, "TestArmor", 1));
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

    public enum Type {
        WEAPON,
        ARMOR,
        FOOD,
        MISC
    }
}
