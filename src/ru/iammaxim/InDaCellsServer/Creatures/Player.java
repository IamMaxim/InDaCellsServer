package ru.iammaxim.InDaCellsServer.Creatures;

import ru.iammaxim.InDaCellsServer.Abilities.BaseAbility;

import java.util.ArrayList;

public class Player extends BaseCreature {
    private Integer stamina;
    private ArrayList<BaseAbility> abilities;

    private Integer strength = 0;
    private Float maximumWeight = 65f;
    private Float weight = 0f;

    private Integer agility = 0;
    private Float accuracy = 60f; //%
    private Float armor = 1f;
    private Float turns = 1f; //?? Оставим до боёвки

    private Integer intelligence = 0;
    private Integer trading = 55; //%
    private Integer recipesSlots = 4;
    private ArrayList<BaseRecipe> knownRecipes;
    private Integer unusedStats = 6;

    public void addStrength(int howMany){
        this.maximumWeight += 5 * howMany;
        this.strength += howMany;
    }

    public void addAgility(int howMany){
        this.armor += 0.3 * howMany;
        this.accuracy += 2 * howMany;
        this.turns += 0.2 * howMany;
    }

    public void addIntelligence(int howMany){
        this.trading += 3 * howMany;
        this.recipesSlots += howMany;
    }

    //Useless getters below
    public Integer getStamina() {
        return stamina;
    }

    public Integer getStrength() {
        return strength;
    }

    public Integer getAgility() {
        return agility;
    }

    public Integer getIntelligence() {
        return intelligence;
    }

    public ArrayList<BaseAbility> getAbilities() {
        return abilities;
    }

    public Float getMaximumWeight() {
        return maximumWeight;
    }

    public Float getWeight() {
        return weight;
    }

    public Float getAccuracy() {
        return accuracy;
    }

    public Float getArmor() {
        return armor;
    }

    public Float getTurns() {
        return turns;
    }

    public int getTrading() {
        return trading;
    }
}
