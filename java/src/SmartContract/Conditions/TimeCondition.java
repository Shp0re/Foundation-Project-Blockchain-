package SmartContract.Conditions;

import main.java.taxreturns.blockchain.Blockchain;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeCondition implements Condition {

    private final long timeAfter;

    public TimeCondition(String time) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
        Date date = sdf.parse(time);
        timeAfter = date.getTime();
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
