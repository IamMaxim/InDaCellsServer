package ru.iammaxim.InDaCellsServer.Quests;

import ru.iammaxim.InDaCellsServer.Creatures.Creature;
import ru.iammaxim.InDaCellsServer.Creatures.Human;
import ru.iammaxim.InDaCellsServer.Items.Item;

public class KillStage extends Stage {
    private final Creature creature;
    private final int amount;
    private int rightNow;

    public KillStage(Quest attachedQuest, String desc, Creature creature, int amount){
        super(attachedQuest, desc, Type.KILL);
        this.creature = creature;
        this.amount = amount;
        rightNow = 0;
    }

    @Override public void onKill(Creature c) {
        if(creature.equals(c)){
            rightNow++;
        }
        if(rightNow == amount){
            done();
        }
    }

    public Creature getCreature() {
        return creature;
    }

    public int getAmount() {
        return amount;
    }

    public int getRightNow() {
        return rightNow;
    }

    @Override public void onItemAdd(Item item) { }

    @Override public void onTalk(Human human) { }
}