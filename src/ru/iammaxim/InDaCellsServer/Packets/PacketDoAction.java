package ru.iammaxim.InDaCellsServer.Packets;

import ru.iammaxim.NetLib.Packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Created by maxim on 10/28/17.
 */

public class PacketDoAction extends Packet {
    public Type type;
    public int targetID;
    public int additionalInt = 0;

    public PacketDoAction() {
    }

    public PacketDoAction(Type type, int targetID) {
        this.type = type;
        this.targetID = targetID;
    }

    public PacketDoAction setAdditionalInt(int i) {
        this.additionalInt = i;
        return this;
    }

    @Override
    public void write(DataOutputStream dos) throws IOException {
        dos.writeInt(type.ordinal());
        dos.writeInt(targetID);
        dos.writeInt(additionalInt);
    }

    @Override
    public void read(DataInputStream dis) throws IOException {
        type = Type.values()[dis.readInt()];
        targetID = dis.readInt();
        additionalInt = dis.readInt();
    }

    public enum Type {
        ATTACK,
        DEFEND,
        ACTIVATE,
        PICKUP_ITEM,
        TALK
    }
}
