package ru.iammaxim.InDaCellsServer.Quests;

import ru.iammaxim.InDaCellsServer.Creatures.Creature;
import ru.iammaxim.InDaCellsServer.Creatures.Human;
import ru.iammaxim.InDaCellsServer.Items.Item;

public abstract class Stage{
    private String description;
    private State state;
    private Type type;

    private Quest attachedQuest;

    public Stage(Quest attachedQuest, String desc, Type type){
        this.attachedQuest = attachedQuest;
        this.description = desc;
        this.type = type;
        this.state = State.CLOSED;
    }

    public Stage(Quest attachedQuest, String desc, Type type, State state){
        this(attachedQuest, desc, type);
        this.state = state;
    }

    public abstract void onItemAdd(Item item);

    public abstract void onTalk(Human human);

    public abstract void onKill(Creature creature);

    public void done(){
        this.state = State.FINISHED;
        this.getAttachedQuest().reevaluate();
    }

    public String getDescription() {
        return description;
    }

    public Quest getAttachedQuest() {
        return attachedQuest;
    }

    public Type getType() {
        return type;
    }

    enum Type{
        KILL,
        COLLECT,
        TALK
    }

    enum State{
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
