/**package org.example;

public class AttackReport {
    private Character from;
    private Character to;
    private boolean bonusHit;
    public AttackReport(Character from, Character to, boolean bonusHit) {
        this.from = from;
        this.to = to;
        this.bonusHit = bonusHit;
    }
    public void attackReport() {
        System.out.println("Attacker name: " + from.getName());
        System.out.println("Enem. name: " + to.getName());
        System.out.println("Attacker base power: " + from.getBasePw());

    }

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
}*/