package vista;

import logic.*;
import java.util.List;
import java.util.Scanner;

public class GameSimulation {

    // Helper method to pause the console animation
    public static void pause(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public static void clearConsole() {
        try {
            String os = System.getProperty("os.name");
            if (os.contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (Exception e) {
            // Fallback for IDEs that don't support console clearing
            for (int i = 0; i < 50; i++) {
                System.out.println();
            }
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        clearConsole();
        System.out.println("Initializing Game Systems...\n");
        pause(1000);

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

        // 4. Create Randomized Enemy
        System.out.println("Spawning a random enemy from the factory...");
        pause(1000);
        Person randomEnemy = CharacterFactory.createRageWarrior();

        // --- NEW: SETUP PARTIES WITH RANKS ---
        Party heroParty = new Party();
        Party enemyParty = new Party();

        // Put Lionny in the Front Row (Rank 0)
        heroParty.addMember(lionny, 0);
        // Put Enemy in the Front Row (Rank 0)
        enemyParty.addMember(randomEnemy, 0);

        clearConsole();
        System.out.println("--- FIGHTERS ---");
        System.out.println(lionny.getCharacterInfo());
        System.out.println(randomEnemy.getCharacterInfo());
        System.out.println("\nPress ENTER to begin combat...");
        scanner.nextLine();

        // 5. Combat Simulation Loop
        int round = 1;

        // Loop continues as long as BOTH parties have at least one alive member
        while (!heroParty.isPartyDefeated() && !enemyParty.isPartyDefeated()) {
            clearConsole();
            System.out.println("========== COMBAT START ==========");
            System.out.println(">>> ROUND " + round + " <<<\n");
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

                    System.out.print("\nEnter skill slot (1-4): ");
                    int choice = scanner.nextInt();
                    System.out.println();

                    // PASS THE ALIVE ENEMIES LIST TO THE ATTACK
                    lionny.attack(choice, enemyParty.getAliveMembers());
                    pause(2500); // Give player time to read the Combat Report

                    System.out.println("\n" + lionny.getName() + " HP: " + lionny.getActuHp() + "/" + lionny.getTotalMaxHp() + " | Stacks: " + lionny.getCurrentJob().getCurrentStack());
                    System.out.println(randomEnemy.getName() + " HP: " + randomEnemy.getActuHp() + "/" + randomEnemy.getTotalMaxHp() + "\n");
                    pause(1500);
                }
            }

            // --- SHIFT RANKS IF ANYONE DIED ---
            enemyParty.shiftForward();

            // --- ENEMY TURN ---
            // Check if enemy party is defeated before taking their turn
            if (!enemyParty.isPartyDefeated() && randomEnemy.isAlive()) {
                System.out.println("\n-- " + randomEnemy.getName() + "'s Turn --");
                pause(800);
                randomEnemy.startTurn();
                randomEnemy.applyHpRegeneration();
                randomEnemy.applyManaRegeneration();

                if (randomEnemy.isAlive()) {
                    System.out.println(randomEnemy.getName() + " attacks furiously!\n");
                    pause(800);

                    // PASS THE ALIVE HEROES LIST
                    randomEnemy.attack(1, heroParty.getAliveMembers());
                    pause(2500);

                    System.out.println("\n" + randomEnemy.getName() + " HP: " + randomEnemy.getActuHp() + "/" + randomEnemy.getTotalMaxHp());
                    System.out.println(lionny.getName() + " HP: " + lionny.getActuHp() + "/" + lionny.getTotalMaxHp() + "\n");
                    pause(2000);
                }
            }

            // --- SHIFT RANKS IF ANYONE DIED ---
            heroParty.shiftForward();

            round++;

            if (round > 100) {
                System.out.println("The battle dragged on too long and both fighters collapsed from exhaustion!");
                break;
            }
        }

        clearConsole();
        System.out.println("========== COMBAT END ==========\n");
        pause(1000);

        // 6. Post-Combat Logic
        if (!heroParty.isPartyDefeated() && enemyParty.isPartyDefeated()) {
            System.out.println(lionny.getName() + " is victorious!");
            pause(800);
            System.out.println("Distributing " + randomEnemy.getPrice() + " EXP...");
            pause(1000);
            lionny.gainExp(randomEnemy.getPrice());

            System.out.println("\n--- FINAL STATS ---");
            System.out.println(lionny.getCharacterInfo());

        } else if (!enemyParty.isPartyDefeated() && heroParty.isPartyDefeated()) {
            System.out.println(randomEnemy.getName() + " crushed the hero! Game Over.");
        } else {
            System.out.println("Mutual destruction! Both fighters have fallen.");
        }

        scanner.close();
    }
}