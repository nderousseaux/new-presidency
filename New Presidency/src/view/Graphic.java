package view;


import model.State;
import model.StateList;
import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

class Graphic{

    private JFrame _f;
    private StateList _stateList;
    private JComboBox<String> data1;
    private JComboBox<String> data2;
    private JComboBox<String> data3;
    private JComboBox<String> data4;
    private XYChart chart;
    private HashMap<JComboBox<String>, String> _anciennesValeurs = new HashMap<>();




    Graphic(StateList stateList){
        _stateList = stateList;
        _f = new JFrame("Graphique d'évolution");


        //Configuration du JFrame
        _f.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        _f.setLayout(new BorderLayout());
        _f.setSize(1350, 1000);
        _f.setResizable(false);

        //Création de l'entête
        JPanel entete = new JPanel();
        _f.getContentPane().add(entete, BorderLayout.NORTH);

        JLabel text = new JLabel("Choisissez une ou plusieurs données : ");
        entete.add(text);

        //Combobox
        String[] valeurs = {"",
                "Budget restant",
                "Taux de réussite",
                "Satisfaction du personnel",
                "Satisfaction étudiante",
                "Dotation récurante pour la formation",
                "Dotation récurante pour la recherche",
                "Dotation spécifique pour la formation",
                "Prime de formation",
                "Investissement en construction",
                "Subventions aux associations étudiantes"};
        data1 = new JComboBox<>(valeurs);
        entete.add(data1);
        data2 = new JComboBox<>(valeurs);
        entete.add(data2);
        data3 = new JComboBox<>(valeurs);
        entete.add(data3);
        data4 = new JComboBox<>(valeurs);
        entete.add(data4);


        _anciennesValeurs.put(data1, "");
        _anciennesValeurs.put(data2, "");
        _anciennesValeurs.put(data3, "");
        _anciennesValeurs.put(data4, "");

        //Si une des valeurs changes, on lance réaffiche le graphique
        data1.addActionListener(actionEvent -> refresh(data1));
        data2.addActionListener(actionEvent -> refresh(data2));
        data3.addActionListener(actionEvent -> refresh(data3));
        data4.addActionListener(actionEvent -> refresh(data4));




        //On crée le graphique
        chart = new XYChartBuilder().width(800).height(600).title(getClass().getSimpleName()).xAxisTitle("Numéro de l'année").yAxisTitle("Valeur").build();
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

    private void refresh(JComboBox<String> data){
        

        //On met à jour le graphique
        String datan = (String) data.getSelectedItem();

        if(!datan.equals("")){
            if(!_anciennesValeurs.containsValue(datan)){
                chart.addSeries( datan, selectItems(datan));
                chart.removeSeries(_anciennesValeurs.get(data));
                _anciennesValeurs.replace(data, datan);

            }


        }
        else{
            if(!_anciennesValeurs.get(data).equals("")){
                chart.removeSeries(_anciennesValeurs.get(data));
            }
        }
        _f.repaint();






    }

    private double[] selectItems(String name){

        double[] values = new double[_stateList.getStates().size()-1];

        //Pour chaque état, on incrémente la liste des valeurs
        int i=0;
        for (State s:_stateList.getStates()) {
            if (i != 0) {
                switch (name){
                    case "Budget restant":
                        values[i-1] = s.getRemainingBudget();
                        break;
                    case "Taux de réussite":
                        values[i-1] = s.getITauxReu();
                        break;
                    case "Satisfaction du personnel":
                        values[i-1] = s.getISatPers();
                        break;
                    case "Satisfaction étudiante":
                        values[i-1] = s.getISatEtu();
                        break;
                    case "Dotation récurante pour la formation":
                        values[i-1] = s.getLDotRecForm();
                        break;
                    case "Dotation récurante pour la recherche":
                        values[i-1] = s.getLDotRecRech();
                        break;
                    case "Dotation spécifique pour la formation":
                        values[i-1] = s.getLDotSpeForm();
                        break;
                    case "Prime de formation":
                        values[i-1] = s.getLPrime();
                        break;
                    case "Investissement en construction":
                        values[i-1] = s.getLImmo();
                        break;
                    case "Subventions aux associations étudiantes":
                        values[i-1] = s.getLSubAssoEtu();
                        break;
                    default:
                        break;
                }

            }


            i+=1;
        }



        return values;
    }

    void close(){
        _f.dispose();
    }


}




