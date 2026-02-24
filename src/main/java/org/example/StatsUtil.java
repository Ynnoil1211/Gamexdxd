package org.example;

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
class CharacterFactory{
    private static final String[] MAGE_NAMES = {
            "Merlin", "Gandalf", "Vivi", "Medivh", "Jaina",
            "Ryze", "Lux", "Veigar", "Kael'thas", "Antonidas",
            "Sypha", "Yennefer", "Triss", "Saruman", "Dumbledore"
    };
    public static MageWarrior createMageWarrior() {
        String name = MAGE_NAMES[(int) (Math.random() * MAGE_NAMES.length)];
        double hp = StatsUtil.calculateVariance(400,0.15);
        int maxMana = StatsUtil.calculateVarianceInt(100, 0.15);
        double basePw = StatsUtil.calculateVariance(10, 0.15);
        double magicPw = StatsUtil.calculateVariance(100, 0.15);
        double baseDefense = StatsUtil.calculateVariance(50, 0.15);
        double magicDefense = StatsUtil.calculateVariance(60, 0.15);
        double criticChance = StatsUtil.calculateVariance(0.05, 0.15);
        double speed = StatsUtil.calculateVariance(15, 0.15);
        double dodge = StatsUtil.calculateVariance(0.1, 0.20);
        int price = StatsUtil.calculateVarianceInt(300, 0.15);
        Equipment defaultStaff = new Equipment("Novice Staff");
        return new MageWarrior(name, hp, basePw, magicPw, baseDefense, magicDefense, criticChance, speed, dodge, price, defaultStaff);
    }
}