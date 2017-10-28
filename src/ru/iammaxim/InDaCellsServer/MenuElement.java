package ru.iammaxim.InDaCellsServer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class MenuElement {
    public enum Type {
        PLAYER,
        ITEM,
        NPC,
        CREATURE,
        ACTIVATOR,
        HEADER
    }

    public Type type;

    public String text;
    public int targetID;

    public MenuElement(Type type, String text, int targetID) {
        this.type = type;
        this.text = text;
        this.targetID = targetID;
    }

    public void write(DataOutputStream dos) throws IOException {
        dos.writeInt(type.ordinal());
        dos.writeUTF(text);
        dos.writeInt(targetID);
    }

    public static MenuElement read(DataInputStream dis) throws IOException {
        return new MenuElement(
                Type.values()[dis.readInt()],
                dis.readUTF(),
                dis.readInt()
        );
    }
}
