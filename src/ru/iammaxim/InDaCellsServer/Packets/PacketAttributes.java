package ru.iammaxim.InDaCellsServer.Packets;

import ru.iammaxim.InDaCellsServer.Creatures.Attribute;
import ru.iammaxim.InDaCellsServer.Creatures.Skill;
import ru.iammaxim.NetLib.Packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;

public class PacketAttributes implements Packet {
    public HashMap<Attribute, Float> attributes = new HashMap<>();
    public HashMap<Skill, Float> skills = new HashMap<>();

    public PacketAttributes() {
    }

    public PacketAttributes(HashMap<Attribute, Float> attributes, HashMap<Skill, Float> skills) {
        this.attributes = attributes;
        this.skills = skills;
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
