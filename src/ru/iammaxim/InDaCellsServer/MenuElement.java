package ru.iammaxim.InDaCellsServer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class MenuElement {
    public Type type;
    public String text;
    public int targetID;
    public float additionalFloat = 0;
    public String additionalString = "";

    public MenuElement(Type type, String text, int targetID) {
        this.type = type;
        this.text = text;
        this.targetID = targetID;
    }

    public static MenuElement read(DataInputStream dis) throws IOException {
        return new MenuElement(
                Type.values()[dis.readInt()],
                dis.readUTF(),
                dis.readInt()
        ).setAdditionalFloat(dis.readFloat())
                .setAdditionalString(dis.readUTF());
    }

    public MenuElement setAdditionalFloat(float f) {
        this.additionalFloat = f;
        return this;
    }

    public MenuElement setAdditionalString(String s) {
        this.additionalString = s;
        return this;
    }

    public void write(DataOutputStream dos) throws IOException {
        dos.writeInt(type.ordinal());
        dos.writeUTF(text);
        dos.writeInt(targetID);
        dos.writeFloat(additionalFloat);
        dos.writeUTF(additionalString);
    }

    public enum Type {
        PLAYER,
        ITEM,
        NPC,
        CREATURE,
        ACTIVATOR,
        HEADER,
        ACTION,
        LOCATION
    }
}
