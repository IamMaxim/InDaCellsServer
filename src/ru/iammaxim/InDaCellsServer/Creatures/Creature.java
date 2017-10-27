package ru.iammaxim.InDaCellsServer.Creatures;


import ru.iammaxim.InDaCellsServer.World.World;
import ru.iammaxim.InDaCellsServer.World.WorldCell;

import java.util.HashMap;

public class Creature {
    protected int x = 0, y = 0;
    protected World world;
    protected float HP;
    protected boolean isAlive;
    protected String name;
    protected HashMap<Attribute, Float> attributes = new HashMap<>();
    protected HashMap<Skill, Float> skils = new HashMap<>();

    public Creature(World world, String name) {
        this.name = name;
        this.world = world;

        world.getCell(x, y).addCreature(this);
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
        return HP;
    }

    public void damage(float damage) {
        this.HP -= damage;
        if (this.HP <= 0)
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
}
