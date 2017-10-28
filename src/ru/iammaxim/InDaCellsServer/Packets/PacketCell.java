package ru.iammaxim.InDaCellsServer.Packets;

import ru.iammaxim.InDaCellsServer.World.WorldCell;
import ru.iammaxim.NetLib.Packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class PacketCell extends Packet {
//    public WorldCell cell;
    public int x, y;
    

    public PacketCell() {
    }

//    public PacketCell(WorldCell cell) {
//        this.cell = cell;
//    }

    public PacketCell(int x, int y, WorldCell cell) {
        this.x = x;
        this.y = y;


    }

    @Override
    public void write(DataOutputStream dos) throws IOException {
        cell.write(dos);
    }

    @Override
    public void read(DataInputStream dis) throws IOException {
        cell = WorldCell.read(dis);
    }
}
