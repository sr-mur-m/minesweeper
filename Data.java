import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Take the current data (date or time).
 * @author  Alberto-Mur (sr.mur.m@gmail.com)
 * @version 1-July-2015
 */
public class Data {
    public static String currentDate() {
        Date currentDate = new Date();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        return format.format(currentDate);
    }
    public static String currentTime() {
        Date currentDate = new Date();
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        return format.format(currentDate);
    }
}
