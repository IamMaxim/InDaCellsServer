package ru.iammaxim.InDaCellsServer.Creatures;

import ru.iammaxim.InDaCellsServer.LogElement;
import ru.iammaxim.InDaCellsServer.Packets.PacketAddToLog;
import ru.iammaxim.InDaCellsServer.Quests.Quest;
import ru.iammaxim.InDaCellsServer.World.World;
import ru.iammaxim.NetLib.NetLib;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class NPC extends Human {
    private ArrayList<Quest> attachedQuests = new ArrayList<>();

    public NPC(World world, String name) {
        super(world, name);
    }


    public void speak(Human p, String text) {
        try {
            NetLib.send(p.name, new PacketAddToLog(new LogElement(LogElement.Type.MESSAGE, text, name)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public NPC attachQuest(Quest q){
        this.attachedQuests.add(q);

        return this;
    }

    public void sendQuestsToClient(Human p) {
        // TODO: Пакет, айдишник

        if(attachedQuests.isEmpty()) return;
        try {
            NetLib.send(p.name, new PacketAddToLog(new LogElement(LogElement.Type.MESSAGE, "Квесты", name)));
            for (Quest quest : attachedQuests){
                NetLib.send(p.name, new PacketAddToLog(new LogElement(LogElement.Type.MESSAGE, quest.getTitle(), name)));

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
