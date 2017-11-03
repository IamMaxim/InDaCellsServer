package ru.iammaxim.InDaCellsClient.GUI;

import ru.iammaxim.InDaCellsServer.MenuElement;
import ru.iammaxim.InDaCellsServer.Packets.PacketMove;
import ru.iammaxim.InDaCellsServer.Packets.PacketSendMessage;
import ru.iammaxim.NetLib.NetLib;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class MainForm {
    private JPanel panel1;
    private JButton button_up;
    private JButton button_down;
    private JButton button_right;
    private JButton button_left;
    private JTextArea logTextArea;
    private JTextField chatTextField;
    private JButton sendButton;
    private JList actions_list;
    private JLabel cell_name;
    private JLabel cell_coords;
    private JButton button1;
    private JButton button2;

    private JPanel mainPanel;
    private ArrayList<MenuElement> elements = new ArrayList<>();
    private boolean isMainPanelHidden = false;

    private void showPanel(JComponent component) {
        if (isMainPanelHidden)
            throw new IllegalStateException("Main panel already hidden");


    }

    public MainForm() {
        mainPanel = (JPanel) panel1.getComponent(0);

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        button_up.addActionListener(e -> {
            NetLib.sendToServer(new PacketMove(PacketMove.Direction.UP));
        });

        button_left.addActionListener(e -> {
            NetLib.sendToServer(new PacketMove(PacketMove.Direction.LEFT));
        });

        button_right.addActionListener(e -> {
            NetLib.sendToServer(new PacketMove(PacketMove.Direction.RIGHT));
        });

        button_down.addActionListener(e -> {
            NetLib.sendToServer(new PacketMove(PacketMove.Direction.DOWN));
        });

        sendButton.addActionListener(e -> {
            String msg = chatTextField.getText();
            if (msg.isEmpty()) {
                chatTextField.setText("");
                NetLib.sendToServer(new PacketSendMessage(msg));
            }
        });


    }

    private JComponent createElementFor(MenuElement element) {
        if (element.type == MenuElement.Type.HEADER) {
            JLabel label = new JLabel(">>> " + element.text + " <<<");
            label.setHorizontalAlignment(SwingConstants.CENTER);
            return label;
        }
        JButton button = new JButton(element.text);
        switch (element.type) {
            case PLAYER:
                button.addActionListener(e -> {

                });
                break;
            case ITEM:
                break;
            case NPC:
                break;
            case CREATURE:
                break;
            case ACTIVATOR:
                break;
            case HEADER:
                break;
        }
        return button;
    }

    private void processElements() {
        actions_list.removeAll();
        for (MenuElement element : elements) {
            actions_list.add(createElementFor(element));
        }
    }
}
