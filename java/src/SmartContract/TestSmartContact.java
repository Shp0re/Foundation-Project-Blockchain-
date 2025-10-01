package SmartContract;

import main.java.taxreturns.blockchain.Blockchain;

import java.util.Calendar;

public class TestSmartContact {
    public static void main(String[] args) {
        long millis = System.currentTimeMillis();
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millis);
        System.out.println(calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE));
    }
}
