package ru.iammaxim.InDaCellsServer.Packets;

import ru.iammaxim.InDaCellsServer.Creatures.Human;
import ru.iammaxim.NetLib.Packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class PacketStats extends Packet {
    public float hp, hunger, sp;
    public float maxHP, maxHunger, maxSP;

    public PacketStats() {
    }

    public PacketStats(Human human) {
        hp = human.getHP();
        hunger = human.getHunger();
        sp = human.getSP();
    }

    @Override
    public void write(DataOutputStream dos) throws IOException {
        dos.writeFloat(hp);
        dos.writeFloat(hunger);
        dos.writeFloat(sp);
        dos.writeFloat(maxHP);
        dos.writeFloat(maxHunger);
        dos.writeFloat(maxSP);
    }

    @Override
    public void read(DataInputStream dis) throws IOException {
        hp = dis.readFloat();
        hunger = dis.readFloat();
        sp = dis.readFloat();
        maxHP = dis.readFloat();
        maxHunger = dis.readFloat();
        maxSP = dis.readFloat();
    }
}
