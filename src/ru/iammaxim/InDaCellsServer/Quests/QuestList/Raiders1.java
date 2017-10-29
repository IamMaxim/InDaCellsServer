package ru.iammaxim.InDaCellsServer.Quests.QuestList;

import ru.iammaxim.InDaCellsServer.Creatures.Creature;
import ru.iammaxim.InDaCellsServer.Creatures.Human;
import ru.iammaxim.InDaCellsServer.Creatures.NPC;
import ru.iammaxim.InDaCellsServer.Creatures.NPCs.GeorgRaidersLeader;
import ru.iammaxim.InDaCellsServer.Creatures.NPCs.TimTrasher;
import ru.iammaxim.InDaCellsServer.Items.Item;
import ru.iammaxim.InDaCellsServer.Quests.Quest;
import ru.iammaxim.InDaCellsServer.Quests.QuestLines;
import ru.iammaxim.InDaCellsServer.Quests.Stage;

public class Raiders1 extends Quest {

    private final NPC _GEORG = new GeorgRaidersLeader();
    private final NPC _TIM = new TimTrasher();

    public Raiders1(String title) {
        super(title);
        addStage(new Stage(this, QuestLines._talk_with + _GEORG.getName()) {
            @Override
            public void onTalk(Human p, NPC npc) {
                if (npc.equals(_GEORG)) {
                    _GEORG.speak(p, QuestLines.raiders_1_georg_intro_speech);
                    done();
                }
            }

            @Override
            public void onKill(Human p, Creature c) {
            }

            @Override
            public void onItemAdd(Human p, Item i) {
            }
        });
        addStage(new Stage(this, QuestLines.raiders_1_lftrashers) {
            @Override
            public void onTalk(Human p, NPC npc) {
                if (npc.equals(_GEORG)) {
                    _GEORG.speak(p, QuestLines.raiders_1_georg_intro_speech);
                    done();
                }
            }

            @Override
            public void onKill(Human p, Creature creature) {
            }

            @Override
            public void onItemAdd(Human p, Item item) {
            }
        });
        addStage(new Stage(this, QuestLines._kill + _TIM.getName()) {
            @Override
            public void onKill(Human p, Creature creature) {
                if (creature.equals(_TIM)) {
                    _TIM.speak(p, QuestLines.raiders_1_tim);
                    done();
                }
            }

            @Override
            public void onItemAdd(Human p, Item item) {
            }

            @Override
            public void onTalk(Human p, NPC npc) {
            }
        });
        addStage(new Stage(this, QuestLines._go_back + _GEORG.getName()) {
            @Override
            public void onItemAdd(Human p, Item item) {
            }

            @Override
            public void onTalk(Human p, NPC npc) {
                if (npc.equals(_GEORG)) {
                    _GEORG.speak(p, QuestLines.raiders_1_finish);
                    done();
                }
            }

            @Override
            public void onKill(Human p, Creature creature) {
            }
        });
    }

    @Override
    public void onQuestEnd() {
        System.out.println("Raiders1 quest complete!");
    }
}
