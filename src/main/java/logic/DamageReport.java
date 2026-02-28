package logic;
import java.util.ArrayList;
import java.util.List;
enum DamageType {
    PHYSICAL, MAGICAL, TRUE, HEAL
}
public class DamageReport {
    private final String attacker;
    private final String target;
    private final int totalPhysicDmg;
    private final int totalMagicDmg;
    private final int totalDmg;
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
        private int totalPhysicDmg = 0;
        private int totalMagicDmg = 0;
        private int totalDmg = 0;
        private boolean isDodged = false;
        private boolean isCritic = false;
        private boolean isKill = false;
        public Builder(String attacker, String target){
            this.attacker = attacker;
            this.target = target;
        }
        public Builder totalPhysicDmg(int totalPhysicDmg){this.totalPhysicDmg = totalPhysicDmg; return this;}
        public Builder totalMagicDmg(int totalMagicDmg){this.totalMagicDmg = totalMagicDmg; return this;}
        public Builder totalDmg(int totalDmg){this.totalDmg = totalDmg; return this;}
        public Builder isDodged(boolean isDodged){this.isDodged = isDodged; return this;}
        public Builder isCritic(boolean isCritic){this.isCritic = isCritic; return this;}
        public Builder isKill(boolean isKill){this.isKill = isKill; return this;}

        public DamageReport build(){
            return new DamageReport( this);
        }
    }
    public void combateReport(){
        StringBuilder log = new StringBuilder();
        if(this.isDodged){
            log.append("DODGEEEEEEEEEDDD! QWQ \n");
        }
        else{
            log.append("Attacker: " + this.attacker + "\n");
            log.append("Target: " + this.target + "\n");
            if(totalPhysicDmg>0) log.append("Total Physic Damage dealed: " + this.totalPhysicDmg + "\n");
            if(totalMagicDmg>0) log.append("\nTotal Magic Damage dealed: " + this.totalMagicDmg + "\n");
            log.append("\nTotal Damage dealed: " + this.totalDmg + "\n");
            if(isCritic) log.append("CRITIC HIT! \n");
            if(isKill) log.append("KILLLLLLLEDDDDDD." +"\n");
        }
        System.out.println(log.toString());
    }

}
