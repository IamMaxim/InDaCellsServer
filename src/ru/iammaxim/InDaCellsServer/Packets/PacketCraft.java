package ru.iammaxim.InDaCellsServer.Packets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import ru.iammaxim.InDaCellsServer.Items.Item;
import ru.iammaxim.NetLib.Packet;

/**
 * Created by maxim on 10/29/17.
 */

public class PacketCraft extends Packet {
    public String name, description;
    public Item.Type type;
    public ArrayList<Integer> ingredients = new ArrayList<>();

    public PacketCraft() {
    }

    public PacketCraft(String name, String description, Item.Type type, ArrayList<Integer> ingredients) {
        this.name = name;
        this.description = description;
        this.type = type;
        this.ingredients = ingredients;
    }


    public void write(DataOutputStream dos) throws IOException {
        dos.writeUTF(name);
        dos.writeUTF(description);
        dos.writeInt(type.ordinal());

        dos.writeInt(ingredients.size());
        for (Integer ingredient : ingredients) {
            dos.writeInt(ingredient);
        }
    }

    @Override
    public void read(DataInputStream dis) throws IOException {
        name = dis.readUTF();
        description = dis.readUTF();
        type = Item.Type.values()[dis.readInt()];

        int ingCount = dis.readInt();
        for (int i = 0; i < ingCount; i++) {
            ingredients.add(dis.readInt());
        }
    }
}
