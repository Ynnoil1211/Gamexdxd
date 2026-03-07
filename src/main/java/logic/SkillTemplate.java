package logic;

import java.util.*;

public class SkillTemplate {
    public interface Ability {
        List<DamageReport> execute(Person attacker, List<Person> targets);

        Ability copy();

        String getAbilityDisplayName();

        boolean[] getValidLaunchRanks();

        boolean[] getValidTargetRanks();

        String getName();

        Map<DamageType, Double> getMultipliers();

        StatusEffect getApplicableEffect();

        AbilityType getAbilityType();

        boolean isAoE();
    }

    public static class BaseAttack implements Ability {
        private final String name;
        private final Job.JobType requiredJob;
        private final int requiredLevel;
        private final int manaCost;
        private final int stackCost;
        private final int stackGain;
        private final boolean[] targetBox;
        private final boolean[] launchBox;
        private Map<DamageType, Double> multipliers;
        private AbilityType abilityType;
        private StatusEffect applicableEffect;
        private boolean isAoE;

        private BaseAttack(Builder builder) {
            this.name = builder.name;
            this.requiredJob = builder.requiredJob;
            this.requiredLevel = builder.requiredLevel;
            this.manaCost = builder.manaCost;
            this.stackGain = builder.stackGain;
            this.stackCost = builder.stackCost;
            this.targetBox = builder.targetBox;
            this.launchBox = builder.launchBox;
            this.multipliers = new HashMap<>(builder.multipliers);
            this.applicableEffect = builder.applicableEffect == null ? null : builder.applicableEffect.copy();
            this.abilityType = builder.abilityType;
            this.isAoE = builder.isAoE;
        }

        public static class Builder {
            private String name = "Basic Attack";
            private int requiredLevel = 1;
            private Job.JobType requiredJob = null;
            private int manaCost = 30;
            private int stackCost = 1;
            private int stackGain = 1;
            private boolean[] targetBox = {true, true, false, false};
            private boolean[] launchBox = {true, true, false, false};
            private Map<DamageType, Double> multipliers = new HashMap<>();
            private StatusEffect applicableEffect = null;
            private AbilityType abilityType = AbilityType.OFFENSIVE;
            private boolean isAoE = false;

            public Builder(String name, int requiredLevel, Job.JobType requiredJob) {
                this.name = name;
                this.requiredLevel = requiredLevel;
                this.requiredJob = requiredJob;
            }

            public Builder manaCost(int cost) {
                this.manaCost = cost;
                return this;
            }

            public Builder stackCost(int cost) {
                this.stackCost = cost;
                return this;
            }

            public Builder stackGain(int gain) {
                this.stackGain = gain;
                return this;
            }

            public Builder targetBox(boolean[] targetBox) {
                this.targetBox = targetBox;
                return this;
            }

            public Builder launchBox(boolean[] launchBox) {
                this.launchBox = launchBox;
                return this;
            }

            public Builder addmultiplier(DamageType type, double value) {
                this.multipliers.put(type, value);
                return this;
            }

            public Builder setMultipliers(Map<DamageType, Double> existingMap) {
                this.multipliers = new HashMap<>(existingMap);
                return this;
            }

            public Builder applicableEffect(StatusEffect effect) {
                this.applicableEffect = effect;
                return this;
            }

            public Builder abilityType(AbilityType type){
                this.abilityType = type;
                return this;
            }
            public Builder isAoE( boolean yes ){
                this.isAoE = yes;
                return this;
            }

            public BaseAttack build() {
                return new BaseAttack(this);
            }
        }


        @Override
        public List<DamageReport> execute(Person self, List<Person> targets) {
            List<DamageReport> finalReports = new ArrayList<>();
            if (self.getCurrentJob() == null || self.getCurrentJob().getJobType() != this.requiredJob) {
                System.out.println("Wrong job class to use this ability!");
                return Collections.emptyList();
            }

            int attackerRank = self.getRank();
            if (!this.launchBox[attackerRank]) {
                System.out.println(self.getName() + " cannot use " + this.name + " from position " + attackerRank + "!");
                return Collections.emptyList();
            }

            if (this.manaCost > 0 && self.getActuMana() < this.manaCost) {
                System.out.println(self.getName() + " doesn't have enough mana!");
                return Collections.emptyList();
            }
            if (this.stackCost > 0 && self.getCurrentJob().getCurrentStack() < this.stackCost) {
                System.out.println(self.getName() + " doesn't have enough stacks!");
                return Collections.emptyList();
            }
            if (this.manaCost > 0) self.reduceMana(this.manaCost);
            if (this.stackCost > 0) self.getCurrentJob().consumeStack(this.stackCost);

            boolean hitAnyone = false;

            for (Person enemy : targets) {
                int enemyRank = enemy.getRank();
                if (this.targetBox[enemyRank]) {
                    DamageReport report = CombatEngine.calculateDmg(self, enemy, this);
                    finalReports.add(report);
                    hitAnyone = true;
                }
            }
            if (!hitAnyone) {
                System.out.println(this.name + " was used, but no enemies were affected!");
            }
            if (this.stackGain > 0 && hitAnyone) {
                self.getCurrentJob().addStack(this.stackGain);
            }

            return finalReports;
        }

        @Override
        public Ability copy() {
            return new Builder(this.name, this.requiredLevel, this.requiredJob)
                    .manaCost(this.manaCost)
                    .stackCost(this.stackCost)
                    .stackGain(this.stackGain)
                    .targetBox(this.targetBox.clone())
                    .launchBox(this.launchBox.clone())
                    .setMultipliers(this.multipliers)
                    .applicableEffect(this.applicableEffect)
                    .abilityType(this.abilityType)
                    .build();
        }

        @Override
        public String getAbilityDisplayName() {
            StringBuilder info = new StringBuilder();
            if (this.name != null) info.append(" Skill name: ").append(this.name + "\n");
            for (Map.Entry<DamageType, Double> entry : this.multipliers.entrySet()) {
                info.append("- ").append(entry.getKey()).append(" Scaling: ").append((int) (entry.getValue() * 100)).append("%\n");
            }
            if (this.manaCost != 0) info.append("Mana Cost: ").append(this.manaCost + "\n");
            if (this.applicableEffect != null) info.append("Applicable Effect: ").append(this.applicableEffect + "\n");
            if (this.stackCost != 0) info.append("Stack Cost: ").append(this.stackCost + "\n");
            if (this.stackGain != 0) info.append("Stack Gain: ").append(this.stackGain + "\n");
            return info.toString();
        }

        @Override
        public boolean[] getValidLaunchRanks() {
            return launchBox;
        }

        @Override
        public boolean[] getValidTargetRanks() {
            return targetBox;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public Map<DamageType, Double> getMultipliers() {
            return multipliers;
        }

        @Override
        public StatusEffect getApplicableEffect() {
            return applicableEffect;
        }

        @Override
        public AbilityType getAbilityType(){
            return abilityType;
        }

        @Override
        public boolean isAoE(){return isAoE;}

    }
}