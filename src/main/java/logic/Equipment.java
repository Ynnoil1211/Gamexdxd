package logic;
import vista.*;
public class Equipment implements Purchasable{
    private String name;
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

    private int price;
    public Equipment(){}

    public Equipment(String name, double bonusHp, double bonusDmg,
                     double bonusMagicDmg, double bonusDefense, double bonusMagicDefense,
                     double bonusSpeed, double bonusCritChance, double bonusDodgeChance,
                     double bonusHpRegen, double bonusManaRegen, int price) {
        this.name = name;
        this.bonusHp = bonusHp;
        this.bonusDmg = bonusDmg;
        this.bonusMagicDmg = bonusMagicDmg;
        this.bonusDefense = bonusDefense;
        this.bonusMagicDefense = bonusMagicDefense;
        this.bonusSpeed = bonusSpeed;
        this.bonusCritChance = bonusCritChance;
        this.bonusDodgeChance = bonusDodgeChance;
        this.bonusHpRegen = bonusHpRegen;
        this.bonusManaRegen = bonusManaRegen;
        this.price = price;
    }

    public String getName() {
        return name;
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

    @Override
    public int getPrice(){
            return price;
        }
    public Equipment clon() {
        return new Equipment(this.name = getName(),
        this.bonusHp = getBonusHp(),
        this.bonusDmg = getBonusDmg(),
        this.bonusMagicDmg = getBonusMagicDmg(),
        this.bonusDefense = getBonusDefense(),
        this.bonusMagicDefense = getBonusMagicDefense(),
        this.bonusSpeed = getBonusSpeed(),
        this.bonusCritChance = getBonusCritChance(),
        this.bonusDodgeChance = getBonusDodgeChance(),
        this.bonusHpRegen = getBonusManaRegen(),
        this.bonusManaRegen = getBonusManaRegen(),
        this.price = getPrice());
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
        private double heal;
        public Heal(){}
        public Heal(String name, String function, int price, double heal){
            super(name, function, price);
            this.heal = heal;
        }

        public double getHeal() {
            return heal;
        }

        public void setHeal(double heal) {
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

