package ru.iammaxim.InDaCellsServer.Quests;

import ru.iammaxim.InDaCellsServer.Creatures.Human;
import ru.iammaxim.InDaCellsServer.Creatures.Player;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class QuestState {
    public int questID;
    public State state;
    private int stage = 0;
    private Quest quest;
    private Human player;

    public QuestState(Player player, int questID) {
        this.player = player;
        this.questID = questID;
        this.quest = Quest.quests.get(questID);
    }

    public QuestState(Player player, Quest quest) {
        this.player = player;
        this.quest = quest;
        questID = quest.id;
        state = State.RUNNING;
    }

    public static QuestState read(Player p, DataInputStream dis) throws IOException {
        int id = dis.readInt();
        QuestState state = new QuestState(p, id);
        state.setStage(dis.readInt());
        state.setState(State.values()[dis.readInt()]);
        return state;
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
        stage++;
    }

    public int getStage() {
        return stage;
    }

    public void setStage(int stage) {
        this.stage = stage;
    }

    public void complete() {
        this.state = State.COMPLETED;
        quest.onQuestEnd(player);
    }

    public boolean isRunning() {
        return state == State.RUNNING;
    }

    public Stage getCurrentStage() {
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
}
