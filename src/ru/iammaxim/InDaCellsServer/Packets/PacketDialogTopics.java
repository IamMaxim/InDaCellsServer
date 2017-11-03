package ru.iammaxim.InDaCellsServer.Packets;

import ru.iammaxim.NetLib.Packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PacketDialogTopics extends Packet {
    public List<String> topics = new ArrayList<>();

    public PacketDialogTopics() {
    }

    public PacketDialogTopics(List<String> topics) {
        this.topics = topics;
    }

    @Override
    public void write(DataOutputStream dos) throws IOException {
        dos.writeInt(topics.size());
        for (String topic : topics) {
            dos.writeUTF(topic);
        }
    }

    @Override
    public void read(DataInputStream dis) throws IOException {
        int count = dis.readInt();
        for (int i = 0; i < count; i++) {
            topics.add(dis.readUTF());
        }
    }
}
