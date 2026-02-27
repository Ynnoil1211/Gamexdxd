package logic;
import vista.*;
public class StatsUtil {
    public static double calculateVariance(double baseValue, double percentage) {
        java.util.Random rand = new java.util.Random();
        double variance = baseValue * percentage;
        // Fix: nextDouble() takes no args. Multiply the result instead.
        return baseValue + (rand.nextDouble() * (variance * 2) - variance);
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

