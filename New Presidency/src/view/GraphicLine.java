package view;



import model.State;
import model.StateList;
import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

class GraphicLine {

    private JFrame _f;
    private StateList _stateList;
    private JComboBox<String> data1;
    private JComboBox<String> data2;
    private XYChart chart;
    private HashMap<JComboBox<String>, String> _anciennesValeurs = new HashMap<>();
    private Boolean isLevier;




    GraphicLine(StateList stateList){
        _stateList = stateList;

        //On crée le JFrame
        _f = new JFrame("Graphique d'évolution");


        //Configuration du JFrame
        _f.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        _f.setLayout(new BorderLayout());
        _f.setSize(1350, 1000);
        _f.setResizable(false);


        //region Entête
        JPanel entete = new JPanel();
        _f.getContentPane().add(entete, BorderLayout.NORTH);

        //Première combobox, choix du type à afficher
        String[] val={"Leviers", "Indicateurs"};
        JComboBox<String> type = new JComboBox<>(val);
        type.addActionListener(actionEvent -> refreshType(type));
        entete.add(type);

        data1 = new JComboBox<>();
        entete.add(data1);
        data2 = new JComboBox<>();
        entete.add(data2);
        _anciennesValeurs.put(data1, "");
        _anciennesValeurs.put(data2, "");
        data1.addActionListener(actionEvent -> refresh(data1));
        data2.addActionListener(actionEvent -> refresh(data2));


        //On affiche une première fois les combobx :
        refreshType(type);
        //endregion


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


        if (datan != null) {
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
        }
        _f.repaint();






    }

    private void refreshType(JComboBox<String> a){

        //On affiche les combobox qui correspondent
        String s = (String)a.getSelectedItem();
        ArrayList<String> valeurs = new ArrayList<>();
        assert s != null;
        if(s.equals("Indicateurs")){
            isLevier = false;
            valeurs.add("");
            valeurs.add("Taux de réussite");
            valeurs.add("Satisfaction du personnel");
            valeurs.add("Satisfaction étudiante");
        }
        else{
            isLevier = true;
            valeurs.add("");
            valeurs.add("Dotation récurante pour la formation");
            valeurs.add("Dotation récurante pour la recherche");
            valeurs.add("Dotation spécifique pour la formation");
            valeurs.add("Prime de formation");
            valeurs.add("Investissement en construction");
            valeurs.add("Subventions aux associations étudiantes");

        }

        //Combobox
        data1.removeAllItems();
        data2.removeAllItems();
        for (String v : valeurs){
            data1.addItem(v);
            data2.addItem(v);
        }

        _anciennesValeurs.replace(data1, "");
        _anciennesValeurs.replace(data2, "");











    }


    private double[] selectItems(String name){

        int nombre = _stateList.getStates().size()-1;

        double[] values = new double[nombre];

        //Pour chaque état, on incrémente la liste des valeurs
        int i=0;
        for (State s:_stateList.getStates()) {
            if (i != _stateList.getStates().size()-1) {
                if(isLevier){
                    switch (name){
                        case "Dotation récurante pour la formation":
                            values[i] = s.getLDotRecForm();
                            break;
                        case "Dotation récurante pour la recherche":
                            values[i] = s.getLDotRecRech();
                            break;
                        case "Dotation spécifique pour la formation":
                            values[i] = s.getLDotSpeForm();
                            break;
                        case "Prime de formation":
                            values[i] = s.getLPrime();
                            break;
                        case "Investissement en construction":
                            values[i] = s.getLImmo();
                            break;
                        case "Subventions aux associations étudiantes":
                            values[i] = s.getLSubAssoEtu();
                            break;
                        default:
                            break;
                    }

                }
                else{
                    switch (name){
                        case "Budget restant":
                            values[i] = s.getRemainingBudget();
                            break;
                        case "Taux de réussite":
                            values[i] = s.getITauxReu();
                            break;
                        case "Satisfaction du personnel":
                            values[i] = s.getISatPers();
                            break;
                        case "Satisfaction étudiante":
                            values[i] = s.getISatEtu();
                            break;
                        default:
                            break;

                    }


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




