package MaxZbiorNiezalezny;

import java.awt.*;
import javax.swing.*;
import javax.swing.BorderFactory;
import javax.swing.border.Border;

public class Statystyki extends JPanel{
    private JLabel info1 = new JLabel();
    private JLabel info2 = new JLabel();
    private JLabel info3 = new JLabel();
    private JLabel info4 = new JLabel();
    private JLabel info5 = new JLabel();
    private JLabel info6 = new JLabel();
    private JLabel info7 = new JLabel();
    private JLabel info8 = new JLabel();
    private JLabel info9 = new JLabel();
    private JLabel info10 = new JLabel();
    private JLabel info11 = new JLabel();
    private JLabel info12 = new JLabel();

    Border blackline = BorderFactory.createLineBorder(Color.black);

    public Statystyki(){
        setLayout(new GridLayout(12,1));
        setBounds(900, 5, 355, 700);

        setBackground(new Color(138, 210, 210));
        setBorder(blackline);

        info1.setText("STATYSTYKI");
        info1.setFont(new Font("Serif", Font.BOLD, 18));
        info1.setHorizontalAlignment(JTextField.CENTER);
        info2.setText("      Ilość populacji : ");
        info3.setText("      Szansa mutacji : ");
        info4.setText("      Ilość generacji : ");
        info5.setText( "OCENY");
        info5.setFont(new Font("Serif", Font.BOLD, 15));
        info5.setHorizontalAlignment(JTextField.CENTER);
        info6.setText("      Najlepszy : ");
        info7.setText("      Średnia : ");
        info8.setText("      Najgorszy : ");
        info9.setText("      Czas wykonywania algorytmu : ");
        info10.setText("GRAF WYNIKOWY");
        info10.setFont(new Font("Serif", Font.BOLD, 15));
        info10.setHorizontalAlignment(JTextField.CENTER);
        info11.setText("      Ilość wierzchołków : ");
        info12.setText("      Ilość krawędzi : ");


        add(info1);
        add(info2);
        add(info3);
        add(info4);
        add(info5);
        add(info6);
        add(info7);
        add(info8);
        add(info9);
        add(info10);
        add(info11);
        add(info12);
    }

    public void ustawStatystyki(String a, String b, String c, int d, int e, int f, double g, int h, int i){
        info2.setText("      Ilość populacji : "+a);
        info3.setText("      Szansa mutacji : "+b);
        info4.setText("      Ilość generacji : "+c);
        info6.setText("      Najlepszy : "+d);
        info7.setText("      Średnia : "+e);
        info8.setText("      Najgorszy : "+f);
        info9.setText("      Czas wykonywania algorytmu : "+g+" sekundy");
        info11.setText("      Ilość wierzchołków : "+h);
        info12.setText("      Ilość krawędzi : "+i);
    }
}
