import java.util.logging.Level;
import java.util.logging.Logger;

public class Calculon {
    private static final Logger logger = Logger.getLogger("com.calculon");
    public int add(String x, String y) {
        logger.info("Adding " + x + " and " + y);
        try {
            return Integer.parseInt(x) + Integer.parseInt(y);
        } catch (NumberFormatException ex) {
            logger.log(Level.SEVERE, "Parsing failure", ex);
            throw ex;
        }
    }
    public static void main(String[] args) {
        new Calculon().add("1", "2");
    }
}

