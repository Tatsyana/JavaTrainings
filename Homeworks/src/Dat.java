import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * @author Tatsyana.
 */
public class Dat {
    public static void main(String[] args) {

        Calendar today = java.util.Calendar.getInstance();
        System.out.println(today.getTime());
        today.add(Calendar.DATE, 200);
        today.add(Calendar.MONTH, 2);
        today.add(Calendar.YEAR, 5);
        System.out.println(today.getTime());

    }
}
