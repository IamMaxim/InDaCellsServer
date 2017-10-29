package ru.iammaxim.InDaCellsServer.Quests.QuestList;

import ru.iammaxim.InDaCellsServer.Creatures.Creature;
import ru.iammaxim.InDaCellsServer.Creatures.Human;
import ru.iammaxim.InDaCellsServer.Creatures.Mobs.Beggar;
import ru.iammaxim.InDaCellsServer.Creatures.NPC;
import ru.iammaxim.InDaCellsServer.Creatures.NPCs.RoyFirstMarshal;
import ru.iammaxim.InDaCellsServer.Creatures.NPCs.TodOldFarmer;
import ru.iammaxim.InDaCellsServer.Items.Item;
import ru.iammaxim.InDaCellsServer.Quests.Quest;
import ru.iammaxim.InDaCellsServer.Quests.QuestLines;
import ru.iammaxim.InDaCellsServer.Quests.Stage;
import ru.iammaxim.InDaCellsServer.World.World;

public class PeaceMakers1 extends Quest {
    private final NPC _ROY;
    private final NPC _TOD;

    private final Creature _BOMJ; //lul

    public PeaceMakers1(int id, World world, String title) {
        super(id, title);

        _ROY = new RoyFirstMarshal(world);
        _TOD = new TodOldFarmer(world);
        _BOMJ = new Beggar(world);

        addStage(new Stage(this, QuestLines._talk_with) {
            @Override
            public void onTalk(Human p, NPC npc) {
                if (npc.getName().equals(_ROY.getName())) {
                    _ROY.speak(p, QuestLines.peacemakers_1_intro);
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
        addStage(new Stage(this, QuestLines.peacemakers_1_find_beggars) {
            private int amount = 3;
            private int rightNow = 0;

            @Override
            public void onKill(Human p, Creature creature) {
                if (creature.getName().equals(_BOMJ.getName())) rightNow++;
                System.out.println("rightNow now: " + rightNow);
                if (rightNow == amount) done(p);
            }

            @Override
            public void onItemAdd(Human p, Item item) {
            }

            @Override
            public void onTalk(Human p, NPC npc) {
            }
        });
        addStage(new Stage(this, QuestLines._talk_with + _TOD) {
            @Override
            public void onTalk(Human p, NPC npc) {
                if (npc.getName().equals(_TOD.getName())) {
                    _TOD.speak(p, QuestLines.peacemakers_1_tod_speech);
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
        addStage(new Stage(this, QuestLines._go_back + _ROY) {
            @Override
            public void onTalk(Human p, NPC npc) {
                if (npc.getName().equals(_ROY.getName())) {
                    _ROY.speak(p, QuestLines.peacemakers_1_finish);
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
