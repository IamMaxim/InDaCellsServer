package ru.iammaxim.NetLib;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public abstract class Packet {
    abstract public void write(DataOutputStream dos) throws IOException;

    abstract public void read(DataInputStream dis) throws IOException;
}
