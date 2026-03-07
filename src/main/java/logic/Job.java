package logic;

import java.util.Map;

public class Job {
    public enum JobType {
        //              Name          HP    Mana  Phys  Mag  Def  M.Def   Spd Dodge Crit+
        BARBARIAN("Barbarian",       1.75, 0.60, 1.30, 0.50, 1.50, 1.10, 0.90, 0.80, 0.00), // +++HP, +++Def
        BREAKER("Breaker",           1.20, 1.00, 1.40, 0.60, 1.20, 1.00, 1.00, 1.00, 0.05), // stacker - MELEE; +hp, ++dmg, needs stacks for abilities: melee + AOE abilities
        WIZARD("Wizard",             0.90, 1.75, 0.40, 1.75, 0.70, 1.40, 0.90, 1.00, 0.00), // mage - RANGED; +hp, +++magicDmg,  needs mana for abilities: AOE abilities + selfBuffing(replenish mana, higher dmg)
        VESTAL("Vestal",             1.10, 1.50, 0.50, 1.30, 1.10, 1.50, 1.00, 1.00, 0.00), // healer - RANGED; +hp, -dmg, needs mana for abilities: team healing, team buff;
        JOKER("Joker",               0.80, 1.30, 1.10, 1.10, 0.80, 1.00, 1.30, 1.30, 0.10), // debuffer - RANGED; -hp, ++dmg, needs mana for abilities: rival team debuff, apply effects, make their dmg healing;
        WHITE("White",               1.30, 1.10, 0.90, 1.00, 1.40, 1.30, 0.80, 0.90, 0.00), // stunner - Melee; ++hp, -dmg, needs mana for abilities: stunning, cc, dmg...;
        DEMON_HUNTER("Demon Hunter", 0.70, 1.00, 1.70, 0.80, 0.60, 0.70, 1.20, 1.10, 0.15), //adc - RANGED; ---hp, +++dmg, mana for abilities: selfBuffing, executing... (aoe attack? -- not implemented -> builds for this).
        SLAYER("Slayer",             0.70, 0.80, 1.60, 1.00, 0.70, 0.70, 1.50, 1.40, 0.25); // assassin - melee and ranged abilities, criticChance++?(passive perhaps); --hp, ++dmg, needs mana: hits enemy on the back row... like {true true true true}


        private final String displayName;
        private final double hpMult;
        private final double manaMult;
        private final double physMult;
        private final double magMult;
        private final double defMult;
        private final double magicDefMult;
        private final double speedMult;
        private final double dodgeMult;
        private final double flatCritBonus;

        JobType(String displayName, double hpMult, double manaMult, double physMult, double magMult,
                double defMult, double magicDefMult, double speedMult, double dodgeMult, double flatCritBonus) {
            this.displayName = displayName;
            this.hpMult = hpMult;
            this.manaMult = manaMult;
            this.physMult = physMult;
            this.magMult = magMult;
            this.defMult = defMult;
            this.magicDefMult = magicDefMult;
            this.speedMult = speedMult;
            this.dodgeMult = dodgeMult;
            this.flatCritBonus = flatCritBonus;
        }

        public String getDisplayName() {
            return displayName;
        }

        public double getHpMult() {
            return hpMult;
        }

        public double getPhysMult() {
            return physMult;
        }

        public double getDefMult() {
            return defMult;
        }

        public double getMagMult() {
            return magMult;
        }

        public double getFlatCritBonus() {
            return flatCritBonus;
        }

        public double getManaMult() {
            return manaMult;
        }

        public double getMagicDefMult() {
            return magicDefMult;
        }

        public double getSpeedMult() {
            return speedMult;
        }

        public double getDodgeMult() {
            return dodgeMult;
        }
    }

    private JobType jobType;
    private int currentStack;
    private final int maxStack = 10;
    private Map<Integer, SkillTemplate.Ability> unlockSkills;

    public Job(JobType jobType) {
        this.jobType = jobType;
        this.currentStack = 1;
        this.unlockSkills = Learnset.getTreeForJob(jobType);
    }

    public SkillTemplate.Ability unlockAbilityDueToLevel(int level) {
        SkillTemplate.Ability abilityUnlocked = unlockSkills.get(level);
        return abilityUnlocked != null ? abilityUnlocked.copy() : null;
    }

    public JobType getJobType() {
        return jobType;
    }

    public int getCurrentStack() {
        return currentStack;
    }

    public int getMaxStack() {
        return maxStack;
    }

    public void consumeStack(int cost) {
        if (this.currentStack >= cost) {
            this.currentStack -= cost;
        }
    }

    public void addStack(int gain) {
        this.currentStack += gain;
        if (currentStack > maxStack) currentStack = maxStack;
    }

    public Map<Integer, SkillTemplate.Ability> getUnlockSkills() {
        return unlockSkills;
    }
}
