package logic;

public class CharacterFactory {
    private static final String[] RAGE_NAMES = {
            "BORRE_1", "BORRE_2", "BORRE_3", "BORRE_4", "BORRE_5",
            "BREYNER_1", "BREYNER_2", "BREYNER_3", "BREYNER_4", "BREYNER_5"
    };

    public static Person createRageWarrior() {
        String name = RAGE_NAMES[(int) (Math.random() * RAGE_NAMES.length)];
        double variancePorcentil = 0.15;

        // Assuming StatsUtil remains unchanged in your project structure
        int maxiHp = StatsUtil.calculateVarianceInt(600, variancePorcentil);
        int maxMana = StatsUtil.calculateVarianceInt(50, variancePorcentil);
        int basePw = StatsUtil.calculateVarianceInt(100, variancePorcentil);
        int magicPw = StatsUtil.calculateVarianceInt(0, variancePorcentil);
        int baseDefense = StatsUtil.calculateVarianceInt(70, variancePorcentil);
        int magicDefense = StatsUtil.calculateVarianceInt(60, variancePorcentil);
        double criticChance = StatsUtil.calculateVariance(0.05, variancePorcentil);
        double criticDamage = StatsUtil.calculateVariance(2.0, variancePorcentil);
        double speed = StatsUtil.calculateVariance(12, variancePorcentil);
        double dodge = StatsUtil.calculateVariance(0.1, variancePorcentil);
        int price = StatsUtil.calculateVarianceInt(300, variancePorcentil);

        // 1. Initialize the Job
        Job barbarianJob = new Job(Job.JobType.BARBARIAN);

        // 2. Fetch an appropriate starting ability for the Job
        SkillTemplate.Ability basicStrike = SkillBook.createStrike(Job.JobType.BARBARIAN);

        // 3. Create default equipment with the required Slot parameter
        Equipment defaultStaff = new Equipment.Builder("Borre's Coding skill (0).", Equipment.Slot.WEAPON)
                .build();

        // 4. Build the character using the updated Person.Builder
        return new Person.Builder(name)
                .maxiHp(maxiHp)
                .maxMana(maxMana)
                .basePw(basePw)
                .baseMagicPw(magicPw)
                .baseDefense(baseDefense)
                .baseMagicDefense(magicDefense)
                .criticChance(criticChance)
                .criticDamage(criticDamage)
                .speed(speed)
                .dodge(dodge)
                .price(price)
                .level(1)
                .currentJob(barbarianJob)
                .activeAbilities(basicStrike)
                .equipment(defaultStaff)
                .build();
    }
}