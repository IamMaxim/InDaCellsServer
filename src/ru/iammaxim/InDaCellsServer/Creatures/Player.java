package ru.iammaxim.InDaCellsServer.Creatures;


import ru.iammaxim.InDaCellsServer.Packets.PacketStats;
import ru.iammaxim.InDaCellsServer.World.World;
import ru.iammaxim.NetLib.NetLib;

import java.io.IOException;

public class Player extends Human {
    protected int statsUpdateTimer = 0;
    protected int spTimer = 0;


    public Player(World world, String name) {
        super(world, name);
    }

    @Override
    public void tick() {
        super.tick();

        spTimer++;
        if (spTimer == maxSP) {
            spTimer = 0;
            sp = 0;
        }

        statsUpdateTimer++;
        if (statsUpdateTimer == 50) {
            statsUpdateTimer = 0;
            try {
                System.out.println("Sending stats: " + hp + " " + maxHP + ", " + sp + " " + maxSP + ", " + hunger + " " + maxHunger);
                NetLib.send(name, new PacketStats(this));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
