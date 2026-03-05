package logic;

public enum DamageType {
    PHYSICAL("Physical"),
    MAGICAL("Magical"),
    TRUE("True"),
    HEAL("Healing"),
    POISON("Poison"),
    BLEED("Bleed"),
    CC("Crowd Control");

    private final String displayName;

    // Constructor for the enum
    DamageType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return this.displayName;
    }
}