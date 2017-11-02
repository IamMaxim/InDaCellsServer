package ru.iammaxim.InDaCellsServer.Dialogs;

import ru.iammaxim.InDaCellsServer.Creatures.NPC;
import ru.iammaxim.InDaCellsServer.Creatures.Player;

import java.util.ArrayList;

public class DialogTopic {
    public final String name, text;
    public final ArrayList<String> npcsAttachedTo = new ArrayList<>();

    public DialogTopic(String name, String text) {
        this.name = name;
        this.text = text;
    }

    public DialogTopic attachToNPC(String name) {
        npcsAttachedTo.add(name);
        return this;
    }

    public boolean needToShow(Player p, NPC npc) {
        boolean needToShow = false;
        for (String s : npcsAttachedTo) {
            if (s.equals(npc.getName())) {
                needToShow = true;
                break;
            }
        }
        return needToShow;
    }

    public void onSay(Player p) {

    }

    public String getName() {
        return name;
    }

    public String getText() {
        return text;
    }
}
