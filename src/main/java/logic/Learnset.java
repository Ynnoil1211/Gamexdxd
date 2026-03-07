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

        initBarbarian();
        initBreaker();
        initWizard();
        initVestal();
        initJoker();
        initWhite();
        initDemonHunter();
        initSlayer();
    }

    private static void addSkill(Job.JobType job, int level, SkillTemplate.Ability ability) {
        masterLearnset.get(job).put(level, ability);
    }

    public static Map<Integer, SkillTemplate.Ability> getTreeForJob(Job.JobType job) {
        return masterLearnset.getOrDefault(job, new HashMap<>());
    }

    // ==========================================
    // 1. BARBARIAN (Melee Tank)
    // ==========================================
    private static void initBarbarian() {
        addSkill(Job.JobType.BARBARIAN, 1, new SkillTemplate.BaseAttack.Builder("Heavy Chop", 1, Job.JobType.BARBARIAN)
                .manaCost(5).abilityType(AbilityType.OFFENSIVE)
                .launchBox(new boolean[]{true, true, false, false}).targetBox(new boolean[]{true, true, false, false})
                .addmultiplier(DamageType.PHYSICAL, 1.2).build());

        addSkill(Job.JobType.BARBARIAN, 3, new SkillTemplate.BaseAttack.Builder("Flesh Tear", 3, Job.JobType.BARBARIAN)
                .manaCost(15).abilityType(AbilityType.OFFENSIVE)
                .launchBox(new boolean[]{true, true, false, false}).targetBox(new boolean[]{true, true, false, false})
                .addmultiplier(DamageType.PHYSICAL, 1.0).addmultiplier(DamageType.BLEED, 0.5).build());

        addSkill(Job.JobType.BARBARIAN, 5, new SkillTemplate.BaseAttack.Builder("War Cry", 5, Job.JobType.BARBARIAN)
                .manaCost(20).abilityType(AbilityType.UTILITY)
                .launchBox(new boolean[]{true, true, false, false}).targetBox(new boolean[]{true, true, true, true})
                .addmultiplier(DamageType.CC, 0.5) // Taunts or debuffs enemies
                .isAoE(true).build());

        addSkill(Job.JobType.BARBARIAN, 7, new SkillTemplate.BaseAttack.Builder("Whirlwind", 7, Job.JobType.BARBARIAN)
                .manaCost(30).abilityType(AbilityType.OFFENSIVE)
                .launchBox(new boolean[]{true, true, false, false}).targetBox(new boolean[]{true, true, false, false}) // Hits front row AoE
                .addmultiplier(DamageType.PHYSICAL, 1.5).isAoE(true).build());

        addSkill(Job.JobType.BARBARIAN, 10, new SkillTemplate.BaseAttack.Builder("Noxus Execution", 10, Job.JobType.BARBARIAN)
                .manaCost(45).abilityType(AbilityType.OFFENSIVE)
                .launchBox(new boolean[]{true, true, false, false}).targetBox(new boolean[]{true, true, false, false})
                .addmultiplier(DamageType.PHYSICAL, 3.0).build()); // Massive single target damage
    }

    // ==========================================
    // 2. BREAKER (Melee Stacker)
    // ==========================================
    private static void initBreaker() {
        addSkill(Job.JobType.BREAKER, 1, new SkillTemplate.BaseAttack.Builder("Jab", 1, Job.JobType.BREAKER)
                .manaCost(0).stackGain(2).abilityType(AbilityType.OFFENSIVE)
                .launchBox(new boolean[]{true, true, false, false}).targetBox(new boolean[]{true, true, false, false})
                .addmultiplier(DamageType.PHYSICAL, 0.8).build());

        addSkill(Job.JobType.BREAKER, 3, new SkillTemplate.BaseAttack.Builder("Combo Builder", 3, Job.JobType.BREAKER)
                .manaCost(10).stackGain(4).abilityType(AbilityType.OFFENSIVE)
                .launchBox(new boolean[]{true, true, false, false}).targetBox(new boolean[]{true, true, false, false})
                .addmultiplier(DamageType.PHYSICAL, 1.0).build());

        addSkill(Job.JobType.BREAKER, 5, new SkillTemplate.BaseAttack.Builder("Earth Shatter", 5, Job.JobType.BREAKER)
                .manaCost(20).stackCost(5).abilityType(AbilityType.OFFENSIVE)
                .launchBox(new boolean[]{true, true, false, false}).targetBox(new boolean[]{true, true, true, true})
                .addmultiplier(DamageType.PHYSICAL, 2.0).isAoE(true).build());

        addSkill(Job.JobType.BREAKER, 7, new SkillTemplate.BaseAttack.Builder("Armor Crush", 7, Job.JobType.BREAKER)
                .manaCost(25).stackCost(3).abilityType(AbilityType.OFFENSIVE)
                .launchBox(new boolean[]{true, true, false, false}).targetBox(new boolean[]{true, true, false, false})
                .addmultiplier(DamageType.PHYSICAL, 1.8).addmultiplier(DamageType.TRUE, 0.5).build());

        addSkill(Job.JobType.BREAKER, 10, new SkillTemplate.BaseAttack.Builder("Limit Break", 10, Job.JobType.BREAKER)
                .manaCost(50).stackCost(10).abilityType(AbilityType.OFFENSIVE)
                .launchBox(new boolean[]{true, true, false, false}).targetBox(new boolean[]{true, true, true, true})
                .addmultiplier(DamageType.PHYSICAL, 4.5).isAoE(true).build()); // Consumes max stacks for nuke
    }

    // ==========================================
    // 3. WIZARD (Ranged Mage)
    // ==========================================
    private static void initWizard() {
        addSkill(Job.JobType.WIZARD, 1, new SkillTemplate.BaseAttack.Builder("Arcane Bolt", 1, Job.JobType.WIZARD)
                .manaCost(15).abilityType(AbilityType.OFFENSIVE)
                .launchBox(new boolean[]{false, false, true, true}).targetBox(new boolean[]{true, true, true, true})
                .addmultiplier(DamageType.MAGICAL, 1.5).build());

        addSkill(Job.JobType.WIZARD, 3, new SkillTemplate.BaseAttack.Builder("Mana Drain", 3, Job.JobType.WIZARD)
                .manaCost(0).abilityType(AbilityType.UTILITY)
                .launchBox(new boolean[]{false, false, true, true}).targetBox(new boolean[]{true, true, true, true})
                .addmultiplier(DamageType.MAGICAL, 0.5) // Weak hit, but replenishes mana in engine
                .build());

        addSkill(Job.JobType.WIZARD, 5, new SkillTemplate.BaseAttack.Builder("Fireball", 5, Job.JobType.WIZARD)
                .manaCost(35).abilityType(AbilityType.OFFENSIVE)
                .launchBox(new boolean[]{false, false, true, true}).targetBox(new boolean[]{true, true, true, true})
                .addmultiplier(DamageType.MAGICAL, 1.8).isAoE(true).build());

        addSkill(Job.JobType.WIZARD, 7, new SkillTemplate.BaseAttack.Builder("Blizzard", 7, Job.JobType.WIZARD)
                .manaCost(45).abilityType(AbilityType.OFFENSIVE)
                .launchBox(new boolean[]{false, false, true, true}).targetBox(new boolean[]{true, true, true, true})
                .addmultiplier(DamageType.MAGICAL, 1.2).addmultiplier(DamageType.CC, 0.8) // Dmg + Slow/Freeze
                .isAoE(true).build());

        addSkill(Job.JobType.WIZARD, 10, new SkillTemplate.BaseAttack.Builder("Meteor Swarm", 10, Job.JobType.WIZARD)
                .manaCost(80).abilityType(AbilityType.OFFENSIVE)
                .launchBox(new boolean[]{false, false, true, true}).targetBox(new boolean[]{true, true, true, true})
                .addmultiplier(DamageType.MAGICAL, 3.5).isAoE(true).build());
    }

    // ==========================================
    // 4. VESTAL (Ranged Healer)
    // ==========================================
    private static void initVestal() {
        addSkill(Job.JobType.VESTAL, 1, new SkillTemplate.BaseAttack.Builder("Holy Light", 1, Job.JobType.VESTAL)
                .manaCost(20).abilityType(AbilityType.HEALING)
                .launchBox(new boolean[]{false, false, true, true}).targetBox(new boolean[]{true, true, true, true})
                .addmultiplier(DamageType.HEAL, 1.5).build());

        addSkill(Job.JobType.VESTAL, 3, new SkillTemplate.BaseAttack.Builder("Smite", 3, Job.JobType.VESTAL)
                .manaCost(15).abilityType(AbilityType.OFFENSIVE)
                .launchBox(new boolean[]{false, false, true, true}).targetBox(new boolean[]{true, true, false, false})
                .addmultiplier(DamageType.MAGICAL, 1.2).build());

        addSkill(Job.JobType.VESTAL, 5, new SkillTemplate.BaseAttack.Builder("Aegis Shield", 5, Job.JobType.VESTAL)
                .manaCost(30).abilityType(AbilityType.UTILITY) // Buffs allied defense
                .launchBox(new boolean[]{false, false, true, true}).targetBox(new boolean[]{true, true, true, true})
                .build());

        addSkill(Job.JobType.VESTAL, 7, new SkillTemplate.BaseAttack.Builder("Divine Grace", 7, Job.JobType.VESTAL)
                .manaCost(50).abilityType(AbilityType.HEALING)
                .launchBox(new boolean[]{false, false, true, true}).targetBox(new boolean[]{true, true, true, true})
                .addmultiplier(DamageType.HEAL, 1.2).isAoE(true).build()); // Heals whole team

        addSkill(Job.JobType.VESTAL, 10, new SkillTemplate.BaseAttack.Builder("Resurrection", 10, Job.JobType.VESTAL)
                .manaCost(100).abilityType(AbilityType.HEALING)
                .launchBox(new boolean[]{false, false, true, true}).targetBox(new boolean[]{true, true, true, true})
                .addmultiplier(DamageType.HEAL, 5.0).build()); // Massive single target heal
    }

    // ==========================================
    // 5. JOKER (Ranged Debuffer)
    // ==========================================
    private static void initJoker() {
        addSkill(Job.JobType.JOKER, 1, new SkillTemplate.BaseAttack.Builder("Toxic Prank", 1, Job.JobType.JOKER)
                .manaCost(15).abilityType(AbilityType.OFFENSIVE)
                .launchBox(new boolean[]{false, true, true, true}).targetBox(new boolean[]{true, true, true, true})
                .addmultiplier(DamageType.POISON, 1.0)
                .applicableEffect(new PoisonEffect("Bad Joke", 3, 15)).build());

        addSkill(Job.JobType.JOKER, 3, new SkillTemplate.BaseAttack.Builder("Cruel Mockery", 3, Job.JobType.JOKER)
                .manaCost(20).abilityType(AbilityType.OFFENSIVE)
                .launchBox(new boolean[]{false, true, true, true}).targetBox(new boolean[]{true, true, true, true})
                .addmultiplier(DamageType.TRUE, 1.0).build()); // True damage cuts through armor

        addSkill(Job.JobType.JOKER, 5, new SkillTemplate.BaseAttack.Builder("Laughing Gas", 5, Job.JobType.JOKER)
                .manaCost(35).abilityType(AbilityType.UTILITY)
                .launchBox(new boolean[]{false, true, true, true}).targetBox(new boolean[]{true, true, true, true})
                .addmultiplier(DamageType.POISON, 0.5).isAoE(true)
                .applicableEffect(new PoisonEffect("Gas", 3, 10)).build());

        addSkill(Job.JobType.JOKER, 7, new SkillTemplate.BaseAttack.Builder("Vampiric Trick", 7, Job.JobType.JOKER)
                .manaCost(30).abilityType(AbilityType.OFFENSIVE)
                .launchBox(new boolean[]{false, true, true, true}).targetBox(new boolean[]{true, true, true, true})
                .addmultiplier(DamageType.MAGICAL, 1.5).addmultiplier(DamageType.HEAL, 0.5).build()); // Dmg + Self Heal

        addSkill(Job.JobType.JOKER, 10, new SkillTemplate.BaseAttack.Builder("Pandemonium", 10, Job.JobType.JOKER)
                .manaCost(60).abilityType(AbilityType.OFFENSIVE)
                .launchBox(new boolean[]{false, false, true, true}).targetBox(new boolean[]{true, true, true, true})
                .addmultiplier(DamageType.TRUE, 1.5).isAoE(true).build()); // True damage to entire enemy team
    }

    // ==========================================
    // 6. WHITE (Melee Stunner)
    // ==========================================
    private static void initWhite() {
        addSkill(Job.JobType.WHITE, 1, new SkillTemplate.BaseAttack.Builder("Shield Bash", 1, Job.JobType.WHITE)
                .manaCost(15).abilityType(AbilityType.UTILITY)
                .launchBox(new boolean[]{true, true, false, false}).targetBox(new boolean[]{true, true, false, false})
                .addmultiplier(DamageType.PHYSICAL, 0.5).addmultiplier(DamageType.CC, 1.0)
                .applicableEffect(new StunEffect("Concussed", 1)).build());

        addSkill(Job.JobType.WHITE, 3, new SkillTemplate.BaseAttack.Builder("Holy Strike", 3, Job.JobType.WHITE)
                .manaCost(20).abilityType(AbilityType.OFFENSIVE)
                .launchBox(new boolean[]{true, true, false, false}).targetBox(new boolean[]{true, true, false, false})
                .addmultiplier(DamageType.PHYSICAL, 0.8).addmultiplier(DamageType.MAGICAL, 0.8).build());

        addSkill(Job.JobType.WHITE, 5, new SkillTemplate.BaseAttack.Builder("Blinding Flash", 5, Job.JobType.WHITE)
                .manaCost(35).abilityType(AbilityType.UTILITY)
                .launchBox(new boolean[]{true, true, false, false}).targetBox(new boolean[]{true, true, true, true})
                .addmultiplier(DamageType.CC, 1.5).isAoE(true).build()); // Lowers enemy accuracy/dodge

        addSkill(Job.JobType.WHITE, 7, new SkillTemplate.BaseAttack.Builder("Hammer of Justice", 7, Job.JobType.WHITE)
                .manaCost(40).abilityType(AbilityType.OFFENSIVE)
                .launchBox(new boolean[]{true, true, false, false}).targetBox(new boolean[]{true, true, false, false})
                .addmultiplier(DamageType.PHYSICAL, 2.0).addmultiplier(DamageType.CC, 1.0)
                .applicableEffect(new StunEffect("Stunned", 1)).build());

        addSkill(Job.JobType.WHITE, 10, new SkillTemplate.BaseAttack.Builder("Grand Cross", 10, Job.JobType.WHITE)
                .manaCost(60).abilityType(AbilityType.OFFENSIVE)
                .launchBox(new boolean[]{true, true, false, false}).targetBox(new boolean[]{true, true, true, true})
                .addmultiplier(DamageType.MAGICAL, 2.0).addmultiplier(DamageType.CC, 0.5).isAoE(true).build());
    }

    // ==========================================
    // 7. DEMON HUNTER (Ranged ADC)
    // ==========================================
    private static void initDemonHunter() {
        addSkill(Job.JobType.DEMON_HUNTER, 1, new SkillTemplate.BaseAttack.Builder("Quick Shot", 1, Job.JobType.DEMON_HUNTER)
                .manaCost(10).abilityType(AbilityType.OFFENSIVE)
                .launchBox(new boolean[]{false, true, true, true}).targetBox(new boolean[]{true, true, true, true})
                .addmultiplier(DamageType.PHYSICAL, 1.4).build());

        addSkill(Job.JobType.DEMON_HUNTER, 3, new SkillTemplate.BaseAttack.Builder("Hunter's Mark", 3, Job.JobType.DEMON_HUNTER)
                .manaCost(15).abilityType(AbilityType.UTILITY) // Self buff (Crit or Power increase)
                .launchBox(new boolean[]{false, true, true, true}).targetBox(new boolean[]{true, true, true, true})
                .build());

        addSkill(Job.JobType.DEMON_HUNTER, 5, new SkillTemplate.BaseAttack.Builder("Arrow Volley", 5, Job.JobType.DEMON_HUNTER)
                .manaCost(35).abilityType(AbilityType.OFFENSIVE)
                .launchBox(new boolean[]{false, false, true, true}).targetBox(new boolean[]{true, true, true, true})
                .addmultiplier(DamageType.PHYSICAL, 1.2).isAoE(true).build());

        addSkill(Job.JobType.DEMON_HUNTER, 7, new SkillTemplate.BaseAttack.Builder("Piercing Arrow", 7, Job.JobType.DEMON_HUNTER)
                .manaCost(25).abilityType(AbilityType.OFFENSIVE)
                .launchBox(new boolean[]{false, true, true, true}).targetBox(new boolean[]{false, false, true, true}) // Backline snipe
                .addmultiplier(DamageType.PHYSICAL, 1.8).addmultiplier(DamageType.TRUE, 0.5).build());

        addSkill(Job.JobType.DEMON_HUNTER, 10, new SkillTemplate.BaseAttack.Builder("Kill Shot", 10, Job.JobType.DEMON_HUNTER)
                .manaCost(50).abilityType(AbilityType.OFFENSIVE)
                .launchBox(new boolean[]{false, false, true, true}).targetBox(new boolean[]{true, true, true, true})
                .addmultiplier(DamageType.PHYSICAL, 3.5).build()); // Executing shot
    }

    // ==========================================
    // 8. SLAYER (Assassin)
    // ==========================================
    private static void initSlayer() {
        addSkill(Job.JobType.SLAYER, 1, new SkillTemplate.BaseAttack.Builder("Lacerate", 1, Job.JobType.SLAYER)
                .manaCost(10).abilityType(AbilityType.OFFENSIVE)
                .launchBox(new boolean[]{true, true, true, true}).targetBox(new boolean[]{true, true, false, false})
                .addmultiplier(DamageType.PHYSICAL, 1.2).addmultiplier(DamageType.BLEED, 0.5).build());

        addSkill(Job.JobType.SLAYER, 3, new SkillTemplate.BaseAttack.Builder("Throwing Knives", 3, Job.JobType.SLAYER)
                .manaCost(15).abilityType(AbilityType.OFFENSIVE)
                .launchBox(new boolean[]{false, true, true, true}).targetBox(new boolean[]{true, true, true, true})
                .addmultiplier(DamageType.PHYSICAL, 1.0).isAoE(true).build());

        addSkill(Job.JobType.SLAYER, 5, new SkillTemplate.BaseAttack.Builder("Shadow Step", 5, Job.JobType.SLAYER)
                .manaCost(20).abilityType(AbilityType.UTILITY) // Self buff (+Dodge, +Crit)
                .launchBox(new boolean[]{true, true, true, true}).targetBox(new boolean[]{true, true, true, true})
                .build());

        addSkill(Job.JobType.SLAYER, 7, new SkillTemplate.BaseAttack.Builder("Ambush", 7, Job.JobType.SLAYER)
                .manaCost(30).abilityType(AbilityType.OFFENSIVE)
                .launchBox(new boolean[]{false, true, true, true}).targetBox(new boolean[]{false, false, true, true}) // Back row targeting only
                .addmultiplier(DamageType.PHYSICAL, 2.5).build());

        addSkill(Job.JobType.SLAYER, 10, new SkillTemplate.BaseAttack.Builder("Assassinate", 10, Job.JobType.SLAYER)
                .manaCost(45).abilityType(AbilityType.OFFENSIVE)
                .launchBox(new boolean[]{true, true, false, false}).targetBox(new boolean[]{true, true, true, true})
                .addmultiplier(DamageType.TRUE, 2.5).build()); // Raw True Damage
    }
}