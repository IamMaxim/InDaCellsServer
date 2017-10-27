package ru.iammaxim.NetLib;

import java.io.DataInputStream;
import java.io.DataOutputStream;

public abstract class Packet {
    abstract void write(DataOutputStream dos);
    abstract void read(DataInputStream dis);
}
