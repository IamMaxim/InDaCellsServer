package ru.iammaxim.InDaCellsServer.World;

import ru.iammaxim.InDaCellsServer.Creatures.Player;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;

public class World {
    private String name;

    // {x, {y, cell}}
    private final HashMap<Integer, HashMap<Integer, WorldCell>> cells = new HashMap<>();
    private HashMap<String, Player> players = new HashMap<>();

    public Player getPlayer(String name) {
        return players.get(name);
    }

    public void addPlayer(String name, Player p) {
        players.put(name, p);
    }

    public void addCell(int x, int y, WorldCell cell) {
        cell.setX(x);
        cell.setY(y);
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

    public int cellsCount() {
        final int[] i = {0};
        synchronized (cells) {
            cells.forEach((x, row) ->
                    i[0] += row.size()
            );
        }
        return i[0];
    }

    public void save(DataOutputStream dos) throws IOException {
        dos.writeInt(cellsCount());

        synchronized (cells) {
            cells.forEach((x, row) ->
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

        synchronized (cells) {
            for (int i = 0; i < count; i++) {
                WorldCell cell = WorldCell.read(this, dis);
                addCell(cell.getX(), cell.getY(), cell);
            }
        }
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
