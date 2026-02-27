package logic;
import vista.*;
import java.util.Random;
interface Ability {
    DamageReport execute(Character attacker, Character target);
    Ability copy();
}

public class Character implements Purchasable {
    private String name;
    private double actuHp;
    private double maxiHp;
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
        public Builder maxMana(int maxMana){ this.maxiHp = maxMana; return this; }
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

    @Override
    public int getPrice(){
        return price;
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

class RageAbility implements Ability{
    private static final double PHYS_BONUS_MULT = 1.015;
    private static final double MAGIC_BONUS_MULT = 1.030;
    private static final int RAGE_GAIN = 15;
    private static final int RAGE_TOTAL = 50;
    private int rage = 0;
    private boolean rageHit = false;
    public RageAbility(int rage, boolean rageHit) {
        this.rage = rage;
        this.rageHit = rageHit;
    }
    @Override
    public DamageReport execute(Character self, Character enemy) {
        double baseDmg = self.getBasePw();
        double baseMagicDmg = self.getBaseMagicPw();
        double enemDefense = enemy.getBaseDefense();
        double enemMagicDefense = enemy.getBaseMagicDefense();
        double bonusDmg = self.getEquip().getBonusDmg();
        double criticChance = self.getCriticChance();
        double criticDamage = self.getCriticDamage();
        double dodgeChance = enemy.getDodge();
        double parcialPhysicDmg = (baseDmg==0? 0 : (baseDmg + (bonusDmg)*PHYS_BONUS_MULT));
        double parcialMagicDmg = (baseMagicDmg==0? 0 : (baseMagicDmg + (bonusDmg)*MAGIC_BONUS_MULT));
        double totalPhysicDmg = parcialPhysicDmg*(100.0/(100.0+enemDefense));
        double totalMagicDmg = parcialMagicDmg*(100.0/(100.0+enemMagicDefense));
        double sum = totalPhysicDmg + totalMagicDmg;
        double chanceForCritic = Math.random();
        double chanceForDodge = Math.random();
        boolean isCritic = (chanceForCritic<criticChance);
        boolean isDodged = (chanceForDodge<dodgeChance);
        double totalDmg = (isCritic?  (sum*criticDamage) : sum);
        boolean isKill = false;
        if(isDodged) {
            sum = 0;
            totalMagicDmg = 0;
            totalDmg = 0;
            totalPhysicDmg = 0;
        }
        else {
            rage += RAGE_GAIN;
            if (rage >= RAGE_TOTAL) {
                rage -= RAGE_TOTAL;
                rageHit = true;
            }
            if (rageHit) {
                totalDmg *= 2;
                rageHit = false;
            }
            enemy.reciDmg(totalDmg);
            if (!enemy.isAlive()) {
                isKill = true;
            }
        }
        return new DamageReport.Builder(self.getName(),enemy.getName()).totalPhysicDmg(totalPhysicDmg)
                .totalMagicDmg(totalMagicDmg).totalDmg(totalDmg).isDodged(isDodged).isCritic(isCritic).isKill(isKill).build();
    }

    @Override
    public RageAbility copy() {
        return new RageAbility(rage, rageHit);
    }

    public int getRage() {
        return rage;
    }
    public void setRage(int rage) {
        this.rage = rage;
    }

}
