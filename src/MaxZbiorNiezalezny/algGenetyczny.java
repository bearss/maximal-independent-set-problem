package MaxZbiorNiezalezny;

import java.util.*;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;


public class algGenetyczny {
    private int min = 0;

    private int iloscW;
    private int iloscK;
    private int iloscPop;
    private int iloscMut;
    private int iloscGen;
    private int wStopu;
    private int mSelekcji;

    private int [][] kraw;
    private int [][] chromosomy;
    private int [][] chromosomy_temp;
    private int [][] dzieci;

    private int [] ruletkaOceny;
    private int [][] rankingOceny;

    private Random losowa;
    private Random losowa2;

    private ChartFrame obraz;
    private JFreeChart wykres;
    private DefaultCategoryDataset dane = new DefaultCategoryDataset();


    public algGenetyczny(int w, int k, int pop, int mut, int gen, int [][] kr, int ws, int ms){
        iloscW = w;
        iloscK = k;
        iloscPop = pop;
        iloscMut = mut;
        iloscGen = gen;
        wStopu = ws;
        mSelekcji = ms;
        kraw = new int[kr.length][kr.length];
        for(int i = 0; i < kr.length; i++)
            for(int j = 0; j < kr.length; j++)
                kraw[i][j] = kr[i][j];
    }




    public void wybierzPopulacjePoczatkowa(){
        losowa = new Random();

        chromosomy = new int[iloscPop][iloscW+2];
        for(int i = 0; i < iloscPop; i++)
            for(int j = 0; j < iloscW; j++)
                chromosomy[i][j] = losowa.nextInt(2);

    }


    private void ocenaPrzystosowania () {

       int [] oceny = new int[iloscPop];
       

       for(int t = 0; t < iloscPop; t++){

       double kara = -10;
       double nagroda = 10;
       double nagrodaPlus = 1;
       double KaraPlus = 1;
       double ocena = 0;

       int iloscWierzcholkow = 0;
       int iloscKrawedzi = 0;

       for (int i = 0; i < iloscW; i++)
           iloscWierzcholkow = iloscWierzcholkow + chromosomy[t][i];

       for (int i = 0; i < iloscW; i++)
           if (chromosomy[t][i] == 1)
               for (int j = 0; j < iloscW; j++)
                   if ((j != i) && (chromosomy[t][j] == 1))
                       if (kraw[i][j] == 1)
                           iloscKrawedzi++;


       if(iloscWierzcholkow == iloscW)
           nagrodaPlus = nagrodaPlus*2;

       if (iloscKrawedzi == 0)
           nagrodaPlus = nagrodaPlus*2;

      if ((iloscKrawedzi == 0) && (iloscWierzcholkow == iloscW))
           nagrodaPlus = nagrodaPlus*2;

       if (iloscKrawedzi >= iloscWierzcholkow)
           KaraPlus = KaraPlus*0.5;

       if (iloscWierzcholkow == 0)
           KaraPlus = KaraPlus*0.5;

       ocena = (iloscWierzcholkow * nagroda + iloscKrawedzi * kara) * KaraPlus * nagrodaPlus;

       if(ocena < min)
           min = (int)ocena;

       oceny[t] = (int)ocena;

       }

       for(int t = 0; t < iloscPop; t++)
         chromosomy[t][iloscW] = oceny[t]-min;

   }





    public void inicjujRuletke(){
        ruletkaOceny = new int[iloscPop];
        int suma = 0;

        for(int i = 0; i < iloscPop; i++){
            suma += chromosomy[i][iloscW];
            ruletkaOceny[i] = suma;
        }
    }


    public void inicjujRanking(){
        int suma = 0;

        rankingOceny = new int[2][iloscPop];
        for(int i = 0; i < iloscPop; i++){
            rankingOceny[0][i] = chromosomy[i][iloscW];
            rankingOceny[1][i] = i;
        }

        bubbleSort(rankingOceny);

        for(int i = 0; i < iloscPop; i++){
            chromosomy[rankingOceny[1][i]][iloscW+1] = i+1;
        }

        ruletkaOceny = new int[iloscPop];

        for(int i = 0; i < iloscPop; i++){
            suma += chromosomy[i][iloscW+1];
            ruletkaOceny[i] = suma;
        }

    }



    public int ruletka(){

        losowa = new Random();
        int liczba = losowa.nextInt(ruletkaOceny[ruletkaOceny.length-1]);

        for(int i = 0; i < iloscPop; i++)
            if(liczba < ruletkaOceny[i])
                return i;

        return -1;
       
    }




    public int[] osobnikNajlepszy(){
        int max = 0;
        int ktory = 0;

        for(int i = 0; i < iloscPop; i++)
            if(chromosomy[i][iloscW] > max){
                max = chromosomy[i][iloscW];
                ktory = i;
            }

        return chromosomy[ktory];
    }



    public int[] osobnikNajgorszy(){
        int mini = 1000000;
        int ktory = 0;

        for(int i = 0; i < iloscPop; i++)
            if(chromosomy[i][iloscW] < mini){
                mini = chromosomy[i][iloscW];
                ktory = i;
            }

        return chromosomy[ktory];
    }

    public int sredniaOcena(){
        int suma = 0;

        for(int i = 0; i < iloscPop; i++)
            suma += chromosomy[i][iloscW];

        return (suma/iloscPop);
    }





