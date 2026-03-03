package logic;
import vista.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
public class Character implements Purchasable {
    private String name;
    private int actuHp, maxiHp, maxMana, actuMana, basePw, baseMagicPw, baseDefense, baseMagicDefense;
    private double hpRegenerate, manaRegenerate, criticChance, criticDamage, dodge, speed;
    private int level, exp;
    private Job currentJob;
    private List<SkillBooks.Ability> unlockAbilities = new ArrayList<>();
    private SkillBooks.Ability[] activeAbilities = new SkillBooks.Ability[4];
    private Equipment equip;
    private final boolean isEnemy;
    private int price;
    //---//
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
        this.level = builder.level;
        this.exp = builder.exp;
        this.activeAbilities = builder.activeAbilities;
        this.unlockAbilities = builder.unlockAbilities;
    }
    //---//
    public static class Builder{
        private String name;
        private int maxiHp = 500;
        private int maxMana = 0;
        private double hpRegenerate = 10;
        private double manaRegenerate = 0;
        private int basePw = 50;
        private int baseMagicPw = 10;
        private int baseDefense = 25;
        private int baseMagicDefense = 20;
        private double criticChance = 0.1;
        private double criticDamage = 2.0;
        private double dodge = 0.1;
        private double speed = 10.0;
        private Equipment equip = new Equipment.Builder("Borre's Coding skill (0).").build();;;;;
        private int level = 0, exp = 0;
        private Job currentJob = null;
        private List<SkillBooks.Ability> unlockAbilities = new ArrayList<>();
        private SkillBooks.Ability[] activeAbilities = new SkillBooks.Ability[4]; //not initialized yet.
        private boolean isEnemy = false;
        private int price = 0;

        public Builder(String name){
            this.name = name;
        }
        public Builder name(String name) { this.name = name; return this; }
        public Builder maxiHp(int maxiHp) { this.maxiHp = maxiHp; return this; }
        public Builder maxMana(int maxMana){ this.maxMana = maxMana; return this; }
        public Builder hpRegenerate(double hpRegenerate) { this.hpRegenerate = hpRegenerate; return this; }
        public Builder manaRegenerate(double manaRegenerate) { this.manaRegenerate = manaRegenerate; return this; }
        public Builder basePw(int basePw) { this.basePw = basePw; return this; }
        public Builder baseMagicPw(int baseMagicPw) { this.baseMagicPw = baseMagicPw; return this; }
        public Builder baseDefense(int baseDefense) { this.baseDefense = baseDefense; return this; }
        public Builder baseMagicDefense(int baseMagicDefense) { this.baseMagicDefense = baseMagicDefense; return this; }
        public Builder criticChance(double criticChance) { this.criticChance = criticChance; return this; }
        public Builder criticDamage(double criticDamage){ this.criticDamage = criticDamage; return this; }
        public Builder dodge(double dodge) { this.dodge = dodge; return this; }
        public Builder speed(double speed) { this.speed = speed; return this; }
        public Builder equip(Equipment equip) { this.equip = equip; return this; }
        public Builder isEnemy(boolean isEnemy) { this.isEnemy = isEnemy; return this; }
        public Builder price(int price) { this.price = price; return this; }
        public Builder level(int level) { this.level = level; return this; }
        public Builder exp(int exp) { this.exp = exp; return this;}
        public Builder currentJob(Job currentJob) { this.currentJob = currentJob; return this;}
        public Builder activeAbilities(SkillBooks.Ability... abilities) { //varargs
            for(int i = 0; (i < abilities.length) && (i < 4); i++){
                if(abilities[i] != null){
                    this.unlockAbilities.add(abilities[i]);
                    this.activeAbilities[i] = abilities[i].copy();
                }
            }
            return this;
        }
        public Builder unlockAbilities (List<SkillBooks.Ability> unlocked, SkillBooks.Ability[] active) {
            this.unlockAbilities = new ArrayList<>(unlocked);
            this.activeAbilities = active.clone();
            return this;
        }
        public Character build() {
            return new Character(this);
        }

    }

    public boolean isAlive(){
        return getActuHp()>0;
    }

    /** public void attack(Character enemy){
        if(this.ability != null){
            this.ability.execute(this, enemy).combateReport();
        }
        else{
            enemy.reciDmg(this.basePw);
        }
    }*/

    public void reciDmg(int dmg){
        this.actuHp -= dmg;
        if(!isAlive()) actuHp = 0;
    }
    public void applyRegeneration() {
        if (isAlive() && hpRegenerate > 0.0) {
            actuHp += (int) (maxiHp*hpRegenerate);
            if (actuHp > maxiHp) {
                actuHp = maxiHp;
            }
        }
    }
    @Override
    public int getPrice(){
        return price;
    }

    public int expToTheNextLevel(){
        return (int) (100 * Math.pow(level, 1.5));
    }

    public void gainExp(int amount){
        this.exp += amount;
        while(this.exp >= expToTheNextLevel()){
            this.exp-=expToTheNextLevel();
            levelUp();
        }
    }

    public void levelUp(){
        this.level++;

        this.actuHp = this.maxiHp;
        this.actuMana = this.maxMana;
        //if(this.currenJob != null && currentJob.getSkillUnlockedAtLevel(this.level)!=null){}
    }

    public Character clon() {
        SkillBooks.Ability[] cloneActive = new SkillBooks.Ability[4];
        for(int i = 0; i < 4; i++){
            cloneActive[i] = (this.activeAbilities[i].copy());
        }

        List<SkillBooks.Ability> cloneUnlock = new ArrayList<>();
        for(SkillBooks.Ability ability : this.unlockAbilities){
            cloneUnlock.add(ability.copy());
        }

        return new Builder(this.name)
                .maxiHp(this.maxiHp).maxMana((int) this.maxMana).hpRegenerate(this.hpRegenerate)
                .manaRegenerate(this.manaRegenerate).basePw(this.basePw).baseMagicPw(this.baseMagicPw)
                .baseDefense(this.baseDefense).baseMagicDefense(this.baseMagicDefense)
                .criticChance(this.criticChance).criticDamage(this.criticDamage)
                .dodge(this.dodge).speed(this.speed).equip(this.equip)
                .level(this.level).currentJob(this.currentJob)
                .activeAbilities(cloneActive).unlockAbilities(cloneUnlock, cloneActive)
                .isEnemy(this.isEnemy).price(this.price).build();
    }

    public String getCharacterInfo() {
        StringBuilder info = new StringBuilder();
        info.append("=== ").append(this.name).append(" ===\n");
        info.append("HP: ").append(this.actuHp).append(" / ").append(this.maxiHp).append("\n");
        info.append("Power: ").append(this.basePw).append("\n");
        info.append("Defense: ").append(this.baseDefense).append("\n");
        info.append("Speed: ").append(String.format("%.2f", this.speed)).append("\n");
        if (this.maxMana > 0) info.append("Mana: ").append(this.actuMana).append(" / ").append(this.maxMana).append("\n");
        if (this.baseMagicPw > 0) info.append("Magic Power: ").append(this.baseMagicPw).append("\n");
        if (this.baseMagicDefense > 0) info.append("Magic Defense: ").append(this.baseMagicDefense).append("\n");
        if (this.criticChance > 0) info.append("Crit Chance: ").append((int)(this.criticChance * 100)).append("%\n");
        if (this.dodge > 0) info.append("Dodge: ").append((int)(this.dodge * 100)).append("%\n");
        if (this.equip != null) info.append("Equipped: ").append(this.equip.getName()).append("\n");
        if (this.activeAbilities != null) info.append("List of Actives Abilities: \n");
        for(SkillBooks.Ability ability : activeAbilities){info.append(ability.getAbilityDisplayName()).append("\n");}
        info.append("\n");
        if(this.unlockAbilities != null) info.append("List of Unlock Abilities: \n");
        for(SkillBooks.Ability ability : unlockAbilities){info.append(ability.getAbilityDisplayName()).append("\n");}
        info.append("\n");
        if (this.price > 0) info.append("Price: ").append(this.price).append("\n");
        return info.toString();
    }

    public int getActuHp() {
        return actuHp;
    }

    public String getName() {
        return name;
    }

    public int getBaseMagicPw() {
        return baseMagicPw;
    }

    public int getMaxMana() {
        return maxMana;
    }

    public int getActuMana() {
        return actuMana;
    }

    public double getHpRegenerate() {
        return hpRegenerate;
    }

    public double getManaRegenerate() {
        return manaRegenerate;
    }

    public int getMaxiHp() {
        return maxiHp;
    }

    public int getBasePw() {
        return basePw;
    }

    public int getBaseDefense() {
        return baseDefense;
    }

    public double getCriticDamage() {
        return criticDamage;
    }

    public int getBaseMagicDefense() {
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

    public int getLevel() {
        return level;
    }

    public int getExp() {
        return exp;
    }

    public Job getCurrentJob() {
        return currentJob;
    }

    public List<SkillBooks.Ability> getUnlockAbilities() {
        return unlockAbilities;
    }

    public SkillBooks.Ability[] getActiveAbilities() {
        return activeAbilities;
    }
}
