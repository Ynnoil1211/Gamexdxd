package org.example;

import java.util.ArrayList;
import java.util.Calendar;

public class Player {
    private String name;
    private Wallet wallet;
    private ArrayList<Character> ownedHeroes;
    private ArrayList<Equipment> equipmentInventory;
    private ArrayList<Item> itemInventory;
    public Player(String name){
        this.name = name;
        this.wallet = new Wallet();
        this.ownedHeroes = new ArrayList<>();
        this.equipmentInventory = new ArrayList<>();
        this.itemInventory = new ArrayList<>();
    }

    public ArrayList<Item> getItemInventory() {
        return itemInventory;
    }

    public ArrayList<Equipment> getInventory() {
        return equipmentInventory;
    }

    public ArrayList<Character> getOwnedHeroes() {
        return ownedHeroes;
    }

    public Wallet getWallet() {
        return wallet;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void showEquipments(){
        for (Equipment eq : equipmentInventory){
            System.out.println("Name: " + eq.getName());
            System.out.println("Bonus DMG: " + eq.getBonusDmg());
        }
    }
    public void showItems(){
        for (Item it : itemInventory){
            System.out.println("Name: " + it.getName());
            System.out.println("function: " + it.getFunction());
        }
    }
    public void showHeroes(){
        for (int i = 0; i < getOwnedHeroes().size(); i++){
            Character ch = getOwnedHeroes().get(i);
            System.out.print(i+1 + " ");
            System.out.println("Name: " + ch.getName());
            System.out.println("Max HP." + ch.getMaxiHp());
            System.out.println("Base Power: " + ch.getBasePw());
            System.out.println("Actual Equipment: " + ch.getEquip().getName());
            System.out.println("Actual Bonus DMG: " + ch.getEquip().getBonusDmg());
            System.out.println("--------------------");
        }
    }
    public void addHero(Character hero){
        this.ownedHeroes.add(hero.clon());
//        this.ownedHeroes.add(hero);
        System.out.println("Success: " + hero.getName() + " has joined your party");
    }
}