    public void procesKrzyzowania(){
        chromosomy_temp = new int[iloscPop][iloscW+1];
        int[] najlepszy = osobnikNajlepszy();
        int rodzic1;
        int rodzic2;
        int n;

        //Dodanie osobnika najlepszego
        losowa = new Random();
        n = losowa.nextInt(iloscPop);


        if(mSelekcji == 1)
            inicjujRuletke();
        if(mSelekcji == 2)
            inicjujRanking();
        for(int i = 0; i < iloscPop; i+=2){
            rodzic1 = ruletka();
            rodzic2 = ruletka();
            krzyzowanie(rodzic1, rodzic2);
            for(int j = 0; j < iloscW; j++){
                chromosomy_temp[i][j] = dzieci[0][j];
                chromosomy_temp[i+1][j] = dzieci[1][j];
            }
            if(i == n)
                for(int k = 0; k < iloscW; k++)
                    chromosomy_temp[n][k] = najlepszy[k];
            if(i+1 == n)
                for(int k = 0; k < iloscW; k++)
                    chromosomy_temp[n][k] = najlepszy[k];
        }

        
           
        // Kopiowanie do prawdziwej tablicy chromosomow
        for(int i = 0; i < iloscPop; i++)
            for(int j = 0; j < iloscW; j++)
                chromosomy[i][j] = chromosomy_temp[i][j];

    }





    public void krzyzowanie(int r1, int r2){
        dzieci = new int[2][iloscW];
        losowa = new Random();
        int liczba = losowa.nextInt(iloscW-2)+1;

        for(int i = 0; i < liczba; i++){
            dzieci[0][i] = chromosomy[r1][i];
            dzieci[1][i] = chromosomy[r2][i];
        }

        for(int i = liczba; i < iloscW; i++){
            dzieci[0][i] = chromosomy[r2][i];
            dzieci[1][i] = chromosomy[r1][i];
        }

    }





    public void procesMutacji(){
        losowa = new Random();
        losowa2 = new Random();
        int n;

        for(int i = 0; i < iloscPop; i++){
            if(losowa.nextInt(100) < iloscMut){
                n = losowa2.nextInt(iloscW);
                if(chromosomy[i][n] == 1)
                    chromosomy[i][n] = 0;
                else
                    chromosomy[i][n] = 1;
            }
        }
    }




    public void etap(int i){
        ocenaPrzystosowania();
        dodajDoWykresu(osobnikNajgorszy()[iloscW], sredniaOcena(), osobnikNajlepszy()[iloscW],i+1);
        procesKrzyzowania();
        procesMutacji();
    }



    // GLOWNA PETLA ALGORYTMU
    public int[] start(){
        int[] staryNajlepszy = new int[iloscW];
        int[] nowyNajlepszy = new int[iloscW];
        int licznik = 0;
        int petla = 0;

        wybierzPopulacjePoczatkowa();
        stworzWykres(osobnikNajgorszy()[iloscW], sredniaOcena(), osobnikNajlepszy()[iloscW]);
        if(wStopu == 1)
            for(int i = 0; i < iloscGen; i++){
                etap(i);
            }
        if(wStopu == 2){
            do{
                staryNajlepszy = osobnikNajlepszy();
                etap(petla);
                nowyNajlepszy = osobnikNajlepszy();
                if(porownaj(staryNajlepszy, nowyNajlepszy))
                    licznik++;
                else
                    licznik = 0;
                petla++;
            }while(licznik < iloscGen);
        }


        return osobnikNajlepszy();
    }



    //FUNKCJE WYKRESOWE
    public void stworzWykres(int g, int s, int l)
    {
        dane.addValue(g, "Najgorszy", "0");
        dane.addValue(s, "Średnia", "0");
        dane.addValue(l, "Najlepszy", "0");

        wykres = ChartFactory.createLineChart("Oceny osobnikow", "Generacja",
                "Ocena", dane, PlotOrientation.VERTICAL, true, true, false);
        obraz = new ChartFrame("Wykres algorytmu genetycznego", wykres);
        obraz.setLocation(5, 5);
        obraz.setVisible(true);

        obraz.pack();
        obraz.repaint();
    }



    public void dodajDoWykresu(int g, int s, int l, int n)
    {
        dane.addValue(g, "Najgorszy", Integer.toString(n));
        dane.addValue(s, "Średnia", Integer.toString(n));
        dane.addValue(l, "Najlepszy", Integer.toString(n));

        obraz.pack();
        obraz.repaint();
    }




    // FUNKCJE POMOCNICZE
    public boolean porownaj(int[] s, int[] n){
        boolean flaga = true;
        for(int i = 0; i < iloscW; i++)
            if(s[i] != n[i])
                flaga = false;

        return flaga;
    }


    public void bubbleSort(int[][] x) {
    int n = x[0].length;
    boolean doMore = true;
    while (doMore) {
        n--;
        doMore = false;
        for (int i=0; i<n; i++) {
            if (x[0][i] > x[0][i+1]) {
                int temp = x[0][i];  x[0][i] = x[0][i+1];  x[0][i+1] = temp;
                int temp2 = x[1][i];  x[1][i] = x[1][i+1];  x[1][i+1] = temp2;
                doMore = true;
            }
        }
    }
    }

    public ChartFrame getObraz(){
        return obraz;
    }

    public int getNajlepszy(){
        return osobnikNajlepszy()[iloscW];
    }

    public int getSrednia(){
        return sredniaOcena();
    }

    public int getNajgorszy(){
        return osobnikNajgorszy()[iloscW];
    }

    public int getWierzcholki(){
        int suma = 0;
        int[] naj = osobnikNajlepszy();

        for (int i = 0; i < iloscW; i++)
           suma = suma + naj[i];

        return suma;
    }

    public int getKrawedzie(){
        int suma = 0;
        int[] naj = osobnikNajlepszy();

        for (int i = 0; i < iloscW; i++)
        {
           if (naj[i] == 1)
           {
               for (int j = 0; j < iloscW; j++)
               {
                   if ((j != i) && (naj[j] == 1))
                   {
                       if (kraw[i][j] == 1)
                           suma++;
                   }

               }
           }
        }

        return suma;
    }
}
