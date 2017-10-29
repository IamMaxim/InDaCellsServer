package ru.iammaxim.InDaCellsServer;

import ru.iammaxim.InDaCellsServer.Items.Item;

public class Scripts {
    public static OnActivate getActivatorScript(int id) {
        switch (id) {
            case 0: // 0,0 Push me
                return h -> {
                    h.damage(5);
                };
            case 1: // 0, -1 Spawn item
                return h -> {
                    if (h == null)
                        System.out.println("Human is null");
                    if (h.getWorld() == null)
                        System.out.println("World is null");
                    if (h.getWorld().getCell(0, -1) == null)
                        System.out.println("Cell is null");
                    if (Item.weapons == null)
                        System.out.println("Weapons is null");
                    if (Item.weapons.get(0) == null)
                        System.out.println("Item is null");
                    if (Item.weapons.get(0).clone() == null)
                        System.out.println("Item clone is null");
                    h.getWorld().getCell(0, -1).addItem(Item.weapons.get(0).clone());
                };
        }

        return null;
    }
}
