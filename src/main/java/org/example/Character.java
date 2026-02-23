package org.example;
import java.util.Random;

interface Purchasable {
    int getPrice();
}
public abstract class Character implements Purchasable {
    private String name;
    private double actuHp;
    private double maxiHp;
    private double basePw;
    private double baseMagicPw;
    private double baseDefense;
    private double baseMagicDefense;
    private double criticChance;
    private double dodge;
    private int price;
    private Equipment equip;
    private double speed;

    private boolean isEnemy;
    public Character(){}
    public Character(String name, double maxiHp, double basePw, double baseMagicPw, double baseDefense, double baseMagicDefense, double criticChance, double speed, double dodge, int price, Equipment equip){
        this.name = name;
        this.actuHp = maxiHp;
        this.maxiHp = maxiHp;
        this.basePw = basePw;
        this.baseMagicPw = baseMagicPw;
        this.baseDefense = baseDefense;
        this.baseMagicDefense = baseMagicDefense;
        this.criticChance = criticChance;
        this.speed = speed;
        this.dodge = dodge;
        this.price = price;
        this.equip = equip;
        this.isEnemy = false;
    }
    public boolean isAlive(){
        return getActuHp()>0;
    }

    public abstract void attack(Character enem);

    public void reciDmg(double dmg){
        this.actuHp -= dmg;
        if(!isAlive()) actuHp = 0;
    }
    @Override
    public int getPrice(){
        return price;
    }

    public abstract Character clon();

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
}
class RageWarrior extends Character{
    private int rage;
    private boolean bonusHit = false;
    //base datas for random
    private static final double randHp = 500;
    private static final double randBasePw = 100;
    private static final double randMagicPw = 0;
    private static final double randBaseDefence = 150;
    private static final double randMagicDefense = 125;
    private static final double randCriticChance = 0.025;
    private static final double randSpeed = 12;
    private static final double randDodge = 0.05;
    private static final int randPrice = 300;
    public RageWarrior(){}
    public RageWarrior(String name, Equipment equip){
        Random rand = new Random();
        double hpVariance = (double) (randHp * 0.15);
        double maxiHp = randHp + (rand.nextDouble((hpVariance * 2) + 1) - hpVariance);

        double pwVariance = (double) (randBasePw * 0.15);
        double basePw = randBasePw + (rand.nextDouble((pwVariance * 2) + 1) - pwVariance);

        double magicPwVariance = (double) (randMagicPw * 0.15);
        double baseMagicPw = randMagicPw + (rand.nextDouble((magicPwVariance * 2) + 1) - magicPwVariance);

        double defenseVariance = (double) (randBaseDefence * 0.15);
        double baseDefense = randBaseDefence + (rand.nextDouble((defenseVariance * 2) + 1) - defenseVariance);

        double magicDefenseVariance = (double) (randMagicDefense * 0.15);
        double baseMagicDefense = randMagicDefense + (rand.nextDouble((magicDefenseVariance * 2) + 1) - magicDefenseVariance);

        double criticVariance = (double) (randCriticChance * 0.15);
        double criticChance = randCriticChance + (rand.nextDouble((criticVariance * 2) + 1) - criticVariance);

        double speedVariance = (double) (randSpeed * 0.15);
        double speed = randSpeed + (rand.nextDouble((speedVariance * 2) + 1) - speedVariance);

        double dodgeVariance = (double) (randDodge * 0.20);
        double dodge = randDodge + (rand.nextDouble((speedVariance * 2) + 1) - speedVariance);

        int priceVariance = (int) (randPrice * 0.15);
        int price = randPrice + (rand.nextInt((priceVariance * 2) + 1) - priceVariance);

        super(name, maxiHp, basePw, baseMagicPw, baseDefense, baseMagicDefense, criticChance, speed, dodge, price, equip);
        this.rage = 0;
    }

    public RageWarrior(String name, double maxiHp, double basePw, double baseMagicPw, double baseDefense, double baseMagicDefense, double criticChance, double speed, double dodge, int price, Equipment equip) {
        super(name, maxiHp, basePw, baseMagicPw, baseDefense, baseMagicDefense, criticChance, speed, dodge, price, equip);
        this.rage = 0;
    }

    public int getRage() {
        return rage;
    }

    public void setRage(int rage) {
        this.rage = rage;
    }

    @Override
    public Character clon(){
        Equipment equipmentCloned = null;
        if (this.getEquip() != null){
            equipmentCloned = this.getEquip().clon();
        }
        return new RageWarrior(
                this.getName(), this.getMaxiHp(), this.getBasePw(),
                this.getBaseMagicPw(), this.getBaseDefense(), this.getBaseMagicDefense(),
                this.getCriticChance(), this.getSpeed(),
                this.getDodge(), this.getPrice(), equipmentCloned);
    }

