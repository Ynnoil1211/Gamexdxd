package vista;
import logic.*;
import java.util.ArrayList;

public class Shop {
    private ArrayList<Character> catalogHeroes;
    private ArrayList<Equipment> catalogEquipments;
    private ArrayList<Item> catalogItems;
    public Shop(){}
    public Shop(ArrayList<Character> catalogHeroes, ArrayList<Equipment> catalogEquipments, ArrayList<Item> catalogItems){
        this.catalogEquipments = catalogEquipments;
        this.catalogHeroes = catalogHeroes;
        this.catalogItems = catalogItems;
    }

    public void showHeroesCatalog() {
        System.out.println("--- HEROES CATALOG ---");
        for (Character hero : catalogHeroes) {
            System.out.println("Name: " + hero.getName());
            System.out.println("Base Power: " + hero.getBasePw());
            System.out.println("Max HP: " + hero.getMaxiHp());
            System.out.println("Price: " + hero.getPrice());
        }
    }

    public void showEquipmentCatalog() {
        System.out.println("--- EQUIPMENT CATALOG ---");
        for (Equipment eq : catalogEquipments) {
            System.out.println("Name: " + eq.getName());
            System.out.println("Bonus DMG: " + eq.getBonusDmg());
            System.out.println("Price: " + eq.getPrice());
        }
    }

    public void showItemCatalog() {
        System.out.println("--- ITEM CATALOG ---");
        for (Item it : catalogItems) {
            System.out.println("Name: " + it.getName());
            System.out.println("Function: " + it.getFunction());
            System.out.println("Price: " + it.getPrice());
        }
    }

    public void buyHero(Player player, int index) {
        if (index >= 0 && index < catalogHeroes.size()) {
            Character hero = catalogHeroes.get(index);
            if (player.getWallet().deductBalance(hero.getPrice())) {
                player.addHero(hero);
            }
        }
    }

    public void buyEquipment(Player player, int index) {
        if (index >= 0 && index < catalogEquipments.size()) {
            Equipment eq = catalogEquipments.get(index);
            if (player.getWallet().deductBalance(eq.getPrice())) {
                player.getInventory().add(eq);
                System.out.println("Success: " + eq.getName() + " added to inventory.");
            }
        }
    }
    public void buyItem(Player player, int index) {
        if (index >= 0 && index < catalogItems.size()) {
            Item it = catalogItems.get(index);
            if (player.getWallet().deductBalance(it.getPrice())) {
                player.getItemInventory().add(it);
                System.out.println("Success: " + it.getName() + " added to inventory.");
            }
            }
    }
}
