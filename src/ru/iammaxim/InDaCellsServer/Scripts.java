package ru.iammaxim.InDaCellsServer;

import ru.iammaxim.InDaCellsServer.Items.Item;

public class Scripts {
    public static OnActivate getActivatorScript(int id) {
        switch (id) {
            case 0: // 0,0 Push me
                return h -> {
                    h.damage(5, 0);
                };
            case 1: // 0, -1 Spawn item
                return h -> {
                    h.getWorld().getCell(0, -1).addItem(Item.weapons.get(0).clone());
                };
            case 2:
                return h -> {
                    h.getWorld().getCell(0, 1).addItem(Item.items.get((int) (Math.random() * (Item.items.size() - 1))).clone());
                };
            case 3:
                return h -> {
                    h.getWorld().getCell(0, 1).addItem(Item.weapons.get((int) (Math.random() * (Item.weapons.size() - 1))).clone());
                };
            case 4:
                return h -> {
                    h.getWorld().getCell(0, 1).addItem(Item.armors.get((int) (Math.random() * (Item.armors.size() - 1))).clone());
                };
        }

        return null;
    }
}
