package ru.iammaxim.InDaCellsServer.Quests;

import ru.iammaxim.InDaCellsServer.Activators.Activator;
import ru.iammaxim.InDaCellsServer.Creatures.Creature;
import ru.iammaxim.InDaCellsServer.Creatures.NPC;
import ru.iammaxim.InDaCellsServer.Creatures.Player;
import ru.iammaxim.InDaCellsServer.Items.Item;
import ru.iammaxim.InDaCellsServer.World.WorldCell;

public class Stage {
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

    public void onActivate(Player p, Activator a) {

    }

    public void onItemAdd(Player p, Item item) {

    }

    public void onTalk(Player p, NPC npc) {

    }

    public void onKill(Player p, Creature creature) {

    }

    public void onComplete(Player p) {

    }

    public void onStart(Player p) {

    }

    public void onDamage(Player p, Creature victim) {

    }

    public String getDescription() {
        return description;
    }

    public Quest getAttachedQuest() {
        return attachedQuest;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public void onItemRemove(Player p, Item item) {

    }

    public void onMove(Player player, WorldCell cell) {

    }

    enum State {
        CLOSED,
        IN_PROGRESS,
        FINISHED
    }
}
