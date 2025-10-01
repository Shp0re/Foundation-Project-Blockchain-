package SmartContract;

import main.java.taxreturns.blockchain.Blockchain;

import java.util.List;
// to do : make so a date and time can be entered
public class TimeCondition implements Condition {

    private final long timeAfter;

    public TimeCondition(long time) {
        timeAfter = time;
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
