package ru.iammaxim.InDaCellsServer.Quests.QuestList;

import ru.iammaxim.InDaCellsServer.Creatures.Human;
import ru.iammaxim.InDaCellsServer.Creatures.NPC;
import ru.iammaxim.InDaCellsServer.Creatures.NPCs.RammCrusher;
import ru.iammaxim.InDaCellsServer.Items.Item;
import ru.iammaxim.InDaCellsServer.Items.ItemWeapon;
import ru.iammaxim.InDaCellsServer.Quests.Quest;
import ru.iammaxim.InDaCellsServer.Quests.QuestLines;
import ru.iammaxim.InDaCellsServer.Quests.Stage;

public class RaidersRammsWeapon extends Quest {
    private final NPC _RAMM = new RammCrusher();
    private final ItemWeapon _RAMMS_WEAPON = Item.weapons.get(1);

    public RaidersRammsWeapon(String title) {
        super(title);
        addStage(new Stage(this, QuestLines._talk_with + _RAMM.getName()) {
            @Override public void onTalk(Human human) {
                if(human.equals(_RAMM)){
                    _RAMM.speak(QuestLines.raiders_ramms_weapon_intro);
                    done();
                }
            }
        });
        addStage(new Stage(this, QuestLines._find + _RAMMS_WEAPON.getName()) {
            @Override public void onItemAdd(Item item) {
                if(item.equals(_RAMMS_WEAPON)){
                    done();
                }
            }
        });

        addStage(new Stage(this, QuestLines._go_back + _RAMM.getName()) {
            @Override public void onTalk(Human human) {
                if(human.equals(_RAMM)){
                    _RAMM.speak(QuestLines.raiders_ramms_weapon_finish);
                    done();
                }
            }
        });
    }

    @Override public void onQuestEnd() {
        System.out.println("Ramm's quest done");
    }
}