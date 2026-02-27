package logic;
import vista.*;
public class StatsUtil {
    public static double calculateVariance(double baseValue, double percentage) {
        java.util.Random rand = new java.util.Random();
        double variance = baseValue * percentage;
        return baseValue + (rand.nextDouble((variance * 2) + 1) - variance);
    }

    public static int calculateVarianceInt(int baseValue, double percentage) {
        java.util.Random rand = new java.util.Random();
        int variance = (int) (baseValue * percentage);
        if (variance == 0) return baseValue;
        return baseValue + (rand.nextInt((variance * 2) + 1) - variance);
    }
}

interface Purchasable {
    int getPrice();
}

class CharacterFactory{
    private static final String[] RAGE_NAMES = {
            "BORRE_1", "BORRE_2", "BORRE_3", "BORRE_4", "BORRE_5",
            "BREYNER_1", "BREYNER_2", "BREYNER_3", "BREYNER_4", "BREYNER_5"
    };
    public static Character createRageWarrior() {
        String name = RAGE_NAMES[(int) (Math.random() * RAGE_NAMES.length)];
        double variancePorcentil = 0.15;
        double maxiHp = StatsUtil.calculateVariance(600,variancePorcentil);
        int maxMana = StatsUtil.calculateVarianceInt(0, variancePorcentil);
        double basePw = StatsUtil.calculateVariance(100, variancePorcentil);
        double magicPw = StatsUtil.calculateVariance(0, variancePorcentil);
        double baseDefense = StatsUtil.calculateVariance(70, variancePorcentil);
        double magicDefense = StatsUtil.calculateVariance(60, variancePorcentil);
        double criticChance = StatsUtil.calculateVariance(0.05, variancePorcentil);
        double criticDamage = StatsUtil.calculateVariance(2.0, variancePorcentil);
        double speed = StatsUtil.calculateVariance(12, variancePorcentil);
        double dodge = StatsUtil.calculateVariance(0.1, variancePorcentil);
        int price = StatsUtil.calculateVarianceInt(300, variancePorcentil);
        Ability ability = new RageAbility(0, false);
        Equipment defaultStaff = new Equipment.Builder("Borre's Coding skill (0).").build();
        return new Character.Builder(name).maxiHp(maxiHp).maxMana(maxMana).basePw(basePw).baseMagicPw(magicPw)
                .baseDefense(baseDefense).baseMagicDefense(magicDefense)
                .criticChance(criticChance).criticDamage(criticDamage)
                .speed(speed).dodge(dodge).price(price).ability(ability)
                .equip(defaultStaff).build();
    }
}