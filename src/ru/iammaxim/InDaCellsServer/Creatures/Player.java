package ru.iammaxim.InDaCellsServer.Creatures;


import ru.iammaxim.InDaCellsServer.Packets.PacketCell;
import ru.iammaxim.InDaCellsServer.Packets.PacketStartAction;
import ru.iammaxim.InDaCellsServer.Packets.PacketStats;
import ru.iammaxim.InDaCellsServer.Packets.PacketUnblockInput;
import ru.iammaxim.InDaCellsServer.World.World;
import ru.iammaxim.NetLib.NetLib;

import java.io.IOException;
import java.net.SocketException;

public class Player extends Human {
    protected int statsUpdateTimer = 0;
    protected int spTimer = 0;


    public Player(World world, String name) {
        super(world, name);
    }

    @Override
    public void move(int newX, int newY) {
        super.move(newX, newY);
        try {
            NetLib.send(name, new PacketStartAction("Moving...", 2));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doMove() {
        super.doMove();
        try {
            NetLib.send(name, new PacketCell(getCurrentCell()));
            NetLib.send(name, new PacketUnblockInput());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void tick() {
        super.tick();

        spTimer++;
        if (spTimer == 1000) {
            spTimer = 0;
            sp = 0;
        }

        statsUpdateTimer++;
        if (statsUpdateTimer == 50) {
            statsUpdateTimer = 0;
            try {
                System.out.println("Sending stats: " + hp + " " + maxHP + ", " + sp + " " + maxSP + ", " + hunger + " " + maxHunger);
                NetLib.send(name, new PacketStats(this));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
