package ru.iammaxim.InDaCellsServer.Quests.QuestList;

import ru.iammaxim.InDaCellsServer.Creatures.Creature;
import ru.iammaxim.InDaCellsServer.Creatures.Human;
import ru.iammaxim.InDaCellsServer.Items.Item;
import ru.iammaxim.InDaCellsServer.Quests.Quest;
import ru.iammaxim.InDaCellsServer.Quests.Stage;
import ru.iammaxim.InDaCellsServer.World.World;

public class FirstQuest extends Quest {
    public FirstQuest(int id, String title) {
        super(title);
        this.addStage(new Stage(this, "Тестовый квест") {

        });
        this.addStage(new Stage(this, "Тестовый квест") {

        });
        this.addStage(new Stage(this, "Тестовый квест") {

        });
    }

    @Override
    public void onQuestEnd(Human h) {
        System.out.println("Quest completed.");
    }
}