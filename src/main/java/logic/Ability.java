package logic;

public interface Ability {
    DamageReport execute(Character attacker, Character target);
    Ability copy();
}
class RageAbility implements Ability{
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
    @Override
    public DamageReport execute(Character self, Character enemy) {
        double baseDmg = self.getBasePw();
        double baseMagicDmg = self.getBaseMagicPw();
        double enemDefense = enemy.getBaseDefense();
        double enemMagicDefense = enemy.getBaseMagicDefense();
        double bonusDmg = (self.getEquip() != null) ? self.getEquip().getBonusDmg() : 0.0;
        double criticChance = self.getCriticChance();
        double criticDamage = self.getCriticDamage();
        double dodgeChance = enemy.getDodge();
        double parcialPhysicDmg = (baseDmg==0? 0 : (baseDmg + (bonusDmg)*PHYS_BONUS_MULT));
        double parcialMagicDmg = (baseMagicDmg==0? 0 : (baseMagicDmg + (bonusDmg)*MAGIC_BONUS_MULT));
        double totalPhysicDmg = parcialPhysicDmg*(100.0/(100.0+enemDefense));
        double totalMagicDmg = parcialMagicDmg*(100.0/(100.0+enemMagicDefense));
        double sum = totalPhysicDmg + totalMagicDmg;
        double chanceForCritic = Math.random();
        double chanceForDodge = Math.random();
        boolean isCritic = (chanceForCritic<criticChance);
        boolean isDodged = (chanceForDodge<dodgeChance);
        double totalDmg = (isCritic?  (sum*criticDamage) : sum);
        boolean isKill = false;
        if(isDodged) {
            sum = 0;
            totalMagicDmg = 0;
            totalDmg = 0;
            totalPhysicDmg = 0;
        }
        else {
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
        return new DamageReport.Builder(self.getName(),enemy.getName()).totalPhysicDmg(totalPhysicDmg)
                .totalMagicDmg(totalMagicDmg).totalDmg(totalDmg).isDodged(isDodged).isCritic(isCritic).isKill(isKill).build();
    }

    @Override
    public RageAbility copy() {
        return new RageAbility(rage, rageHit);
    }

    public int getRage() {
        return rage;
    }
    public void setRage(int rage) {
        this.rage = rage;
    }

}

