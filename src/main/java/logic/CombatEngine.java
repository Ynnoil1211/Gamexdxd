package logic;

import java.util.Map;

public class CombatEngine {
    private static final double PHYS_BONUS_MULT = 1.015;
    private static final double MAGIC_BONUS_MULT = 1.030;

    public static DamageReport calculateDmg(Character attacker, Character target, SkillTemplate.Ability ability) {
        boolean isDodged = Math.random() < target.getTotalDodge();
        DamageReport.Builder reportBuilder = new DamageReport.Builder(attacker.getName(), target.getName())
                .abilityName(ability.getName())
                .abilityType(ability.getAbilityType())
                .isDodged(isDodged);

        if (isDodged){
            return reportBuilder.build();
        } else {
            Map<DamageType, Double> multipliers = ability.getMultipliers();
            StatusEffect effect = ability.getApplicableEffect();

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

                    case POISON:
                        calculatedAmount = (int) (10 * mult);
                        target.addStatusEffect(new PoisonEffect(effect.getName(), effect.getDuration(), calculatedAmount));
                        break;

                    case BLEED:
                        calculatedAmount = (int) (15 * mult);
                        target.addStatusEffect(new BleedEffect(effect.getName(), effect.getDuration(), calculatedAmount));
                        break;

                    case CC:
                        calculatedAmount = (int) effect.getDuration();
                        target.addStatusEffect(new StunEffect(effect.getName(), effect.getDuration()));
                        break;

                    case HEAL:
                        calculatedAmount = (int) (attacker.getTotalMagicPw() * mult * critMult);
                        target.receiveHealing(calculatedAmount);
                        break;

                }

                reportBuilder.addAmount(type, calculatedAmount);
                if (type != DamageType.HEAL && type != DamageType.POISON && type != DamageType.BLEED && type != DamageType.CC) {
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
}