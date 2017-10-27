package ru.iammaxim.InDaCellsServer.World;

import java.util.HashMap;

public class World {
    private HashMap<Integer, HashMap<Integer, Cell>> _WORLD = new HashMap<>();

    public void addCell(){

    }

    public Cell getCell(Integer x, Integer y){
        return _WORLD.get(y).get(x);
    }
}
