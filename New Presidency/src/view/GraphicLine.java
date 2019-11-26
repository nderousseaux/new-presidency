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

/**
 * <b> <i>GraphicLine</i> est une classe qui permet d'afficher les graphiques linaires</b>
 * <p>
 *     Le graphique représente l'évolution des indicateurs et des leviers au fils des tours.
 *     Elle contient :
 *     <ul><li>Une JFrame</li>
 *     <li>La liste des états</li>
 *     <li>Le graphique</li>
 *     </ul>
 * </p>
 *
 * @see StateList
 * @see TextualView
 * @see GraphicalView
 *
 * @author nderousseaux
 * @version 2.0
 */
public class GraphicLine {

    private JFrame _f;
    private StateList _stateList;
    private XYChart chart;

    private HashMap<JComboBox<String>, String> _anciennesValeurs = new HashMap<>();
    private Boolean isLevier;


    /**
     * Instancier et ouvrir la fenêtre graphique
     *
     * @param stateList La liste des états de tout les tours
     *
     * @see GraphicLine#refresh(JComboBox)
     * @see GraphicLine#refreshType(JComboBox, JComboBox, JComboBox)
     * @see GraphicLine#selectItems(String)
     * @see GraphicLine#close()
     *
     * @since 1.0
     */
    public GraphicLine(StateList stateList){
        _stateList = stateList;

        //On crée le JFrame
        _f = new JFrame("Graphique d'évolution");
        _f.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        _f.setLayout(new BorderLayout());
        _f.setSize(1350, 1000);
        _f.setResizable(false);


        //region Entête de la page
        JPanel entete = new JPanel();
        _f.getContentPane().add(entete, BorderLayout.NORTH);

        //Deux comboBox, les deux valeurs à afficher
        JComboBox<String> data1 = new JComboBox<>();
        entete.add(data1);
        JComboBox<String> data2 = new JComboBox<>();
        entete.add(data2);
        _anciennesValeurs.put(data1, "");
        _anciennesValeurs.put(data2, "");
        data1.addActionListener(actionEvent -> refresh(data1));
        data2.addActionListener(actionEvent -> refresh(data2));

        //Première combobox, choix du type à afficher
        String[] val={"Leviers", "Indicateurs"};
        JComboBox<String> type = new JComboBox<>(val);
        type.addActionListener(actionEvent -> refreshType(type, data1, data2));
        entete.add(type);

        //On refrech les comboBox
        refreshType(type, data1, data2);
        //endregion

        //region Graphique
        chart = new XYChartBuilder().width(800).height(600).title(getClass().getSimpleName()).xAxisTitle("Numéro de l'année").yAxisTitle("Valeur").build();
        JPanel chartPanel = new XChartPanel<>(chart);
        _f.getContentPane().add(chartPanel, BorderLayout.CENTER);
        //endregion

        //region Bas de la page
        //On crée le bouton fermer
        JPanel pied = new JPanel();
        _f.getContentPane().add(pied, BorderLayout.SOUTH);
        JButton button = new JButton("Fermer la fenêtre");
        button.addActionListener(actionEvent -> close());
        pied.add(button);
        //endregion

        _f.pack();
        _f.setVisible(true);
    }


    /**
     * Fonction pour mettre les comboBox de sélection des données
     * <p>
     *     La mise à jour se fait en fonctios de la valeur sélectionné dans la première comboBox <i>Leviers/Indicateurs</i>.
     *     Si le première comboBox est sur la valeur "Leviers", on affiche la liste des leviers.
     *     Si elle est sur la valeur "Indicateurs", on affiche la liste des Indicateurs.
     *     La mise à jour se fait au moment où la valeur de la comboBox est modifiée
     * </p>
     *
     * @param a La comboBox de sélection du type de série (Indicateurs/Levier)
     * @param data1 L'une des deux comboBox à mettre à jour
     * @param data2 La deuxième des comboBox à mettre à jour.
     *
     * @since 1.0
     */
    private void refreshType(JComboBox<String> a, JComboBox<String> data1, JComboBox<String> data2){

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

    /**
     * Fonction pour mettre à jour le graphique
     * <p>
     *     La mise à jour se fait en fonction des valeurs sélectionnées dans les JComboBox.
     *     La mise à jour se fait au moment où la valeur d'une comboBox est modifiée
     * </p>
     *
     * @param data comboBox dont la valeur à été modifiée.
     *
     * @since 1.0
     */
    private void refresh(JComboBox<String> data){
        

        //On met à jour le graphique
        String datan = (String) data.getSelectedItem();

        //On ajoute ou on supprime les séries de données
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

    /**
     * Fonction de sélection des données.
     * <p>
     *     Fonction qui sélectionne la bonne série de donnée (dans StateList) en fonction du nom de la série
     * </p>
     *
     * @param name Nom de la série dont on cherche les valeurs
     *
     * @return Tableau de l'évolution de la valeur séléctionné. Une case par période.
     *
     * @since 1.0
     */
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

    /**
     * Fonction de fermeture de la fenêtre
     *
     * @since 1.0
     */
    public void close(){
        _f.dispose();
    }
}