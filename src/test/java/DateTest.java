import org.junit.Test;

import java.text.ParseException;
import java.util.Date;

/**
 * Created by LynAs on 21-Mar-16
 */
public class DateTest {
    @Test
    public void getTodayStartAndEndTime() throws ParseException {
        Date date = new Date();
        date.setHours(00);
        date.setMinutes(00);
        date.setSeconds(00);
        System.out.println(date);
        date.setHours(23);
        date.setMinutes(59);
        date.setSeconds(59);
        System.out.println(date);
    }


    @Test
    public void test(){
        String aaa = new String("sazzad");
        String bbb = aaa;

        bbb = bbb + "sss";

        System.out.println(bbb);
        System.out.println(aaa);


        Date date = new Date();

        System.out.println(date);
        Date other = new Date(date.getTime());
        other.setHours(00);
        other.setMinutes(00);
        other.setSeconds(00);
        System.out.println(other);
        System.out.println(date);

    }

}
