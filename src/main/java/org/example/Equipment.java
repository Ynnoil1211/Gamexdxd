package org.example;

public class Equipment implements Purchasable{
        private String name;
        private int bonusDmg;
        private int price;
        public Equipment(){}
        public Equipment(String name, int bonusDmg, int price) {
            this.name = name;
            this.bonusDmg = bonusDmg;
            this.price = price;
        }

        public int getBonusDmg() {
            return bonusDmg;
        }

        public void setBonusDmg(int bonusDmg) {
            this.bonusDmg = bonusDmg;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        @Override
        public int getPrice(){
            return price;
        }
        public org.example.Equipment clon() {
            return new org.example.Equipment(this.getName(), this.getBonusDmg(), this.getPrice());
        }
    }
    class Item implements Purchasable{
        private String name;
        private String function;
        private int price;
        public Item(){}
        public Item(String name, String function, int price){
            this.name = name;
            this.function = function;
            this.price = price;
        }
        @Override
        public int getPrice() {
            return price;
        }

        public String getFunction() {
            return function;
        }

        public void setFunction(String function) {
            this.function = function;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
    class Heal extends Item{
        private int heal;
        public Heal(){}
        public Heal(String name, String function, int price, int heal){
            super(name, function, price);
            this.heal = heal;
        }

        public int getHeal() {
            return heal;
        }

        public void setHeal(int heal) {
            this.heal = heal;
        }
    }
    class Poison extends Item{
        private int dmg;
        public Poison(){}
        public Poison(String name, String function, int price, int dmg){
            super(name, function, price);
            this.dmg = dmg;
        }
        public int getDmg() {
            return dmg;
        }
        public void setDmg(int dmg) {
            this.dmg = dmg;
        }
    }

