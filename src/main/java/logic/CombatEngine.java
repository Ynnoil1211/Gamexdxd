package logic;

import java.util.Map;

public class CombatEngine {
    private static final double PHYS_BONUS_MULT = 1.015;
    private static final double MAGIC_BONUS_MULT = 1.030;

    public static DamageReport calculateDmg(Character attacker, Character target, Map<DamageType, Double> multipliers, StatusEffect effect) {
        boolean isDodged = Math.random() < target.getDodge();
        DamageReport.Builder reportBuilder = new DamageReport.Builder(attacker.getName(), target.getName())
                .isDodged(isDodged);

        if (isDodged) return reportBuilder.build();

        boolean isCritic = Math.random() < attacker.getCriticChance();
        reportBuilder.isCritic(isCritic);
        double critMult = isCritic ? attacker.getCriticDamage() : 1.0;

        int bonusPhys = (attacker.getEquip() != null) ? attacker.getEquip().getBonusDmg() : 0;
        int bonusMagic = (attacker.getEquip() != null) ? attacker.getEquip().getBonusMagicDmg() : 0;

        int totalDamageToDeal = 0;

        // --- DYNAMIC DAMAGE LOOP ---
        for (Map.Entry<DamageType, Double> entry : multipliers.entrySet()) {
            DamageType type = entry.getKey();
            double mult = entry.getValue();

            if (mult <= 0) continue;

            int calculatedAmount = 0;

            switch (type) {
                case PHYSICAL:
                    double rawPhys = (attacker.getBasePw() + bonusPhys) * mult * PHYS_BONUS_MULT * critMult;
                    calculatedAmount = (int) (rawPhys * (100.0 / (100.0 + target.getBaseDefense()))); // Armor reduces it
                    break;

                case MAGICAL:
                    double rawMagic = (attacker.getBaseMagicPw() + bonusMagic) * mult * MAGIC_BONUS_MULT * critMult;
                    calculatedAmount = (int) (rawMagic * (100.0 / (100.0 + target.getBaseMagicDefense()))); // Magic Armor reduces it
                    break;

                case TRUE:
                    calculatedAmount = (int) (attacker.getBasePw() * mult * critMult);
                    break;

                case HEAL:
                    calculatedAmount = (int) (attacker.getBaseMagicPw() * mult * critMult);
                    target.receiveHealing(calculatedAmount); // You might want to make a specific target.heal(amount) method later!
                    break;

                case POISON:
                    calculatedAmount = (int) (10 * mult);
                    target.addStatusEffect(new StatusEffect(StatusEffect.EffectType.POISON, calculatedAmount, 2));
                    break;
            }

            reportBuilder.addAmount(type, calculatedAmount);

            if (type != DamageType.HEAL) {
                totalDamageToDeal += calculatedAmount;
            }
        }

       //dmgDealing
        if (totalDamageToDeal > 0) {
            target.receiveDmg(totalDamageToDeal);
        }
        if (effect != null) {
            target.addStatusEffect(effect); // Adds it to the Character's active effects list
            reportBuilder.appliedEffect(effect); // Tells the report to print it
        }

        reportBuilder.isKill(!target.isAlive());

        return reportBuilder.build();
    }
}