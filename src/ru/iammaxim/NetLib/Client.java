package ru.iammaxim.NetLib;

import java.io.*;
import java.net.Socket;

public class Client {
    public final Object lock = new Object();
    public InputStream is;
    public OutputStream os;
    public DataOutputStream dos;
    public DataInputStream dis;
    public String name;
    private Socket socket;

    public Client(Socket socket, boolean hasName) throws IOException {
        this.socket = socket;
        is = socket.getInputStream();
        os = socket.getOutputStream();
        dos = new DataOutputStream(os);
        dis = new DataInputStream(is);
        if (hasName)
            name = dis.readUTF();
        System.out.println("Client connected. " + (name != null ? name : "unknown name"));
    }
}
