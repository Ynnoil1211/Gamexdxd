package vista;
import logic.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // 1. Crear catalogos
        ArrayList<Equipment> armas = new ArrayList<>();
        armas.add(new Equipment("Hacha de Hierro", 15, 100));
        armas.add(new Equipment("Baculo Mistico", 20, 150));

        ArrayList<Item> items = new ArrayList<>();
        items.add(new Heal("Pocion Menor", "Cura 30 HP", 20, 30));
        items.add(new Poison("Veneno de Arana", "Hace 15 de dano", 50, 15));

        ArrayList<Character> heroes = new ArrayList<>();
        Equipment armaBasica = new Equipment("Manos desnudas", 0, 0);

        // Usamos los 5 argumentos que tiene tu constructor actual.
        // Nota: Como no pusiste el precio en el constructor de RageWarrior, el Shop leera el precio como 0.
        heroes.add(new RageWarrior("Brbaro Novato", 100, 10, armaBasica, 300,10.6));
        heroes.add(new MageWarrior("Mago Aprendiz", 80, 5, 50, armaBasica, 250,40));

        Shop tienda = new Shop(heroes, armas, items);
        Player jugador = new Player("Jugador 1");

        boolean jugando = true;

        System.out.println("Bienvenido a la Tienda del Gremio, " + jugador.getName() + "!");

        while (jugando) {
            System.out.println("\n=================================");
            System.out.println("Tu Billetera: " + jugador.getWallet().getBalance() + " monedas");
            System.out.println("1. Ver mi Inventario y Equipo");
            System.out.println("2. Ver Catalogo de Heroes");
            System.out.println("3. Ver Catalogo de Armas");
            System.out.println("4. Ver Catalogo de Items");
            System.out.println("5. Equipar a un Heroe");
            System.out.println("6. Salir");
            System.out.print("Elige una opcion: ");

            String opcion = scanner.nextLine();

            switch (opcion) {
                case "1":
                    System.out.println("\n--- MI EQUIPO ---");
                    jugador.showHeroes();
                    System.out.println("\n--- MIS ARMAS ---");
                    jugador.showEquipments();
                    break;
                case "2":
                    tienda.showHeroesCatalog();
                    System.out.print("\nIntroduce el numero del heroe a comprar : ");
                    try {
                        int indice = Integer.parseInt(scanner.nextLine());
                        indice--;
                        tienda.buyHero(jugador, indice);
                    } catch (NumberFormatException e) {
                        System.out.println("Operacion cancelada.");
                    }
                    break;
                case "3":
                    tienda.showEquipmentCatalog();
                    System.out.print("\nIntroduce el numero del arma a comprar : ");
                    try {
                        int indice = Integer.parseInt(scanner.nextLine());
                        indice--;
                        tienda.buyEquipment(jugador, indice);
                    } catch (NumberFormatException e) {
                        System.out.println("Operacion cancelada.");
                    }
                    break;
                case "4":
                    tienda.showItemCatalog();
                    // Falta implementar tienda.buyItem(jugador, indice);
                    break;
                case "5":
                    System.out.println("\n--- CAMBIO DE EQUIPAMIENTO ---");
                    if (jugador.getOwnedHeroes().isEmpty()) {
                        System.out.println("Aun no tienes heroes en tu equipo.");
                        break;
                    }
                    if (jugador.getInventory().isEmpty()) {
                        System.out.println("Aun no tienes armas en tu inventario para equipar.");
                        break;
                    }

                    // Paso A: Elegir el heroe
                    //xdxd.
                    System.out.println("Selecciona un heroe:");
                    for (int i = 0; i < jugador.getOwnedHeroes().size(); i++) {
                        Character h = jugador.getOwnedHeroes().get(i);
                        System.out.println(i+1 + ". " + h.getName() + " (Arma actual: " + h.getEquip().getName() + ")");
                    }
                    System.out.print("Numero del heroe: ");
                    int heroIdx = Integer.parseInt(scanner.nextLine());
                    heroIdx--;

                    // Paso B: Elegir el arma nueva
                    System.out.println("\nSelecciona el arma de tu inventario:");
                    for (int i = 0; i < jugador.getInventory().size(); i++) {
                        System.out.println(i+1 + ". " + jugador.getInventory().get(i).getName() + " (Daño: +" + jugador.getInventory().get(i).getBonusDmg() + ")");
                    }
                    System.out.print("Numero del arma: ");
                    int eqIdx = Integer.parseInt(scanner.nextLine());
                    eqIdx--;

                    // Paso C: Hacer el intercambio
                    Character heroeSeleccionado = jugador.getOwnedHeroes().get(heroIdx);
                    Equipment armaNueva = jugador.getInventory().get(eqIdx);
                    Equipment armaVieja = heroeSeleccionado.getEquip();

                    heroeSeleccionado.setEquip(armaNueva); // Le damos el arma nueva al heroe
                    jugador.getInventory().remove(eqIdx);  // Sacamos el arma nueva del inventario
                    jugador.getInventory().add(armaVieja); // Guardamos el arma vieja en el inventario

                    System.out.println("Exito: " + heroeSeleccionado.getName() + " ahora esta equipado con " + armaNueva.getName());
                    break;
                case "6":
                    jugando = false;
                    System.out.println("Guardando y saliendo...");
                    break;
                default:
                    System.out.println("Opcion no valida.");
            }
        }
        scanner.close();
    }
}