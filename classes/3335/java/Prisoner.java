import java.util.HashSet;
import java.util.Set;

public class Prisoner {

    private String prisonId;
    
    public Prisoner(final String prisonId) {
        this.prisonId = prisonId;
    }
    
    public static void main(String[] args) {
        Set<Prisoner> roll = new HashSet<>();
        
        roll.add(new Prisoner("123"));
        roll.add(new Prisoner("123"));
        System.out.println(roll.size());
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((prisonId == null) ? 0 : prisonId.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Prisoner other = (Prisoner) obj;
        if (prisonId == null) {
            if (other.prisonId != null) {
                return false;
            }
        } else if (!prisonId.equals(other.prisonId)) {
            return false;
        }
        return true;
    }
}
