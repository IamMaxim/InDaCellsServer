package ru.iammaxim.InDaCellsServer.World;

import ru.iammaxim.InDaCellsServer.Creatures.Player;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;

public class World {
    // {x, {y, cell}}
    private final HashMap<Integer, HashMap<Integer, WorldCell>> map = new HashMap<>();
    private String name;
    private HashMap<String, Player> players = new HashMap<>();
    private final HashMap<Integer, WorldCell> cells = new HashMap<>();

    public World(String name) {
        this.name = name;
    }

    public Player getPlayer(String name) {
        return players.get(name);
    }

    public void addPlayer(Player p) {
        players.put(p.getName(), p);
    }

    public void addCell(int x, int y, WorldCell cell) {
        cell.setX(x);
        cell.setY(y);
        HashMap<Integer, WorldCell> row = map.computeIfAbsent(x, k -> new HashMap<>());
        row.put(y, cell);
        cells.put(cell.getID(), cell);
    }

    public void addCell(WorldCell cell) {
        cells.put(cell.getID(), cell);
    }

    public WorldCell getCell(int x, int y) {
        HashMap<Integer, WorldCell> row = map.get(x);
        if (row == null)
            return null;
        return row.get(y);
    }

    public WorldCell getCell(int id) {
        return cells.get(id);
    }

    @Override
    public String toString() {
        return name;
    }

    public int cellsCount() {
        final int[] i = {0};
        synchronized (map) {
            map.forEach((x, row) ->
                    i[0] += row.size()
            );
        }
        return i[0];
    }

    public void save(DataOutputStream dos) throws IOException {
        dos.writeInt(cellsCount());

        synchronized (map) {
            map.forEach((x, row) ->
                    row.forEach((y, cell) -> {
                        try {
                            cell.write(dos);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }));
        }
    }

    public void load(DataInputStream dis) throws IOException {
        int count = dis.readInt();

        synchronized (map) {
            for (int i = 0; i < count; i++) {
                WorldCell cell = WorldCell.read(this, dis);
                addCell(cell.getX(), cell.getY(), cell);
            }
        }
    }

    public String getName() {
        return name;
    }

    public HashMap<Integer, HashMap<Integer, WorldCell>> getMap() {
        return map;
    }

    public HashMap<String, Player> getPlayers() {
        return players;
    }

    public HashMap<Integer, WorldCell> getCells() {
        return cells;
    }
}
