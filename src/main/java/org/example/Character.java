package org.example;

public abstract class Character {
    private String name;
    private int actuHp;
    private int maxiHp;
    private int basePw;
    private Equipment equip;
    public Character(){}
    public Character(String name, int actuHp, int maxiHp, int basePw, Equipment equip){
        this.name = name;
        this.actuHp = actuHp;
        this.maxiHp = maxiHp;
        this.basePw = basePw;
        this.equip = equip;
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

    public boolean alive(){
        return this.actuHp>0;
    }

    public abstract void attack(Character enem);

    public void reciDmg(int dmg){
        this.actuHp -= dmg;
        if(!alive()) actuHp = 0;
    }
}
class Equipment{
    private String name;
    private int bonusDmg;
    public Equipment(){}
    public Equipment(String name, int bonusDmg) {
        this.name = name;
        this.bonusDmg = bonusDmg;
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
}
class RageWarrior extends Character{
    private int rage;
    private boolean bonusHit = false;
    public RageWarrior(){}
    public RageWarrior(String name, int actuHp, int maxiHp, int basePw, Equipment equip){
        super(name,  actuHp,  maxiHp,  basePw,  equip);
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
        if(!enem.alive()){
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
    public MageWarrior(String name, int actuHp, int maxiHp, int basePw, int actuMana, Equipment equip){
        super(name,  actuHp,  maxiHp,  basePw,  equip);
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
        if(!enem.alive()){
            System.out.println("ENEM. DIED. ");
        } else{
            System.out.println("ENEM. ACTUAL HP AFTER DMG = " + enem.getActuHp());
        }
    }
}