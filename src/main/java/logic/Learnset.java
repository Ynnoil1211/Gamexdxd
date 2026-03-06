package logic;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

public class Learnset {
    private static final Map<Job.JobType, Map<Integer, SkillTemplate.Ability>> masterLearnset = new EnumMap<>(Job.JobType.class);
    public static void initialize() {
        System.out.println("Loading Job Learnsets...");
        for (Job.JobType type : Job.JobType.values()) {
            masterLearnset.put(type, new HashMap<>());
        }

        // --- BREAKER (Melee Stacker) ---
        // Level 1: Generate stacks
        addSkill(Job.JobType.BREAKER, 1, new SkillTemplate.BaseAttack.Builder("Build Momentum", 1, Job.JobType.BREAKER)
                .manaCost(0)
                .stackGain(2) // Gains 2 stacks!
                .abilityType(AbilityType.OFFENSIVE)
                .addmultiplier(DamageType.PHYSICAL, 1.0)
                .build());

        // Level 5: Consume stacks for huge AoE
        addSkill(Job.JobType.BREAKER, 5, new SkillTemplate.BaseAttack.Builder("Earth Shatter", 5, Job.JobType.BREAKER)
                .manaCost(20)
                .stackCost(5) // Consumes 5 stacks!
                .abilityType(AbilityType.OFFENSIVE)
                .addmultiplier(DamageType.PHYSICAL, 2.5)
                .targetBox(new boolean[]{true, true, true, true}) // Hits EVERYONE
                .build());

        // --- VESTAL (Ranged Healer) ---
        addSkill(Job.JobType.VESTAL, 1, new SkillTemplate.BaseAttack.Builder("Holy Light", 1, Job.JobType.VESTAL)
                .manaCost(25)
                .abilityType(AbilityType.HEALING)
                .addmultiplier(DamageType.HEAL, 1.5) // 150% Magic Power
                .targetBox(new boolean[]{true, true, true, true})
                .build());

        // --- JOKER (Ranged Debuffer) ---
        addSkill(Job.JobType.JOKER, 1, new SkillTemplate.BaseAttack.Builder("Toxic Prank", 1, Job.JobType.JOKER)
                .manaCost(15)
                .abilityType(AbilityType.OFFENSIVE)
                .addmultiplier(DamageType.POISON, 1.0)
                .applicableEffect(new PoisonEffect("Bad Joke", 3, 15)) // Applies Poison!
                .build());

        // --- WHITE (Melee Stunner) ---
        addSkill(Job.JobType.WHITE, 3, new SkillTemplate.BaseAttack.Builder("Shield Bash", 3, Job.JobType.WHITE)
                .manaCost(20)
                .abilityType(AbilityType.UTILITY)
                .addmultiplier(DamageType.PHYSICAL, 0.5)
                .addmultiplier(DamageType.CC, 1.0)
                .applicableEffect(new StunEffect("Concussed", 1)) // 1 Turn CC!
                .build());

        // (Add the rest of your Barbarian, Wizard, and Demon Hunter skills here using addSkill...)
    }

    // Helper method to keep the initialize block clean
    private static void addSkill(Job.JobType job, int level, SkillTemplate.Ability ability) {
        masterLearnset.get(job).put(level, ability);
    }

    // This is how the Job class safely fetches its specific tree
    public static Map<Integer, SkillTemplate.Ability> getTreeForJob(Job.JobType job) {
        return masterLearnset.getOrDefault(job, new HashMap<>());
    }
}