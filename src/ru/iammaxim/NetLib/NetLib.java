package ru.iammaxim.NetLib;

import ru.iammaxim.InDaCellsServer.Packets.*;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Arrays;
import java.util.HashMap;

public class NetLib {
    private static ServerSocket ss;
    private static Client server;
    private static final HashMap<String, Client> clients = new HashMap<>();
    private static HashMap<Integer, Class<? extends Packet>> packets = new HashMap<>();
    private static HashMap<Class<? extends Packet>, Integer> packetIds = new HashMap<>();
    private static OnClientConnect onClientConnect;
    private static OnPacketReceive onPacketReceive;

    public static void register(int id, Class<? extends Packet> packet) {
        packets.put(id, packet);
        packetIds.put(packet, id);

        System.out.println("Registered packet " + packet.getName() + " with id " + id);
    }

    public static void registerAll() {
        int counter = 0;

        register(counter++, PacketAddToLog.class);
        register(counter++, PacketAttributes.class);
        register(counter++, PacketCell.class);
        register(counter++, PacketDoAction.class);
        register(counter++, PacketInventory.class);
        register(counter++, PacketInventoryChange.class);
        register(counter++, PacketMove.class);
        register(counter++, PacketStartAction.class);
        register(counter++, PacketStats.class);
        register(counter++, PacketUnblockInput.class);
    }

    public static void startServer(int port) throws IOException {
        ss = new ServerSocket(port);
        new Thread(() -> {
            new Thread(NetLib::loop).start();
            System.out.println("Starting acceptor loop on server side...");
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
                            if (onClientConnect != null)
                                onClientConnect.onClientConnect(c);
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

    public static void start(String ip, int port, String name) {
        new Thread(() -> {
            try {
                server = new Client(new Socket(ip, port), false);
                server.dos.writeUTF(name);
                clients.put(null, server);
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
        System.out.println("Starting packet receiver loop...");
        while (true) {
            synchronized (clients) {
                for (Client c : clients.values()) {
                    try {
                        if (c.dis.available() > 0) {
                            int packetID = c.dis.readInt();
                            int len = c.dis.readInt();

                            System.out.println(packetID);
                            System.out.println(len);

                            byte[] arr = new byte[len];
                            if (len > 0) {
                                c.dis.readFully(arr);
                            }


                            System.out.println(Arrays.toString(arr));

                            Class<? extends Packet> p = packets.get(packetID);

                            if (p == null)
                                throw new IllegalStateException("No packet found with id " + packetID);

                            Packet packet = p.newInstance();
                            packet.read(new DataInputStream(new ByteArrayInputStream(arr)));

                            if (onPacketReceive != null)
                                onPacketReceive.onPacketReceive(c, packet);

                            System.out.println("Packet " + packet.getClass().getSimpleName() + " read from " + (c.name != null ? c.name : "Unknown name"));
                        }
                    } catch (Exception e) {
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
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            packet.write(new DataOutputStream(baos));
            int packetID = packetIds.get(packet.getClass());
            c.dos.writeInt(packetID);
            c.dos.writeInt(baos.size());
            if (baos.size() > 0)
                c.dos.write(baos.toByteArray(), 0, baos.size());

            System.out.println("Packet " + packet.getClass().getSimpleName() + " (" + packetID + " " + baos.size() + ") written to " + (c.name != null ? c.name : "unknown client"));
            System.out.println(Arrays.toString(baos.toByteArray()));
        } catch (SocketException e) {
            e.printStackTrace();
            clients.remove(c.name);
        }
    }

    public static void send(String name, Packet packet) throws IOException {
        Client c;
        synchronized (clients) {
            c = clients.get(name);
        }
        if (c != null)
            send(c, packet);
//        else
//            throw new IllegalArgumentException("No such name found while sending packet");
    }

    public static void sendToServer(Packet packet) {
        new Thread(() -> {
            try {
                send(server, packet);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public static void setOnClientConnect(OnClientConnect onClientConnect) {
        NetLib.onClientConnect = onClientConnect;
    }

    public static void setOnPacketReceive(OnPacketReceive onPacketReceive) {
        NetLib.onPacketReceive = onPacketReceive;
    }
}
