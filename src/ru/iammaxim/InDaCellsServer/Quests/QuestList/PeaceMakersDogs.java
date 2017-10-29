package ru.iammaxim.InDaCellsServer.Quests.QuestList;

import ru.iammaxim.InDaCellsServer.Creatures.Creature;
import ru.iammaxim.InDaCellsServer.Creatures.Mobs.Dog;
import ru.iammaxim.InDaCellsServer.Creatures.NPC;
import ru.iammaxim.InDaCellsServer.Creatures.NPCs.JoCaptain;
import ru.iammaxim.InDaCellsServer.Items.Item;
import ru.iammaxim.InDaCellsServer.Items.ItemWeapon;
import ru.iammaxim.InDaCellsServer.Quests.Quest;
import ru.iammaxim.InDaCellsServer.Quests.QuestLines;
import ru.iammaxim.InDaCellsServer.Quests.Stage;
import ru.iammaxim.InDaCellsServer.World.World;

public class PeaceMakersDogs extends Quest {
    private final NPC _JO = new JoCaptain();
    private final Creature _DOG;

    public PeaceMakersDogs(World world, String title) {
        super(title);
        _DOG = new Dog(world);
        addStage(new Stage(this, QuestLines._talk_with + _JO.getName()) {
            @Override
            public void onTalk(NPC npc) {
                if (npc.equals(_JO)) {
                    _JO.speak(QuestLines.peacemakers_jo_dogs_intro);
                    done();
                }
            }

            @Override
            public void onItemAdd(Item item) {
            }

            @Override
            public void onKill(Creature creature) {
            }
        });
        addStage(new Stage(this, QuestLines._kill + _DOG.getName()) {
            private int amount = 3;
            private int rightNow = 0;

            @Override
            public void onKill(Creature creature) {
                if (creature.equals(_DOG)) rightNow++;
                if (rightNow == amount) done();
            }

            @Override
            public void onItemAdd(Item item) {
            }

            @Override
            public void onTalk(NPC npc) {
            }
        });

        addStage(new Stage(this, QuestLines._go_back + _JO.getName()) {
            @Override
            public void onTalk(NPC npc) {
                if (npc.equals(_JO)) {
                    _JO.speak(QuestLines.peacemakers_jo_dogs_finish);
                    done();
                }
            }

            @Override
            public void onItemAdd(Item item) {
            }

            @Override
            public void onKill(Creature creature) {
            }
        });
    }

    @Override
    public void onQuestEnd() {
        System.out.println("Ramm's quest done");
    }
}