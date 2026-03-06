package logic;

import java.util.*;

public class Character implements Purchasable {
    private String name;
    private int actuHp, maxiHp, maxMana, actuMana, basePw, baseMagicPw, baseDefense, baseMagicDefense;
    private double hpRegenerate, manaRegenerate, criticChance, criticDamage, dodge, speed;
    private int level, exp;
    private Job currentJob;
    private List<SkillTemplate.Ability> unlockAbilities = new ArrayList<>();
    private SkillTemplate.Ability[] activeAbilities = new SkillTemplate.Ability[4];
    private final List<StatusEffect> activeEffects = new ArrayList<>();
    private Map<Equipment.Slot, Equipment> equipmentMap;
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
        this.equipmentMap = builder.equipmentMap;
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
        private Map<Equipment.Slot, Equipment> equipmentMap = new HashMap<>();
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
        public Builder isEnemy(boolean isEnemy) { this.isEnemy = isEnemy; return this; }
        public Builder price(int price) { this.price = price; return this; }
        public Builder level(int level) { this.level = level; return this; }
        public Builder exp(int exp) { this.exp = exp; return this;}
        public Builder currentJob(Job job) { this.currentJob = job; return this;}
        public Builder equipment(Equipment equip) {
            if (equip != null) {
                this.equipmentMap.put(equip.getSlot(), equip);
            }
            return this;
        }
        public Builder equipmentMap(Map<Equipment.Slot, Equipment> equipmentMap) {
            this.equipmentMap = new HashMap<>(equipmentMap);
            return this;
        }

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
        return actuHp>0;
    }

    public void handleDeath(){
        this.actuHp = 0;
        this.actuMana = 0;
        this.activeEffects.clear();
    }

    public void attack(int skillSlot, List<Character> enemies) {
        int index = skillSlot - 1;
        if (index >= 0 && index < 4 && this.activeAbilities[index] != null) {
            SkillTemplate.Ability chosenAbility = this.activeAbilities[index];
            List<DamageReport> report = chosenAbility.execute(this, enemies);
            for(DamageReport re : report) {
                if (re != null) {
                    re.combatReport();
                }
            }

        } else {
            System.out.println(this.getName() + " tried to use an empty or invalid slot.");
        }
    }
    //Effects processing:
    public void addStatusEffect(StatusEffect newEffect) {
        for (StatusEffect e : activeEffects) {
            if (e.getClass().equals(newEffect.getClass())) {
                e.stack(newEffect);
                return;
            }
        }
        activeEffects.add(newEffect);
        newEffect.onApply(this);
    }

    public void startTurn() {
        Iterator<StatusEffect> it = activeEffects.iterator();
        while (it.hasNext()) {
            StatusEffect effect = it.next();

            if (effect.preventsAction()) {
                System.out.println(name + " is CC'd and skips turn!");
            }

            boolean expired = effect.tick(this);
            if (expired) {
                it.remove();
            }
        }
    }

    /**
     *
     *  public boolean isDmgHealing(){
     *         return this.activeEffects.stream().anyMatch(StatusEffect::isDmgHealing);
     *     }
     */
    //Stat INT calculate:

    public int getEffectiveStat(StatType type, int baseValue) {
        int total = baseValue;
        for (StatusEffect e : activeEffects) {
            total += e.getStatModifier(type);
        }
        return total;
    }
    public int getTotalMaxHp() {
        int total = this.maxiHp;
        for (Equipment eq : equipmentMap.values()) total += eq.getBonusHp();
        return Math.max(1, getEffectiveStat(StatType.MAX_HP, total));
    }

    public int getTotalMaxMana() {
        int total = this.maxMana;
        for (Equipment eq : equipmentMap.values()) total += eq.getBonusMaxMana();
        return Math.max(0, getEffectiveStat(StatType.MAX_MANA, total));
    }

    public int getTotalPw() {
        int total = this.basePw;
        for (Equipment eq : equipmentMap.values()) total += eq.getBonusDmg();
        return Math.max(0, getEffectiveStat(StatType.POWER, total));
    }

    public int getTotalMagicPw() {
        int total = this.baseMagicPw;
        for (Equipment eq : equipmentMap.values()) total += eq.getBonusMagicDmg();
        return Math.max(0, getEffectiveStat(StatType.MAGIC_POWER, total));
    }

    public int getTotalDefense() {
        int total = this.baseDefense;
        for (Equipment eq : equipmentMap.values()) total += eq.getBonusDefense();
        return Math.max(0, getEffectiveStat(StatType.DEFENSE, total));
    }

    public int getTotalMagicDefense() {
        int total = this.baseMagicDefense;
        for (Equipment eq : equipmentMap.values()) total += eq.getBonusMagicDefense();
        return Math.max(0, getEffectiveStat(StatType.MAGIC_DEFENSE, total));
    }

    //DOUBLE STAT CALCULATE
    private int getEffectModSum(StatType type) {
        int sum = 0;
        for (StatusEffect e : activeEffects) {
            sum += e.getStatModifier(type);
        }
        return sum;
    }
    public double getTotalHpRegenerate() {
        double total = this.hpRegenerate;
        for (Equipment eq : equipmentMap.values()) total += eq.getBonusHpRegen();
        double effectMod = getEffectModSum(StatType.HP_REGEN) / 100.0;
        return Math.max(0.0, total + effectMod);
    }

    public double getTotalManaRegenerate() {
        double total = this.manaRegenerate;
        for (Equipment eq : equipmentMap.values()) total += eq.getBonusManaRegen();
        double effectMod = getEffectModSum(StatType.MANA_REGEN) / 100.0;
        return Math.max(0.0, total + effectMod);
    }

    public double getTotalCriticChance() {
        double total = this.criticChance;
        for (Equipment eq : equipmentMap.values()) total += eq.getBonusCritChance();
        double effectMod = getEffectModSum(StatType.CRIT_CHANCE) / 100.0;
        return Math.max(0.0, total + effectMod);
    }

    public double getTotalCriticDamage() {
        double total = this.criticDamage;
        for (Equipment eq : equipmentMap.values()) total += eq.getBonusCritDmg();
        double effectMod = getEffectModSum(StatType.CRIT_DAMAGE) / 100.0;
        return Math.max(1.0, total + effectMod);
    }

    public double getTotalDodge(){
        double total = this.dodge;
        for (Equipment eq : equipmentMap.values()) total += eq.getBonusDodgeChance();

        double effectMod = getEffectModSum(StatType.DODGE) / 100.0;
        return Math.min(0.90, Math.max(0.0, total + effectMod)); // Cap dodge at 90% so characters aren't invincible
    }

    public double getTotalSpeed() {
        double total = this.speed;
        for (Equipment eq : equipmentMap.values()) total += eq.getBonusSpeed();

        int effectMod = getEffectModSum(StatType.SPEED);
        return Math.max(0.0, total + effectMod);
    }

    public double getTotalLifesteal() {
        double total = 0.0;
        for (Equipment eq : equipmentMap.values()) {
            total += eq.getBonusLifesteal();
        }
        total += getEffectModSum(StatType.LIFE_STEAL) / 100.0;
        return Math.max(0.0, total);
    }

    public boolean hasStatusEffect(){
        return !this.activeEffects.isEmpty();
    }

    public void receiveDmg(int dmg){
        this.actuHp -= dmg;
        if(this.actuHp <= 0){
            this.actuHp = 0;
            handleDeath();
        }
    }
    public void applyHpRegeneration() {
        if (isAlive() && getTotalHpRegenerate() > 0.0) {
            int maxHp = getTotalMaxHp();
            actuHp += (int) (maxHp * getTotalHpRegenerate());
            if (actuHp > maxHp) {
                actuHp = maxHp;
            }
        }
    }

    public void applyManaRegeneration() {
        if (isAlive() && getTotalManaRegenerate() > 0.0) {
            int maxMana = getTotalMaxMana();
            actuMana += (int) (maxMana * getTotalManaRegenerate());
            if (actuMana > maxMana) {
                actuMana = maxMana;
            }
        }
    }

    public void receiveHealing(int amount){
        if (!isAlive()) {
            return;
        }
        this.actuHp += amount;
        int maxHp = getTotalMaxHp();

        if (this.actuHp > maxHp) {
            this.actuHp = maxHp;
        }
    }

    public void validateHealthBounds(){
        int currentMax = getTotalMaxHp();

        if(this.actuHp>currentMax){
            this.actuHp = currentMax;
        }
    }

    public void equipItem(Equipment newGear) {
        int oldMaxHp = getTotalMaxHp();
        double healthPercentage = (double) this.actuHp / oldMaxHp;
        int oldMaxMana = getTotalMaxMana();
        double manaPercentage = oldMaxMana > 0 ? (double) this.actuMana / oldMaxMana : 0.0;

        this.equipmentMap.put(newGear.getSlot(), newGear);
        int newMaxHp = getTotalMaxHp();

        this.actuMana = (int) (getTotalMaxMana() * manaPercentage);
        this.actuHp = (int) (newMaxHp * healthPercentage);
    }

    public Equipment unequipItem(Equipment.Slot slot) {
        if (!this.equipmentMap.containsKey(slot)) return null;

        int oldMaxHp = getTotalMaxHp();
        double healthPercentage = (double) this.actuHp / oldMaxHp;

        int oldMaxMana = getTotalMaxMana();
        double manaPercentage = oldMaxMana > 0 ? (double) this.actuMana / oldMaxMana : 0.0;

        Equipment removedItem = this.equipmentMap.remove(slot);

        this.actuHp = (int) (getTotalMaxHp() * healthPercentage);
        this.actuMana = (int) (getTotalMaxMana() * manaPercentage);

        return removedItem;
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
        this.actuHp = getTotalMaxHp();
        this.actuMana = getTotalMaxMana();
        if (this.currentJob != null) {
            SkillTemplate.Ability newSkill = this.currentJob.unlockAbilityDueToLevel(this.level);
            if (newSkill != null) {
                System.out.println(this.name + " learned a new skill: " + newSkill.getName() + "!");
                this.unlockAbilities.add(newSkill);
                for (int i = 0; i < this.activeAbilities.length; i++) {
                    if (this.activeAbilities[i] == null) {
                        this.activeAbilities[i] = newSkill;
                        System.out.println("-> Automatically equipped " + newSkill.getName() + " to Slot " + (i + 1) + "!");
                        break;
                    }
                }
            }
        }
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
                .dodge(this.dodge).speed(this.speed)
                .equipmentMap(this.equipmentMap)
                .level(this.level).currentJob(this.currentJob)
                .activeAbilities(cloneActive).unlockAbilities(cloneUnlock, cloneActive)
                .isEnemy(this.isEnemy).price(this.price).build();
    }

    public String getCharacterInfo() {
        StringBuilder info = new StringBuilder();
        info.append("=== ").append(this.name).append(" ===\n");
        // Use getTotal...() for all display stats!
        info.append("HP: ").append(this.actuHp).append(" / ").append(getTotalMaxHp()).append("\n");
        info.append("Power: ").append(getTotalPw()).append("\n");
        info.append("Defense: ").append(getTotalDefense()).append("\n");
        info.append("Speed: ").append(String.format("%.2f", getTotalSpeed())).append("\n");

        if (getTotalMaxMana() > 0) info.append("Mana: ").append(this.actuMana).append(" / ").append(getTotalMaxMana()).append("\n");
        if (getTotalMagicPw() > 0) info.append("Magic Power: ").append(getTotalMagicPw()).append("\n");
        if (getTotalMagicDefense() > 0) info.append("Magic Defense: ").append(getTotalMagicDefense()).append("\n");
        if (getTotalCriticChance() > 0) info.append("Crit Chance: ").append((int)(getTotalCriticChance() * 100)).append("%\n");
        if (getTotalDodge() > 0) info.append("Dodge: ").append((int)(getTotalDodge() * 100)).append("%\n");

        if (!this.equipmentMap.isEmpty()) {
            info.append("Equipment:\n");
            for (Equipment eq : this.equipmentMap.values()) {
                info.append(" - [").append(eq.getSlot()).append("] ").append(eq.getName()).append("\n");
            }
        }
        if (this.activeAbilities != null) {
            info.append("List of Actives Abilities: \n");
            for (SkillTemplate.Ability ability : activeAbilities) {
                if(ability != null) info.append(ability.getAbilityDisplayName()).append("\n");
            }
            info.append("\n");
        }
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
    public boolean reduceMana(int manaCost){
        if (this.actuMana < manaCost) {
            return false;
        }
        this.actuMana -= manaCost;
        return true;
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

    public List<StatusEffect> getActiveEffects() {
        return activeEffects;
    }

    public Map<Equipment.Slot, Equipment> getEquipmentMap() {
        return equipmentMap;
    }
}
