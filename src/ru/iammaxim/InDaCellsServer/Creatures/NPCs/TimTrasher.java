package ru.iammaxim.InDaCellsServer.Creatures.NPCs;

import ru.iammaxim.InDaCellsServer.Creatures.NPC;
import ru.iammaxim.InDaCellsServer.World.World;

public class TimTrasher extends NPC{
    private static final String name = "Тим, Мусорщик";

    public TimTrasher(World world) {
        super(world, name);
    }
}
