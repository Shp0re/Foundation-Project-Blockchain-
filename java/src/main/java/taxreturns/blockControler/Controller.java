package main.java.taxreturns.blockControler;
import main.java.taxreturns.blockchain.Block;
import main.java.taxreturns.blockchain.BlockchainImpl;
import java.security.InvalidParameterException;
import java.util.*;

public class Controller{
    // Account Identifier Map (AIM)
    private final MultiValueHashMap<String, String> AIM = new MultiValueHashMap<>();
    private final HashMap<String, MultiChannel> orgMap = new HashMap<String, MultiChannel>();

    public Controller(){
    }

    private MultiChannel getSpecificItem(String identifier){
        MultiChannel item = this.orgMap.get(identifier);
        if(item == null){
            throw new NoSuchElementException();
        }else{
            return item;
        }
    }

    public void addAccount(String[] newAccountData){
        BlockchainImpl ledger = new BlockchainImpl();
        SingleChannel newAccount = new SingleChannel(newAccountData[0], ledger);
        try{
            MultiChannel accountOrganisation = this.getSpecificItem(newAccountData[1]);
            accountOrganisation.addChannel(newAccount);
            System.out.println("\nLOG: new account ID:"+newAccountData[0]+" added to ORG:" + accountOrganisation.getID());
        } catch (NoSuchElementException e) {
            throw new RuntimeException(e);
        }
    }

    public void addOrganisation(String[] orgData){
        try{
            if(orgData[0] != null){
                if(orgData.length > 1) {
                    this.orgMap.put(orgData[0], new MultiChannel(orgData[0], orgData[1]));
                }else{
                    this.orgMap.put(orgData[0], new MultiChannel(orgData[0]));
                }
                System.out.println("\nLOG: new org created ID:"+ orgData[0]);
            }else{
                throw new Exception("No Name Provided");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void LinkIDtoAcc(String id, String accID,String orgID){
        if(accID.length() >=  7 && accID.length() <= 8) {
            if(accID.length() == 7){
                accID = "0" + accID;
            }
                AIM.put(id, orgID + accID);
                System.out.println("\nLOG: Account ID:"+orgID+accID+" Has been tied to a Tax Number");
        }else{
            throw new InvalidParameterException("Account ID is incorrectly formated");
        }
    }

    private SingleChannel getAccount(String fullID){
        try{
            String orgID = fullID.substring(0, 6);
            String accID = fullID.substring(6, 14);
            SingleChannel account = this.orgMap.get(orgID).getSpecificItem(accID);
            if(account == null){
                throw new NoSuchElementException("\nERROR: Account with fullID "+fullID+ " Cannot be found");
            }else{
                return account;
            }
        } catch (Exception e) {
            System.out.println("ERROR: Account "+fullID+" Is not formatted properly and/or dose not exist");
            throw new RuntimeException(e);
        }
    }

    public List<Block> getTransactions(String taxID){
        List<String> accountIDs;
        ArrayList<Block> transactions = new ArrayList<>();
        accountIDs = AIM.get(taxID);
        try{
            for (String accountID : accountIDs) {
                transactions.addAll(getAccount(accountID).getAllItems());
            }
            return transactions;
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    public void logTransaction(String[] transactionData){
        SingleChannel account;
        if ((transactionData[0].length()) != 14){
            throw new InvalidParameterException("\nERROR: Account ID is incorrectly formated. Currently size:"+transactionData[0].length()+" Needs to be Length: 14");
        }else{
            account = getAccount(transactionData[0]);
            ArrayList<String> transactionInfo = new ArrayList<>(Arrays.asList(transactionData).subList(2, transactionData.length));
            transactionInfo.addFirst(transactionData[0].substring(6));
            account.addBlock(new Block(Long.parseLong(transactionData[1]), transactionInfo));
        }
    }


}

