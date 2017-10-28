package ru.iammaxim.InDaCellsServer.Creatures;


import ru.iammaxim.InDaCellsServer.Activators.Activator;
import ru.iammaxim.InDaCellsServer.Packets.PacketUnblockInput;
import ru.iammaxim.InDaCellsServer.World.World;
import ru.iammaxim.InDaCellsServer.World.WorldCell;
import ru.iammaxim.NetLib.NetLib;

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
    protected State state = State.IDLE;
    protected int actionCounter = 0;
    protected int maxActionCounter = 200;
    protected int newX, newY;
    protected int actionTargetID = -1;
    protected int id;

    public Creature() {
    }

    public Creature(World world, String name) {
        this.name = name;
        this.world = world;

        world.getCell(x, y).addCreature(this);
    }

    public static Creature read(DataInputStream dis) throws IOException {
        Creature creature = new Creature();
        creature.name = dis.readUTF();
        creature.x = dis.readInt();
        creature.y = dis.readInt();
        creature.hp = dis.readFloat();

        return creature;
    }

    public int getID() {
        return id;
    }

    private void doMove() {
        WorldCell oldCell = world.getCell(x, y);
        WorldCell newCell = world.getCell(newX, newY);

        if (oldCell == null || newCell == null) {
            System.out.println("Can't move from [" + x + ", " + y + "] to [" + newX + ", " + newY + "] because one of cells doesn't exist");
        } else {
            oldCell.removeCreature(this);
            newCell.addCreature(this);

            this.x = newX;
            this.y = newY;
        }

        if (this instanceof Player)
            try {
                NetLib.send(name, new PacketUnblockInput());
            } catch (IOException e) {
                e.printStackTrace();
            }
    }

    public void move(int newX, int newY) {
        state = State.MOVING;
        this.newX = newX;
        this.newY = newY;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public World getWorld() {
        return world;
    }

    public Creature setWorld(World world) {
        this.world = world;
        return this;
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
        if (actionCounter != -1) {
            if (actionCounter == maxActionCounter) {
                if (state == State.MOVING) {
                    doMove();
                } else if (state == State.ACTIVATING) {
                    doActivate();
                } else if (state == State.ATTACKING) {
                    doAttack();
                } else if (state == State.DEFENDING) {
                    // just reset state, we don't need to do something
                }
                state = State.IDLE;
                actionCounter = -1;
            }
            actionCounter++;
        }
    }

    public void defend() {
        setState(State.DEFENDING, 0);
    }

    public void activate(int activatorID) {
        setState(State.ACTIVATING, activatorID);
    }

    private void doActivate() {
        Activator a = getCurrentCell().getActivator(actionTargetID);
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

    public String getName() {
        return name;
    }

    public Creature setName(String name) {
        this.name = name;
        return this;
    }

    public void setMaxHP(float maxHP) {
        this.maxHP = maxHP;
    }

    public void maxHP() {
        hp = maxHP;
    }

    public void setState(State state, int maxActionCounter) {
        this.state = state;
        this.actionCounter = 0;
        this.maxActionCounter = maxActionCounter;
    }

    public State getState() {
        return state;
    }

    private void doAttack() {
        Creature victim = getCurrentCell().getCreature(actionTargetID);

        // TODO: change to real value
        victim.damage(1);
    }

    public void attack(int targetID) {
        setState(State.ATTACKING, 100);
        actionTargetID = targetID;
    }

    public enum State {
        MOVING,
        IDLE,
        ACTIVATING,
        ATTACKING,
        DEFENDING
    }
}
