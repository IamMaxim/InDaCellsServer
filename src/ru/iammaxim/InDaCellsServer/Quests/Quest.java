package ru.iammaxim.InDaCellsServer.Quests;

import ru.iammaxim.InDaCellsServer.Creatures.Human;
import ru.iammaxim.InDaCellsServer.Quests.QuestList.PeaceMakers1;
import ru.iammaxim.InDaCellsServer.Quests.QuestList.PeaceMakersDogs;
import ru.iammaxim.InDaCellsServer.Quests.QuestList.Raiders1;
import ru.iammaxim.InDaCellsServer.Quests.QuestList.RaidersRammsWeapon;
import ru.iammaxim.InDaCellsServer.World.World;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class Quest {
    public static HashMap<Integer, Quest> quests = new HashMap<>();

    public final int id;
    public final String title;
    public final ArrayList<Stage> stages = new ArrayList<>();
    protected Stage currentStage;

    public Quest(int id, String title){
        this.id = id;
        this.title = title;
//        this.currentStage = stages.get(0);
    }

    public static void registerQuest(Quest q){
        quests.put(q.getId(), q);
    }

    public static void registerQuests(World world){
        registerQuest(new Raiders1(0, world, "Квест Рейдеров"));
        registerQuest(new RaidersRammsWeapon(1, world, "Дубина Рамма"));

        registerQuest(new PeaceMakers1(2, world, "Квест Миротворцев"));
        registerQuest(new PeaceMakersDogs(3, world, "Защитник"));
    }

    public void onQuestEnd(Human p){
        p.questDone(this.id);
    }

    public void addStage(Stage stage){
        stages.add(stage);
    }

    public void reevaluate(Human p){
        boolean inProgressOrClosed = false;
        for(int i = 0; i < stages.size(); i++) {
            if (stages.get(i).getState() == Stage.State.FINISHED
                    && stages.get(i + 1) != null
                    && stages.get(i + 1).getState() != Stage.State.IN_PROGRESS) {
                stages.get(i + 1).setState(Stage.State.IN_PROGRESS);
            }
            if(stages.get(i).getState() == Stage.State.CLOSED
                    || stages.get(i).getState() == Stage.State.IN_PROGRESS){
                inProgressOrClosed = true;
            }
        }
        if( ! inProgressOrClosed)
            this.onQuestEnd(p);
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Stage getCurrentStage(){
        return currentStage;
    }
}