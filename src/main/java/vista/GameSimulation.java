package vista;

import logic.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GameSimulation {

    // Helper method to pause the console animation
    public static void pause(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // Restore interrupted status
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Initializing Game Systems...\n");
        pause(1000); // 1-second pause

        // 1. Initialize Master Learnset
        Learnset.initialize();
        pause(500);

        // 2. Setup Jobs & Fetch Abilities for Hero
        Job breakerJob = new Job(Job.JobType.BREAKER);
        SkillTemplate.Ability buildMomentum = Learnset.getTreeForJob(Job.JobType.BREAKER).get(1);
        SkillTemplate.Ability earthShatter = Learnset.getTreeForJob(Job.JobType.BREAKER).get(5);

        // 3. Create Hero
        Person lionny = new Person.Builder("Lionny")
                .maxiHp(1200)
                .basePw(150)
                .baseDefense(40)
                .speed(15.0)
                .criticChance(0.2)
                .level(1)
                .currentJob(breakerJob)
                .activeAbilities(buildMomentum, earthShatter)
                .build();

        Equipment mechSword = new Equipment.Builder("Monowire Blade", Equipment.Slot.WEAPON)
                .bonusDmg(40)
                .lifesteal(0.15)
                .build();

        Equipment hotdogSnack = new Equipment.Builder("Cheesy Home Hot Dog", Equipment.Slot.ACCESSORY)
                .bonusHpRegen(0.02)
                .bonusMaxMana(20)
                .build();

        lionny.equipItem(mechSword);
        lionny.equipItem(hotdogSnack);

        // 4. Create Randomized Enemy using CharacterFactory
        System.out.println("\nSpawning a random enemy from the factory...");
        pause(1500); // 1.5-second suspense pause
        Person randomEnemy = CharacterFactory.createRageWarrior();

        // Setup Target Lists for the CombatEngine
        List<Person> heroes = new ArrayList<>();
        heroes.add(lionny);

        List<Person> enemies = new ArrayList<>();
        enemies.add(randomEnemy);

        System.out.println("\n--- FIGHTERS ---");
        System.out.println(lionny.getCharacterInfo());
        System.out.println(randomEnemy.getCharacterInfo());
        pause(2000); // Give the player time to read stats

        // 5. Combat Simulation Loop
        System.out.println("========== COMBAT START ==========\n");
        pause(1000);

        int round = 1;
        while (lionny.isAlive() && randomEnemy.isAlive()) {
            System.out.println(">>> ROUND " + round + " <<<");
            pause(800);

            // --- HERO TURN ---
            if (lionny.isAlive()) {
                System.out.println("-- " + lionny.getName() + "'s Turn --");
                lionny.startTurn();
                lionny.applyHpRegeneration();
                lionny.applyManaRegeneration();

                if (lionny.isAlive()) {
                    System.out.println("\nSelect an ability to use, " + lionny.getName() + ":");
                    SkillTemplate.Ability[] abilities = lionny.getActiveAbilities();
                    for (int i = 0; i < abilities.length; i++) {
                        if (abilities[i] != null) {
                            System.out.println("[" + (i + 1) + "] " + abilities[i].getName());
                        }
                    }

                    System.out.print("Enter skill slot (1-4): ");
                    int choice = scanner.nextInt();
                    System.out.println();

                    lionny.attack(choice, enemies);
                    pause(1500); // Pause after attack so player can read the Combat Report

                    System.out.println(lionny.getName() + " HP: " + lionny.getActuHp() + "/" + lionny.getTotalMaxHp() + " | Stacks: " + lionny.getCurrentJob().getCurrentStack());
                    System.out.println(randomEnemy.getName() + " HP: " + randomEnemy.getActuHp() + "/" + randomEnemy.getTotalMaxHp() + "\n");
                    pause(1500); // Pause before the enemy attacks
                }
            }

            // --- ENEMY TURN ---
            if (randomEnemy.isAlive()) {
                System.out.println("-- " + randomEnemy.getName() + "'s Turn --");
                pause(800);
                randomEnemy.startTurn();
                randomEnemy.applyHpRegeneration();
                randomEnemy.applyManaRegeneration();

                if (randomEnemy.isAlive()) {
                    System.out.println(randomEnemy.getName() + " attacks furiously!");
                    pause(800);
                    randomEnemy.attack(1, heroes);
                    pause(1500); // Pause to read enemy combat report

                    System.out.println(randomEnemy.getName() + " HP: " + randomEnemy.getActuHp() + "/" + randomEnemy.getTotalMaxHp());
                    System.out.println(lionny.getName() + " HP: " + lionny.getActuHp() + "/" + lionny.getTotalMaxHp() + " | Stacks: " + lionny.getCurrentJob().getCurrentStack() + "\n");
                    pause(2000); // Long pause before the next round starts
                }
            }
            round++;

            if (round > 100) {
                System.out.println("The battle dragged on too long and both fighters collapsed from exhaustion!");
                break;
            }
        }

        System.out.println("========== COMBAT END ==========\n");
        pause(1000);

        // 6. Post-Combat Logic
        if (lionny.isAlive() && !randomEnemy.isAlive()) {
            System.out.println(lionny.getName() + " is victorious!");
            pause(800);
            System.out.println("Distributing " + randomEnemy.getPrice() + " EXP...");
            pause(1000);
            lionny.gainExp(randomEnemy.getPrice());

            System.out.println("\n--- FINAL STATS ---");
            System.out.println(lionny.getCharacterInfo());

        } else if (randomEnemy.isAlive() && !lionny.isAlive()) {
            System.out.println(randomEnemy.getName() + " crushed the hero! Game Over.");
        } else if (!lionny.isAlive() && !randomEnemy.isAlive()) {
            System.out.println("Mutual destruction! Both fighters have fallen.");
        }

        scanner.close();
    }
}