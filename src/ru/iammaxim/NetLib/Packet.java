package ru.iammaxim.NetLib;

import java.io.DataInputStream;
import java.io.DataOutputStream;

public abstract class Packet {
    abstract public void write(DataOutputStream dos);
    abstract public void read(DataInputStream dis);
}
