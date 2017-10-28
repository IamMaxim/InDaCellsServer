package ru.iammaxim.InDaCellsServer.Quests;

import ru.iammaxim.InDaCellsServer.Creatures.Creature;
import ru.iammaxim.InDaCellsServer.Creatures.Human;
import ru.iammaxim.InDaCellsServer.Items.Item;

public class TalkStage extends Stage {
    private final Human neededToTalkWith;

    public TalkStage(Quest attachedQuest, String desc, Human h){
        super(attachedQuest, desc, Type.TALK);
        this.neededToTalkWith = h;
    }

    @Override public void onItemAdd(Item item) {}

    @Override public void onTalk(Human human) {
        if(human.equals(neededToTalkWith))
            this.done();
    }

    @Override public void onKill(Creature creature) {}

    public Human getNeededToTalkWith() {
        return neededToTalkWith;
    }
}