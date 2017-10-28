package ru.iammaxim.InDaCellsServer.Items;

public class ItemWeapon extends Item {
    private float damage;
    //private HashMap<AdditionalStats, Float> stats = new HashMap<>();

    public ItemWeapon(int id, String name, float damage, float weight) {
        super(id, Type.ARMOR, name, weight);
        this.damage = damage;
    }

    /*public ItemWeapon addStat(AdditionalStats stat, float value){
        stats.put(AdditionalStats.ACCURACY, value);

        return this;
    }*/

    public float getDamage() {
        return damage;
    }

    /*public enum AdditionalStats{
        ACCURACY
    }*/
}
