package ru.iammaxim.InDaCellsServer.Quests;

import ru.iammaxim.InDaCellsServer.Creatures.Creature;
import ru.iammaxim.InDaCellsServer.Creatures.Human;
import ru.iammaxim.InDaCellsServer.Items.Item;

public class CollectStage extends Stage {
    private final Item item;
    private final int amount;
    private int rightNow;

    public CollectStage(Quest attachedQuest, String desc, Item item, int amount){
        super(attachedQuest, desc, Stage.Type.COLLECT);
        this.item = item;
        this.amount = amount;
        rightNow = 0;
    }

    @Override public void onItemAdd(Item i) {
        if(this.item.equals(i)){
            rightNow++;
        }
        if(rightNow == amount){
            done();
        }
    }

    public Item getItem() {
        return item;
    }

    public int getAmount() {
        return amount;
    }

    public int getRightNow() {
        return rightNow;
    }

    @Override public void onTalk(Human human) {}

    @Override public void onKill(Creature creature) {}
}
