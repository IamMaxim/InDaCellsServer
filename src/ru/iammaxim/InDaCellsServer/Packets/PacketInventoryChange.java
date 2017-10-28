package ru.iammaxim.InDaCellsServer.Packets;

import ru.iammaxim.InDaCellsServer.Items.Item;
import ru.iammaxim.NetLib.Packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class PacketInventoryChange extends Packet {
    public enum Type {
        ADD,
        REMOVE
    }

    public Type type;
    public int index;
    public Item item;

    public PacketInventoryChange() {
    }

    public PacketInventoryChange(Type type, int index) {
        this.type = type;
        this.index = index;
    }

    public PacketInventoryChange(Type type, Item item) {
        this.type = type;
        this.item = item;
    }

    @Override
    public void write(DataOutputStream dos) throws IOException {
        dos.writeInt(type.ordinal());

        if (type == Type.REMOVE)
            dos.writeInt(index);
        else if (type == Type.ADD)
            item.write(dos);
    }

    @Override
    public void read(DataInputStream dis) throws IOException {
        type = Type.values()[dis.readInt()];

        if (type == Type.REMOVE)
            index = dis.readInt();
        else if (type == Type.ADD)
            item = Item.read(dis);
    }
}
