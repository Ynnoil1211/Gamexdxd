package logic;
import java.util.Map;

public class Job {
    public static enum JobType{
        BARBARIAN("Barbarian"),
        BREAKER("Breaker"),
        WIZARD("Wizard"),
        VESTAL("Vestal"),
        DEMON_HUNTER("Demon Hunter");
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
    private final int maxStack = 6;
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
