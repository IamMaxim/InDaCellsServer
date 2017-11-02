package ru.iammaxim.InDaCellsServer.Quests;

import ru.iammaxim.InDaCellsServer.Activators.Activator;
import ru.iammaxim.InDaCellsServer.Creatures.Creature;
import ru.iammaxim.InDaCellsServer.Creatures.NPC;
import ru.iammaxim.InDaCellsServer.Creatures.Player;
import ru.iammaxim.InDaCellsServer.Dialogs.DialogTopic;
import ru.iammaxim.InDaCellsServer.Items.Item;
import ru.iammaxim.InDaCellsServer.World.WorldCell;

import java.util.ArrayList;

public class QuestStage {
    private String description;
    private State state;
    private Quest attachedQuest;
    private ArrayList<DialogTopic> entries = new ArrayList<>();

    public QuestStage(Quest attachedQuest, String description) {
        this.attachedQuest = attachedQuest;
        this.description = description;
        this.state = State.CLOSED;
    }

    public QuestStage(Quest attachedQuest, String desc, State state) {
        this(attachedQuest, desc);
        this.state = state;
    }

    public void onActivate(Player p, Activator a) {

    }

    public void onItemAdd(Player p, Item item) {

    }

    public void onTalk(Player p, NPC npc, String topic) {

    }

    public void onKill(Player p, Creature creature) {

    }

    public void onComplete(Player p) {

    }

    public void onStart(Player p) {
        p.addMessage(attachedQuest.title, description);
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

    public ArrayList<DialogTopic> getEntries() {
        ArrayList<DialogTopic> entries1 = new ArrayList<>();
        entries1.addAll(entries);
        return entries1;
    }

    public void addDialogEntry(DialogTopic e) {
        entries.add(e);
    }

    public void onAttack(Player player, Creature creature) {

    }

    enum State {
        CLOSED,
        IN_PROGRESS,
        FINISHED
    }
}
