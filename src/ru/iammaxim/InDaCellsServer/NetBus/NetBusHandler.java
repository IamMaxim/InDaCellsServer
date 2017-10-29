package ru.iammaxim.InDaCellsServer.NetBus;

import ru.iammaxim.NetLib.Client;
import ru.iammaxim.NetLib.Packet;

public interface NetBusHandler {
    void handle(Client c, Packet p);
}
