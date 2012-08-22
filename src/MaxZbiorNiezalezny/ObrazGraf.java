package MaxZbiorNiezalezny;

import java.awt.Paint;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.BorderFactory;
import javax.swing.border.Border;
import java.util.*;

import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.SparseGraph;

import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
import edu.uci.ics.jung.algorithms.layout.KKLayout;
import edu.uci.ics.jung.algorithms.layout.StaticLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.EditingModalGraphMouse;
import edu.uci.ics.jung.visualization.control.ModalGraphMouse.Mode;
import org.apache.commons.collections15.Factory;
import org.apache.commons.collections15.Transformer;
import edu.uci.ics.jung.visualization.renderers.Renderer.VertexLabel.Position;

import edu.uci.ics.jung.algorithms.cluster.WeakComponentClusterer;

public class ObrazGraf {

    private Border blackline = BorderFactory.createLineBorder(Color.black);
    private Random losowa1;
    private Random losowa2;
    private int w1;
    private int w2;

    private Graph<Integer,Integer> graf;
    private int [][] krawedzie;
    private int [][] krawedzie_temp;
    private int [][] krawedzie_temp2;
    private int iloscW;
    private int iloscK;

    private Layout<Integer,Integer> plan;
    private VisualizationViewer<Integer,Integer> obraz;
    private EditingModalGraphMouse myszka;
    private Factory<Integer> wFabryka;
    private Factory<Integer> kFabryka;

    private int[] rozwiazanie;


    private Transformer<Integer,Paint> malowanie = new Transformer<Integer,Paint>() {
        public Paint transform(Integer i) {
            return Color.magenta;
        }
    };

    private Transformer<Integer,Paint> malowanieWynik = new Transformer<Integer,Paint>() {
        public Paint transform(Integer i) {
            if(rozwiazanie[i] == 1)
                return Color.green;
            else
                return Color.magenta;
        }
    };

    public ObrazGraf() {
        iloscW = 0;
        iloscK = 0;

        graf = new SparseGraph<Integer,Integer>();
        krawedzie = null;

        wFabryka = new Factory<Integer>(){
            public Integer create(){
                return iloscW++;
            }
        };

        kFabryka = new Factory<Integer>(){
            public Integer create(){
                return iloscK++;
            }
        };

        plan = new StaticLayout(graf);
        plan.setSize(new Dimension(780,540));

        obraz = new VisualizationViewer<Integer,Integer>(plan);
        obraz.setBounds(56,60,780,540);
        obraz.setPreferredSize(new Dimension(780,540));
        obraz.setBackground(new Color(225, 243, 243));
        obraz.setBorder(blackline);
        obraz.getRenderContext().setVertexLabelTransformer(new ToStringLabeller());

        myszka = new EditingModalGraphMouse(obraz.getRenderContext(), wFabryka, kFabryka, 1f, 1f);
        myszka.setMode(Mode.EDITING);
        obraz.setGraphMouse(myszka);

        obraz.getRenderer().getVertexLabelRenderer().setPosition(Position.CNTR);
        pomalujStandardowo();

    }

    public ObrazGraf(int w, int k){

        Set<Set<Integer>> clusters;
        int licznik = 0;

        do{

        graf = new SparseGraph<Integer, Integer>();

        for(int i = 0; i < w; i++)
            graf.addVertex(i);

        krawedzie = new int[w][w];
        for(int i = 0; i < w; i++)
            for(int j = 0; j < w; j++)
                krawedzie[i][j] = 0;

        losowa1 = new Random();
        losowa2 = new Random();

        for(int i = 0; i < k; i++){
            do{
                w1 = losowa1.nextInt(w);
                w2 = losowa2.nextInt(w);
                if((w1 != w2) && (krawedzie[w1][w2] == 0))
                    graf.addEdge(i, w1, w2);
            } while((w1 == w2) || (krawedzie[w1][w2] == 1));

            graf.addEdge(i, w1, w2);
            krawedzie[w1][w2] = 1;
            krawedzie[w2][w1] = 1;
        }

        clusters = new WeakComponentClusterer().transform(graf);
        licznik++;

        }while(clusters.size() != 1 && licznik != 20000);

        plan = new KKLayout(graf);
        plan.setSize(new Dimension(780,540));

        obraz = new VisualizationViewer<Integer,Integer>(plan);
        obraz.setBounds(56,60,780,540);
        obraz.setPreferredSize(new Dimension(780,540));
        obraz.setBackground(new Color(225, 243, 243));
        obraz.setBorder(blackline);
        obraz.getRenderContext().setVertexLabelTransformer(new ToStringLabeller());
        obraz.getRenderer().getVertexLabelRenderer().setPosition(Position.CNTR);
        pomalujStandardowo();

        for (int i = 0; i < w; i++)
            plan.lock(i, true);
    }

    public VisualizationViewer<Integer,Integer> getObraz(){
        return obraz;
    }

    public EditingModalGraphMouse getMyszka(){
        return myszka;
    }

    public int getW(){
        return graf.getVertexCount();
    }

    public int getK(){
        return graf.getEdgeCount();
    }

    public int[][] getKrawedzie(){
        return krawedzie;
    }

    public void uzupelnijKrawedzie(){
        
        int w = graf.getVertexCount();
        int lw = 0;
        Collection<Integer> zbiorwierzcholkow = graf.getVertices();
        Iterator<Integer> iterw = zbiorwierzcholkow.iterator();
        for(int i = 0; i < w; i++){
            lw = iterw.next();
        }
        
        int k = graf.getEdgeCount();
        Collection<Integer> zbiorkrawedzi = graf.getEdges();
        Iterator<Integer> iterk = zbiorkrawedzi.iterator();


        // Stworzenie macierzy krawedzi
        krawedzie_temp = new int[lw+1][lw+1];
        for(int i = 0; i < lw+1; i++)
            for(int j = 0; j < lw+1; j++)
                krawedzie_temp[i][j] = 0;


        for (int i = 0; i < k; i++)
        {

                int tmp = iterk.next();

                krawedzie_temp[graf.getEndpoints(tmp).getFirst()][graf.getEndpoints(tmp).getSecond()] = 1;
                krawedzie_temp[graf.getEndpoints(tmp).getSecond()][graf.getEndpoints(tmp).getFirst()] = 1;
        }

        // Przetransformowanie macierzy do odpowiedniej formy
        int n = 0;
        int licz = 0;
        iterw = zbiorwierzcholkow.iterator();

        krawedzie_temp2 = new int[lw+1][w];
        for(int l = 0; l < w; l++){
            n = iterw.next();
            for(int i = 0; i < lw+1; i++)
                krawedzie_temp2[i][licz] = krawedzie_temp[i][n];
            licz++;
        }

        n = 0;
        licz = 0;
        iterw = zbiorwierzcholkow.iterator();
        
        krawedzie = new int[w][w];
        for(int l = 0; l < w; l++){
            n = iterw.next();
            for(int j = 0; j < w; j++)
                krawedzie[licz][j] = krawedzie_temp2[n][j];
            licz++;
        }


    }

    public void pomalujStandardowo(){
        obraz.getRenderContext().setVertexFillPaintTransformer(malowanie);
    }

    public void pomalujWynik(int [] rozw){
        rozwiazanie = rozw;
        obraz.getRenderContext().setVertexFillPaintTransformer(malowanieWynik);
    }

}
