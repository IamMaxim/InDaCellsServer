package ru.iammaxim.NetLib;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public interface Packet {
    void write(DataOutputStream dos) throws IOException;
    void read(DataInputStream dis) throws IOException;
}
