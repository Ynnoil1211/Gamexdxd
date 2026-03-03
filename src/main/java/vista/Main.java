package vista;
import logic.*;
import logic.Character; // To avoid confusion with java.lang.Character
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== INITIALIZING GAME ===");

        // 1. Create Data for the Shop
        ArrayList<Character> shopHeroes = new ArrayList<>();
        shopHeroes.add(new Character.Builder("Ynnoil").maxiHp(1000).maxMana(2000).basePw(1000).baseDefense(1575).dodge(0.5).ability(new SkillTemplate.RageAbility(0, false)).price(500).build());
        shopHeroes.add(CharacterFactory.createRageWarrior());
        shopHeroes.add(CharacterFactory.createRageWarrior());

        ArrayList<Equipment> shopEquips = new ArrayList<>();
        shopEquips.add(new Equipment.Builder("Sword of Thousand Truths")
                .bonusDmg(45).bonusCritChance(0.15).price(350).build());
        shopEquips.add(new Equipment.Builder("Heavy Iron Armor")
                .bonusDefense(50).bonusHp(150).price(200).build());

        ArrayList<Item> shopItems = new ArrayList<>();
        shopItems.add(new Item("Health Potion", "Heals 100 HP", 50));

        // 2. Setup Shop and Player
        Shop shop = new Shop(shopHeroes, shopEquips, shopItems);
        Player player1 = new Player("PlayerOne");

        System.out.println("\n=== WELCOME TO THE SHOP ===");
        System.out.println("Wallet Balance: " + player1.getWallet().getBalance() + " Gold");

        shop.showHeroesCatalog();
        System.out.println();
        shop.showEquipmentCatalog();

        // 3. Player Buys Things
        System.out.println("\n=== TRANSACTION PHASE ===");
        shop.buyHero(player1, 0); // Buys the first hero
        shop.buyEquipment(player1, 0); // Buys the sword

        System.out.println("\n=== PLAYER INVENTORY ===");
        player1.showHeroes();
        player1.showEquipments();

        // 4. Simulate a Battle
        System.out.println("\n=== BATTLE SIMULATION ===");
        if (!player1.getOwnedHeroes().isEmpty()) {
            // Get the hero the player just bought
            Character myHero = player1.getOwnedHeroes().get(0);

            // Generate an enemy directly from the factor
            Character enemy = CharacterFactory.createRageWarrior();
            System.out.println("A wild " + enemy.getName() + " appears with " + enemy.getMaxiHp() + " HP!");

            // Execute an attack
            if (myHero.getAbility() != null) {
                myHero.attack(enemy);
            } else {
                System.out.println(myHero.getName() + " has no ability equipped!");
            }
        } else {
            System.out.println("Player has no heroes to battle with!");
        }
    }
}