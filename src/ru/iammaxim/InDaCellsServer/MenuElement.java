package ru.iammaxim.InDaCellsServer;

public class MenuElement {
    public enum Type {
        PLAYER,
        ITEM,
        HUMAN,
        CREATURE,
        ACTIVATOR
    }

    public Type type;
    public String text;
}
