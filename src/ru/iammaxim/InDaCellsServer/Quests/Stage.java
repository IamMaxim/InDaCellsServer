package ru.iammaxim.InDaCellsServer.Quests;

import ru.iammaxim.InDaCellsServer.Creatures.Creature;
import ru.iammaxim.InDaCellsServer.Creatures.Human;
import ru.iammaxim.InDaCellsServer.Creatures.NPC;
import ru.iammaxim.InDaCellsServer.Items.Item;

public abstract class Stage {
    private String description;
    private State state;

    private Quest attachedQuest;

    public Stage(Quest attachedQuest, String desc) {
        this.attachedQuest = attachedQuest;
        this.description = desc;
        this.state = State.CLOSED;
    }

    public Stage(Quest attachedQuest, String desc, State state) {
        this(attachedQuest, desc);
        this.state = state;
    }

    public abstract void onItemAdd(Human p, Item item);

    public abstract void onTalk(Human p, NPC npc);

    public abstract void onKill(Human p, Creature creature);

    public void done() {
        this.state = State.FINISHED;
        this.getAttachedQuest().reevaluate();
    }

    public String getDescription() {
        return description;
    }

    public Quest getAttachedQuest() {
        return attachedQuest;
    }

    enum State {
        CLOSED,
        IN_PROGRESS,
        FINISHED
    }

    public void setState(State state) {
        this.state = state;
    }

    public State getState() {
        return state;
    }
}
