package logic;

import java.lang.classfile.instruction.CharacterRange;

public class SkillBooks {
    public static interface Ability {
        DamageReport execute(Character attacker, Character target);
        Ability copy();
        String getAbilityDisplayName();
        boolean[] getValidLaunchRanks();
        boolean[] getValidTargetRanks();
    }
        public static class BaseAttack {
            private String name;
            private double physMultiplier;
            private double magicMultiplier;
            private Job.JobType requiredJob;
            private int requiredLevel;
            private int manaCost;
            private int stackCost;
            private boolean[] targetBox;
            private boolean[] launchBox;

            private BaseAttack(Builder builder) {
                this.name = builder.name;
                this.physMultiplier = builder.physMultiplier;
                this.magicMultiplier = builder.magicMultiplier;
                this.requiredJob = builder.requiredJob;
                this.requiredLevel = builder.requiredLevel;
                this.manaCost = builder.manaCost;
                this.stackCost = builder.stackCost;
                this.targetBox = builder.targetBox;
                this.launchBox = builder.launchBox;
            }

            public static class Builder {
                private String name = "Basic Attack";
                private int requiredLevel = 1;
                private Job.JobType requiredJob = null;
                private double physMultiplier = 1.0;
                private double magicMultiplier = 1.1;
                private int manaCost = 50;
                private int stackCost = 1;
                private int stackGain = 1;
                private boolean[] targetBox = {true, true, false, false};
                private boolean[] launchBox = {true, true, false, false};

                public Builder(String name, int requiredLevel, Job.JobType requiredJob) {
                    this.name = name;
                    this.requiredLevel = requiredLevel;
                    this.requiredJob = requiredJob;
                }
                public Builder physMultiplier(double multiplier) {this.physMultiplier = multiplier;return this;}
                public Builder magicMultiplier(double multiplier) {this.magicMultiplier = multiplier;return this;}
                public Builder manaCost(int cost) {this.manaCost = cost;return this;}
                public Builder stackCost(int cost) {this.stackCost = cost;return this;}
                public Builder stackGain(int gain) {this.stackGain = gain; return this;}
                public Builder targetBox(boolean[] targetBox) {this.targetBox = targetBox;return this;}
                public Builder launchBox(boolean[] launchBox) {this.launchBox = launchBox;return this;}
                public BaseAttack build() {return new BaseAttack(this);}

                @Override
                public DamageReport execute(Character self, Character enemy){
                    if (self.getCurrentJob() != null && self.getCurrentJob().getJobType() == requiredJob) {
                        if (stackCost > 0 && ! (self.getCurrentJob().getCurrentStack()<stackCost)) {
                            System.out.println("Not enough stacks to use " + name + "!");
                            return null;
                        }
                        if (stackGain > 0) {
                            self.getCurrentJob().stackGain(stackGain);
                        }
                    }
                    //CombatEngine
                    int physDmg = (int) (self.getBasePw() * physMultiplier);
                    int magicDmg = (int) (self.getBaseMagicPw() * magicMultiplier);
                    int totalDmg = physDmg + magicDmg;

                    enemy.reciDmg(totalDmg);
                    return new DamageReport.Builder(self.getName(), enemy.getName())
                            .totalPhysicDmg(physDmg).totalMagicDmg(magicDmg).totalDmg(totalDmg)
                            .isKill(!enemy.isAlive()).build();
                }
                @Override
                public boolean[] getValidLaunchRanks() {return launchBox;}
                @Override
                public boolean[] getValidTargetRanks() {return targetBox;}
            }
        }


    }