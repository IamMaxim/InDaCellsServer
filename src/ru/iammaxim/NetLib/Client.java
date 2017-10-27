package ru.iammaxim.NetLib;

import java.io.*;
import java.net.Socket;

public class Client {
    private Socket socket;
    public InputStream is;
    public OutputStream os;
    public DataOutputStream dos;
    public DataInputStream dis;

    public Client(Socket socket) throws IOException {
        this.socket = socket;
        is = socket.getInputStream();
        os = socket.getOutputStream();
        dos = new DataOutputStream(os);
        dis = new DataInputStream(is);
    }
}
