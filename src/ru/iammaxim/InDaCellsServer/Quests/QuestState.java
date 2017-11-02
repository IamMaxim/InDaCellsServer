package ru.iammaxim.InDaCellsServer.Quests;

import ru.iammaxim.InDaCellsServer.Creatures.NPC;
import ru.iammaxim.InDaCellsServer.Creatures.Player;
import ru.iammaxim.InDaCellsServer.Dialogs.DialogTopic;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class QuestState {
    public int questID;
    public State state;
    private int stage = 0;
    private Quest quest;
    private Player player;

    public QuestState(Player player, int questID, int stage, State state) {
        this.player = player;
        this.questID = questID;
        this.quest = Quest.quests.get(questID);
        this.stage = stage;
        this.state = state;
    }

    public QuestState(Player player, Quest quest) {
        this.player = player;
        this.quest = quest;
        questID = quest.id;
        state = State.RUNNING;
        player.addMessage("Quest started: " + quest.title);
        getCurrentStage().onStart(player);
    }

    public static QuestState read(Player p, DataInputStream dis) throws IOException {
        return new QuestState(p, dis.readInt(), dis.readInt(), State.values()[dis.readInt()]);
    }

    public void write(DataOutputStream dos) throws IOException {
        dos.writeInt(questID);
        dos.writeInt(stage);
        dos.writeInt(state.ordinal());
    }

    public boolean isCompleted() {
        return state == State.COMPLETED;
    }

    public void nextStage() {
        getQuest().getStage(stage).onComplete(player);
        stage++;
        if (stage == quest.stages.size())
            complete();
        else
            getQuest().getStage(stage).onStart(player);
    }

    public int getStage() {
        return stage;
    }

    public void setStage(int stage) {
        getQuest().getStage(stage).onComplete(player);
        this.stage = stage;
        getQuest().getStage(stage).onStart(player);
    }

    public void complete() {
        this.state = State.COMPLETED;
        quest.onQuestEnd(player);
        player.addMessage("Quest completed: " + quest.title);
    }

    public boolean isRunning() {
        return state == State.RUNNING;
    }

    public QuestStage getCurrentStage() {
        return quest.getStage(stage);
    }

    public void setState(State state) {
        this.state = state;
    }

    public State getState() {
        return state;
    }

    public Quest getQuest() {
        return quest;
    }

    public enum State {
        NOT_STARTED,
        RUNNING,
        COMPLETED
    }

    public ArrayList<DialogTopic> getTopics(NPC npc) {
        ArrayList<DialogTopic> topics = getCurrentStage().getEntries();
        topics.removeIf(e -> !e.needToShow(player, npc));
        return topics;
    }
}
