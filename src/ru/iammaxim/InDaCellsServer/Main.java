package ru.iammaxim.InDaCellsServer;

import ru.iammaxim.NetLib.NetLib;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        NetLib.registerAll();
        NetLib.startServer(23671);
        Server server = new Server();
        server.startSaveThread();
        server.run();
    }
}
