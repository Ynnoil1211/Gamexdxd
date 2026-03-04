package logic;

import java.util.HashMap;
import java.util.Map;

public abstract class StatusEffect {
    protected String name;
    protected int duration;
    protected int power;
    protected boolean isCrowdControl;
    protected boolean isDmgHealing;

    protected Map<StatType, Integer> statModifiers = new HashMap<>();

    public StatusEffect(String name, int duration, int power) {
        this.name = name;
        this.duration = duration;
        this.power = power;
        this.isCrowdControl = false;
        this.isDmgHealing = false;
    }

    public void onApply(Character target) {
        System.out.println(target.getName() + " is affected by " + name + "!");
    }

    public abstract void onTick(Character target);

    public void onRemove(Character target) {
        System.out.println(name + " fades from " + target.getName() + ".");
    }

    public boolean tick(Character target) {
        if (duration <= 0) return true; // Return true if expired

        onTick(target); // Execute specific logic
        duration--;

        if (duration <= 0) {
            onRemove(target);
            return true; // Expired
        }
        return false; // Still active
    }

    public void stack(StatusEffect newEffect) {
        this.duration = Math.max(this.duration, newEffect.duration);
    }

    public String getName() { return name; }

    public boolean preventsAction() { return isCrowdControl; }

    public int getStatModifier(StatType stat) {
        return statModifiers.getOrDefault(stat, 0);
    }

    protected void addStatMod(StatType stat, int val) {
        statModifiers.put(stat, val);
    }

    public int getDuration() {
        return duration;
    }

    public int getPower() {
        return power;
    }
}
class PoisonEffect extends StatusEffect {
    public PoisonEffect(String name, int duration, int power) {
        super(name, duration, power);
    }

    @Override
    public void onTick(Character target) {
        System.out.println(target.getName() + " suffers " + power + " poison damage.");
        target.receiveDmg(power);
    }

    @Override
    public void stack(StatusEffect newEffect) {
        super.stack(newEffect); // Reset duration
        this.power += newEffect.power; // Increase damage
        System.out.println("Poison intensifies! Current power: " + this.power);
    }

    public PoisonEffect copy(){
        return new PoisonEffect(this.name, this.duration, this.power);
    }

}

class BleedEffect extends StatusEffect {
    public BleedEffect(String name, int duration, int power) {
        super(name, duration, power);
    }

    @Override
    public void onTick(Character target) {
        System.out.println(target.getName() + " suffers " + power + " bleed damage.");
        target.receiveDmg(power);
    }

    @Override
    public void stack(StatusEffect newEffect) {
        super.stack(newEffect); // Reset duration
        this.power += newEffect.power; // Increase damage
        System.out.println("Hurt intensifies! Current power: " + this.power);
    }

    public BleedEffect copy(){
        return new BleedEffect(this.name, this.duration, this.power);
    }


}

 class StunEffect extends StatusEffect {
    public StunEffect(String name, int duration) {
        super(name, duration, 0);
        this.isCrowdControl = true; // Flags the system to skip turn
    }

    @Override
    public void onTick(Character target) {
        System.out.println(target.getName() + " cannot move!");
    }

    @Override
     public void stack(StatusEffect newEffect){
        super.stack(newEffect);
        this.duration += newEffect.duration;
        System.out.println("Stunning time increase! Current duration: " + this.duration);
    }

     public StunEffect copy(){
         return new StunEffect(this.name, this.duration);
     }

 }

/**
 * class DmgHealingEffect extends StatusEffect{
 *     public DmgHealingEffect(String name, int duration, int power) {
 *         super.(name, duration, power);
 *         this.isDmgHealing = true;
 *     }
 *     @Override
 *     public void onTick(Character target) {
 *         System.out.println(target.getName() + "'s damage will be healing the enemies");
 *         target.receiveHealing(power);
 *     }
 *
 * }
 */
