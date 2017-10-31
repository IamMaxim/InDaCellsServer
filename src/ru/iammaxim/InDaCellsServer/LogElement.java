package ru.iammaxim.InDaCellsServer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class LogElement {
    public Type type;
    public String message;
    public String nick;

    public LogElement(Type type, String message, String nick) {
        this.type = type;
        this.message = message;
        this.nick = nick;
    }

    public static LogElement read(DataInputStream dis) throws IOException {
        return new LogElement(
                Type.values()[dis.readInt()],
                dis.readUTF(),
                dis.readUTF()
        );
    }

    public void write(DataOutputStream dos) throws IOException {
        dos.writeInt(type.ordinal());
        dos.writeUTF(message);
        dos.writeUTF(nick);
    }

    public enum Type {
        INFO,
        MESSAGE
    }
}
