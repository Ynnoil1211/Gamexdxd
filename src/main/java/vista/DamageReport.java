package vista;
import logic.*;
import java.util.ArrayList;
import java.util.List;
enum DamageType {
    PHYSICAL, MAGICAL, TRUE, HEAL
}
public class DamageReport {


    // --- 1. DATA FIELDS (Private & Final) ---
    private final String attackerName;
    private final String targetName;
    private final int damageDealt;
    private final int targetRemainingHp;

    // Status Flags
    private final boolean isCritical;
    private final boolean isDodge;
    private final boolean isKill;
    private final DamageType type;

    // The "Black Box" log for your console details
    private final List<String> combatLog;

    // --- 2. PRIVATE CONSTRUCTOR (Only the Builder can use this) ---
    private DamageReport(Builder builder) {
        this.attackerName = builder.attackerName;
        this.targetName = builder.targetName;
        this.damageDealt = builder.damageDealt;
        this.targetRemainingHp = builder.targetRemainingHp;
        this.isCritical = builder.isCritical;
        this.isDodge = builder.isDodge;
        this.isKill = builder.isKill;
        this.type = builder.type;
        this.combatLog = builder.combatLog;
    }

    // --- 3. GETTERS (No Setters - It's Read Only) ---
    public String getAttackerName() { return attackerName; }
    public String getTargetName() { return targetName; }
    public int getDamageDealt() { return damageDealt; }
    public int getTargetRemainingHp() { return targetRemainingHp; }
    public boolean isCritical() { return isCritical; }
    public boolean isDodge() { return isDodge; }
    public boolean isKill() { return isKill; }
    public DamageType getType() { return type; }
    public List<String> getCombatLog() { return combatLog; }

    // --- 4. THE BUILDER ---
    public static class Builder {
        // Required parameters
        private String attackerName;
        private String targetName;

        // Optional parameters (Initialized to defaults)
        private int damageDealt = 0;
        private int targetRemainingHp = 0;
        private boolean isCritical = false;
        private boolean isDodge = false;
        private boolean isKill = false;
        private DamageType type = DamageType.PHYSICAL;
        private List<String> combatLog = new ArrayList<>();

        // Constructor starts with the mandatory info
        public Builder(String attackerName, String targetName) {
            this.attackerName = attackerName;
            this.targetName = targetName;
        }

        // Fluent Setters
        public Builder damage(int val) { this.damageDealt = val; return this; }
        public Builder remainingHp(int val) { this.targetRemainingHp = val; return this; }
        public Builder critical(boolean val) { this.isCritical = val; return this; }
        public Builder dodge(boolean val) { this.isDodge = val; return this; }
        public Builder kill(boolean val) { this.isKill = val; return this; }
        public Builder type(DamageType val) { this.type = val; return this; }

        // Powerful method to add logs one by one
        public Builder addLog(String message) {
            this.combatLog.add(message);
            return this;
        }

        public DamageReport build() {
            return new DamageReport(this);
        }
    }
}
