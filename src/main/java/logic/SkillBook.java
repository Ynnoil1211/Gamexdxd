package logic;

public class SkillBook {
    public static SkillTemplate.BaseAttack createStrike(Job.JobType job) {
        return new SkillTemplate.BaseAttack.Builder("Heavy Strike", 1, job)
                .manaCost(0)
                .addmultiplier(DamageType.PHYSICAL, 2)
                .targetBox(new boolean[]{true, true, false, false})
                .launchBox(new boolean[]{true, true, false, false})
                .abilityType(AbilityType.OFFENSIVE)
                .build();
    }

    public static SkillTemplate.BaseAttack creaAoE(Job.JobType job) {
        return new SkillTemplate.BaseAttack.Builder("AoE", 1, job)
                .manaCost(40)
                .addmultiplier(DamageType.MAGICAL, 1.8)
                .targetBox(new boolean[]{true, true, true, true})
                .launchBox(new boolean[]{false, false, true, true})
                .abilityType(AbilityType.OFFENSIVE)
                .build();
    }

    public static SkillTemplate.BaseAttack createCurar(Job.JobType job) {
        return new SkillTemplate.BaseAttack.Builder("Curar", 1, job)
                .manaCost(30)
                .addmultiplier(DamageType.HEAL, 1.2)
                .targetBox(new boolean[]{true, true, true, true})
                .launchBox(new boolean[]{false, false, true, true})
                .abilityType(AbilityType.HEALING)
                .build();
    }

    public static SkillTemplate.BaseAttack createPoison(Job.JobType job) {
        StatusEffect poison = new PoisonEffect("Venom", 3, 10);
        return new SkillTemplate.BaseAttack.Builder("Poison", 1, job)
                .manaCost(25)
                .addmultiplier(DamageType.PHYSICAL, 0.8)
                .addmultiplier(DamageType.POISON, 1.0)
                .applicableEffect(poison)
                .targetBox(new boolean[]{true, false, false, false})
                .launchBox(new boolean[]{true, true, false, false})
                .build();
    }
}

