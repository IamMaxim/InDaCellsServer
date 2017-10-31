package ru.iammaxim.InDaCellsClient;

import ru.iammaxim.InDaCellsServer.NetBus.NetBus;
import ru.iammaxim.NetLib.NetLib;

public class Main {
    public static void main(String[] args) {
        NetLib.registerAll();
        Client client = new Client();
        client.registerHandlers();
        NetLib.setOnPacketReceive(NetBus::handle);
        NetLib.start("localhost", 23671, "IamMaxim");
        client.run();
    }
}
