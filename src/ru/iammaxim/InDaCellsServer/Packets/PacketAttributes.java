package ru.iammaxim.InDaCellsServer.Packets;

import ru.iammaxim.InDaCellsServer.Creatures.Attribute;
import ru.iammaxim.NetLib.Packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;

public class PacketAttributes extends Packet {
    public HashMap<Attribute, Float> attributes = new HashMap<>();

    public PacketAttributes() {
    }

    @Override
    public void write(DataOutputStream dos) throws IOException {
        dos.writeInt(attributes.size());
        attributes.forEach((a, value) -> {

        });
    }

    @Override
    public void read(DataInputStream dis) throws IOException {

    }
}
