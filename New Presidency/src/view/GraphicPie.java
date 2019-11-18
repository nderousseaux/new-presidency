package view;


import model.Lever;
import model.LeverList;
import model.State;
import model.StateList;
import org.knowm.xchart.*;

import javax.swing.*;
import java.awt.*;
import java.util.Collection;
import java.util.HashMap;

class GraphicPie {

    private JFrame _f;
    private Collection<Lever> _leverList;

    private PieChart chart;
    private HashMap<JComboBox<String>, String> _anciennesValeurs = new HashMap<>();




    GraphicPie(Collection<Lever> leverList, Double budget){
        _leverList = leverList;
        _f = new JFrame("Comment vous avez utilisé votre budget !");


        //Configuration du JFrame
        _f.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        _f.setLayout(new BorderLayout());
        _f.setSize(1350, 1000);
        _f.setResizable(false);

        //On crée le graphique
        chart = new PieChartBuilder().width(800).height(600).title(getClass().getSimpleName()).build();

        //On ajoute les données
        for (Lever l:_leverList) {
            if(l.getBudget() != 0){
                chart.addSeries(l.getName(), l.getBudget());
            }

        }
        if(budget != 0){
            chart.addSeries("Non-utilisé", budget);
        }


        JPanel chartPanel = new XChartPanel<>(chart);
        _f.getContentPane().add(chartPanel, BorderLayout.CENTER);

        //On crée le bouton fermer
        JPanel pied = new JPanel();
        _f.getContentPane().add(pied, BorderLayout.SOUTH);


        JButton button = new JButton("Fermer la fenêtre");
        button.addActionListener(actionEvent -> close());
        pied.add(button);

        _f.pack();
        _f.setVisible(true);
    }



    void close(){
        _f.dispose();
    }


}




