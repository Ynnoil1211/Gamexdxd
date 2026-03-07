package logic;

import java.util.Random;

public class EnemyFactory {

    private static final Random random = new Random();

    // A pool of fun, generic enemy names
    private static final String[] ENEMY_NAMES = {
            "Goblin Scout", "Orc Bruiser", "Dark Cultist", "Bandit Rogue",
            "Skeleton Warrior", "Corrupted Knight", "Wild Beast", "Shadow Mage"
    };

    /**
     * Generates a complete, leveled Enemy Party in one line of code!
     * Example: Party enemies = EnemyFactory.createEnemyParty(5, 3); // Three Lv. 5 enemies
     */
    public static Party createEnemyParty(int partyLevel, int numberOfEnemies) {
        Party enemyParty = new Party();

        // Cap the enemies at 4 since we only have Ranks 0, 1, 2, 3
        int actualSize = Math.min(numberOfEnemies, 4);

        for (int i = 0; i < actualSize; i++) {
            // Assign them ranks from front (0) to back (3)
            Person enemy = createRandomEnemy(partyLevel, i);
            enemyParty.addMember(enemy);
        }

        System.out.println("A group of " + actualSize + " enemies appeared!");
        return enemyParty;
    }

    /**
     * Creates a single randomized enemy.
     */
    private static Person createRandomEnemy(int level, int rank) {
        // 1. Pick a random name and Job
        String name = ENEMY_NAMES[random.nextInt(ENEMY_NAMES.length)];
        Job.JobType randomJob = Job.JobType.values()[random.nextInt(Job.JobType.values().length)];

        // 2. Build the base person (Scale their base stats by level!)
        // Assuming you have a Builder for Person. Adjust variable names if yours differ slightly.
        Person enemy = new Person.Builder(name)
                .maxiHp(80 + (level * 15)) // Scales HP with level
                .basePw(10 + (level * 3))     // Scales Power with level
                .baseDefense(5 + (level * 2)) // Scales Defense with level
                .baseMagicPw(10 + (level * 3))
                .speed(10 + level)
                .rank(rank)
                .build();

        // 3. Assign the Job (This automatically applies the stat multipliers!)
        enemy.setJob(new Job(randomJob));

        // 4. Equip random skills from our SkillBook based on their job
        equipRandomSkills(enemy, randomJob);

        return enemy;
    }

    /**
     * Gives the enemy 2 random skills to use in combat.
     */
    private static void equipRandomSkills(Person enemy, Job.JobType job) {
        // Everyone gets a basic attack based on their job's primary damage type
        if (job == Job.JobType.WIZARD || job == Job.JobType.VESTAL) {
            enemy.equipSkill(SkillBook.createMagicBolt(job));
        } else {
            enemy.equipSkill(SkillBook.createHeavyStrike(job));
        }

        // Give them a random secondary "special" skill
        int roll = random.nextInt(4); // Rolls 0, 1, 2, or 3

        switch (roll) {
            case 0:
                enemy.equipSkill(SkillBook.createCleave(job));
                break;
            case 1:
                enemy.equipSkill(SkillBook.createPoisonDart(job));
                break;
            case 2:
                // Only give heals to jobs that make sense, otherwise give a CC skill
                if (job == Job.JobType.VESTAL || job == Job.JobType.WHITE) {
                    enemy.equipSkill(SkillBook.createMinorHeal(job));
                } else {
                    enemy.equipSkill(SkillBook.createShieldBash(job));
                }
                break;
            case 3:
                enemy.equipSkill(SkillBook.createPiercingShot(job));
                break;
        }
    }
}