package ru.iammaxim.InDaCellsServer.Activators;

import ru.iammaxim.InDaCellsServer.Creatures.Human;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Activator {
    private String name;

    void activate(Human h) {
        System.out.println("Human " + h.getName() + " used activator");
    }

    public void write(DataOutputStream dos) throws IOException {
        dos.writeUTF(name);
    }

    public static Activator read(DataInputStream dis) throws IOException {
        Activator activator = new Activator();

        activator.name = dis.readUTF();

        return activator;
    }

    public String getName() {
        return name;
    }
}
