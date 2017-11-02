package ru.iammaxim.InDaCellsServer.Packets;

import ru.iammaxim.InDaCellsServer.LogElement;
import ru.iammaxim.NetLib.Packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class PacketAddToLog extends Packet {
    public LogElement element;

    public PacketAddToLog() {
    }

    public PacketAddToLog(LogElement element) {
        this.element = element;
    }

    public PacketAddToLog(String message) {
        this.element = new LogElement(LogElement.Type.INFO, message, "");
    }

    @Override
    public void write(DataOutputStream dos) throws IOException {
        element.write(dos);
    }

    @Override
    public void read(DataInputStream dis) throws IOException {
        element = LogElement.read(dis);
    }
}
