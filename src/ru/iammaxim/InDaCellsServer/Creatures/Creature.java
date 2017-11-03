package ru.iammaxim.InDaCellsServer.Creatures;


import ru.iammaxim.InDaCellsServer.Activators.Activator;
import ru.iammaxim.InDaCellsServer.Items.Item;
import ru.iammaxim.InDaCellsServer.Packets.PacketCell;
import ru.iammaxim.InDaCellsServer.World.World;
import ru.iammaxim.InDaCellsServer.World.WorldCell;
import ru.iammaxim.NetLib.NetLib;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Iterator;

public class Creature {
    protected int x = 0, y = 0;
    protected World world;
    protected float hp, maxHP;
    protected boolean isAlive;
    protected String name;
    protected float damage;
    protected State state = State.IDLE;
    protected int actionCounter = -1;
    protected int maxActionCounter = -1;
    protected int newX, newY;
    protected int actionTargetID = -1;
    protected int attackMode = -1;
    protected int id;
    protected Type type;
    protected int cellID;

    public Creature(World world, String name, int hp, int damage) {
        this(world, name);
        setMaxHP(hp);
        maxHP();
        setDamage(damage);
    }

    public Creature() {
        this.damage = 1;
        if (this instanceof Player)
            type = Type.PLAYER;
        else if (this instanceof NPC)
            type = Type.NPC;
        else
            type = Type.CREATURE;
    }

    public Creature(World world, String name) {
        this();
        this.name = name;
        this.world = world;
        id = (int) (Math.random() * Integer.MAX_VALUE);
    }

    public static Creature read(World world, DataInputStream dis) throws IOException {
        Type type = Type.values()[dis.readInt()];
        int id = dis.readInt();
        String name = dis.readUTF();
        Creature creature = null;

        switch (type) {
            case CREATURE:
                creature = new Creature(world, name);
                break;
            case NPC:
                creature = new NPC(world, name);
                break;
            case PLAYER:
                creature = new Player(world, name);
                break;
        }

        creature.type = type;
        creature.id = id;
        creature.name = name;
        creature.x = dis.readInt();
        creature.y = dis.readInt();
        creature.hp = dis.readFloat();
        creature.maxHP = dis.readFloat();

        if (creature instanceof Human) {
            ((Human) creature).hunger = dis.readFloat();
            ((Human) creature).maxHunger = dis.readFloat();
            ((Human) creature).sp = dis.readFloat();
            ((Human) creature).maxSP = dis.readFloat();

            int invSize = dis.readInt();
            for (int i = 0; i < invSize; i++) {
                ((Human) creature).inventory.add(Item.read(dis));
            }
        }

        return creature;
    }

    public static Creature read(DataInputStream dis) throws IOException {
        Creature creature = new Creature();
        creature.name = dis.readUTF();
        creature.x = dis.readInt();
        creature.y = dis.readInt();
        creature.hp = dis.readFloat();
        creature.maxHP = dis.readFloat();

        if (creature instanceof Human) {
            ((Human) creature).hunger = dis.readFloat();
            ((Human) creature).maxHunger = dis.readFloat();
            ((Human) creature).sp = dis.readFloat();
            ((Human) creature).maxSP = dis.readFloat();
        }

        return creature;
    }

    public int getID() {
        return id;
    }

    protected boolean doMove() {
        WorldCell oldCell = world.getCell(cellID);
        WorldCell newCell = world.getCell(actionTargetID);

        if (oldCell == null || newCell == null) {
            System.out.println("Can't move because one of cells doesn't exist");
            return false;
        }

        oldCell.removeCreature(this);
        newCell.addCreature(this);
        return true;
    }

    public void move(int newX, int newY) {
        setState(State.MOVING, 50);
        WorldCell dest = world.getCell(newX, newY);
        if (dest == null) {
            System.out.println("Can't move " + name + " to [" + newX + ", " + newY + "] because destination is null");
            return;
        }
        this.actionTargetID = dest.getID();
    }

    public void move(int cellID) {
        setState(State.MOVING, 50);
        this.actionTargetID = cellID;
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

    public void damage(float damage, int mode) {
        this.hp -= damage;
        if (this.hp <= 0)
            die();
    }

    protected void die() {
        isAlive = false;

        getCurrentCell().getPlayers().forEach(p -> {
            p.addMessage(getName() + " died.");
        });

        getCurrentCell().removeCreature(this);
        world.getCell(0, 0).addCreature(this);
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
                } else if (state == State.PICKING_UP) {
                    doPickup();
                }
                state = State.IDLE;
                actionCounter = -1;
            }
            actionCounter++;
        }
    }

    protected void doPickup() {
        Iterator<Item> it = getCurrentCell().getItems().iterator();

        while (it.hasNext()) {
            Item i = it.next();

            if (i.getID() == actionTargetID) {
                getCurrentCell().removeItem(i);
                world.getPlayer(name).addItem(i);
                break;
            }
        }
    }

    public void defend() {
        setState(State.DEFENDING, 200);
    }

    public void activate(int activatorID) {
        setState(State.ACTIVATING, 100);
        actionTargetID = activatorID;
    }

    protected void doActivate() {
        Activator a = getCurrentCell().getActivator(actionTargetID);

        if (a != null)
            a.activate(this);
    }

    public float getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public WorldCell getCurrentCell() {
        return world.getCell(cellID);
    }

    public void write(DataOutputStream dos) throws IOException {
        dos.writeInt(type.ordinal());
        dos.writeInt(id);
        dos.writeUTF(name);
        dos.writeInt(x);
        dos.writeInt(y);
        dos.writeFloat(hp);
        dos.writeFloat(maxHP);
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

    protected void doAttack() {
        Creature victim = getCurrentCell().getCreature(actionTargetID);

        // check if victim already left cell
        if (victim == null) {
            System.out.println("victim is null");
            return;
        }

        getCurrentCell().getPlayers().forEach(p -> p.addMessage(getName() + " attacking " + victim.getName() + " for " + damage + " damage"));

        victim.damage(getDamage(), attackMode);

        if (victim.isAlive())
            getCurrentCell().getPlayers().forEach(p -> p.addMessage(victim.getName() + " has " + victim.getHP() + " HP left"));
    }

    public void attack(int targetID, int mode) {
        setState(State.ATTACKING, 100);
        actionTargetID = targetID;
        attackMode = mode;
    }

    public void setCellID(int cellID) {
        this.cellID = cellID;
    }

    public enum Type {
        CREATURE,
        NPC,
        PLAYER
    }

    public enum State {
        MOVING,
        IDLE,
        ACTIVATING,
        ATTACKING,
        PICKING_UP,
        DEFENDING
    }
}
