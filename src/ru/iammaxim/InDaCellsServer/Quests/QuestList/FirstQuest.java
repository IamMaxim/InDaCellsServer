package ru.iammaxim.InDaCellsServer.Quests.QuestList;

import ru.iammaxim.InDaCellsServer.Creatures.Creature;
import ru.iammaxim.InDaCellsServer.Creatures.Human;
import ru.iammaxim.InDaCellsServer.Items.Item;
import ru.iammaxim.InDaCellsServer.Quests.CollectStage;
import ru.iammaxim.InDaCellsServer.Quests.KillStage;
import ru.iammaxim.InDaCellsServer.Quests.Quest;
import ru.iammaxim.InDaCellsServer.Quests.TalkStage;
import ru.iammaxim.InDaCellsServer.World.World;

public class FirstQuest extends Quest{
    public FirstQuest(int id, String title) {
        super(title);
        this.addStage(new KillStage(this, "Тестовый квест", new Creature(), 10));
        this.addStage(new CollectStage(this, "Тестовый квест", Item.items.get(0), 10));
        this.addStage(new TalkStage(new Human(new World("World"), "Name")));
    }

    @Override
    public void onQuestEnd(Human h) {
        h.addXp(500);
    }
}