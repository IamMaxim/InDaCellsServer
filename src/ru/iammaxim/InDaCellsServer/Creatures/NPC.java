package ru.iammaxim.InDaCellsServer.Creatures;

import ru.iammaxim.InDaCellsServer.LogElement;
import ru.iammaxim.InDaCellsServer.Packets.PacketAddToLog;
import ru.iammaxim.InDaCellsServer.World.World;
import ru.iammaxim.NetLib.NetLib;

import java.io.IOException;

public class NPC extends Human {
    public NPC(World world, String name) {
        super(world, name);
    }

    public void speak(Human p, String text) {
//        System.out.println(name + ": " + text);
        try {
            NetLib.send(p.name, new PacketAddToLog(new LogElement(LogElement.Type.MESSAGE, text, name)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
