package ru.iammaxim.InDaCellsServer.Quests;

import ru.iammaxim.InDaCellsServer.Creatures.Human;

import java.util.ArrayList;

public abstract class Quest {
    public final String title;
    public final ArrayList<Stage> stages = new ArrayList<>();
    protected Stage currentStage;

    public Quest(String title){
        this.title = title;
    }

    public abstract void onQuestEnd(Human h);

    public void addStage(Stage stage){
        stages.add(stage);
    }

    public void reevaluate(){
        for(int i = 0; i < stages.size(); i++) {
            if (stages.get(i).getState() == Stage.State.FINISHED
                    && stages.get(i + 1) != null
                    && stages.get(i + 1).getState() != Stage.State.IN_PROGRESS) {
                stages.get(i + 1).setState(Stage.State.IN_PROGRESS);
            }
        }
    }

    public String getTitle() {
        return title;
    }

    public Stage getCurrentStage(){
        return currentStage;
    }
}