package logic;
import java.util.Map;

public class Job {
        public static enum JobType{
            BARBARIAN("Barbarian"), // warrior - MELEE; +++hp +++defense, mana for
            BREAKER("Breaker"),     // stacker - MELEE; +hp, ++dmg, need stacks for abilities: melee + AOE abilities
            WIZARD("Wizard"),       // mage - RANGED; +hp, +++magicDmg,  need mana for abilities: AOE abilities + selfBuffing(replenish mana, higher dmg)
            VESTAL("Vestal"),       // healer - RANGED; +hp, -dmg, need mana for abilities: team healing, team buff;
            JOKER("Joker"),         // debuffer - RANGED; -hp, ++dmg, need mana for abilities: rival team debuff, apply effects, make their dmg healing;
            WHITE("White"),         // stunner - Melee; ++hp, -dmg, need mana for abilities: stunning, cc, dmg...;
            DEMON_HUNTER("Demon Hunter"); //adc - RANGED; --hp, +++dmg, mana for abilities: selfBuffing, executing... (aoe attack? -- not implemented -> builds for this).
            private final String displayName;
            JobType(String displayName){
                this.displayName = displayName;
            }
            public String getDisplayName(){
                return displayName;
            }
        }
    private JobType jobType;
    private int currentStack;
    private final int maxStack = 10;
    private Map<Integer, SkillTemplate.Ability> unlockSkills;

    public Job(JobType jobType){
        this.jobType = jobType;
        this.currentStack=0;
    }
    public SkillTemplate.Ability unlockAbilityDueToLevel(Job.JobType jobType, int level){
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

    public void consumeStack(int cost){
        if(this.currentStack>=cost){
            this.currentStack-=cost;
        }
    }

    public void addStack(int gain){
        this.currentStack += gain;
        if(currentStack>maxStack) currentStack = maxStack;
    }

    public Map<Integer, SkillTemplate.Ability> getUnlockSkills() {
        return unlockSkills;
    }
}
