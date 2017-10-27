package ru.iammaxim.NetLib.Tests;

import ru.iammaxim.NetLib.Packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class TestPacket extends Packet {

    @Override
    public void write(DataOutputStream dos) {
        try {
            dos.writeUTF("Hello world");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void read(DataInputStream dis) {
        try {
            System.out.println(dis.readUTF());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
