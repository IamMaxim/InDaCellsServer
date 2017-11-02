package ru.iammaxim.InDaCellsServer.Quests.QuestList;

import ru.iammaxim.InDaCellsServer.Creatures.Player;
import ru.iammaxim.InDaCellsServer.Dialogs.DialogTopic;
import ru.iammaxim.InDaCellsServer.Items.Item;
import ru.iammaxim.InDaCellsServer.Quests.Quest;
import ru.iammaxim.InDaCellsServer.Quests.QuestStage;

public class TestQuest1 extends Quest {
    public TestQuest1(int id, String title) {
        super(id, title);

        QuestStage stage0 = new QuestStage(this, "This is description of first stage of the TestQuest1");
        stage0.addDialogEntry(new DialogTopic("Test entry", "Hmm, if you see this, the output of dialog lines works perfectly! Now, try advancing in the quest") {
            @Override
            public void onSay(Player p) {
                p.getQuestState(id).nextStage();
            }
        }.attachToNPC("Test NPC"));

        QuestStage stage1 = new QuestStage(this, "Wow, looks like you advanced to stage 2! Keep moving!");
        stage1.addDialogEntry(new DialogTopic("Test entry 2", "Move forward! 1 more stage!") {
            @Override
            public void onSay(Player p) {
                p.getQuestState(id).nextStage();
            }
        }.attachToNPC("Test NPC"));

        QuestStage stage2 = new QuestStage(this, "Whoa, the final one!") {
            @Override
            public void onStart(Player p) {
                p.getQuestState(id).nextStage();
            }

            @Override
            public void onComplete(Player p) {
                p.addMessage("TestQuest1 completed! Congrats!");
            }
        };

        addStage(stage0);
        addStage(stage1);
        addStage(stage2);
    }

    @Override
    public void onQuestEnd(Player p) {
        p.addItem(Item.weapons.get((int) (Math.random() * Item.weapons.size() - 1)).clone());
    }
}
