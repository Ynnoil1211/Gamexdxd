package logic;
public class Item implements Purchasable {
    private String name;
    private String function;
    private int price;

    public Item(String name, String function, int price) {
        this.name = name;
        this.function = function;
        this.price = price;
    }
    public String getName() { return name; }
    public String getFunction() { return function; }
    @Override public int getPrice() { return price; }
}
class Heal extends Item{
    private double heal;

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