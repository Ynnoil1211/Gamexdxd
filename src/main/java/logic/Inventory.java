package logic;

import java.util.ArrayList;
import java.util.List;

public class Inventory {
    private final int maxCapacity;
    private final List<Equipment> items; // Currently holds Equipment, can be changed to an Item interface later

    public Inventory(int maxCapacity) {
        this.maxCapacity = maxCapacity;
        this.items = new ArrayList<>();
    }

    // --- ADDING AND REMOVING ---

    public boolean addItem(Equipment item) {
        if (item == null) return false;

        if (this.items.size() < this.maxCapacity) {
            this.items.add(item);
            System.out.println("Added " + item.getName() + " to inventory.");
            return true;
        } else {
            System.out.println("Inventory is FULL! Cannot carry " + item.getName() + ".");
            return false;
        }
    }

    public Equipment removeItem(int index) {
        if (index >= 0 && index < this.items.size()) {
            Equipment removed = this.items.remove(index);
            return removed;
        }
        System.out.println("Invalid inventory slot.");
        return null;
    }

    public Equipment removeItem(Equipment item) {
        if (this.items.remove(item)) {
            return item;
        }
        return null;
    }

    // --- UI DISPLAY ---

    public void displayInventory() {
        System.out.println("\n=== INVENTORY (" + items.size() + "/" + maxCapacity + ") ===");
        if (items.isEmpty()) {
            System.out.println("  (Empty)");
        } else {
            for (int i = 0; i < items.size(); i++) {
                Equipment eq = items.get(i);
                System.out.println(" [" + i + "] " + eq.getName() + " (" + eq.getSlot() + ")");
            }
        }
        System.out.println("=====================\n");
    }

    // --- GETTERS ---

    public List<Equipment> getItems() {
        return items;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public boolean isFull() {
        return items.size() >= maxCapacity;
    }
}
//public void equipFromInventory(int inventoryIndex) {
//    if (inventoryIndex < 0 || inventoryIndex >= inventory.getItems().size()) {
//        System.out.println("Invalid item slot!");
//        return;
//    }
//
//    // 1. Get the item from inventory (don't remove it yet)
//    Equipment itemToEquip = inventory.getItems().get(inventoryIndex);
//
//    // 2. Check if we already have something in that slot
//    Equipment oldItem = unequipItem(itemToEquip.getSlot());
//
//    // 3. Remove the new item from the inventory and equip it
//    inventory.removeItem(inventoryIndex);
//    equipItem(itemToEquip);
//    System.out.println(this.name + " equipped " + itemToEquip.getName() + "!");
//
//    // 4. Put the old item into the inventory
//    if (oldItem != null) {
//        inventory.addItem(oldItem);
//        System.out.println(oldItem.getName() + " was returned to inventory.");
//    }
//}
//
//// Add a quick getter so menus can access it
//public Inventory getInventory() {
//    return inventory;
//}