package ru.iammaxim.InDaCellsServer.World;

import java.util.HashMap;

public class World {
    private String name;

    // {x, {y, cell}}
    private HashMap<Integer, HashMap<Integer, WorldCell>> cells = new HashMap<>();

    public void addCell(int x, int y, WorldCell cell) {
        HashMap<Integer, WorldCell> row = cells.computeIfAbsent(x, k -> new HashMap<>());
        row.put(y, cell);
    }

    public WorldCell getCell(int x, int y) {
        HashMap<Integer, WorldCell> row = cells.get(x);
        if (row == null)
            return null;
        return row.get(y);
    }

    public World(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    public void save() {

    }

    public void load() {

    }

    public String getName() {
        return name;
    }

    public HashMap<Integer, HashMap<Integer, WorldCell>> getCells() {
        return cells;
    }
}
