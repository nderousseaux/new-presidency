package view;


import model.Lever;
import model.LeverList;
import model.State;
import model.StateList;
import org.knowm.xchart.*;
import org.knowm.xchart.style.Styler;

import javax.swing.*;
import java.awt.*;
import java.util.Collection;
import java.util.HashMap;

/**
 * <b> <i>GraphicPie</i> est une classe qui permet d'afficher les graphiques circulaire.</b>
 * <p>
 *     Le graphique représente la manière dont le joueur à utilisé son budget durant le tour.
 *     Il s'affiche après la fin du tour.
 *     Elle contient :
 *     <ul><li>Une JFrame</li>
 *     <li>La liste des leviers</li>
 *     <li>Le graphique</li>
 *     </ul>
 * </p>
 *
 * @see LeverList
 * @see GraphicalView
 *
 * @author nderousseaux
 * @version 1.0
 */
public class GraphicPie extends JPanel{

    private Collection<Lever> _leverList;
    private Double _budget;
    private PieChart _chart;

    /**
     * Instancier et ouvrir la fenêtre graphique
     *
     * @param leverList La liste des leviers, et le budget aloué à chacun d'entre eux.
     * @param budget Budget total du tour.
     *
     *
     * @since 1.0
     */
    public GraphicPie(Collection<Lever> leverList, Double budget){
        _leverList = leverList;
        _budget=budget;

        //On crée le graphique
        _chart = new PieChartBuilder().width(500).height(500).title("Répartition du budget du tour").build();
        //On ajoute les données
        for (Lever l:_leverList) {
            if(l.getBudget() != 0){
                _chart.addSeries(l.getName(), l.getBudget());
            }
        }
        if(budget != 0){
            _chart.addSeries("Non-utilisé", _budget);
        }

        JPanel chartPanel = new XChartPanel<>(_chart);
        this.add(chartPanel, BorderLayout.CENTER);
    }

    public GraphicPie refresh(Collection<Lever> leverList, Double budget){
        return new GraphicPie(leverList,budget);
    }
}




