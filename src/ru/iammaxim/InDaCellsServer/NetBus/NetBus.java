package ru.iammaxim.InDaCellsServer.NetBus;

import ru.iammaxim.NetLib.Client;
import ru.iammaxim.NetLib.Packet;

import java.util.HashMap;

public class NetBus {
    public static HashMap<Class<? extends Packet>, NetBusHandler> handlers = new HashMap<>();

    public static void register(Class<? extends Packet> clazz, NetBusHandler handler) {
//        System.out.println("Registering handler for " + clazz.getSimpleName());
        handlers.put(clazz, handler);
    }

    public static void handle(Client c, Packet p) {
        NetBusHandler handler = handlers.get(p.getClass());

        if (handler == null)
            throw new IllegalStateException("Ho handler found for " + p.getClass().getSimpleName());

        handler.handle(c, p);
    }
}
