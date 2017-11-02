package ru.iammaxim.InDaCellsServer.Creatures;

import ru.iammaxim.InDaCellsServer.Dialogs.DialogTopic;
import ru.iammaxim.InDaCellsServer.Quests.Quest;
import ru.iammaxim.InDaCellsServer.World.World;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class NPC extends Human {
    private ArrayList<Quest> attachedQuests = new ArrayList<>();
    private ArrayList<DialogTopic> topics = new ArrayList<>();

    public NPC(World world, String name) {
        super(world, name);
    }

    public NPC addTopic(DialogTopic topic) {
        topics.add(topic);
        return this;
    }

    public void say(Player p, String topic) {
        String text;
        if (topic.equals(""))
            text = "Здравствуй, путник!";
        else
            text = getAnswerForTopic(p, topic);

        p.addMessage(name, text);
        sendQuestsToClient(p);
        sendTopicsToClient(p);

        for (DialogTopic dialogTopic : getTopics(p)) {
            if (dialogTopic.getName().equals(topic)) {
                dialogTopic.onSay(p);
                break;
            }
        }
    }

    public NPC attachQuest(Quest q) {
        this.attachedQuests.add(q);
        return this;
    }

    public ArrayList<DialogTopic> getTopics(Player p) {
        ArrayList<DialogTopic> topics1 = new ArrayList<>();
        p.getQuests().forEach(q -> topics1.addAll(q.getTopics(this)));
        topics1.addAll(topics);
        return topics1;
    }

    private void sendTopicsToClient(Player p) {
        ArrayList<DialogTopic> topics1 = getTopics(p);
        if (topics1.isEmpty())
            return;
        p.addMessage(name, "Темы:\n" + String.join("\n", topics1.stream().map(DialogTopic::getName).collect(Collectors.toList())));
    }

    public void sendQuestsToClient(Player p) {
        if (attachedQuests.isEmpty())
            return;
        p.addMessage(name, "Квесты:\n" + String.join("\n", attachedQuests.stream().map(Quest::getTitle).collect(Collectors.toList())));
    }

    public String getAnswerForTopic(Player p, String topic) {
        String answer = null;
        for (DialogTopic dialogTopic : getTopics(p)) {
            if (dialogTopic.getName().equals(topic)) {
                answer = dialogTopic.getText();
                break;
            }
        }
        if (answer != null)
            return answer;
        return "Извини, я не понимаю, о чем ты говоришь.";
    }
}
