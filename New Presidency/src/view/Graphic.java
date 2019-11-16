package view;

import org.knowm.xchart.QuickChart;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.XYChart;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class Graphic{

    private JFrame f;

    public Graphic(String name, String nameX, String nameY, String title, double[] dataX, double[] dataY){

        //On crée le graphique
        XYChart chart = QuickChart.getChart(name, nameX, nameY, title, dataX, dataY);


        f = new JFrame(name);
        f.setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel chartPanel = new XChartPanel(chart);
        f.add(chartPanel);
        f.pack();
        f.setVisible(true);
    }

    public void close(){
        f.dispose();
    }


}




