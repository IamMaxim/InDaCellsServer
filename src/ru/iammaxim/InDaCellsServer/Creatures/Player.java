package ru.iammaxim.InDaCellsServer.Creatures;


import ru.iammaxim.InDaCellsServer.Items.Item;
import ru.iammaxim.InDaCellsServer.Packets.*;
import ru.iammaxim.InDaCellsServer.World.World;
import ru.iammaxim.InDaCellsServer.World.WorldCell;
import ru.iammaxim.NetLib.NetLib;

import java.io.IOException;

public class Player extends Human {
    protected int statsUpdateTimer = 0;

    public Player(World world, String name) {
        super(world, name);
    }

    @Override
    public void addItem(Item item) {
        super.addItem(item);
        try {
            NetLib.send(name, new PacketInventoryChange(PacketInventoryChange.Type.ADD, item));
        } catch (IOException e) {
            e.printStackTrace();
        }

        attachedQuests.forEach(q -> q.getCurrentStage().onItemAdd(this, item));
    }

    @Override
    public void removeItem(int index) {
        super.removeItem(index);
        try {
            NetLib.send(name, new PacketInventoryChange(PacketInventoryChange.Type.REMOVE, index));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doAttack() {
        super.doAttack();

        if (getCurrentCell().getCreature(actionTargetID) != null && !getCurrentCell().getCreature(actionTargetID).isAlive())
            attachedQuests.forEach(q -> q.getCurrentStage().onKill(this, getCurrentCell().getCreature(actionTargetID)));
    }

    @Override
    public void move(int newX, int newY) {
        super.move(newX, newY);
        try {
            NetLib.send(name, new PacketStartAction("Moving...", 2));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doMove() {
        super.doMove();
        try {
            NetLib.send(name, new PacketUnblockInput());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void talk(int targetID) {
        WorldCell cell = getCurrentCell();
        NPC npc = cell.getNPC(targetID);
        if (npc != null)
            npc.speak(this, "Здравствуй, путник!");
        attachedQuests.forEach(q -> q.getCurrentStage().onTalk(this, npc));
    }

    @Override
    public void tick() {
        super.tick();

        statsUpdateTimer++;
        if (statsUpdateTimer == 50) {
            statsUpdateTimer = 0;
            try {
//                System.out.println("Sending stats: " + hp + " " + maxHP + ", " + sp + " " + maxSP + ", " + hunger + " " + maxHunger);
                NetLib.send(name, new PacketStats(this));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
