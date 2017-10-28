package ru.iammaxim.InDaCellsServer.Creatures;


import ru.iammaxim.InDaCellsServer.World.World;
import ru.iammaxim.InDaCellsServer.World.WorldCell;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;

public class Creature {
    protected int x = 0, y = 0;
    protected World world;
    protected float hp, maxHP;
    protected boolean isAlive;
    protected String name;
    protected HashMap<Attribute, Float> attributes = new HashMap<>();
    protected HashMap<Skill, Float> skills = new HashMap<>();

    public Creature() {
    }

    public Creature(World world, String name) {
        this.name = name;
        this.world = world;

        world.getCell(x, y).addCreature(this);
    }

    public Creature setWorld(World world) {
        this.world = world;
        return this;
    }

    public Creature setName(String name) {
        this.name = name;
        return this;
    }

    public boolean move(int newX, int newY) {
        WorldCell oldCell = world.getCell(x, y);
        WorldCell newCell = world.getCell(newX, newY);

        if (oldCell == null || newCell == null) {
            System.out.println("Can't move from [" + x + ", " + y + "] to [" + newX + ", " + newY + "] because one of cells doesn't exist");
            return false;
        }

        oldCell.removeCreature(this);
        newCell.addCreature(this);

        this.x = newX;
        this.y = newY;

        return true;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public World getWorld() {
        return world;
    }

    public float getHP() {
        return hp;
    }

    public void damage(float damage) {
        this.hp -= damage;
        if (this.hp <= 0)
            die();
    }

    public void die() {
        isAlive = false;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void tick() {

    }

    public WorldCell getCurrentCell() {
        return world.getCell(x, y);
    }

    public void write(DataOutputStream dos) throws IOException {
        dos.writeUTF(name);
        dos.writeInt(x);
        dos.writeInt(y);
        dos.writeFloat(hp);
    }

    public static Creature read(DataInputStream dis) throws IOException {
        Creature creature = new Creature();
        creature.name = dis.readUTF();
        creature.x = dis.readInt();
        creature.y = dis.readInt();
        creature.hp = dis.readFloat();

        return creature;
    }

    public String getName() {
        return name;
    }

    public void setMaxHP(float maxHP) {
        this.maxHP = maxHP;
    }
}
