import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by andrey on 16.02.2017.
 */
public class DateUtil {
    final DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    int interval = 1000; // 1000 ms
    Calendar now = Calendar.getInstance();

    public String getCurrentDate() {
        new Timer(interval, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(dateFormat.format(now.getTime()));
            }
        }).start();

        try {
            Thread.currentThread().join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return dateFormat.format(now.getTime());
    }
}
