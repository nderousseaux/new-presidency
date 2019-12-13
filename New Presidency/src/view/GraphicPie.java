package view;


import model.Lever;
import model.LeverList;
import model.State;
import model.StateList;
import org.knowm.xchart.*;
import org.knowm.xchart.style.Styler;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
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
public class GraphicPie extends JTabbedPane{

    private Collection<Lever> _leverList;
    private Double _budget;
    private PieChart _chart;

    /**
     * Constructeur qui instancie et ouvre la fenêtre graphique
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
        double somme = 0;
        //On ajoute les données (sauf les salaires)
        ArrayList<String> donneesSalaire = new ArrayList<>();
        donneesSalaire.add("LNbTituForm");
        donneesSalaire.add("LSalTituForm");
        donneesSalaire.add("LNbContrForm");
        donneesSalaire.add("LSalContrForm");
        donneesSalaire.add("LNbTituRech");
        donneesSalaire.add("LSalTituRech");
        donneesSalaire.add("LNbContrRech");
        donneesSalaire.add("LSalContrRech");
        HashMap<String, Double> donneesSal = new HashMap<>();
        for (Lever l:_leverList) {
            if(l.getBudget() != 0 && !donneesSalaire.contains(l.getAbreviation())){
                _chart.addSeries(l.getName(), l.getBudget());
                somme += l.getBudget();
            }
            if(donneesSalaire.contains((l.getAbreviation()))){
                donneesSal.put(l.getAbreviation(), l.getBudget());
            }
        }
        //On règle les Coût salaire
        _chart.addSeries("Coût salaires titulaires formation", donneesSal.get("LNbTituForm") * donneesSal.get("LSalTituForm"));
        somme += donneesSal.get("LNbTituForm") * donneesSal.get("LSalTituForm");
        _chart.addSeries("Coût salaires contractuels formation", donneesSal.get("LNbContrForm") * donneesSal.get("LSalContrForm"));
        somme += donneesSal.get("LNbContrForm") * donneesSal.get("LSalContrForm");
        _chart.addSeries("Coût salaires titulaires recherche", donneesSal.get("LNbTituRech") * donneesSal.get("LSalTituRech"));
        somme += donneesSal.get("LNbTituRech") * donneesSal.get("LSalTituRech");
        _chart.addSeries("Coût salaires contractuels recherche", donneesSal.get("LNbContrRech") * donneesSal.get("LSalContrRech"));
        somme += donneesSal.get("LNbContrRech") * donneesSal.get("LSalContrRech");
        if(budget != 0){
            _chart.addSeries("Non-utilisé", _budget);
            somme+=_budget;
        }

        //On supprime toutes les séries qui sont en dessous de 7%
        double restant = 0;
        for(Lever l:_leverList){
            if(l.getBudget()/somme<0.07){
                _chart.removeSeries(l.getName());
                restant+=l.getBudget();
            }
        }
        if(donneesSal.get("LNbTituForm") * donneesSal.get("LSalTituForm")< 0.02){
            _chart.removeSeries("Coût salaires titulaires formation");
            restant += donneesSal.get("LNbTituForm") * donneesSal.get("LSalTituForm");
        }
        if(donneesSal.get("LNbContrForm") * donneesSal.get("LSalContrForm")< 0.02){
            _chart.removeSeries("Coût salaires contractuels formation");
            restant += donneesSal.get("LNbContrForm") * donneesSal.get("LSalContrForm");
        }
        if(donneesSal.get("LNbTituRech") * donneesSal.get("LSalTituRech")< 0.02){
            _chart.removeSeries("Coût salaires titulaires recherche");
            restant += donneesSal.get("LNbTituRech") * donneesSal.get("LSalTituRech");
        }
        if(donneesSal.get("LNbContrRech") * donneesSal.get("LSalContrRech")< 0.02){
            _chart.removeSeries("Coût salaires contractuels recherche");
            restant += donneesSal.get("LNbContrRech") * donneesSal.get("LSalContrRech");
        }

        _chart.addSeries("Autres leviers", restant);

        JPanel chartPanel = new XChartPanel<>(_chart);
        this.add(chartPanel);
    }

    /**
     * Fonction de <b>rafraichissement du graphique</b>, appelant le constructeur
     * @param leverList Liste des objets <b>Lever</b>
     * @param budget Budget
     * @return Objet <b>GraphicPie</b> instancié
     */
    public GraphicPie refresh(Collection<Lever> leverList, Double budget){
        return new GraphicPie(leverList,budget);
    }
}




