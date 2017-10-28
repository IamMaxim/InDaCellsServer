package ru.iammaxim.InDaCellsServer.World;

import ru.iammaxim.InDaCellsServer.Creatures.Player;

import java.util.HashMap;

public class World {
    private String name;

    // {x, {y, cell}}
    private HashMap<Integer, HashMap<Integer, WorldCell>> cells = new HashMap<>();
    private HashMap<String, Player> players = new HashMap<>();

    public Player getPlayer(String name) {
        return players.get(name);
    }

    public void addPlayer(String name, Player p) {
        players.put(name, p);
    }

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

    public HashMap<String, Player> getPlayers() {
        return players;
    }
}
