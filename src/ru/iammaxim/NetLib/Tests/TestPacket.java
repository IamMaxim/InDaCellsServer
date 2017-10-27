package ru.iammaxim.NetLib.Tests;

import ru.iammaxim.NetLib.Packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class TestPacket extends Packet {

    @Override
    public void write(DataOutputStream dos) throws IOException {
        dos.writeUTF("Hello world");
    }

    @Override
    public void read(DataInputStream dis) throws IOException {
        System.out.println(dis.readUTF());
    }
}
