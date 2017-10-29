package ru.iammaxim.InDaCellsServer.Quests.QuestList;

import ru.iammaxim.InDaCellsServer.Creatures.Creature;
import ru.iammaxim.InDaCellsServer.Creatures.Mobs.Hedgehog;
import ru.iammaxim.InDaCellsServer.Creatures.NPC;
import ru.iammaxim.InDaCellsServer.Creatures.NPCs.TimTrasher;
import ru.iammaxim.InDaCellsServer.Items.Item;
import ru.iammaxim.InDaCellsServer.Quests.*;

public class Example extends Quest{
    public Example(int id, String title) {
        super(title);
        this.addStage(new Stage(this, "Тестовый убивающий квест") {
            private int needed = 10;
            private int rightNow = 0;
            @Override public void onKill(Creature c) {
                Creature hedgehog = new Hedgehog();
                if(c.equals(hedgehog)) rightNow++;
                if(rightNow == needed) done();
            }

            @Override public void onItemAdd(Item i) {}
            @Override public void onTalk(NPC h) {}
        });
        this.addStage(new Stage(this, "Тестовый собирающий квест"){
            private int needed = 10;
            private int rightNow = 0;
            private Item neededItem = Item.items.get(0);
            @Override public void onItemAdd(Item item) {
                if(item.equals(neededItem)) rightNow++;
                if(rightNow == needed) done();
            }

            @Override public void onTalk(NPC n) {}
            @Override public void onKill(Creature c) {}
        });
        this.addStage(new Stage(this, "Desc") {
            private NPC neededNPC = new TimTrasher();
            @Override public void onTalk(NPC npc) {
                if(npc.equals(neededNPC)) done();
            }

            @Override public void onItemAdd(Item item) {}
            @Override public void onKill(Creature creature) {}
        });
    }

    @Override
    public void onQuestEnd() {
        System.out.println("Quest completed.");
    }
}