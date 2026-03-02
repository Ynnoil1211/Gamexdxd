package logic;
public class SkillBooks {
    public static interface Ability {
        DamageReport execute(Character attacker, Character target);
        Ability copy();
        String getAbilityDisplayName();
        boolean[] getValidLaunchRanks();
        boolean[] getValidTargetRanks();
    }
    public static class RageAbility implements Ability {
        private static final double PHYS_BONUS_MULT = 1.015;
        private static final double MAGIC_BONUS_MULT = 1.030;
        private static final int RAGE_GAIN = 15;
        private static final int RAGE_TOTAL = 50;
        private int rage = 0;
        private boolean rageHit = false;

        public RageAbility(int rage, boolean rageHit) {
            this.rage = rage;
            this.rageHit = rageHit;
        }
        // need to be reconstructed.
        @Override
        public DamageReport execute(Character self, Character enemy) {
            int baseDmg = self.getBasePw();
            int baseMagicDmg = self.getBaseMagicPw();
            int enemDefense = enemy.getBaseDefense();
            int enemMagicDefense = enemy.getBaseMagicDefense();
            int bonusDmg = (self.getEquip() != null) ? self.getEquip().getBonusDmg() : 0;
            double criticChance = self.getCriticChance();
            double criticDamage = self.getCriticDamage();
            double dodgeChance = enemy.getDodge();
            int parcialPhysicDmg = baseDmg == 0 ? 0 : (int) (baseDmg + (bonusDmg) * PHYS_BONUS_MULT);
            int parcialMagicDmg = baseMagicDmg == 0 ? 0 : (int) (baseMagicDmg + (bonusDmg) * MAGIC_BONUS_MULT);
            int totalPhysicDmg = (int) (parcialPhysicDmg * (100.0 / (100.0 + enemDefense)));
            int totalMagicDmg = (int) (parcialMagicDmg * (100.0 / (100.0 + enemMagicDefense)));
            int sum = totalPhysicDmg + totalMagicDmg;
            double chanceForCritic = Math.random();
            double chanceForDodge = Math.random();
            boolean isCritic = (chanceForCritic < criticChance);
            boolean isDodged = (chanceForDodge < dodgeChance);
            int totalDmg = isCritic ? (int) (sum * criticDamage) : sum;
            boolean isKill = false;
            if (isDodged) {
                sum = 0;
                totalMagicDmg = 0;
                totalDmg = 0;
                totalPhysicDmg = 0;
            } else {
                rage += RAGE_GAIN;
                if (rage >= RAGE_TOTAL) {
                    rage -= RAGE_TOTAL;
                    rageHit = true;
                }
                if (rageHit) {
                    totalDmg *= 2;
                    rageHit = false;
                }
                enemy.reciDmg(totalDmg);
                if (!enemy.isAlive()) {
                    isKill = true;
                }
            }
            return new DamageReport.Builder(self.getName(), enemy.getName()).totalPhysicDmg(totalPhysicDmg)
                    .totalMagicDmg(totalMagicDmg).totalDmg(totalDmg).isDodged(isDodged).isCritic(isCritic).isKill(isKill).build();
        }

        @Override
        public RageAbility copy() {
            return new RageAbility(rage, rageHit);
        }

        @Override
        public String getAbilityDisplayName() {
            return "Fuaaaaah (Rage).";
        }

        @Override
        public boolean[] getValidLaunchRanks() {
            return new boolean[]{true, true, false, false};
        }

        @Override
        public boolean[] getValidTargetRanks() {
            return new boolean[]{true, true, false, false};
        }

        public int getRage() {
            return rage;
        }

        public void setRage(int rage) {
            this.rage = rage;
        }

    }

}