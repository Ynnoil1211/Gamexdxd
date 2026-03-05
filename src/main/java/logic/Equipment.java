package logic;
import vista.*;
interface Special{
    Special specialAction(Character target);
}
public class Equipment implements Purchasable{
    public enum Slot{
        WEAPON, ARMOR, ACCESSORY
    }
    private String name;
    private String description;
    private Slot slot;
    private int bonusHp;
    private int bonusDmg;
    private int bonusMaxMana;
    private int bonusMagicDmg;
    private int bonusDefense;
    private int bonusMagicDefense;
    private double bonusSpeed;
    private double bonusCritChance;
    private double bonusCritDmg;
    private double bonusDodgeChance;
    private double bonusHpRegen;
    private double bonusManaRegen;
    private double bonusLifesteal;
    private StatusEffect onHitEffect;
    private double onHitChance;
    private int price;

    private Equipment(Builder builder){
        this.name = builder.name;
        this.description = builder.description;
        this.slot = builder.slot;
        this.bonusHp = builder.bonusHp;
        this.bonusMaxMana = builder.bonusMaxMana;
        this.bonusDmg = builder.bonusDmg;
        this.bonusMagicDmg = builder.bonusMagicDmg;
        this.bonusDefense = builder.bonusDefense;
        this.bonusMagicDefense = builder.bonusMagicDefense;
        this.bonusSpeed = builder.bonusSpeed;
        this.bonusCritChance = builder.bonusCritChance;
        this.bonusCritDmg = builder.bonusCritDmg;
        this.bonusDodgeChance = builder.bonusDodgeChance;
        this.bonusHpRegen = builder.bonusHpRegen;
        this.bonusManaRegen = builder.bonusManaRegen;
        this.bonusLifesteal = builder.bonusLifesteal;
        this.onHitEffect = builder.onHitEffect;
        this.onHitChance = builder.onHitChance;
        this.price = builder.price;
    }

    public static class Builder{
        private String name = "Borre's Coding skill";
        private String description = "Borre is Gay";
        private Slot slot = Slot.WEAPON;
        private int bonusHp = 0;
        private int bonusMaxMana = 0;
        private int bonusDmg = 0;
        private int bonusMagicDmg = 0;
        private int bonusDefense = 0;
        private int bonusMagicDefense = 0;
        private double bonusSpeed = 0;
        private double bonusCritChance = 0;
        private double bonusCritDmg = 0;
        private double bonusDodgeChance = 0;
        private double bonusHpRegen = 0;
        private double bonusManaRegen= 0;
        private double bonusLifesteal = 0.0;
        private StatusEffect onHitEffect = null;
        private double onHitChance = 0.0;
        private int price = 0;

        public Builder(String name, Slot slot){
            this.name = name;
            this.slot = slot;
        }
        public Builder name(String name) {this.name = name; return this;}
        public Builder description(String description) {this.description = description; return this;}
        public Builder bonusHp(int bonusHp) {this.bonusHp = bonusHp; return this;}
        public Builder bonusDmg(int bonusDmg) {this.bonusDmg = bonusDmg; return this;}
        public Builder bonusMaxMana(int bonusMaxMana) {this.bonusMaxMana = bonusMaxMana; return this;}
        public Builder bonusMagicDmg(int bonusMagicDmg) {this.bonusMagicDmg = bonusMagicDmg; return this;}
        public Builder bonusDefense(int bonusDefense){this.bonusDefense = bonusDefense; return this;}
        public Builder bonusMagicDefense(int bonusMagicDefense) {this.bonusMagicDefense = bonusMagicDefense; return this;}
        public Builder bonusSpeed(double bonusSpeed) {this.bonusSpeed = bonusSpeed; return this;}
        public Builder bonusCritChance(double bonusCritChance) {this.bonusCritChance = bonusCritChance; return this;}
        public Builder bonusCritDmg(double bonusCritDmg) {this.bonusCritDmg = bonusCritDmg; return this;}
        public Builder bonusDodgeChance(double bonusDodgeChance) {this.bonusDodgeChance = bonusDodgeChance; return this;}
        public Builder bonusHpRegen(double bonusHpRegen) {this.bonusHpRegen = bonusHpRegen; return this;}
        public Builder bonusManaRegen(double bonusManaRegen) {this.bonusManaRegen = bonusManaRegen; return this;}
        public Builder lifesteal(double val) { this.bonusLifesteal = val; return this; }
        public Builder price(int price) {this.price = price; return this;}
        public Builder onHitEffect(StatusEffect effect) {this.onHitEffect = effect;return this;}
        public Builder onHitEffect(double chance) {this.onHitChance = chance;return this;}

