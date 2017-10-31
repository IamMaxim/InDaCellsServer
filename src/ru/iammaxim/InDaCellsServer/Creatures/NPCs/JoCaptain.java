package ru.iammaxim.InDaCellsServer.Creatures.NPCs;

import ru.iammaxim.InDaCellsServer.Creatures.NPC;
import ru.iammaxim.InDaCellsServer.World.World;

public class JoCaptain extends NPC {
    private static final String name = "Джо, Капитан.";

    public JoCaptain(World world) {
        super(world, name);
    }
}
