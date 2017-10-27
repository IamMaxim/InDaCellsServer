package ru.iammaxim.InDaCellsServer.Creatures;

import ru.iammaxim.InDaCellsServer.Spells.BaseSpell;

import java.util.ArrayList;

public class Player extends BaseCreature {
    private int stamina;
    private ArrayList<BaseSpell> spells;
    private int attributes; //TODO

    public int getStamina() {
        return stamina;
    }

    public ArrayList<BaseSpell> getSpells() {
        return spells;
    }

    public int getAttributes() {
        return attributes;
    }
}
