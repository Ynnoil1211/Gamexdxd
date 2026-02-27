package logic;
import vista.*;
interface Special{
    Special specialAction(Character target);
}
public class Equipment implements Purchasable{
    private String name;
    private String description;
    private double bonusHp;
    private double bonusDmg;
    private double bonusMagicDmg;
    private double bonusDefense;
    private double bonusMagicDefense;
    private double bonusSpeed;
    private double bonusCritChance;
    private double bonusDodgeChance;
    private double bonusHpRegen;
    private double bonusManaRegen;
    //--/
    private int price;
    public Equipment(){}

    private Equipment(Builder builder){
        this.name = builder.name;
        this.description = builder.description;
        this.bonusHp = builder.bonusHp;
        this.bonusDmg = builder.bonusDmg;
        this.bonusMagicDmg = builder.bonusMagicDmg;
        this.bonusDefense = builder.bonusDefense;
        this.bonusMagicDefense = builder.bonusMagicDefense;
        this.bonusSpeed = builder.bonusSpeed;
        this.bonusCritChance = builder.bonusCritChance;
        this.bonusDodgeChance = builder.bonusDodgeChance;
        this.bonusHpRegen = builder.bonusHpRegen;
        this.bonusManaRegen = builder.bonusManaRegen;
        this.price = builder.price;
    }

    public static class Builder{
        private String name = "Borre's Coding skill";
        private String description = "Borre is Gay";
        private double bonusHp = 0;
        private double bonusDmg = 0;
        private double bonusMagicDmg = 0;
        private double bonusDefense = 0;
        private double bonusMagicDefense = 0;
        private double bonusSpeed = 0;
        private double bonusCritChance = 0;
        private double bonusDodgeChance = 0;
        private double bonusHpRegen = 0;
        private double bonusManaRegen= 0;
        private int price = 0;

        public Builder(String name){
            this.name = name;
        }
        public Builder name(String name) {this.name = name; return this;}
        public Builder description(String description) {this.description = description; return this;}
        public Builder bonusHp(double bonusHp) {this.bonusHp = bonusHp; return this;}
        public Builder bonusDmg(double bonusDmg) {this.bonusDmg = bonusDmg; return this;}
        public Builder bonusMagicDmg(double bonusMagicDmg) {this.bonusMagicDmg = bonusMagicDmg; return this;}
        public Builder bonusDefense(double bonusDefense){this.bonusDefense = bonusDefense; return this;}
        public Builder bonusMagicDefense(double bonusMagicDefense) {this.bonusMagicDefense = bonusMagicDefense; return this;}
        public Builder bonusSpeed(double bonusSpeed) {this.bonusSpeed = bonusSpeed; return this;}
        public Builder bonusCritChance(double bonusCritChance) {this.bonusCritChance = bonusCritChance; return this;}
        public Builder bonusDodgeChance(double bonusDodgeChance) {this.bonusDodgeChance = bonusDodgeChance; return this;}
        public Builder bonusHpRegen(double bonusHpRegen) {this.bonusHpRegen = bonusHpRegen; return this;}
        public Builder bonusManaRegen(double bonusManaRegen) {this.bonusManaRegen = bonusManaRegen; return this;}
        public Builder price(int price) {this.price = price; return this;}

        public Equipment build(){
            return new Equipment(this);
        }
    }

    @Override
    public int getPrice(){
        return price;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public double getBonusHp() {
        return bonusHp;
    }

    public double getBonusDmg() {
        return bonusDmg;
    }

    public double getBonusMagicDmg() {
        return bonusMagicDmg;
    }

    public double getBonusDefense() {
        return bonusDefense;
    }

    public double getBonusMagicDefense() {
        return bonusMagicDefense;
    }

    public double getBonusSpeed() {
        return bonusSpeed;
    }

    public double getBonusCritChance() {
        return bonusCritChance;
    }

    public double getBonusDodgeChance() {
        return bonusDodgeChance;
    }

    public double getBonusHpRegen() {
        return bonusHpRegen;
    }

    public double getBonusManaRegen() {
        return bonusManaRegen;
    }
}




