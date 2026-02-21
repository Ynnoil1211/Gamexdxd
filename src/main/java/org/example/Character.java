package org.example;
interface Purchasable {
    int getPrice();
}
public abstract class Character implements Purchasable {
    private String name;
    private int actuHp;
    private double speed;
    private int maxiHp;
    private int price;
    private int basePw;
    private Equipment equip;
    public Character(){}
    public Character(String name, int maxiHp, int basePw, Equipment equip, int price, double speed){
        this.name = name;
        this.actuHp = maxiHp;
        this.maxiHp = maxiHp;
        this.basePw = basePw;
        this.equip = equip;
        this.price = price;
        this.speed = speed;
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
}
class RageWarrior extends Character{
    private int rage;
    private boolean bonusHit = false;
    public RageWarrior(){}
    public RageWarrior(String name, int maxiHp, int basePw, Equipment equip, int price, double speed){
        super(name, maxiHp, basePw,  equip, price, speed);
        this.rage = 0;
    }
    public int getRage() {
        return rage;
    }

    public void setRage(int rage) {
        this.rage = rage;
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
    public MageWarrior(){}
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
class Equipment implements Purchasable{
    private String name;
    private int bonusDmg;
    private int price;
    public Equipment(){}
    public Equipment(String name, int bonusDmg, int price) {
        this.name = name;
        this.bonusDmg = bonusDmg;
        this.price = price;
    }

    public int getBonusDmg() {
        return bonusDmg;
    }

    public void setBonusDmg(int bonusDmg) {
        this.bonusDmg = bonusDmg;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public int getPrice(){
        return price;
    }
}
class Item implements Purchasable{
    private String name;
    private String function;
    private int price;
    public Item(){}
    public Item(String name, String function, int price){
        this.name = name;
        this.function = function;
        this.price = price;
    }
    @Override
    public int getPrice() {
        return price;
    }

    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function = function;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
class Heal extends Item{
    private int heal;
    public Heal(){}
    public Heal(String name, String function, int price, int heal){
        super(name, function, price);
        this.heal = heal;
    }

    public int getHeal() {
        return heal;
    }

    public void setHeal(int heal) {
        this.heal = heal;
    }
}
class Poison extends Item{
    private int dmg;
    public Poison(){}
    public Poison(String name, String function, int price, int dmg){
        super(name, function, price);
        this.dmg = dmg;
    }
    public int getDmg() {
        return dmg;
    }
    public void setDmg(int dmg) {
        this.dmg = dmg;
    }
}