package ru.iammaxim.InDaCellsServer.Dialogs;

import ru.iammaxim.InDaCellsServer.Creatures.Player;

public abstract class DialogEntry {
    public abstract boolean needToShow(Player p);

    public abstract void onClick(Player p);
}
