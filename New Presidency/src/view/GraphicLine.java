package view;

import model.State;
import model.StateList;
import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * <b> <i>GraphicLine</i> est une classe qui permet d'afficher les graphiques linaires</b>
 * <p>
 *     Le graphique représente l'évolution des indicateurs et des leviers au fils des tours.
 *     Elle contient :
 *     <li>La liste des états</li>
 *     <li>Le graphique</li>
 *     </ul>
 * </p>
 *
 * @see StateList
 * @see GraphicalView
 *
 * @author nderousseaux
 * @version 4.0
 */
public class GraphicLine extends JPanel{
    private StateList _stateList;
    private XYChart _chart;
    private ArrayList<String> _seriesAjoutees = new ArrayList<>();

    /**
     * Instancier et ouvrir la fenêtre graphique
     *
     * @param stateList La liste des états de tout les tours
     *
     * @see GraphicLine#addSerie(String)
     * @see GraphicLine#delSerie(String)
     * @see GraphicLine#selectData(String)
     *
     * @since 1.0
     */
    public GraphicLine(StateList stateList){
        _stateList = stateList;

        //On crée le JPanel
        this.setLayout(new BorderLayout());


        //region Graphique
        _chart = new XYChartBuilder().width(800).height(600).title(getClass().getSimpleName()).xAxisTitle("Numéro de l'année").yAxisTitle("Valeur").title("Graphiques d'évolution").build();
        JPanel chartPanel = new XChartPanel<>(_chart);
        this.add(chartPanel, BorderLayout.CENTER);
        //endregion
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
     * @throws IllegalArgumentException Si le paramètre name n'est pas un levier si un indicateur connu
     *
     * @since 3.0
     */
//TODO:Fonction qui vérifie si une série est sur le graphique
    private double[] selectData(String name){

        int nombre = _stateList.getStates().size()-1;

        double[] values = new double[nombre];

        //On parcourt tout les états de toutes les périodes. On crée une liste de l'évolution des états pour un indicateur donné.
        int i=0;
        for (State s:_stateList.getStates()) {
            if (i != _stateList.getStates().size() - 1) {
                try{
                    values[i] = s.getLever(name);
                }
                catch (Exception IllegalArgumentException){
                    values[i] = s.getIndicator(name);
                }
                i += 1;
            }
        }
        return values;
    }

    /**
     * Fonction pour ajouter une série sur le graphique
     * <p>
     *     L'appel à la fonction fera afficher la séries de données correspondante au graphique.
     *     Ainsi, l'appel "Dotation récurante en Recherche" fera affiche la série de la dotation récurante en recherche sur le grahpique.
     * </p>
     *
     * @param nom Le nom de la série à ajouter
     *
     * @see GraphicLine#selectData(String)
     *
     * @since 2.0
     */
    public void addSerie(String nom){
        _chart.addSeries(nom, selectData(nom));
        _seriesAjoutees.add(nom);
        this.repaint();
    }

    /**
     * Fonction pour supprimer une série sur le graphique
     * <p>
     *     L'appel à la fonction supprimera la séries de données correspondante au graphique.
     *     Ainsi, l'appel "Dotation récurante en Recherche" supprimera la série de la dotation récurante en recherche sur le grahpique.
     * </p>
     *
     * @param nom Le nom de la série à supprimer
     *
     *
     * @since 2.0
     */
    public void delSerie(String nom){
        _chart.removeSeries(nom);
        _seriesAjoutees.remove(nom);
        this.repaint();
    }

    /**
     * Fonction pour tester si une série est déja sur le graphique
     *
     * @param nom Le nom de la série à supprimer
     *
     * @return Booléen, vrai si la série est déjà sur le graphique.
     *
     * @since 4.0
     */
    public boolean hasSerie(String nom){
        return _seriesAjoutees.contains(nom);
    }
}