package org.example;

public class Main {
    public static void main(String[] args) {
        // 1. Creamos las armas
        Equipment hacha = new Equipment("Hacha de Guerra", 15);
        Equipment baculo = new Equipment("Báculo Arcano", 20);

        // 2. Creamos a los luchadores
        // (String name, int actuHp, int maxiHp, int basePw, Equipment equip)
        RageWarrior conan = new RageWarrior("Conan el Bárbaro", 100, 100, 10, hacha);

        // (String name, int actuHp, int maxiHp, int basePw, int actuMana, Equipment equip)
        MageWarrior gandalf = new MageWarrior("Gandalf", 80, 80, 5, 30, baculo);

        System.out.println("⚔️ ¡COMIENZA EL COMBATE! ⚔️");
        System.out.println("---------------------------------");



        int ronda = 1;

        // 3. El bucle del juego (mientras ambos estén vivos)
        while (conan.alive() && gandalf.alive()) {
            System.out.println("\n--- RONDA " + ronda + " ---");

            // Ataca Conan
            System.out.println(">> Turno de " + conan.getName());
            conan.attack(gandalf);

            // Verificamos si Gandalf sobrevivió antes de que pueda contraatacar
            if (gandalf.alive()) {
                System.out.println("\n>> Turno de " + gandalf.getName());
                gandalf.attack(conan);
            }

            ronda++;
        }

        System.out.println("\n=================================");
        if (conan.alive()) {
            System.out.println("🏆 ¡" + conan.getName() + " ES EL GANADOR! 🏆");
        } else {
            System.out.println("🏆 ¡" + gandalf.getName() + " ES EL GANADOR! 🏆");
        }
    }
}