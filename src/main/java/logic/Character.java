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
        return getActuHp()>0;
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
        // Max HP should generally never drop below 1, even with debuffs
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
        double effectMod = getEffectModSum(StatType.HP_REGEN) / 100.0;
        return Math.max(0.0, total + effectMod);
    }

    public double getTotalManaRegenerate() {
        double total = this.manaRegenerate;
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
        // If you ever add Crit Damage to Equipment, loop through it here

        double effectMod = getEffectModSum(StatType.CRIT_DAMAGE) / 100.0;
        return Math.max(1.0, total + effectMod); // Crit damage shouldn't drop below 1.0 (100% normal damage)
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

        // Speed is a flat number, so we DO NOT divide by 100.0 here!
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
                .dodge(this.dodge).speed(this.speed)
                .equipmentMap(this.equipmentMap)
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
