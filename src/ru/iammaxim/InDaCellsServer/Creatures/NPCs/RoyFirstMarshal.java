package ru.iammaxim.InDaCellsServer.Creatures.NPCs;

import ru.iammaxim.InDaCellsServer.Creatures.NPC;
import ru.iammaxim.InDaCellsServer.World.World;

public class RoyFirstMarshal extends NPC {
    private static final String name = "Рой Томпсон, Первый маршалл.";

    public RoyFirstMarshal(World world) {
        super(world, name);
    }
}