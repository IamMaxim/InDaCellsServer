package ru.iammaxim.InDaCellsServer.Packets;

import ru.iammaxim.InDaCellsServer.Quests.Quest;
import ru.iammaxim.NetLib.Packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class PacketQuestList extends Packet {
    public ArrayList<String> questNames = new ArrayList<>();

    public PacketQuestList() {
    }

    public PacketQuestList(ArrayList<Quest> quests) {
        quests.forEach(q -> questNames.add(q.title));
    }


    @Override
    public void write(DataOutputStream dos) throws IOException {
        dos.writeInt(questNames.size());
        for (String q : questNames) {
            dos.writeUTF(q);
        }
    }

    @Override
    public void read(DataInputStream dis) throws IOException {
        int count = dis.readInt();
        for (int i = 0; i < count; i++) {
            questNames.add(dis.readUTF());
        }
    }
}