        public Equipment build(){
            return new Equipment(this);
        }
    }
    public String getEquipmentInfo(){
        StringBuilder info = new StringBuilder();
        info.append("Name: ").append(name).append("\n");
        info.append("Description: ").append(description).append("\n");
        info.append("Usage: ").append(slot).append("\n");

        if (getBonusHp() != 0) info.append("Bonus HP: ").append(getBonusHp()).append("\n");
        if (getBonusMaxMana() != 0) info.append("Bonus Max Mana: ").append(getBonusMaxMana()).append("\n");
        if (getBonusDmg() != 0) info.append("Bonus DMG: ").append(getBonusDmg()).append("\n");
        if (getBonusMagicDmg() != 0) info.append("Bonus Magic DMG: ").append(getBonusMagicDmg()).append("\n");
        if (getBonusDefense() != 0) info.append("Bonus Defense: ").append(getBonusDefense()).append("\n");
        if (getBonusMagicDefense() != 0) info.append("Bonus Magic Defense: ").append(getBonusMagicDefense()).append("\n");
        if (getBonusSpeed() != 0) info.append("Bonus Speed: ").append(getBonusSpeed()).append("\n");
        if (getBonusCritChance() != 0) info.append("Bonus Crit Chance: ").append((int)(getBonusCritChance() * 100)).append("%\n");
        if (getBonusCritDmg() != 0) info.append("Bonus Crit DMG: ").append((int)(getBonusCritDmg() * 100)).append("%\n");
        if (getBonusDodgeChance() != 0) info.append("Bonus Dodge Chance: ").append((int)(getBonusDodgeChance() * 100)).append("%\n");
        if (getBonusLifesteal() != 0) info.append("Bonus Lifesteal: ").append((int)(getBonusLifesteal() * 100)).append("%\n");
        if (getBonusHpRegen() != 0) info.append("Bonus HP Regen: ").append(getBonusHpRegen()).append("\n");
        if (getBonusManaRegen() != 0) info.append("Bonus Mana Regen: ").append(getBonusManaRegen()).append("\n");
        if (getOnHitEffect() != null) {
            info.append("On-Hit Effect: ").append(getOnHitEffect().getName())
                    .append(" (").append((int)(getOnHitChance() * 100)).append("% chance)\n");
        }
        if (getPrice() != 0) info.append("Price: ").append(getPrice()).append("\n");
        return info.toString();
    }

    @Override
    public int getPrice() { return price; }
    public Slot getSlot() { return slot; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public int getBonusHp() { return bonusHp; }
    public int getBonusMaxMana() { return bonusMaxMana; }
    public int getBonusDmg() { return bonusDmg; }
    public int getBonusMagicDmg() { return bonusMagicDmg; }
    public int getBonusDefense() { return bonusDefense; }
    public int getBonusMagicDefense() { return bonusMagicDefense; }
    public double getBonusSpeed() { return bonusSpeed; }
    public double getBonusCritChance() { return bonusCritChance; }
    public double getBonusCritDmg() { return bonusCritDmg; }
    public double getBonusDodgeChance() { return bonusDodgeChance; }
    public double getBonusHpRegen() { return bonusHpRegen; }
    public double getBonusManaRegen() { return bonusManaRegen; }
    public double getBonusLifesteal() { return bonusLifesteal; }
    public StatusEffect getOnHitEffect() { return onHitEffect; }
    public double getOnHitChance() { return onHitChance; }
}




