package logic;
import java.util.Map;
import java.util.HashMap;

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
    private Map<Integer, SkillBooks.Ability> unlockSkills;

    public Job(JobType jobType){
        this.jobType = jobType;
        this.currentStack=0;
    }
    public SkillBooks.Ability unlockAbilityDueToLevel(Job.JobType jobType, int level){
        return unlockAbilityDueToLevel(jobType, level).copy();
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

    public void stackGain(int gain){
        this.currentStack += gain;
        if(currentStack>maxStack) currentStack = maxStack;
    }

    public Map<Integer, SkillBooks.Ability> getUnlockSkills() {
        return unlockSkills;
    }
}
