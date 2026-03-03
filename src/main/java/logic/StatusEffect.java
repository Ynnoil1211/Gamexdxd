package logic;

public class StatusEffect {
    public static enum EffectType { POISON, BLEED, REGEN, STUN }

    private final EffectType type;
    private final int power;
    private int duration;

    public StatusEffect(EffectType type, int power, int duration) {
        this.type = type;
        this.power = power;
        this.duration = duration;
    }

    public void processEffect(Character target) {
        if (duration <= 0) return;
        switch (type) {
            case POISON:
                System.out.println(target.getName() + " suffers " + power + " Poison damage!");
                target.receiveDmg(power); //Bypasses armor...
                break;
            case BLEED:
                System.out.println(target.getName() + " takes " + power + " " + type + " damage!");
                target.receiveDmg(power);
                break;
            case REGEN:
                System.out.println(target.getName() + " regenerates " + power + " HP.");
                target.receiveHealing(power);
                break;
            case STUN:
                System.out.println(target.getName() + " is STUNNED!");
                break;
        }
        duration--;
        if (duration == 0) {
            System.out.println("The " + type + " effect on " + target.getName() + " has worn off.");
        }
    }

    public EffectType getType() { return type; }
    public int getDuration() { return duration; }
    public int getPower() { return power; }

    public StatusEffect copy() {
        return new StatusEffect(this.type, this.power, this.duration);
    }
}