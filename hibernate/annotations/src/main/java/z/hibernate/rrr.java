package z.hibernate;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.*;


public class rrr {

    public static void main(String[] args) {
        makeSomeTransactionRecords(600);

    }

    static void makeSomeTransactionRecords(int count){
        Random random = new Random();

        for (int i = 0; i < count; i++) {
            System.out.println("insert into Transaction (datetime, sender_account_id, receiver_account_id, amount) values ( "
                    +"'"+dateTime() + "', "
                    +(random.nextInt(105)+1) + ", "
                    +(random.nextInt(105)+1) + ", "
                    +(random.nextInt(100000)+1)
                    +" );");
        }
    }

    // 2008-10-23 10:37:22

    static void makeSomeLoanRecords(int count){
        Random random = new Random();
        int get;
        int set;
        int repaid;

        for (int i = 0; i < count; i++) {
            get = (random.nextInt(1000000)+1000);
            set = (random.nextInt(1000000)+1000);

            if (get <= set){
                set = get;
                repaid = 1;
            } else repaid = 0;

            System.out.println("insert into Loan (person_id, amount, date, rate, period, repaid_amount, is_repaid) values ( "
                    +(random.nextInt(105)+1) + ", "
                    +get + ", '"
                    +date() + "', "
                    +(random.nextInt(105)+1) + ", "
                    +randomVal() + ", "
                    +set + ", "
                    +repaid
                    +" );");
        }
    }

    static String date(){
        Random random = new Random();
        return ""+(random.nextInt(122)+1900)+"-"+(random.nextInt(11)+1)+"-"+(random.nextInt(27)+1);
    }

    static String dateTime(){
        Random random = new Random();
        String s = ""+(random.nextInt(52)+1970)+"-"+(random.nextInt(11)+1)+"-"+(random.nextInt(27)+1);
        s +=" " + (random.nextInt(23)+1) + ":" + (random.nextInt(59)+1) + ":" + (random.nextInt(59)+1);
        return s;
    }


    static String randomVal(){
        MathContext context = new MathContext(2, RoundingMode.HALF_UP);
        double value = ((double)  (new Random().nextInt(80)+1) / (new Random().nextInt(33)+1));

        if (value < 1){
            value +=1;
        }

        BigDecimal result = new BigDecimal(value, context);

        return result.toString();
    }


}

