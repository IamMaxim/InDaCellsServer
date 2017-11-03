package ru.iammaxim.InDaCellsClient;

import ru.iammaxim.InDaCellsServer.LogElement;
import ru.iammaxim.InDaCellsServer.MenuElement;
import ru.iammaxim.InDaCellsServer.NetBus.NetBus;
import ru.iammaxim.InDaCellsServer.Packets.*;
import ru.iammaxim.NetLib.NetLib;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Scanner;

public class Client {
    private ArrayList<MenuElement> currentCell = new ArrayList<>();
    private ArrayList<MenuElement> activableElements = new ArrayList<>();
    private int x, y;
    private String cellName;

    public void run() {
        loop();
    }

    private void loop() {
        Scanner scanner = new Scanner(System.in);
        boolean needToRun = true;
        while (needToRun) {
            System.out.printf("> ");
            String s = scanner.nextLine();
            String[] tokens = s.split(" ");

            switch (tokens[0]) {
                case "exit":
                case "quit":
                    needToRun = false;
                    break;
                case "move":
                    if (tokens.length < 2) {
                        System.out.println("Too few args");
                        continue;
                    }

                    PacketMove.Direction dir = null;

                    switch (tokens[1]) {
                        case "left":
                            dir = PacketMove.Direction.LEFT;
                            break;
                        case "right":
                            dir = PacketMove.Direction.RIGHT;
                            break;
                        case "up":
                            dir = PacketMove.Direction.UP;
                            break;
                        case "down":
                            dir = PacketMove.Direction.DOWN;
                            break;
                    }

                    if (dir == null) {
                        System.out.println("Invalid direction");
                        continue;
                    }

                    NetLib.sendToServer(new PacketMove(dir));
                    break;
                case "activate":
                    if (tokens.length < 2) {
                        System.out.println("Invalid index");
                        continue;
                    }

                    try {
                        int index = Integer.parseInt(tokens[1]) - 1;
                        if (index < 0 || index >= activableElements.size()) {
                            System.out.println("No element with such index");
                            continue;
                        }

                        MenuElement element = activableElements.get(index);
                        switch (element.type) {
                            case CREATURE:
                            case PLAYER:
                                System.out.println("To attack " + element.text + " type \"attack " + (index + 1) + " <type>\"");
                                break;
                            case ITEM:
                                NetLib.sendToServer(new PacketDoAction(PacketDoAction.Type.PICKUP_ITEM, element.targetID));
                                break;
                            case NPC:
                                System.out.println("To attack " + element.text + " type \"attack " + (index + 1) + " <type>\"");
                                System.out.println("To talk to " + element.text + " type \"talk " + (index + 1) + "\"");
                                break;
                            case ACTIVATOR:
                                NetLib.sendToServer(new PacketDoAction(PacketDoAction.Type.ACTIVATE, element.targetID));
                                break;
                            case HEADER:
                                System.out.println("LOL, u r trying to activate a header?!? Seriously?");
                                break;
                            case ACTION:
                                System.out.println("To do action, type \"doAction " + (index + 1) + "\"");
                                break;
                            case LOCATION:
                                System.out.println("To go to location, type \"goto " + (index + 1) + "\"");
                                break;
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid index");
                    }
                    break;
                case "attack":
                    if (tokens.length < 3) {
                        System.out.println("Invalid arg count");
                        continue;
                    }

                    try {
                        int index = Integer.parseInt(tokens[1]) - 1;
                        if (index < 0 || index >= activableElements.size()) {
                            System.out.println("No element with such index");
                            continue;
                        }

                        int attackType = Integer.parseInt(tokens[2]);
                        if (attackType < 0 || attackType > 3) {
                            System.out.println("Invalid attack type. Allowed are 0-3");
                            continue;
                        }

                        MenuElement element = activableElements.get(index);

                        NetLib.sendToServer(new PacketDoAction(PacketDoAction.Type.ATTACK, element.targetID).setAdditionalInt(attackType));
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid number");
                    }
                    break;
                case "talk":
                    if (tokens.length < 2) {
                        System.out.println("Invalid index");
                        continue;
                    }

                    String topic = "";

                    if (tokens.length > 2) {
                        StringBuilder sb = new StringBuilder();
                        for (int i = 2; i < tokens.length; i++) {
                            sb.append(tokens[i]);
                            if (i < tokens.length - 1)
                                sb.append(" ");
                        }
                        topic = sb.toString();
                    }

                    try {
                        int index = Integer.parseInt(tokens[1]) - 1;
                        MenuElement element = activableElements.get(index);

                        System.out.println("Topic: " + topic);
                        NetLib.sendToServer(new PacketDoAction(PacketDoAction.Type.TALK, element.targetID).setAdditionalString(topic));
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid index");
                    }
                    break;
                case "cell":
                    printCurrentCell();
                    break;
                case "quests":
                    NetLib.sendToServer(new PacketRequestQuestList());
                    break;
                case "doAction":
                    if (tokens.length < 2) {
                        System.out.println("Invalid action");
                        continue;
                    }

                    String action = "";

                    if (tokens.length > 2) {
                        StringBuilder sb = new StringBuilder();
                        for (int i = 2; i < tokens.length; i++) {
                            sb.append(tokens[i]);
                            if (i < tokens.length - 1)
                                sb.append(" ");
                        }
                        action = sb.toString();
                    }

                    NetLib.sendToServer(new PacketDoAction(PacketDoAction.Type.DO_ACTION, 0).setAdditionalString(action));
                    break;
                case "goto":
                    if (tokens.length < 2) {
                        System.out.println("Invalid index");
                        continue;
                    }


                    int index = Integer.parseInt(tokens[1]) - 1;
                    if (index < 0 || index >= activableElements.size()) {
                        System.out.println("No element with such index");
                        continue;
                    }

                    MenuElement element = activableElements.get(index);

                    if (element.type != MenuElement.Type.LOCATION) {
                        System.out.println("Element selected is not entrance");
                        break;
                    }

                    NetLib.sendToServer(new PacketDoAction(PacketDoAction.Type.GO_TO, element.targetID));

                    break;
            }
        }
    }

    public void registerHandlers() {
        NetBus.register(PacketCell.class, (c, p) -> {
            currentCell.clear();
            activableElements.clear();
            PacketCell packet = (PacketCell) p;
            x = packet.x;
            y = packet.y;
            cellName = packet.name;
            currentCell.addAll(packet.elements);
            activableElements.addAll(packet.elements);
            activableElements.removeIf(e -> e.type == MenuElement.Type.HEADER);
            printCurrentCell();
        });

        NetBus.register(PacketInventory.class, (c, p) -> {

        });

        NetBus.register(PacketInventoryChange.class, (c, p) -> {

        });

        NetBus.register(PacketStartAction.class, (c, p) -> {
            PacketStartAction packet = (PacketStartAction) p;
            System.out.println("Doing action: " + packet.name + " for " + packet.durationSeconds);
        });

        NetBus.register(PacketStats.class, (c, p) -> {
            boolean printStats = false;

            if (printStats) {
                PacketStats packet = (PacketStats) p;
                DecimalFormat df = new DecimalFormat("#.#");
                System.out.println("Stats:" +
                        " ❤️: " + df.format(packet.hp) + "/" + df.format(packet.maxHP) +
                        "; ⚡: " + df.format(packet.sp) + "/" + df.format(packet.maxSP) +
                        "; \uD83C\uDF57: " + df.format(packet.hunger) + "/" + df.format(packet.maxHunger));
            }
        });

        NetBus.register(PacketUnblockInput.class, (c, p) -> {

        });

        NetBus.register(PacketAddToLog.class, (c, p) -> {
            PacketAddToLog packet = (PacketAddToLog) p;
            if (packet.element.type == LogElement.Type.INFO) {
                System.out.println("Info: " + packet.element.message);
            } else if (packet.element.type == LogElement.Type.MESSAGE)
                System.out.println(packet.element.nick + ": " + packet.element.message);
        });

        NetBus.register(PacketQuestList.class, (c, p) -> {
            System.out.println("Quests:");
            ((PacketQuestList) p).questNames.forEach(System.out::println);
        });

        NetBus.register(PacketDialogTopics.class, (c, p) -> {
            PacketDialogTopics packet = (PacketDialogTopics) p;
            System.out.println("Available topics:");
            packet.topics.forEach(System.out::println);
        });
    }

    private void printCurrentCell() {
        System.out.println("Cell \"" + cellName + "\" [" + x + ", " + y + "]");
        currentCell.forEach(e -> {
            if (e.type == MenuElement.Type.HEADER)
                System.out.println(" <<< " + e.text + " >>> ");
            else
                System.out.println((activableElements.indexOf(e) + 1) + ". " + e.text);
        });
    }
}
