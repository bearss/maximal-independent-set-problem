package MaxZbiorNiezalezny;

import java.awt.*;
import javax.swing.*;
import javax.swing.BorderFactory;
import javax.swing.border.Border;


public class ParametryGraf extends JPanel {
    private JButton grafRecznie = new JButton("Stwórz graf myszką");
    private JButton grafDane = new JButton("Wpisz parametry grafu");
    private JButton ok = new JButton("OK");
    private JTextField iloscWierzcholkow = new JTextField(5);
    private JTextField iloscKrawedzi = new JTextField(5);
    private JLabel etykietaWierzcholki = new JLabel("Ilość wierzchołków:");
    private JLabel etykietaKrawedzi = new JLabel("Ilość krawędzi:");

    Border blackline = BorderFactory.createLineBorder(Color.black);
    
    public ParametryGraf(){
        setLayout(new FlowLayout());
        setBounds(3, 5, 888, 38);
        setBackground(new Color(138, 210, 210));
        setBorder(blackline);

        fieldsDisabled();

        iloscWierzcholkow.setHorizontalAlignment(JTextField.CENTER);
        iloscKrawedzi.setHorizontalAlignment(JTextField.CENTER);

        add(grafRecznie);
        add(grafDane);
        add(new JLabel("        "));
        add(etykietaWierzcholki);
        add(iloscWierzcholkow);
        add(new JLabel("     "));
        add(etykietaKrawedzi);
        add(iloscKrawedzi);
        add(new JLabel("     "));
        add(ok);
    }

    public JButton getGrafRecznie(){
        return grafRecznie;
    }

    public JButton getGrafDane(){
        return grafDane;
    }

    public JButton getOk(){
        return ok;
    }

    public JTextField getIloscWierzcholkow(){
        return iloscWierzcholkow;
    }

    public JTextField getIloscKrawedzi(){
        return iloscKrawedzi;
    }

    public void allDisabled(){
        grafRecznie.setEnabled(false);
        grafDane.setEnabled(false);
        iloscWierzcholkow.setEnabled(false);
        etykietaWierzcholki.setEnabled(false);
        iloscKrawedzi.setEnabled(false);
        etykietaKrawedzi.setEnabled(false);
        ok.setEnabled(false);
    }

    public void fieldsDisabled(){
        grafRecznie.setEnabled(true);
        grafDane.setEnabled(true);
        iloscWierzcholkow.setEnabled(false);
        etykietaWierzcholki.setEnabled(false);
        iloscKrawedzi.setEnabled(false);
        etykietaKrawedzi.setEnabled(false);
        ok.setEnabled(false);
    }

    public void buttonsDisabled(){
        grafRecznie.setEnabled(false);
        grafDane.setEnabled(false);
        iloscWierzcholkow.setEnabled(true);
        etykietaWierzcholki.setEnabled(true);
        iloscKrawedzi.setEnabled(true);
        etykietaKrawedzi.setEnabled(true);
        ok.setEnabled(true);
    }

    
    public boolean checkWierzcholki() {
        int dl = iloscWierzcholkow.getText().length();
        if (dl == 0)
            return false;

        char[] tz = new char[dl];
        tz = iloscWierzcholkow.getText().toCharArray();

        for (int i = 0; i < dl; i++)
        {
            if (!(Character.isDigit(tz[i])))
                return false;
        }

        if(Integer.valueOf(iloscWierzcholkow.getText()) > 150 )
            return false;

        return true;
    }

    public boolean checkKrawedzi() {
        //Obliczanie ile może być maksymalnie krawędzi dla podanej liczby wierzchołków
        if(!checkWierzcholki())
            return false;

        int maxkrawedzi = 0;
        for(int i = 0; i < Integer.valueOf(iloscWierzcholkow.getText()) - 1; i++)
            maxkrawedzi += i + 1;

        //Koniec obliczania

        int dl = iloscKrawedzi.getText().length();
        if (dl == 0)
            return false;

        char[] tz = new char[dl];
        tz = iloscKrawedzi.getText().toCharArray();

        for (int i = 0; i < dl; i++)
        {
            if (!(Character.isDigit(tz[i])))
                return false;
        }

        if(Integer.valueOf(iloscKrawedzi.getText()) > maxkrawedzi || Integer.valueOf(iloscKrawedzi.getText()) < Integer.valueOf(iloscWierzcholkow.getText()) - 1)
            return false;

        return true;
    }

}
