package ru.iammaxim.NetLib;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

public class NetLib {
    private static ServerSocket ss;
    private static ArrayList<Client> clients = new ArrayList<>();
    private static HashMap<Integer, Class<Packet>> packets = new HashMap<>();

    public static void register(int id, Class<Packet> packet) {
        packets.put(id, packet);
    }

    public static void startServer(int port) throws IOException {
        ss = new ServerSocket(port);
        new Thread(() -> {
            while (true) {
                try {
                    clients.add(new Client(ss.accept()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public static void start() {
        new Thread(NetLib::loop);
    }

    private static void loop() {
        while (true) {
            for (Client c : clients) {
                try {
                    if (c.dis.available() > 8) {
                        int packetID = c.dis.readInt();
                        int len = c.dis.readInt();

                        byte[] arr = new byte[len];
                        c.dis.readFully(arr);
                        packets.get(packetID).newInstance().read(new DataInputStream(new ByteArrayInputStream(arr)));

                    }
                } catch (IOException | IllegalAccessException | InstantiationException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static
}
