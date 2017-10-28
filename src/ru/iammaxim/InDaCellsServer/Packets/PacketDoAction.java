package ru.iammaxim.InDaCellsServer.Packets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import ru.iammaxim.NetLib.Packet;

/**
 * Created by maxim on 10/28/17.
 */

public class PacketDoAction extends Packet {
    public enum Type {
        ATTACK,
        DEFEND,
        ACTIVATE,
        PICKUP_ITEM,
        TALK
    }

    public Type type;
    public int targetID;

    public PacketDoAction() {
    }

    public PacketDoAction(Type type, int targetID) {
        this.type = type;
        this.targetID = targetID;
    }

    @Override
    public void write(DataOutputStream dos) throws IOException {
        dos.writeInt(type.ordinal());
        dos.writeInt(targetID);
    }

    @Override
    public void read(DataInputStream dis) throws IOException {
        type = Type.values()[dis.readInt()];
        targetID = dis.readInt();
    }
}
