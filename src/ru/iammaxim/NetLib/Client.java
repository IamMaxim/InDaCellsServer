package ru.iammaxim.NetLib;

import com.sun.media.sound.InvalidDataException;

import java.io.*;
import java.net.Socket;

public class Client {
    private Socket socket;
    public InputStream is;
    public OutputStream os;
    public DataOutputStream dos;
    public DataInputStream dis;
    public String name;

    public Client(Socket socket, boolean hasName) throws IOException {
        this.socket = socket;
        is = socket.getInputStream();
        os = socket.getOutputStream();
        dos = new DataOutputStream(os);
        dis = new DataInputStream(is);
        if (hasName)
            name = dis.readUTF();
    }
}