    @Override
    public void attack(Character enem) {
        double bonusDmg = getEquip().getBonusDmg();
        System.out.println("BASE POWER = " + getBasePw());
        System.out.println("BONUS DMG = " + bonusDmg);
        System.out.println("ACTUAL RAGE = " + getRage());
        double totalDmg = getBasePw() + bonusDmg;
        System.out.println("TOTAL DMG = " + totalDmg);
        if(bonusHit){
            System.out.println("RAGE HIT! ");
            totalDmg*=2;
            bonusHit = !bonusHit;
        }
        System.out.println("ENEM. ACTUAL HP: " + enem.getActuHp());
        enem.reciDmg(totalDmg);
        if(!enem.isAlive()){
            System.out.println("ENEM. DIED. ");
        } else{
            System.out.println("ENEM. ACTUAL HP AFTER DMG = " + enem.getActuHp());
        }
        rage += 10;
        if(rage>=50){
            rage = 0;
            bonusHit = true;
        }
    }


}
class MageWarrior extends Character{
    private int actuMana;
    private static final double randHp = 500;
    private static final double randBasePw = 100;
    private static final double randMagicPw = 0;
    private static final double randBaseDefence = 150;
    private static final double randMagicDefense = 125;
    private static final double randCriticChance = 0.025;
    private static final double randSpeed = 12;
    private static final double randDodge = 0.05;
    private static final int randPrice = 300;

    public MageWarrior(){}
    public MageWarrior(String name, Equipment equip){
        Random rand = new Random();
        double hpVariance = (double) (randHp * 0.15);
        double maxiHp = randHp + (rand.nextDouble((hpVariance * 2) + 1) - hpVariance);

        double pwVariance = (double) (randBasePw * 0.15);
        double basePw = randBasePw + (rand.nextDouble((pwVariance * 2) + 1) - pwVariance);

        double magicPwVariance = (double) (randMagicPw * 0.15);
        double baseMagicPw = randMagicPw + (rand.nextDouble((magicPwVariance * 2) + 1) - magicPwVariance);

        double defenseVariance = (double) (randBaseDefence * 0.15);
        double baseDefense = randBaseDefence + (rand.nextDouble((defenseVariance * 2) + 1) - defenseVariance);

        double magicDefenseVariance = (double) (randMagicDefense * 0.15);
        double baseMagicDefense = randMagicDefense + (rand.nextDouble((magicDefenseVariance * 2) + 1) - magicDefenseVariance);

        double criticVariance = (double) (randCriticChance * 0.15);
        double criticChance = randCriticChance + (rand.nextDouble((criticVariance * 2) + 1) - criticVariance);

        double speedVariance = (double) (randSpeed * 0.15);
        double speed = randSpeed + (rand.nextDouble((speedVariance * 2) + 1) - speedVariance);

        double dodgeVariance = (double) (randDodge * 0.20);
        double dodge = randDodge + (rand.nextDouble((speedVariance * 2) + 1) - speedVariance);

        int priceVariance = (int) (randPrice * 0.15);
        int price = randPrice + (rand.nextInt((priceVariance * 2) + 1) - priceVariance);

        super(name, maxiHp, basePw, baseMagicPw, baseDefense, baseMagicDefense, criticChance, speed, dodge, price, equip);
        this.actuMana = 100;
    }

    public MageWarrior(String name, double maxiHp, double basePw, double baseMagicPw, double baseDefense, double baseMagicDefense, double criticChance, double speed, double dodge, int price, Equipment equip) {
        super(name, maxiHp, basePw, baseMagicPw, baseDefense, baseMagicDefense, criticChance, speed, dodge, price, equip);
        this.actuMana = 100;
    }

    @Override
    public Character clon(){
        Equipment equipmentCloned = null;
        if (this.getEquip() != null){
            equipmentCloned = this.getEquip().clon();
        }
        return new MageWarrior(
                this.getName(), this.getMaxiHp(), this.getBasePw(),
                this.getBaseMagicPw(), this.getBaseDefense(), this.getBaseMagicDefense(),
                this.getCriticChance(), this.getSpeed(),
                this.getDodge(), this.getPrice(), equipmentCloned);
    }

    @Override
    public void attack(Character enem) {
        double bonusDmg = getEquip().getBonusDmg();
        System.out.println("BASE POWER = " + getBasePw());
        System.out.println("BONUS DMG = " + bonusDmg);
        System.out.println("ACTUAL MANA = " + getActuMana());
        double totalDmg = getBasePw() + bonusDmg;
        if(getActuMana()<0){
            System.out.println("ACTUAL MANA IS LOWER THAN 0, NO BONUS DMG DEALER.");
            totalDmg = getBasePw();
        } else{
            setActuMana(getActuMana()-10);
        }
        System.out.println("ENEM. ACTUAL HP: " + enem.getActuHp());
        enem.reciDmg(totalDmg);
        if(!enem.isAlive()){
            System.out.println("ENEM. DIED. ");
        } else{
            System.out.println("ENEM. ACTUAL HP AFTER DMG = " + enem.getActuHp());
        }
    }

    public int getActuMana() {
        return actuMana;
    }

    public void setActuMana(int actuMana) {
        this.actuMana = actuMana;
    }
}
