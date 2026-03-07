package logic;

import java.util.ArrayList;
import java.util.List;

public class Party {
    private Person[] roster;
    public Party() {
        this.roster = new Person[4];
    }
    public boolean addMember(Person person, int rank) {
        if (rank < 0 || rank >= 4) {
            System.out.println("Invalid rank! Must be 0, 1, 2, or 3.");
            return false;
        }
        if (this.roster[rank] != null) {
            System.out.println("Slot " + rank + " is already occupied by " + this.roster[rank].getName());
            return false;
        }

        this.roster[rank] = person;
        person.setRank(rank); // update on attribute
        return true;
    }

    public List<Person> getAliveMembers() {
        List<Person> alive = new ArrayList<>();
        for (Person p : this.roster) {
            if (p != null && p.isAlive()) {
                alive.add(p);
            }
        }
        return alive;
    }

    public void shiftForward() {
        for (int i = 0; i < 3; i++) {
            if (this.roster[i] == null || !this.roster[i].isAlive()) {
                for (int j = i + 1; j < 4; j++) {
                    if (this.roster[j] != null && this.roster[j].isAlive()) {
                        this.roster[i] = this.roster[j];
                        this.roster[i].setRank(i);
                        this.roster[j] = null;
                        break;
                    }
                }
            }
        }
    }
    public Person getMemberAt(int rank) {
        if (rank >= 0 && rank < 4) {
            return this.roster[rank];
        }
        return null;
    }

    public boolean isPartyDefeated() {
        return getAliveMembers().isEmpty();
    }
}