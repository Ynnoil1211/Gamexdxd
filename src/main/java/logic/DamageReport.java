package logic;
import java.util.ArrayList;
import java.util.List;
enum DamageType {
    PHYSICAL, MAGICAL, TRUE, HEAL
}
public class DamageReport {
    private final String attacker;
    private final String target;
    private final double totalPhysicDmg;
    private final double totalMagicDmg;
    private final double totalDmg;
    private final boolean isDodged;
    private final boolean isCritic;
    private final boolean isKill;

    private DamageReport(Builder builder){
        this.attacker = builder.attacker;
        this.target = builder.target;
        this.totalPhysicDmg = builder.totalPhysicDmg;
        this.totalMagicDmg = builder.totalMagicDmg;
        this.totalDmg = builder.totalDmg;
        this.isDodged = builder.isDodged;
        this.isCritic = builder.isCritic;
        this.isKill = builder.isKill;
    }
    public static class Builder {
        private String attacker;
        private String target;
        private double totalPhysicDmg = 0;
        private double totalMagicDmg = 0;
        private double totalDmg = 0;
        private boolean isDodged = false;
        private boolean isCritic = false;
        private boolean isKill = false;
        public Builder(String attacker, String target){
            this.attacker = attacker;
            this.target = target;
        }
        public Builder totalPhysicDmg(double totalPhysicDmg){this.totalPhysicDmg = totalPhysicDmg; return this;}
        public Builder totalMagicDmg(double totalMagicDmg){this.totalMagicDmg = totalMagicDmg; return this;}
        public Builder totalDmg(double totalDmg){this.totalDmg = totalDmg; return this;}
        public Builder isDodged(boolean isDodged){this.isDodged = isDodged; return this;}
        public Builder isCritic(boolean isCritic){this.isCritic = isCritic; return this;}
        public Builder isKill(boolean isKill){this.isKill = isKill; return this;}

        public DamageReport build(){
            return new DamageReport( this);
        }


    }

}
