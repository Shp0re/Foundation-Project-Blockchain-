package main.java.taxreturns.frontEnd;

import javax.swing.*;

public class LoginPage extends JPanel{
    private JPanel panel1;
    private JPasswordField passwordField1;
    private JButton continueButton;
    private JTextPane pleaseEnterYourDidgitalTextPane;
    private loginListener listener;

    public LoginPage(){
        continueButton.addActionListener(e ->{
            if(listener != null){
                listener.onLoginAttempt(new String(passwordField1.getPassword()));
            }
        });
    }

    public void pageVisible(boolean visible){
        this.panel1.setVisible(visible);
        System.out.println(STR."LOG: Login page visible? set to \{visible}");
    }

    public JPanel getMainPanel(){
        return this.panel1;
    }

    public void setLoginListener(loginListener listener){
        this.listener = listener;
    }

}


