package logic;
import vista.*;
import java.util.Random;
public class Character implements Purchasable {
    private String name;
    private double actuHp;
    private double maxiHp;
    private double maxMana;
    private double actuMana;
    private double hpRegenerate;
    private double manaRegenerate;
    private double basePw;
    private double baseMagicPw;
    private double baseDefense;
    private double baseMagicDefense;
    private double criticChance;
    private double criticDamage;
    private double dodge;
    private double speed;
    private Ability ability;
    private Equipment equip;
    private boolean isEnemy;
    //---//
    private int price;
    private Character(Builder builder){
        this.name = builder.name;
        this.maxiHp = builder.maxiHp;
        this.actuHp = builder.maxiHp;
        this.actuMana = builder.maxMana;
        this.maxMana = builder.maxMana;
        this.hpRegenerate = builder.hpRegenerate;
        this.manaRegenerate = builder.manaRegenerate;
        this.basePw = builder.basePw;
        this.baseMagicPw = builder.baseMagicPw;
        this.baseDefense = builder.baseDefense;
        this.baseMagicDefense = builder.baseMagicDefense;
        this.criticChance = builder.criticChance;
        this.criticDamage = builder.criticDamage;
        this.dodge = builder.dodge;
        this.speed = builder.speed;
        this.equip = builder.equip;
        this.isEnemy = builder.isEnemy;
        this.price = builder.price;
        this.ability = builder.ability;
    }
    //---//
    public static class Builder{
        private String name;
        private double maxiHp = 500;
        private int maxMana = 0;
        private double hpRegenerate = 10;
        private double manaRegenerate = 0;
        private double basePw = 50;
        private double baseMagicPw = 10;
        private double baseDefense = 25;
        private double baseMagicDefense = 20;
        private double criticChance = 0.1;
        private double criticDamage = 2.0;
        private double dodge = 0.1;
        private double speed = 10;
        private Equipment equip = null;
        private Ability ability = null;
        private boolean isEnemy = false;
        private int price = 0;

        public Builder(String name){
            this.name = name;
        }
        public Builder name(String name) { this.name = name; return this; }
        public Builder maxiHp(double maxiHp) { this.maxiHp = maxiHp; return this; }
        public Builder maxMana(int maxMana){ this.maxMana = maxMana; return this; }
        public Builder hpRegenerate(double hpRegenerate) { this.hpRegenerate = hpRegenerate; return this; }
        public Builder manaRegenerate(double manaRegenerate) { this.manaRegenerate = manaRegenerate; return this; }
        public Builder basePw(double basePw) { this.basePw = basePw; return this; }
        public Builder baseMagicPw(double baseMagicPw) { this.baseMagicPw = baseMagicPw; return this; }
        public Builder baseDefense(double baseDefense) { this.baseDefense = baseDefense; return this; }
        public Builder baseMagicDefense(double baseMagicDefense) { this.baseMagicDefense = baseMagicDefense; return this; }
        public Builder criticChance(double criticChance) { this.criticChance = criticChance; return this; }
        public Builder criticDamage(double criticDamage){ this.criticDamage = criticDamage; return this; }
        public Builder dodge(double dodge) { this.dodge = dodge; return this; }
        public Builder speed(double speed) { this.speed = speed; return this; }
        public Builder equip(Equipment equip) { this.equip = equip; return this; }
        public Builder isEnemy(boolean isEnemy) { this.isEnemy = isEnemy; return this; }
        public Builder price(int price) { this.price = price; return this; }
        public Builder ability(Ability ability) { this.ability = ability; return this; }

        public Character build() {
            return new Character(this);
        }

    }

    public boolean isAlive(){
        return getActuHp()>0;
    }

    public void reciDmg(double dmg){
        this.actuHp -= dmg;
        if(!isAlive()) actuHp = 0;
    }
    public void applyRegeneration() {
        if (isAlive() && hpRegenerate > 0) {
            actuHp += hpRegenerate;
            if (actuHp > maxiHp) {
                actuHp = maxiHp;
            }
        }
    }
    @Override
    public int getPrice(){
        return price;
    }

    public Character clon() {
        return new Builder(this.name)
                .maxiHp(this.maxiHp).maxMana((int) this.maxMana).hpRegenerate(this.hpRegenerate)
                .manaRegenerate(this.manaRegenerate).basePw(this.basePw).baseMagicPw(this.baseMagicPw)
                .baseDefense(this.baseDefense).baseMagicDefense(this.baseMagicDefense)
                .criticChance(this.criticChance).criticDamage(this.criticDamage)
                .dodge(this.dodge).speed(this.speed).equip(this.equip)
                .ability(this.ability != null ? this.ability.copy() : null)
                .isEnemy(this.isEnemy).price(this.price).build();
    }

    public double getActuHp() {
        return actuHp;
    }

    public String getName() {
        return name;
    }

    public double getBaseMagicPw() {
        return baseMagicPw;
    }

    public double getMaxMana() {
        return maxMana;
    }

    public double getActuMana() {
        return actuMana;
    }

    public double getHpRegenerate() {
        return hpRegenerate;
    }

    public double getManaRegenerate() {
        return manaRegenerate;
    }

    public double getMaxiHp() {
        return maxiHp;
    }

    public double getBasePw() {
        return basePw;
    }

    public double getBaseDefense() {
        return baseDefense;
    }

    public double getCriticDamage() {
        return criticDamage;
    }

    public double getBaseMagicDefense() {
        return baseMagicDefense;
    }

    public double getCriticChance() {
        return criticChance;
    }

    public double getDodge() {
        return dodge;
    }

    public Equipment getEquip() {
        return equip;
    }

    public double getSpeed() {
        return speed;
    }

    public boolean isEnemy() {
        return isEnemy;
    }

    public Ability getAbility() {
        return ability;
    }
}
