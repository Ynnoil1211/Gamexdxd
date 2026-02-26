package logic;
import vista.*;
public interface Ability {
    DamageReport execute(Character attacker, Character target);
    Ability copy();
}
