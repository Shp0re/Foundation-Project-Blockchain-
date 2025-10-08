package main.java.taxreturns.smartContract.conditions;

import main.java.taxreturns.blockchain.Blockchain;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeCondition implements Condition {

    private final long timeAfter;
    private String timeString;

    public TimeCondition(String time) throws ParseException {
        timeString = time;
        SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
        Date date = sdf.parse(time);
        timeAfter = date.getTime();
    }

    @Override
    public String name() {
        return "TimeCondition";
    }

    public String output() {
        return ("Check if the latest block was created after " + timeString + "\n");
    }

    public Boolean check(Blockchain blockchain) {
        boolean result = false;
        long lastestTime = blockchain.getLatestBlock().getTimeStamp();
        if (lastestTime > timeAfter) {
            result = true;
        }
        return result;
    }
}
