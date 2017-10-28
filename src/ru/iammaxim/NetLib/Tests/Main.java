package ru.iammaxim.NetLib.Tests;

import ru.iammaxim.NetLib.NetLib;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        NetLib.register(0, TestPacket.class);

        NetLib.startServer(4568);
        System.out.println("Server started");

        NetLib.start("localhost", 4568, "Player1");
        Thread.sleep(500);

        for (int i = 0; i < 4; i++) {
            NetLib.sendToServer(new TestPacket());
            System.out.println("Packet sent");
        }
        NetLib.server().dos.writeUTF("TESTNAME");
    }
}
