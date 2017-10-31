package ru.iammaxim.InDaCellsServer.Activators;

import ru.iammaxim.InDaCellsServer.Creatures.Creature;
import ru.iammaxim.InDaCellsServer.OnActivate;
import ru.iammaxim.InDaCellsServer.Scripts;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Activator {
    private String name;
    private int id;
    private String description = "";

    public Activator() {
    }

    public Activator(int id, String name) {
        this.id = id;
        this.name = name;
//        this.id = (int) (Math.random() * Integer.MAX_VALUE);
    }

    public static Activator read(DataInputStream dis) throws IOException {
        Activator activator = new Activator();

        activator.id = dis.readInt();
        activator.name = dis.readUTF();
        activator.description = dis.readUTF();

        return activator;
    }

    public void activate(Creature c) {
        System.out.println("Creature " + c.getName() + " used activator");
        OnActivate script = Scripts.getActivatorScript(getID());
        if (script != null)
            script.onActivate(c);
        else
            System.out.println("WARNING! No script found for activator " + getName() + " with ID " + getID());
    }

    public void write(DataOutputStream dos) throws IOException {
        dos.writeInt(id);
        dos.writeUTF(name);
        dos.writeUTF(description);
    }

    public String getName() {
        return name;
    }

    public int getID() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public Activator setDescription(String s) {
        this.description = s;
        return this;
    }
}
