package ru.iammaxim.InDaCellsServer.Packets;

import ru.iammaxim.NetLib.Packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class PacketMove extends Packet {
    enum Direction {
        LEFT,
        RIGHT,
        UP,
        DOWN
    }

    public Direction dir;

    public PacketMove() {

    }

    public PacketMove(Direction dir) {
        this.dir = dir;
    }

    @Override
    public void write(DataOutputStream dos) throws IOException {
        dos.writeInt(dir.ordinal());
    }

    @Override
    public void read(DataInputStream dis) throws IOException {
        dir = Direction.values()[dis.readInt()];
    }
}
