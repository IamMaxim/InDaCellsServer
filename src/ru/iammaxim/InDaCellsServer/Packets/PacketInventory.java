package ru.iammaxim.InDaCellsServer.Packets;

import ru.iammaxim.InDaCellsServer.Items.Item;
import ru.iammaxim.NetLib.Packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class PacketInventory implements Packet {
    public ArrayList<Item> inv = new ArrayList<>();

    public PacketInventory() {
    }

    public PacketInventory(ArrayList<Item> inv) {
        this.inv = inv;
    }

    @Override
    public void write(DataOutputStream dos) throws IOException {
        dos.writeInt(inv.size());
        for (Item item : inv) {
            item.write(dos);
        }
    }

    @Override
    public void read(DataInputStream dis) throws IOException {
        int count = dis.readInt();
        for (int i = 0; i < count; i++) {
            inv.add(Item.read(dis));
        }
    }
}
