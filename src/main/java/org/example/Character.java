package org.example;
import java.util.Random;

interface Purchasable {
    int getPrice();
}
public abstract class Character implements Purchasable {
    private String name;
    private int actuHp;
    private int maxiHp;
    private int basePw;
    private int baseMagicPw;
    private int baseDefense;
    private int baseMagicDefense;
    private double criticChance;
    private double dodge;
    private int price;
    private Equipment equip;

    private double speed;
    private boolean isEnemy;
    public Character(){}
    public Character(String name, int maxiHp, int basePw, Equipment equip, int price, double speed){
        this.name = name;
        this.actuHp = maxiHp;
        this.maxiHp = maxiHp;
        this.basePw = basePw;
        this.equip = equip;
        this.price = price;
        this.speed = speed;
        this.isEnemy = false;
    }

    public double getSpeed() {
        return speed;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getBasePw() {
        return basePw;
    }
    public void setBasePw(int basePw) {
        this.basePw = basePw;
    }
    public int getMaxiHp() {
        return maxiHp;
    }
    public void setMaxiHp(int maxiHp) {
        this.maxiHp = maxiHp;
    }
    public int getActuHp() {
        return actuHp;
    }
    public Equipment getEquip() {
        return equip;
    }
    public void setEquip(Equipment equip) {
        this.equip = equip;
    }

    public void setActuHp(int actuHp) {
        if (actuHp > this.maxiHp) {
            this.actuHp = this.maxiHp;
        } else {
            this.actuHp = actuHp;
        }
    }

    public boolean isAlive(){
        return getActuHp()>0;
    }

    public abstract void attack(Character enem);

    public void reciDmg(int dmg){
        this.actuHp -= dmg;
        if(!isAlive()) actuHp = 0;
    }
    @Override
    public int getPrice(){
        return price;
    }

    public abstract Character clon();

}
class RageWarrior extends Character{
    private int rage;
    private boolean bonusHit = false;
    //base datas for random
     private static final int randHp = 500;
     private static final int randPw = 125;
     private static final int randPrice = 300;
     private static final int randSpeed = 12;

    public RageWarrior(){}
    public RageWarrior(String name, Equipment equip){
        Random rand = new Random();
        int hpVariance = (int) (randHp * 0.10);
        int maxiHp = randHp + (rand.nextInt((hpVariance * 2) + 1) - hpVariance);

        int pwVariance = (int) (125 * 0.15);
        int basePw = 125 + (rand.nextInt((pwVariance * 2) + 1) - pwVariance);

        int priceVariance = (int) (300 * 0.15);
        int price = 300 + (rand.nextInt((priceVariance * 2) + 1) - priceVariance);

        double speedVariance = (double) (12 * 0.20);
        double speed = 12 + (rand.nextDouble((speedVariance * 2) + 1) - speedVariance);

        super(name, maxiHp, basePw, equip, price, speed);
        this.rage = 0;
    }

    public RageWarrior(String name, int maxiHp, int basePw, Equipment equipmentCloned, int price, double speed) {
        super(name, maxiHp, basePw, equipmentCloned, price, speed);
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
                this.getName(),
                this.getMaxiHp(),
                this.getBasePw(),
                equipmentCloned,
                this.getPrice(),
                this.getSpeed()
        );
    }

    @Override
    public void attack(Character enem) {
        int bonusDmg = getEquip().getBonusDmg();
        System.out.println("BASE POWER = " + getBasePw());
        System.out.println("BONUS DMG = " + bonusDmg);
        System.out.println("ACTUAL RAGE = " + getRage());
        int totalDmg = getBasePw() + bonusDmg;
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
    private static final int randHp = 300;
    private static final int randPw = 100;
    private static final int randPrice = 350;
    private static final int randSpeed = 15;
    public MageWarrior(){}

    public MageWarrior(String name, Equipment equip){
        Random rand = new Random();
        int hpVariance = (int) (200 * 0.10);
        int maxiHp = 200 + (rand.nextInt((hpVariance * 2) + 1) - hpVariance);

        int pwVariance = (int) (125 * 0.15);
        int basePw = 125 + (rand.nextInt((pwVariance * 2) + 1) - pwVariance);

        int priceVariance = (int) (300 * 0.15);
        int price = 300 + (rand.nextInt((priceVariance * 2) + 1) - priceVariance);

        double speedVariance = (double) (12 * 0.20);
        double speed = 12 + (rand.nextDouble((speedVariance * 2) + 1) - speedVariance);

        super(name, maxiHp, basePw, equip, price, speed);
    }

    public MageWarrior(String name, int maxiHp, int basePw, int actuMana, Equipment equip, int price, double speed){
        super(name,  maxiHp,  basePw,  equip, price, speed);
        this.actuMana = actuMana;
    }

    public int getActuMana() {
        return actuMana;
    }

    public void setActuMana(int actuMana) {
        this.actuMana = actuMana;
    }

    @Override
    public Character clon(){
        Equipment equipmentCloned = null;
        if (this.getEquip() != null){
            equipmentCloned = this.getEquip().clon();
        }
        return new MageWarrior(
                this.getName(),
                this.getMaxiHp(),
                this.getBasePw(),
                this.getActuMana(),
                equipmentCloned,
                this.getPrice(),
                this.getSpeed()
        );
    }

    @Override
    public void attack(Character enem) {
        int bonusDmg = getEquip().getBonusDmg();
        System.out.println("BASE POWER = " + getBasePw());
        System.out.println("BONUS DMG = " + bonusDmg);
        System.out.println("ACTUAL MANA = " + getActuMana());
        int totalDmg = getBasePw() + bonusDmg;
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
}
