package ru.iammaxim.InDaCellsServer.Quests;

import ru.iammaxim.InDaCellsServer.Creatures.Player;
import ru.iammaxim.InDaCellsServer.Quests.QuestList.TestQuest1;
import ru.iammaxim.InDaCellsServer.World.World;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class Quest {
    public static HashMap<Integer, Quest> quests = new HashMap<>();

    public final int id;
    public final String title;
    public final ArrayList<QuestStage> stages = new ArrayList<>();

    public Quest(int id, String title) {
        this.id = id;
        this.title = title;
    }

    public static void registerQuest(Quest q) {
        quests.put(q.getId(), q);
    }

    public static void registerQuests(World world) {
/*        registerQuest(new Raiders1(0, world, "Квест Рейдеров"));
        registerQuest(new RaidersRammsWeapon(1, world, "Дубина Рамма"));

        registerQuest(new PeaceMakers1(2, world, "Квест Миротворцев"));
        registerQuest(new PeaceMakersDogs(3, world, "Защитник"));*/

        registerQuest(new TestQuest1(0, "Test quest"));
    }

    public void onQuestEnd(Player p) {

    }

    public void addStage(QuestStage stage) {
        stages.add(stage);
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public QuestStage getStage(int stageIndex) {
        return stages.get(stageIndex);
    }
}