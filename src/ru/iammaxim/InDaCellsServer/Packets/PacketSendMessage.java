package ru.iammaxim.InDaCellsServer.Packets;

import ru.iammaxim.NetLib.Packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class PacketSendMessage extends Packet {
    public String message;

    public PacketSendMessage() {
        message = "message";
    }

    public PacketSendMessage(String message) {
        this.message = message;
    }

    @Override
    public void write(DataOutputStream dos) throws IOException {
        dos.writeUTF(message);
    }

    @Override
    public void read(DataInputStream dis) throws IOException {
        message = dis.readUTF();
    }
}