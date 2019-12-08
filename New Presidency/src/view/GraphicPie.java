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
public class GraphicPie {

    private JFrame _f;
    private Collection<Lever> _leverList;
    private PieChart _chart;

    /**
     * Instancier et ouvrir la fenêtre graphique
     *
     * @param leverList La liste des leviers, et le budget aloué à chacun d'entre eux.
     * @param budget Budget total du tour.
     *
     * @see GraphicPie#close()
     *
     * @since 1.0
     */
    public GraphicPie(Collection<Lever> leverList, Double budget){
        _leverList = leverList;

        //Configuration du JFrame
        _f = new JFrame("Comment vous avez utilisé votre budget !");
        _f.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        _f.setLayout(new BorderLayout());
        _f.setSize(1350, 1000);
        _f.setResizable(false);

        //On crée le graphique
        _chart = new PieChartBuilder().width(800).height(600).title(getClass().getSimpleName()).build();

        //On ajoute les données
        for (Lever l:_leverList) {
            if(l.getBudget() != 0){
                _chart.addSeries(l.getName(), l.getBudget());
            }

        }
        if(budget != 0){
            _chart.addSeries("Non-utilisé", budget);
        }

        JPanel chartPanel = new XChartPanel<>(_chart);
        _f.getContentPane().add(chartPanel, BorderLayout.CENTER);

        //On crée le bouton fermer
        JPanel pied = new JPanel();
        _f.getContentPane().add(pied, BorderLayout.SOUTH);
        JButton button = new JButton("Fermer la fenêtre");
        button.addActionListener(actionEvent -> close());
        pied.add(button);

        _f.pack();
        _f.setVisible(true);
        _f.setAlwaysOnTop(true);
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




