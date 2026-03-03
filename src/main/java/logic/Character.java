package logic;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Character implements Purchasable {
    private String name;
    private int actuHp, maxiHp, maxMana, actuMana, basePw, baseMagicPw, baseDefense, baseMagicDefense;
    private double hpRegenerate, manaRegenerate, criticChance, criticDamage, dodge, speed;
    private int level, exp;
    private Job currentJob;
    private List<SkillTemplate.Ability> unlockAbilities = new ArrayList<>();
    private SkillTemplate.Ability[] activeAbilities = new SkillTemplate.Ability[4];
    private final List<StatusEffect> activeEffects = new ArrayList<>();
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
        private double hpRegenerate = 0.02;
        private double manaRegenerate = 0.02;
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
        private List<SkillTemplate.Ability> unlockAbilities = new ArrayList<>();
        private SkillTemplate.Ability[] activeAbilities = new SkillTemplate.Ability[4]; //not initialized yet.
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
        public Builder currentJob(Job job) { this.currentJob = job; return this;}
        public Builder activeAbilities(SkillTemplate.Ability... abilities) { //varargs
            for(int i = 0; (i < abilities.length) && (i < 4); i++){
                if(abilities[i] != null){
                    this.unlockAbilities.add(abilities[i]);
                    this.activeAbilities[i] = abilities[i].copy();
                }
            }
            return this;
        }
        public Builder unlockAbilities (List<SkillTemplate.Ability> unlocked, SkillTemplate.Ability[] active) {
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

    public void attack(int skillSlot, Character enemy) {
        int index = skillSlot - 1;
        if (index >= 0 && index < 4 && this.activeAbilities[index] != null) {
            SkillTemplate.Ability chosenAbility = this.activeAbilities[index];

            DamageReport report = chosenAbility.execute(this, enemy);

            if (report != null) {
                report.combatReport();
            }

        } else {
            System.out.println(this.getName() + " tried to use an empty or invalid slot.");
        }
    }
    //Effects processing:
    public void addStatusEffect(StatusEffect effect) {
        this.activeEffects.add(effect.copy());
        System.out.println(this.getName() + " was afflicted with " + effect.getType() + "!");
    }
    public void processEffectsOnTurnStart() {
        Iterator<StatusEffect> iterator = activeEffects.iterator();
        while (iterator.hasNext()) {
            StatusEffect effect = iterator.next();
            effect.processEffect(this);

            if (effect.getDuration() <= 0) {
                iterator.remove();
            }
        }
    }
    public boolean hasStatusEffect(){
        return !this.activeEffects.isEmpty();
    }

    public void receiveDmg(int dmg){
        this.actuHp -= dmg;
        if(!isAlive()) actuHp = 0;
    }
    public void applyHpRegeneration() {
        if (isAlive() && hpRegenerate > 0.0) {
            actuHp += (int) (maxiHp*hpRegenerate);
            if (actuHp > maxiHp) {
                actuHp = maxiHp;
            }
        }
    }

    public void applyManaRegeneration() {
        if (isAlive() && manaRegenerate > 0.0) {
            actuMana += (int) (maxiHp*manaRegenerate);
            if (actuMana > maxMana) {
                actuMana = maxMana;
            }
        }
    }

    public void receiveHealing(int amount){
        this.actuHp += amount;
        if(this.actuHp > this.maxiHp) this.actuHp = this.maxiHp;
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
        this.maxiHp*=1.2;
        this.maxMana*=1.1;
        this.basePw*=1.1;
        this.baseMagicPw*=1.1;
        this.baseDefense*=1.1;
        this.baseMagicDefense*=1.1;
        //refreshes:
        this.actuHp = this.maxiHp;
        this.actuMana = this.maxMana;
        //if(this.currenJob != null && currentJob.getSkillUnlockedAtLevel(this.level)!=null){}
    }

    public Character clon() {
        SkillTemplate.Ability[] cloneActive = new SkillTemplate.Ability[4];
        for(int i = 0; i < 4; i++){
            cloneActive[i] = (this.activeAbilities[i].copy());
        }

        List<SkillTemplate.Ability> cloneUnlock = new ArrayList<>();
        for(SkillTemplate.Ability ability : this.unlockAbilities){
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
        assert activeAbilities != null;
        for(SkillTemplate.Ability ability : activeAbilities){info.append(ability.getAbilityDisplayName()).append("\n");}
        info.append("\n");
        if(this.unlockAbilities != null) info.append("List of Unlock Abilities: \n");
        assert unlockAbilities != null;
        for(SkillTemplate.Ability ability : unlockAbilities){info.append(ability.getAbilityDisplayName()).append("\n");}
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
    public void reduceMana(int manaCost){
        this.actuMana -= manaCost;
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

    public List<SkillTemplate.Ability> getUnlockAbilities() {
        return unlockAbilities;
    }

    public SkillTemplate.Ability[] getActiveAbilities() {
        return activeAbilities;
    }
}
