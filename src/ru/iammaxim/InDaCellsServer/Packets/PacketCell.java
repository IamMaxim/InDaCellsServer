package ru.iammaxim.InDaCellsServer.Packets;

import ru.iammaxim.InDaCellsServer.Creatures.NPC;
import ru.iammaxim.InDaCellsServer.Creatures.Player;
import ru.iammaxim.InDaCellsServer.MenuElement;
import ru.iammaxim.InDaCellsServer.World.WorldCell;
import ru.iammaxim.NetLib.Packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class PacketCell extends Packet {
    //    public WorldCell cell;
    public int x, y;
    public ArrayList<MenuElement> elements = new ArrayList<>();


    public PacketCell() {
    }

//    public PacketCell(WorldCell cell) {
//        this.cell = cell;
//    }

    public PacketCell(WorldCell cell) {
        this.x = cell.getX();
        this.y = cell.getY();

        elements.add(new MenuElement(MenuElement.Type.HEADER, "Игроки", 0));

        cell.getCreatures().forEach(c -> {
            if (c instanceof Player)
                elements.add(new MenuElement(MenuElement.Type.PLAYER, c.getName(), c.getID()));
        });

        elements.add(new MenuElement(MenuElement.Type.HEADER, "NPC", 0));

        cell.getCreatures().forEach(c -> {
            if (c instanceof NPC)
                elements.add(new MenuElement(MenuElement.Type.NPC, c.getName(), c.getID()));
        });

        elements.add(new MenuElement(MenuElement.Type.HEADER, "Существа", 0));

        cell.getCreatures().forEach(c -> {
            if (!(c instanceof Player) && !(c instanceof NPC))
                elements.add(new MenuElement(MenuElement.Type.CREATURE, c.getName(), c.getID()));
        });

        elements.add(new MenuElement(MenuElement.Type.HEADER, "Предметы", 0));

        cell.getItems().forEach(i -> {
            elements.add(new MenuElement(MenuElement.Type.ITEM, i.getName(), i.getID()));
        });

        elements.add(new MenuElement(MenuElement.Type.HEADER, "Активаторы", 0));

        cell.getActivators().forEach(a -> {
            elements.add(new MenuElement(MenuElement.Type.ACTIVATOR, a.getName(), a.getID()));
        });
    }

    @Override
    public void write(DataOutputStream dos) throws IOException {
        dos.writeInt(elements.size());
        elements.forEach(e -> {
            try {
                e.write(dos);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });
    }

    @Override
    public void read(DataInputStream dis) throws IOException {
        int count = dis.readInt();
        for (int i = 0; i < count; i++) {
            elements.add(MenuElement.read(dis));
        }
    }
}
