package ru.iammaxim.NetLib;

public interface OnPacketReceive {
    void onPacketReceive(Client c, Packet p);
}
