package ru.iammaxim.InDaCellsServer.Packets;

import ru.iammaxim.NetLib.Packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class PacketStartAction extends Packet {
    public String name;
    public float durationSeconds;

    public PacketStartAction() {
    }

    public PacketStartAction(String name, float durationSeconds) {
        this.name = name;
        this.durationSeconds = durationSeconds;
    }

    @Override
    public void write(DataOutputStream dos) throws IOException {
        dos.writeUTF(name);
        dos.writeFloat(durationSeconds);
    }

    @Override
    public void read(DataInputStream dis) throws IOException {
        name = dis.readUTF();
        durationSeconds = dis.readFloat();
    }
}
