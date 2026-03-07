package logic;

public class SkillBook {

    // ==========================================
    // 1. SINGLE TARGET STRIKES (Offensive)
    // ==========================================

    // Standard Melee Front-to-Front
    public static SkillTemplate.BaseAttack createHeavyStrike(Job.JobType job) {
        return new SkillTemplate.BaseAttack.Builder("Heavy Strike", 1, job)
                .manaCost(0)
                .addmultiplier(DamageType.PHYSICAL, 1.5)
                .targetBox(new boolean[]{true, true, false, false})
                .launchBox(new boolean[]{true, true, false, false})
                .abilityType(AbilityType.OFFENSIVE)
                .build();
    }

    // Ranged Armor Piercing (Hits anyone, launched from anywhere)
    public static SkillTemplate.BaseAttack createPiercingShot(Job.JobType job) {
        return new SkillTemplate.BaseAttack.Builder("Piercing Shot", 1, job)
                .manaCost(10)
                .addmultiplier(DamageType.PHYSICAL, 1.2)
                .addmultiplier(DamageType.TRUE, 0.3) // Sneaks in true damage
                .targetBox(new boolean[]{true, true, true, true})
                .launchBox(new boolean[]{true, true, true, true})
                .abilityType(AbilityType.OFFENSIVE)
                .build();
    }

    // Basic Magic Bolt (Ranged, scales with magic)
    public static SkillTemplate.BaseAttack createMagicBolt(Job.JobType job) {
        return new SkillTemplate.BaseAttack.Builder("Magic Bolt", 1, job)
                .manaCost(15)
                .addmultiplier(DamageType.MAGICAL, 1.6)
                .targetBox(new boolean[]{true, true, true, true})
                .launchBox(new boolean[]{false, false, true, true})
                .abilityType(AbilityType.OFFENSIVE)
                .build();
    }

    // ==========================================
    // 2. AREA OF EFFECT (AoE)
    // ==========================================

    // Front-Row Melee Cleave
    public static SkillTemplate.BaseAttack createCleave(Job.JobType job) {
        return new SkillTemplate.BaseAttack.Builder("Cleave", 1, job)
                .manaCost(15)
                .addmultiplier(DamageType.PHYSICAL, 1.0)
                .targetBox(new boolean[]{true, true, false, false}) // Only hits the front row
                .launchBox(new boolean[]{true, true, false, false})
                .abilityType(AbilityType.OFFENSIVE)
                .isAoE(true) // Remember our AoE flag!
                .build();
    }

    // Full Party Magic Blast
    public static SkillTemplate.BaseAttack createFireStorm(Job.JobType job) {
        return new SkillTemplate.BaseAttack.Builder("Fire Storm", 1, job)
                .manaCost(40)
                .addmultiplier(DamageType.MAGICAL, 1.8)
                .targetBox(new boolean[]{true, true, true, true})
                .launchBox(new boolean[]{false, false, true, true})
                .abilityType(AbilityType.OFFENSIVE)
                .isAoE(true)
                .build();
    }

    // Full Party Physical Rain (Like an Arrow Volley)
    public static SkillTemplate.BaseAttack createArrowRain(Job.JobType job) {
        return new SkillTemplate.BaseAttack.Builder("Arrow Rain", 1, job)
                .manaCost(30)
                .addmultiplier(DamageType.PHYSICAL, 1.2)
                .targetBox(new boolean[]{true, true, true, true})
                .launchBox(new boolean[]{false, false, true, true})
                .abilityType(AbilityType.OFFENSIVE)
                .isAoE(true)
                .build();
    }

    // ==========================================
    // 3. HEALING
    // ==========================================

    // Small, cheap single-target heal
    public static SkillTemplate.BaseAttack createMinorHeal(Job.JobType job) {
        return new SkillTemplate.BaseAttack.Builder("Minor Heal", 1, job)
                .manaCost(15)
                .addmultiplier(DamageType.HEAL, 1.0)
                .targetBox(new boolean[]{true, true, true, true})
                .launchBox(new boolean[]{false, false, true, true}) // Usually from backline
                .abilityType(AbilityType.HEALING)
                .build();
    }

    // Huge single-target emergency heal
    public static SkillTemplate.BaseAttack createGreatHeal(Job.JobType job) {
        return new SkillTemplate.BaseAttack.Builder("Great Heal", 1, job)
                .manaCost(40)
                .addmultiplier(DamageType.HEAL, 2.5)
                .targetBox(new boolean[]{true, true, true, true})
                .launchBox(new boolean[]{false, false, true, true})
                .abilityType(AbilityType.HEALING)
                .build();
    }

    // Party-wide AoE Heal
    public static SkillTemplate.BaseAttack createGroupHeal(Job.JobType job) {
        return new SkillTemplate.BaseAttack.Builder("Group Heal", 1, job)
                .manaCost(50)
                .addmultiplier(DamageType.HEAL, 1.2)
                .targetBox(new boolean[]{true, true, true, true})
                .launchBox(new boolean[]{false, false, true, true})
                .abilityType(AbilityType.HEALING)
                .isAoE(true) // Hits whole party
                .build();
    }

    // ==========================================
    // 4. STATUS EFFECTS (Utility / Debuff)
    // ==========================================

    // Ranged Poison application
    public static SkillTemplate.BaseAttack createPoisonDart(Job.JobType job) {
        return new SkillTemplate.BaseAttack.Builder("Poison Dart", 1, job)
                .manaCost(20)
                .addmultiplier(DamageType.PHYSICAL, 0.5) // Tiny initial damage
                .addmultiplier(DamageType.POISON, 1.0)
                .applicableEffect(new PoisonEffect("Venom", 3, 15)) // 3 turns, 15 dmg
                .targetBox(new boolean[]{true, true, true, true})
                .launchBox(new boolean[]{false, true, true, true})
                .abilityType(AbilityType.OFFENSIVE)
                .build();
    }

    // Melee Stun application
    public static SkillTemplate.BaseAttack createShieldBash(Job.JobType job) {
        return new SkillTemplate.BaseAttack.Builder("Shield Bash", 1, job)
                .manaCost(25)
                .addmultiplier(DamageType.PHYSICAL, 0.8)
                .addmultiplier(DamageType.CC, 1.0)
                .applicableEffect(new StunEffect("Dizzied", 1)) // Miss 1 turn
                .targetBox(new boolean[]{true, true, false, false})
                .launchBox(new boolean[]{true, true, false, false})
                .abilityType(AbilityType.UTILITY)
                .build();
    }

    // Melee Bleed application (Great for assassins/beasts)
    public static SkillTemplate.BaseAttack createRupture(Job.JobType job) {
        // Assuming you make a BleedEffect class similar to Poison
        return new SkillTemplate.BaseAttack.Builder("Rupture", 1, job)
                .manaCost(15)
                .addmultiplier(DamageType.PHYSICAL, 1.2)
                .addmultiplier(DamageType.BLEED, 1.0)
                // .applicableEffect(new BleedEffect("Bleeding", 2, 20)) // Uncomment if you have BleedEffect
                .targetBox(new boolean[]{true, true, false, false})
                .launchBox(new boolean[]{true, true, false, false})
                .abilityType(AbilityType.OFFENSIVE)
                .build();
    }
}