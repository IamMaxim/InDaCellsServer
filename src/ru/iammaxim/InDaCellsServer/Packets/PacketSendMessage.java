package ru.iammaxim.InDaCellsServer.Packets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import ru.iammaxim.NetLib.Packet;

public class PacketSendMessage extends Packet {
    public String message;

    public PacketSendMessage() {
        message = "message";
    }

    public PacketSendMessage(String message){
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