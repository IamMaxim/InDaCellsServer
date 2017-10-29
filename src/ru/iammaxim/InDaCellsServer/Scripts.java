package ru.iammaxim.InDaCellsServer;

public class Scripts {
    public static OnActivate getActivatorScript(int id) {
        switch (id) {
            case 0: // 0,0 Push me
                return h -> {
                    h.damage(5);
                };
        }

        return null;
    }
}
