package MaxZbiorNiezalezny;

import java.awt.*;
import javax.swing.*;


public class ParametryAlgorytm extends JPanel {
    private JButton rozwiaz = new JButton("Rozwiąż");
    private JTextField iloscPopulacji = new JTextField();
    private JTextField szansaMutacji = new JTextField();
    private JTextField iloscGeneracji = new JTextField();
    private JLabel etykietaPopulacji = new JLabel("Wielkość populacji", JLabel.CENTER);
    private JLabel etykietaMutacji = new JLabel("Szansa mutacji (0%-100%)", JLabel.CENTER);
    private JLabel etykietaGeneracji = new JLabel("Liczba Generacji ", JLabel.CENTER);


    public ParametryAlgorytm(){
        setLayout(new GridLayout(2,4));
        setBounds(150, 655, 600, 56);
        setBackground(new Color(197, 230, 230));

        allDisabled();

        iloscPopulacji.setHorizontalAlignment(JTextField.CENTER);
        szansaMutacji.setHorizontalAlignment(JTextField.CENTER);
        iloscGeneracji.setHorizontalAlignment(JTextField.CENTER);
        

        add(etykietaPopulacji);
        add(etykietaMutacji);
        add(etykietaGeneracji);
        add(new JLabel(""));
        add(iloscPopulacji);
        add(szansaMutacji);
        add(iloscGeneracji);
        add(rozwiaz);

    }

    public JButton getRozwiaz(){
        return rozwiaz;
    }

    public JTextField getIloscPopulacji(){
        return iloscPopulacji;
    }

    public JTextField getSzansaMutacji(){
        return szansaMutacji;
    }

    public JTextField getIloscGeneracji(){
        return iloscGeneracji;
    }

    public JLabel getEtykietaGeneracji(){
        return etykietaGeneracji;
    }


    public void allDisabled(){
        etykietaPopulacji.setEnabled(false);
        etykietaMutacji.setEnabled(false);
        etykietaGeneracji.setEnabled(false);
        iloscPopulacji.setEnabled(false);
        szansaMutacji.setEnabled(false);
        iloscGeneracji.setEnabled(false);
        rozwiaz.setEnabled(false);
    }

    public void allEnabled(){
        etykietaPopulacji.setEnabled(true);
        etykietaMutacji.setEnabled(true);
        etykietaGeneracji.setEnabled(true);
        iloscPopulacji.setEnabled(true);
        szansaMutacji.setEnabled(true);
        iloscGeneracji.setEnabled(true);
        rozwiaz.setEnabled(true);
    }

    public boolean checkPopulacja() {
        int dl = iloscPopulacji.getText().length();
        if (dl == 0)
            return false;

        char[] tz = new char[dl];
        tz = iloscPopulacji.getText().toCharArray();

        for (int i = 0; i < dl; i++)
        {
            if (!(Character.isDigit(tz[i])))
                return false;
        }

        if(Integer.valueOf(iloscPopulacji.getText()) > 1000 || Integer.valueOf(iloscPopulacji.getText()) < 1)
            return false;

        if(Integer.valueOf(iloscPopulacji.getText()) % 2 != 0)
            return false;

        return true;
    }

    public boolean checkMutacja() {
        int dl = szansaMutacji.getText().length();
        if (dl == 0)
            return false;

        char[] tz = new char[dl];
        tz = szansaMutacji.getText().toCharArray();

        for (int i = 0; i < dl; i++)
        {
            if (!(Character.isDigit(tz[i])))
                return false;
        }

        if(Integer.valueOf(szansaMutacji.getText()) > 100 || Integer.valueOf(szansaMutacji.getText()) < 0)
            return false;

        return true;
    }

    public boolean checkGeneracja() {
        int dl = iloscGeneracji.getText().length();
        if (dl == 0)
            return false;

        char[] tz = new char[dl];
        tz = iloscGeneracji.getText().toCharArray();

        for (int i = 0; i < dl; i++)
        {
            if (!(Character.isDigit(tz[i])))
                return false;
        }

        if(Integer.valueOf(iloscGeneracji.getText()) > 2000 || Integer.valueOf(iloscGeneracji.getText()) < 1)
            return false;

        return true;
    }

}
