package logic;
import java.util.HashMap;
import java.util.Map;

public class DamageReport {
    private final String attacker;
    private final String target;
    private final Map<DamageType, Integer> amounts;
    private final StatusEffect appliedEffect;
    private final int lifestealAmount;
    private final int totalDmg;
    private final boolean isDodged;
    private final boolean isCritic;
    private final boolean isKill;

    private DamageReport(Builder builder) {
        this.attacker = builder.attacker;
        this.target = builder.target;
        this.amounts = builder.amounts;
        this.lifestealAmount = builder.lifestealAmount;
        this.totalDmg = builder.totalDmg;
        this.appliedEffect = builder.appliedEffect;
        this.isDodged = builder.isDodged;
        this.isCritic = builder.isCritic;
        this.isKill = builder.isKill;
    }

    public static class Builder {
        private String attacker;
        private String target;
        private Map<DamageType, Integer> amounts = new HashMap<>();
        private int lifestealAmount = 0;
        private int totalDmg = 0;
        private StatusEffect appliedEffect = null;
        private boolean isDodged = false;
        private boolean isCritic = false;
        private boolean isKill = false;

        public Builder(String attacker, String target) {
            this.attacker = attacker;
            this.target = target;
        }

        public Builder addAmount(DamageType type, int amount) {
            if (amount > 0) {
                this.amounts.put(type, this.amounts.getOrDefault(type, 0) + amount);
                if (type != DamageType.HEAL) {
                    this.totalDmg += amount;
                }
            }
            return this;
        }

        public Builder appliedEffect(StatusEffect effect) {
            this.appliedEffect = effect;
            return this;
        }
        public Builder lifesteal(int amount) {
            this.lifestealAmount = amount;
            return this;
        }
        public Builder isDodged(boolean isDodged) { this.isDodged = isDodged; return this; }
        public Builder isCritic(boolean isCritic) { this.isCritic = isCritic; return this; }
        public Builder isKill(boolean isKill) { this.isKill = isKill; return this; }

        public DamageReport build() {
            return new DamageReport(this);
        }
    }

    public void combatReport() {
        StringBuilder log = new StringBuilder();

        log.append("=== COMBAT REPORT ===\n");
        log.append("Attacker: ").append(this.attacker).append("\n");
        log.append("Target: ").append(this.target).append("\n");

        if (this.isDodged) {
            log.append("Result: DODGEEEEEEEEEDDD! QWQ\n");
        } else {
            if (this.isCritic) log.append("CRITIC HIT!\n");

            for (Map.Entry<DamageType, Integer> entry : amounts.entrySet()) {
                log.append("- ").append(entry.getKey()).append(" Amount: ").append(entry.getValue()).append("\n");
            }


            if (this.totalDmg > 0) {
                log.append("Total Damage Dealt: ").append(this.totalDmg).append("\n");
            }

            if (this.lifestealAmount > 0){
                log.append("Lifesteal: ").append(this.lifestealAmount).append("\n");
            }
            if (this.appliedEffect != null) {
                log.append("AFFLICTED: Target is suffering from ")
                        .append(this.appliedEffect.getName()) // e.g., POISON
                        .append(" for ")
                        .append(this.appliedEffect.getDuration()) // e.g., 3
                        .append(" turns!\n");
            }

            if (this.isKill) log.append("Result: KILLLLLLLEDDDDDD.\n");
        }
        log.append("=====================\n");

        System.out.println(log.toString());
    }
}
