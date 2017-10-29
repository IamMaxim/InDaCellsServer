package ru.iammaxim.InDaCellsServer.Quests.QuestList;

import ru.iammaxim.InDaCellsServer.Creatures.Creature;
import ru.iammaxim.InDaCellsServer.Creatures.Human;
import ru.iammaxim.InDaCellsServer.Creatures.Mobs.Dog;
import ru.iammaxim.InDaCellsServer.Creatures.NPC;
import ru.iammaxim.InDaCellsServer.Creatures.NPCs.JoCaptain;
import ru.iammaxim.InDaCellsServer.Items.Item;
import ru.iammaxim.InDaCellsServer.Quests.Quest;
import ru.iammaxim.InDaCellsServer.Quests.QuestLines;
import ru.iammaxim.InDaCellsServer.Quests.Stage;
import ru.iammaxim.InDaCellsServer.World.World;

public class PeaceMakersDogs extends Quest {
    private final NPC _JO;
    private final Creature _DOG;

    public PeaceMakersDogs(int id, World world, String title) {
        super(id, title);

        _JO = new JoCaptain(world);
        _DOG = new Dog(world);

        addStage(new Stage(this, QuestLines._talk_with + _JO.getName()) {
            @Override
            public void onTalk(Human p, NPC npc) {
                if (npc.equals(_JO)) {
                    _JO.speak(p, QuestLines.peacemakers_jo_dogs_intro);
                    done(p);
                }
            }

            @Override
            public void onItemAdd(Human p, Item item) {
            }

            @Override
            public void onKill(Human p, Creature creature) {
            }
        });
        addStage(new Stage(this, QuestLines._kill + _DOG.getName()) {
            private int amount = 3;
            private int rightNow = 0;

            @Override
            public void onKill(Human p, Creature creature) {
                if (creature.equals(_DOG)) rightNow++;
                if (rightNow == amount) done(p);
            }

            @Override
            public void onItemAdd(Human p, Item item) {
            }

            @Override
            public void onTalk(Human p, NPC npc) {
            }
        });

        addStage(new Stage(this, QuestLines._go_back + _JO.getName()) {
            @Override
            public void onTalk(Human p, NPC npc) {
                if (npc.equals(_JO)) {
                    _JO.speak(p, QuestLines.peacemakers_jo_dogs_finish);
                    done(p);
                }
            }

            @Override
            public void onItemAdd(Human p, Item item) {
            }

            @Override
            public void onKill(Human p, Creature creature) {
            }
        });

        setFirstStage();
    }
}