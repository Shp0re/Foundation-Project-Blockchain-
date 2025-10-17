package main.java.taxreturns.frontEnd;

import Node.*;
import main.java.taxreturns.blockControler.Controller;
import main.java.taxreturns.blockchain.Block;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

public class UIController extends JFrame {
    private CardLayout cardLayout;
    private JPanel cardPanel;
    private LoginPage loginPage;
    private ShowTransactionsPage showTransactionsPage;
    private NodeGroup nodeGroup;
    public Controller manager; // its public bc I cant be bothered to add getters and setters

    public UIController(NodeGroup nodeGroup){
        this.nodeGroup = nodeGroup;
        this.manager = new Controller(nodeGroup);
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        loginPage = new LoginPage();

        loginPage.setLoginListener(digitalID -> {
            try{
                List<Block> transactions = manager.getTransactions(digitalID);
                if (transactions.isEmpty()){
                    throw new NoSuchElementException("Error: Account "+digitalID+" didn't return any data");
                }
                Object[][] tableData = new Object[transactions.size()][5];

                for(int i = 0; i < transactions.size(); i++) {
                    List<String> tempTransactionData = transactions.get(i).getData();
                    tableData[i][0] = transactions.get(i).getDigitalSignature(); // get transaction id
                    for (int j = 0; j < 4; j++) {
                        tableData[i][j+1] = tempTransactionData.get(j);
                    }
                }
                this.showTransactionsPage = new ShowTransactionsPage(tableData);
                cardPanel.add(showTransactionsPage.getMainPage(), "transactions");
                cardLayout.show(cardPanel, "transactions");
            } catch (RuntimeException e) {
                System.out.println(STR."WARNING: User has entered incorrect Data. Causing Error:\{e}");
                JOptionPane.showMessageDialog(this, "Invalid login", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        cardPanel.add(loginPage.getMainPanel(), "login");
        add(cardPanel);
        this.setContentPane(cardPanel);
        cardLayout.show(cardPanel, "login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
    }

    // reads in transaction data from a csv file and then adds it to the manager
    public void getMassTransactionData(String fileName) throws FileNotFoundException{
        ArrayList<String[]> transactions = new ArrayList<>();
        try(BufferedReader reader = new BufferedReader(new FileReader(fileName))){
            String line;
            while((line = reader.readLine()) != null){
                String[] values = line.split(",");
                transactions.add(values);
            }
        }catch (IOException e){
            System.out.println("WARNING: File "+fileName+" Cannot be found canceling this opperation");
            throw new FileNotFoundException("file not found");
        }
            for(int i = 0; i < transactions.size(); i++) {
                try {
                    this.manager.logTransaction(transactions.get(i));
                }catch (Exception e){
                    System.out.println("WARNING: Exception when reading in data: "+e);
                }
            }
    }
}
