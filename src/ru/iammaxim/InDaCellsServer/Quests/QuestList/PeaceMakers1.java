package ru.iammaxim.InDaCellsServer.Quests.QuestList;

import ru.iammaxim.InDaCellsServer.Creatures.Creature;
import ru.iammaxim.InDaCellsServer.Creatures.Human;
import ru.iammaxim.InDaCellsServer.Creatures.Mobs.Beggar;
import ru.iammaxim.InDaCellsServer.Creatures.NPC;
import ru.iammaxim.InDaCellsServer.Creatures.NPCs.RoyFirstMarshal;
import ru.iammaxim.InDaCellsServer.Creatures.NPCs.TodOldFarmer;
import ru.iammaxim.InDaCellsServer.Quests.Quest;
import ru.iammaxim.InDaCellsServer.Quests.QuestLines;
import ru.iammaxim.InDaCellsServer.Quests.Stage;

public class PeaceMakers1 extends Quest {
    private final NPC _ROY = new RoyFirstMarshal();
    private final NPC _TOD = new TodOldFarmer();

    private final Creature _BOMJ = new Beggar(); //lul

    public PeaceMakers1(String title) {
        super(title);
        addStage(new Stage(this, QuestLines._talk_with) {
            @Override public void onTalk(Human human) {
                if(human.equals(_ROY)){
                    _ROY.speak(QuestLines.peacemakers_1_intro);
                    done();
                }
            }
        });
        addStage(new Stage(this, QuestLines.peacemakers_1_find_beggars) {
            private int amount = 3;
            private int rightNow = 0;
            @Override public void onKill(Creature creature) {
                if(creature.equals(_BOMJ)) rightNow++;
                if(rightNow == amount) done();
            }
        });
        addStage(new Stage(this, QuestLines._talk_with + _TOD) {
            @Override public void onTalk(Human human) {
                if(human.equals(_TOD)){
                    _TOD.speak(QuestLines.peacemakers_1_tod_speech);
                    done();
                }
            }
        });
        addStage(new Stage(this, QuestLines._go_back + _ROY) {
            @Override public void onTalk(Human human) {
                if(human.equals(_ROY)){
                    _ROY.speak(QuestLines.peacemakers_1_finish);
                    done();
                }
            }
        });
    }

    @Override public void onQuestEnd() {
        System.out.println("PeaceMakers1 done!");
    }
}
