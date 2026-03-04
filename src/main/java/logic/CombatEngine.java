package logic;

import java.util.Map;

public class CombatEngine {
    private static final double PHYS_BONUS_MULT = 1.015;
    private static final double MAGIC_BONUS_MULT = 1.030;

    public static DamageReport calculateDmg(Character attacker, Character target, Map<DamageType, Double> multipliers, StatusEffect effect) {
        boolean isDodged = Math.random() < target.getTotalDodge();
        DamageReport.Builder reportBuilder = new DamageReport.Builder(attacker.getName(), target.getName())
                .isDodged(isDodged);

        if (isDodged) return reportBuilder.build();

        boolean isCritic = Math.random() < attacker.getTotalCriticChance();
        reportBuilder.isCritic(isCritic);

        double critMult = isCritic ? attacker.getTotalCriticDamage() : 1.0;

        int totalDamageToDeal = 0;

        // --- DYNAMIC DAMAGE LOOP ---
        for (Map.Entry<DamageType, Double> entry : multipliers.entrySet()) {
            DamageType type = entry.getKey();
            double mult = entry.getValue();

            if (mult <= 0) continue;

            int calculatedAmount = 0;

            switch (type) {
                case PHYSICAL:
                    double rawPhys = attacker.getTotalPw() * mult * PHYS_BONUS_MULT * critMult;
                    calculatedAmount = (int) (rawPhys * (100.0 / (100.0 + target.getTotalDefense())));
                    break;

                case MAGICAL:
                    double rawMagic = attacker.getTotalMagicPw() * mult * MAGIC_BONUS_MULT * critMult;
                    calculatedAmount = (int) (rawMagic * (100.0 / (100.0 + target.getTotalMagicDefense())));
                    break;

                case TRUE:
                    calculatedAmount = (int) (attacker.getTotalPw() * mult * critMult);
                    break;

                case HEAL:
                    calculatedAmount = (int) (attacker.getTotalMagicPw() * mult * critMult);
                    target.receiveHealing(calculatedAmount);
                    break;

                case POISON:
                    calculatedAmount = (int) (10 * mult);
                    target.addStatusEffect(new PoisonEffect("Poison", 2, calculatedAmount));
                    break;
            }

            reportBuilder.addAmount(type, calculatedAmount);
            if (type != DamageType.HEAL && type != DamageType.POISON) {
                totalDamageToDeal += calculatedAmount;
            }
        }

        if (totalDamageToDeal > 0) {
            target.receiveDmg(totalDamageToDeal);
        }
        double lifestealPct = attacker.getTotalLifesteal();
        if (lifestealPct > 0.0) {
            int healAmount = (int) (totalDamageToDeal * lifestealPct);
            attacker.receiveHealing(healAmount);
            reportBuilder.lifesteal(healAmount);
        }
        if (effect != null) {
            target.addStatusEffect(effect);
            reportBuilder.appliedEffect(effect);
        }

        reportBuilder.isKill(!target.isAlive());

        return reportBuilder.build();
    }
}