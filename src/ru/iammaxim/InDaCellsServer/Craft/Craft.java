package ru.iammaxim.InDaCellsServer.Craft;

import ru.iammaxim.InDaCellsServer.Creatures.Human;
import ru.iammaxim.InDaCellsServer.Items.Item;
import ru.iammaxim.InDaCellsServer.Items.ItemCrafted;

import java.util.ArrayList;

public class Craft {
    public static void craft(Human h, Item.Type type, String name, String description, ArrayList<Integer> ingredients) {
        ArrayList<Item> inv = h.getInventory();

        for (Integer index : ingredients) {
            inv.remove((int) index);
        }

        ItemCrafted item = (ItemCrafted) new ItemCrafted(-1, type, name).setDescription(description);
        inv.add(item);
    }
}
