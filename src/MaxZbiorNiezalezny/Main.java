package MaxZbiorNiezalezny;

import edu.uci.ics.jung.visualization.control.ModalGraphMouse.Mode;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Main extends JFrame {

    static Main GUI;

    private int[] rozwiazanie;

    private JButton gotowe = new JButton(" GOTOWE ");
    private JRadioButton edycja;
    private JRadioButton przenoszenie;
    private ButtonGroup grupa;

    private ParametryGraf parametryGraf;
    private ParametryAlgorytm parametryAlgorytm;
    private ObrazGraf obrazGraf;
    private algGenetyczny algorytm;
    private Statystyki statystyki;

    private JRadioButton ruletka;
    private JRadioButton ranking;
    private JLabel tekst;

    private JRadioButton liczbaGeneracji;
    private JRadioButton generacjeBezZmian;
    private JLabel tekst2;

    private int warunekStopu;
    private int metodaSelekcji;

    private double start;
    private double stop;

    public Main(){

        parametryGraf = new ParametryGraf();
        parametryAlgorytm = new ParametryAlgorytm();
        statystyki = new Statystyki();
        obrazGraf = null;

        // Ustawienia okna
        setTitle("Algorytm genetyczny rozwiązujący problem maksymalnego zbioru niezależnego");
        setVisible(true);
        setSize(1270, 750);
        setLocation(5,5);
        setResizable(false);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        getContentPane().setBackground(new Color(197, 230, 230));

        // Ustawienia przycisków JFrame'a
        gotowe.setEnabled(false);
        gotowe.setBounds(400, 610, 100, 30);


        edycja = new JRadioButton("Tryb Edycji");
        edycja.setMnemonic(KeyEvent.VK_E);
        edycja.setSelected(true);
        edycja.setBounds(66, 600, 150, 25);
        edycja.setBackground(new Color(197, 230, 230));
        edycja.setVisible(false);

        przenoszenie = new JRadioButton("Tryb Przenoszenia");
        przenoszenie.setMnemonic(KeyEvent.VK_P);
        przenoszenie.setBounds(66, 625, 150, 25);
        przenoszenie.setBackground(new Color(197, 230, 230));
        przenoszenie.setVisible(false);

        grupa = new ButtonGroup();
        grupa.add(edycja);
        grupa.add(przenoszenie);



        // Wybór metody selekcji
        ruletka = new JRadioButton("Ruletka");
        ruletka.setSelected(true);


        ranking = new JRadioButton("Ranking");
        ButtonGroup group = new ButtonGroup();
        group.add(ruletka);
        ruletka.setBounds(666, 600, 150, 25);
        ruletka.setBackground(new Color(197, 230, 230));
        ruletka.setVisible(false);
        group.add(ranking);
        ranking.setBounds(666, 625, 150, 25);
        ranking.setBackground(new Color(197, 230, 230));
        ranking.setVisible(false);
        tekst = new JLabel("Metoda selekcji: ");
        tekst.setBounds(565, 600, 150, 25);
        tekst.setBackground(new Color(197, 230, 230));
        tekst.setVisible(false);


        // Wybor warunku stopu
        liczbaGeneracji = new JRadioButton("Generacji łącznie");
        liczbaGeneracji.setSelected(true);
        generacjeBezZmian = new JRadioButton("Generacji bez zmian");
        ButtonGroup group2 = new ButtonGroup();
        group2.add(liczbaGeneracji);
        liczbaGeneracji.setBounds(166, 600, 150, 25);
        liczbaGeneracji.setBackground(new Color(197, 230, 230));
        liczbaGeneracji.setVisible(false);
        group2.add(generacjeBezZmian);
        generacjeBezZmian.setBounds(166, 625, 150, 25);
        generacjeBezZmian.setBackground(new Color(197, 230, 230));
        generacjeBezZmian.setVisible(false);

        tekst2 = new JLabel("Warunek stopu: ");
        tekst2.setBounds(65, 600, 150, 25);
        tekst2.setBackground(new Color(197, 230, 230));
        tekst2.setVisible(false);



        // Dodawanie elementów do JFrame               
        getContentPane().add(ruletka);
        getContentPane().add(ranking);
        getContentPane().add(tekst);

        getContentPane().add(liczbaGeneracji);
        getContentPane().add(generacjeBezZmian);
        getContentPane().add(tekst2);

        getContentPane().add(edycja);
        getContentPane().add(przenoszenie);
        getContentPane().add(parametryGraf);
        getContentPane().add(parametryAlgorytm);
        getContentPane().add(statystyki);
        getContentPane().add(gotowe);


        // Dodawanie actionlistner'ów do radiobutton
        class ALEdycja implements ActionListener {
            public void actionPerformed(ActionEvent e) {
                obrazGraf.getMyszka().setMode(Mode.EDITING);
            }
        }

        edycja.addActionListener(new ALEdycja());

        class ALPrzenoszenie implements ActionListener {
            public void actionPerformed(ActionEvent e) {
                obrazGraf.getMyszka().setMode(Mode.PICKING);
            }
        }

        przenoszenie.addActionListener(new ALPrzenoszenie());

        class ALliczbaGeneracji implements ActionListener {
            public void actionPerformed(ActionEvent e) {
                parametryAlgorytm.getIloscGeneracji().setText("500");
                parametryAlgorytm.getEtykietaGeneracji().setText("Liczba Generacji ");
                warunekStopu = 1;
            }
        }

        liczbaGeneracji.addActionListener(new ALliczbaGeneracji());

        class ALgeneracjeBezZmian implements ActionListener {
            public void actionPerformed(ActionEvent e) {
                parametryAlgorytm.getIloscGeneracji().setText("50");
                parametryAlgorytm.getEtykietaGeneracji().setText("Generacji bez zmian ");
                warunekStopu = 2;
            }
        }

        generacjeBezZmian.addActionListener(new ALgeneracjeBezZmian());

        class ALruletka implements ActionListener {
            public void actionPerformed(ActionEvent e) {
                metodaSelekcji = 1;
            }
        }

        ruletka.addActionListener(new ALruletka());

        class ALranking implements ActionListener {
            public void actionPerformed(ActionEvent e) {
                metodaSelekcji = 2;
            }
        }

        ranking.addActionListener(new ALranking());


        // Dodawanie actionlistner'ów do przycisków
        class ALGrafRecznie implements ActionListener {
            public void actionPerformed(ActionEvent e) {
                if(obrazGraf != null)
                    getContentPane().remove(obrazGraf.getObraz());
                obrazGraf = new ObrazGraf();
                getContentPane().add(obrazGraf.getObraz());


                gotowe.setEnabled(true);
                edycja.setVisible(true);
                edycja.setSelected(true);
                przenoszenie.setVisible(true);
            }
        }

        parametryGraf.getGrafRecznie().addActionListener(new ALGrafRecznie());

        class ALGrafDane implements ActionListener {
            public void actionPerformed(ActionEvent e) {
                parametryGraf.getIloscWierzcholkow().setText("30");
                parametryGraf.getIloscKrawedzi().setText("50");
                if(obrazGraf != null){
                    getContentPane().remove(obrazGraf.getObraz());
                    repaint();
                }


                parametryGraf.buttonsDisabled();
                edycja.setVisible(false);
                przenoszenie.setVisible(false);
            }
        }

        parametryGraf.getGrafDane().addActionListener(new ALGrafDane());

        class ALOk implements ActionListener {
            public void actionPerformed(ActionEvent e) {
                parametryGraf.getIloscWierzcholkow().setBackground(Color.white);
                parametryGraf.getIloscKrawedzi().setBackground(Color.white);
                if(!parametryGraf.checkWierzcholki())
                    parametryGraf.getIloscWierzcholkow().setBackground(new Color(255,153,153));
                if(!parametryGraf.checkKrawedzi())
                    parametryGraf.getIloscKrawedzi().setBackground(new Color(255,153,153));
                if(!parametryGraf.checkWierzcholki() || !parametryGraf.checkKrawedzi())
                    return;
                obrazGraf = new ObrazGraf(Integer.valueOf(parametryGraf.getIloscWierzcholkow().getText()),Integer.valueOf(parametryGraf.getIloscKrawedzi().getText()));
                getContentPane().add(obrazGraf.getObraz());


                parametryGraf.fieldsDisabled();
                gotowe.setEnabled(true);
            }
        }

        parametryGraf.getOk().addActionListener(new ALOk());

        class ALGotowe implements ActionListener {
            public void actionPerformed(ActionEvent e) {
                warunekStopu = 1;
                liczbaGeneracji.setSelected(true);

                metodaSelekcji = 1;
                ruletka.setSelected(true);
                
                parametryAlgorytm.getIloscPopulacji().setText("500");
                parametryAlgorytm.getEtykietaGeneracji().setText("Liczba generacji ");
                parametryAlgorytm.getSzansaMutacji().setText("1");
                parametryAlgorytm.getIloscGeneracji().setText("500");
                obrazGraf.getObraz().setGraphMouse(null);
                if(obrazGraf.getKrawedzie() == null)
                    obrazGraf.uzupelnijKrawedzie();


                obrazGraf.pomalujStandardowo();
                repaint();

                parametryGraf.allDisabled();
                parametryAlgorytm.allEnabled();
                gotowe.setEnabled(false);
                edycja.setVisible(false);
                przenoszenie.setVisible(false);
                ruletka.setVisible(true);
                ranking.setVisible(true);
                tekst.setVisible(true);
                liczbaGeneracji.setVisible(true);
                generacjeBezZmian.setVisible(true);
                tekst2.setVisible(true);
            }
        }

        gotowe.addActionListener(new ALGotowe());

        class ALRozwiaz implements ActionListener {
            public void actionPerformed(ActionEvent e) {
                parametryAlgorytm.getIloscPopulacji().setBackground(Color.white);
                parametryAlgorytm.getSzansaMutacji().setBackground(Color.white);
                parametryAlgorytm.getIloscGeneracji().setBackground(Color.white);

                if(!parametryAlgorytm.checkPopulacja())
                    parametryAlgorytm.getIloscPopulacji().setBackground(new Color(255,153,153));
                if(!parametryAlgorytm.checkMutacja())
                    parametryAlgorytm.getSzansaMutacji().setBackground(new Color(255,153,153));
                if(!parametryAlgorytm.checkGeneracja())
                    parametryAlgorytm.getIloscGeneracji().setBackground(new Color(255,153,153));
                if(!parametryAlgorytm.checkPopulacja() || (!parametryAlgorytm.checkMutacja() || !parametryAlgorytm.checkGeneracja()))
                    return;



                algorytm = new algGenetyczny(obrazGraf.getW(), obrazGraf.getK(), Integer.valueOf(parametryAlgorytm.getIloscPopulacji().getText()), Integer.valueOf(parametryAlgorytm.getSzansaMutacji().getText()), Integer.valueOf(parametryAlgorytm.getIloscGeneracji().getText()), obrazGraf.getKrawedzie(), warunekStopu, metodaSelekcji);
                start = System.currentTimeMillis();
                rozwiazanie = algorytm.start();
                stop = System.currentTimeMillis();
                obrazGraf.pomalujWynik(rozwiazanie);
                repaint();

                statystyki.ustawStatystyki(parametryAlgorytm.getIloscPopulacji().getText(), parametryAlgorytm.getSzansaMutacji().getText(), parametryAlgorytm.getIloscGeneracji().getText(), algorytm.getNajlepszy(), algorytm.getSrednia(), algorytm.getNajgorszy(), (stop-start)/1000 , algorytm.getWierzcholki(), algorytm.getKrawedzie());

                parametryGraf.fieldsDisabled();
                parametryAlgorytm.allDisabled();
                gotowe.setEnabled(true);
                ruletka.setVisible(false);
                ranking.setVisible(false);
                tekst.setVisible(false);
                liczbaGeneracji.setVisible(false);
                generacjeBezZmian.setVisible(false);
                tekst2.setVisible(false);

                algorytm.getObraz().toFront();
            }
        }

        parametryAlgorytm.getRozwiaz().addActionListener(new ALRozwiaz());
    }


    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(new Runnable()
        {
            public void run()
            {
                GUI = new Main();
            }
        });
    }
}
