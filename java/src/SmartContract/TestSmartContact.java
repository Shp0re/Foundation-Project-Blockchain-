package SmartContract;

import SmartContract.Conditions.Condition;
import SmartContract.Conditions.TimeCondition;
import SmartContract.SmartScripts.PrintScript;
import main.java.taxreturns.blockchain.Block;
import main.java.taxreturns.blockchain.Blockchain;
import main.java.taxreturns.blockchain.BlockchainImpl;

import java.text.ParseException;
import java.util.List;

public class TestSmartContact {
    public static void main(String[] args) {

        Blockchain blockchain = new BlockchainImpl();

        List<String> testData = List.of("ImportantInformation", "SensitiveData", "ClassifiedDocuments");

        Block block = new Block(29328102, testData);
        Block block1 = new Block(121838213, testData);

        blockchain.addBlock(block);
        blockchain.addBlock(block1);

        TimeCondition AfterJan1st99 = null;
        try {
            AfterJan1st99 = new TimeCondition("01-01-1999 00:00:00");
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        PrintScript printScript = new PrintScript();

        List<Condition> conditions = List.of(AfterJan1st99);

        SmartFunctions printIfAfterJan1st99 = new SmartFunctions(conditions,printScript);

        List<SmartFunctions> smartFunctions = List.of(printIfAfterJan1st99);

        Contract contract = new Contract(29328102,testData,smartFunctions,null);

        contract.run(blockchain);

//        long millis = System.currentTimeMillis();
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTimeInMillis(millis);
//        System.out.println(calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE));
    }
}
