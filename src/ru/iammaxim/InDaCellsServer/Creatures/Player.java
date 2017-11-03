package ru.iammaxim.InDaCellsServer.Creatures;


import ru.iammaxim.InDaCellsServer.Activators.Activator;
import ru.iammaxim.InDaCellsServer.Items.Item;
import ru.iammaxim.InDaCellsServer.LogElement;
import ru.iammaxim.InDaCellsServer.Packets.*;
import ru.iammaxim.InDaCellsServer.Quests.Quest;
import ru.iammaxim.InDaCellsServer.Quests.QuestState;
import ru.iammaxim.InDaCellsServer.World.World;
import ru.iammaxim.InDaCellsServer.World.WorldCell;
import ru.iammaxim.NetLib.NetLib;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Player extends Human {
    protected int statsUpdateTimer = 0;

    @Override
    public void die() {
        super.die();
        try {
            NetLib.send(name, new PacketCell(world.getCell(0, 0)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected HashMap<Integer, QuestState> attachedQuests = new HashMap<>();


    public Player(World world, String name) {
        super(world, name);
    }

    public void startQuest(int id) {
        Quest quest = Quest.quests.get(id);

        if (quest == null) {
            System.out.println("No quest with id " + id + " found");
            return;
        }

        if (!attachedQuests.containsKey(id))
            attachedQuests.put(id, new QuestState(this, quest));
        else
            System.out.println("Quest " + quest.title + " is already started!");
    }

    public void completeQuest(int id) {
        QuestState questState = attachedQuests.get(id);

        if (questState == null) {
            System.out.println("Quest with id " + id + " was not started, so can't complete it");
            return;
        }

        if (questState.isCompleted()) {
            System.out.println("Quest " + id + " already completed, can't complete again");
        }

        questState.complete();
    }

    @Override
    protected void doPickup() {
        super.doPickup();
        unblockInput();
    }

    public ArrayList<QuestState> getQuests() {
        ArrayList<QuestState> quests = new ArrayList<>();
        attachedQuests.forEach((id, q) -> {
            if (q.isRunning())
                quests.add(q);
        });
        return quests;
    }

    @Override
    public void addItem(Item item) {
        super.addItem(item);

        getQuests().forEach(q -> q.getCurrentStage().onItemAdd(this, item));
        try {
            NetLib.send(name, new PacketInventoryChange(PacketInventoryChange.Type.ADD, item));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeItem(int index) {
        super.removeItem(index);
        getQuests().forEach(q -> q.getCurrentStage().onItemRemove(this, inventory.get(index)));
        try {
            NetLib.send(name, new PacketInventoryChange(PacketInventoryChange.Type.REMOVE, index));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doActivate() {
        super.doActivate();
        unblockInput();
        Activator a = getCurrentCell().getActivator(actionTargetID);
        if (a != null)
            getQuests().forEach(q -> q.getCurrentStage().onActivate(this, a));
    }

    @Override
    protected void doAttack() {
        super.doAttack();
        unblockInput();

        Creature c = getCurrentCell().getCreature(actionTargetID);
        if (c != null) {
            getQuests().forEach(q -> q.getCurrentStage().onAttack(this, c));
            if (!c.isAlive())
                getQuests().forEach(q -> q.getCurrentStage().onKill(this, c));
        }
    }

    @Override
    public void move(int newX, int newY) {
        super.move(newX, newY);
        try {
            NetLib.send(name, new PacketStartAction("Moving...", 0.5f));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void move(int cellID) {
        super.move(cellID);
        try {
            NetLib.send(name, new PacketStartAction("Moving...", 0.5f));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void unblockInput() {
        try {
            NetLib.send(name, new PacketUnblockInput());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected boolean doMove() {
        boolean moved = super.doMove();
        this.unblockInput();
        if (moved) {
            WorldCell cell = getCurrentCell();
            if (cell.getDescription() != null && !cell.getDescription().isEmpty()) {
                this.addMessage(cell.getDescription());
            }
            getQuests().forEach(q -> q.getCurrentStage().onMove(this, getCurrentCell()));
        }
        return moved;
    }

    public void talk(int targetID, String topic) {
        WorldCell cell = getCurrentCell();
        NPC npc = cell.getNPC(targetID);
        if (npc != null) {
            npc.say(this, topic);
            getQuests().forEach(q -> q.getCurrentStage().onTalk(this, npc, topic));
        }
    }

    @Override
    public void tick() {
        super.tick();

        statsUpdateTimer++;
        if (statsUpdateTimer == 100) {
            statsUpdateTimer = 0;
            try {
//                System.out.println("Sending stats: " + hp + " " + maxHP + ", " + sp + " " + maxSP + ", " + hunger + " " + maxHunger);
                NetLib.send(name, new PacketStats(this));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void addMessage(String nick, String message) {
        try {
            NetLib.send(name, new PacketAddToLog(new LogElement(LogElement.Type.MESSAGE, message, nick)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addMessage(String message) {
        try {
            NetLib.send(name, new PacketAddToLog(new LogElement(LogElement.Type.INFO, message, "")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public QuestState getQuestState(int id) {
        return attachedQuests.get(id);
    }
}
