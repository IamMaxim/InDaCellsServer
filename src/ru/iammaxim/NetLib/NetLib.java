package ru.iammaxim.NetLib;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class NetLib {
    private static ServerSocket ss;
    private static Client server;
    private static final HashMap<String, Client> clients = new HashMap<>();
    private static HashMap<Integer, Class<? extends Packet>> packets = new HashMap<>();
    private static HashMap<Class<? extends Packet>, Integer> packetIds = new HashMap<>();

    public static void register(int id, Class<? extends Packet> packet) {
        packets.put(id, packet);
        packetIds.put(packet, id);
    }

    public static void startServer(int port) throws IOException {
        ss = new ServerSocket(port);
        new Thread(() -> {
            System.out.println("Starting acceptor loop...");
            while (true) {
                try {
                    Socket s = ss.accept();
                    new Thread(() -> {
                        Client c;
                        try {
                            c = new Client(s, true);
                            synchronized (clients) {
                                clients.put(c.name, c);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }).start();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public static void start(String ip, int port, String name) throws IOException {
        server = new Client(new Socket(ip, port), false);
        new Thread(() -> {
            try {
                server.dos.writeUTF(name);
                loop();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public static Client server() {
        return server;
    }

    private static void loop() {
        System.out.println("Starting receiver loop...");
        while (true) {
            synchronized (clients) {
                for (Client c : clients.values()) {
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

            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private static void send(Client c, Packet packet) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        packet.write(new DataOutputStream(baos));
        c.dos.writeInt(packetIds.get(packet.getClass()));
        c.dos.writeInt(baos.size());
        c.dos.write(baos.toByteArray());
        System.out.println("Packet written");
    }

    public static void send(String name, Packet packet) throws IOException {
        Client c;
        synchronized (clients) {
            c = clients.get(name);
        }
        if (c == null)
            throw new IllegalArgumentException("No such name found while sending packet");
        send(c, packet);
    }

    public static void sendToServer(Packet packet) throws IOException {
        send(server, packet);
    }
}
