package ru.iammaxim.InDaCellsServer.Creatures.NPCs;

import ru.iammaxim.InDaCellsServer.Creatures.NPC;
import ru.iammaxim.InDaCellsServer.World.World;

public class GeorgRaidersLeader extends NPC{
    private static final String name = "Георг, Капитан рейдеров";

    public GeorgRaidersLeader(World world) {
        super(world, name);
    }
}
