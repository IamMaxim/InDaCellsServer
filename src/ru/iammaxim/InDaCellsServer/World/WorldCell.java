package ru.iammaxim.InDaCellsServer.World;


import ru.iammaxim.InDaCellsServer.Creatures.Creature;

import java.util.ArrayList;

public class WorldCell {
    private String name;
    private ArrayList<Creature> creatures = new ArrayList<>();

    @Override
    public String toString() {
        if (name != null)
            return name;
        else return "Cell";
    }

    public void addCreature(Creature creature) {
        creatures.add(creature);
    }

    public void removeCreature(Creature creature) {
        creatures.remove(creature);
    }

    public ArrayList<Creature> getCreatures() {
        return creatures;
    }
}
