package ru.iammaxim.InDaCellsServer.Containers;

import ru.iammaxim.InDaCellsServer.Items.Item;

import java.util.ArrayList;

public class Container {
    protected ArrayList<Item> items = new ArrayList<>();

    public void open() {
    }

    public ArrayList<Item> getContent() {
        return items;
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public void removeItem(Item item) {
        items.remove(item);
    }

    public void close() {
    }
}
