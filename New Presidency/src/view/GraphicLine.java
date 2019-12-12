package view;

import model.*;
import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import javax.swing.*;
import javax.swing.border.Border;
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
 * @version 5.0
 */
public class GraphicLine extends JTabbedPane{
    private StateList _stateList;
    private XYChart _chartLevier;
    private XYChart _chartIndicateur;
    private ArrayList<String> _seriesAjoutees = new ArrayList<>();

    /**
     * Instancier et ouvrir les graphiques
     *
     * @param stateList La liste des états de tout les tours
     *
     * @see GraphicLine#addSerie(IndicLever)
     * @see GraphicLine#delSerie(IndicLever)
     * @see GraphicLine#selectData(IndicLever)
     *
     * @since 1.0
     */
    public GraphicLine(StateList stateList){
        _stateList = stateList;


        //region Graphique
        _chartLevier = new XYChartBuilder().width(800).height(600).title(getClass().getSimpleName()).xAxisTitle("Numéro de l'année").yAxisTitle("Valeur").title("Graphiques d'évolution des leviers").build();
        JPanel chartPanelLevier = new XChartPanel<>(_chartLevier);
        this.addTab("Leviers", chartPanelLevier);
        _chartIndicateur = new XYChartBuilder().width(800).height(600).title(getClass().getSimpleName()).xAxisTitle("Numéro de l'année").yAxisTitle("Valeur").title("Graphiques d'évolution des indicateurs").build();
        JPanel chartPanelIndicateur = new XChartPanel<>(_chartIndicateur);
        this.addTab("Indicateurs", chartPanelIndicateur);

        JPanel indice = new JPanel();
        JTextArea label = new JTextArea("\n\n\n\n\n\n\n\n\nPour afficher les graphiques, cliquez sur l'indicateur ou le levier que vous voulez afficher. \nRecliquez dessus pour l'effacer du graphique.\n\n\n\n\n\n\n\n\n");
        Font font = new Font("Arial", Font.BOLD,20);
        label.setFont(font);

        indice.add(label);

        this.addTab("Indices", indice);
        this.setSelectedIndex(2);
        //endregion
    }

    /**
     * Méthode qui affiche le message d'aide du premier tour.
     *
     * @since 5.0
     */
    public void debut(){
        this.removeAll();
        String text = "Les graphiques d'évolution n'ont pas de sens au premier tour. Ils seront affichés au tour suivant.";
        Border innerBorder=BorderFactory.createLineBorder(Color.black,3);
        JLabel textLabel=new JLabel("<html><p style=\"text-align:center\">"+text+"</p></html>");
        textLabel.setBorder(innerBorder);
        textLabel.setFont(new Font("Arial",Font.ITALIC,20));

        JPanel panel=new JPanel();
        panel.setLayout(new GridLayout(1,1));
        panel.add(textLabel);
        panel.setSize(250,100);
        panel.setBackground(new Color(208,233,234));

        this.add("Indices", panel);
    }

    /**
     * Fonction de sélection des données.
     * <p>
     *     Fonction qui sélectionne la bonne série de donnée (dans StateList) en fonction du nom de la série
     * </p>
     *
     * @param indicLever Série dont on cherche les valeurs
     *
     * @return Tableau de l'évolution de la valeur séléctionné. Une case par période.
     *
     *
     * @since 4.0
     */
    private double[] selectData(IndicLever indicLever){

        int nombre = _stateList.getStates().size()-1;

        double[] values = new double[nombre];

        //On parcourt tout les états de toutes les périodes. On crée une liste de l'évolution des états pour un indicateur donné.
        int i=0;
        if(indicLever instanceof Indicator){
            for(State s:_stateList.getStates()){
                if (i != 0) {
                    values[i-1] = s.getIndicator(indicLever.getAbreviation());
                }
                i++;
            }
        }
        if(indicLever instanceof Lever){
            for(State s:_stateList.getStates()){
                if(i!=_stateList.getStates().size()-1){
                    values[i] = s.getLever(indicLever.getAbreviation());
                }
                i++;
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
     * @param indicLever Série à ajouter
     *
     * @see GraphicLine#selectData(IndicLever)
     *
     * @since 4.0
     */
    public void addSerie(IndicLever indicLever){
        if(indicLever instanceof Indicator){
            _chartIndicateur.addSeries(indicLever.getName(), selectData(indicLever));
            this.setSelectedIndex(1);
        }
        if(indicLever instanceof Lever){
            _chartLevier.addSeries(indicLever.getName(), selectData(indicLever));
            this.setSelectedIndex(0);
        }

        _seriesAjoutees.add(indicLever.getAbreviation());
        try{
            this.remove(2);
        }
        catch (Exception e){

        }
        this.repaint();
    }

    /**
     * Fonction pour supprimer une série sur le graphique
     * <p>
     *     L'appel à la fonction supprimera la séries de données correspondante au graphique.
     *     Ainsi, l'appel "Dotation récurante en Recherche" supprimera la série de la dotation récurante en recherche sur le grahpique.
     * </p>
     *
     * @param indicLever Série à supprimer
     *
     *
     * @since 2.0
     */
    public void delSerie(IndicLever indicLever){
        if(indicLever instanceof Indicator){
            _chartIndicateur.removeSeries(indicLever.getName());
        }
        if(indicLever instanceof Lever){
            _chartLevier.removeSeries(indicLever.getName());
        }
        _seriesAjoutees.remove(indicLever.getAbreviation());

        if(_seriesAjoutees.size() == 0){
            String text = "Pour afficher les graphiques, cliquez sur l'indicateur ou le levier que vous voulez afficher. \nRecliquez dessus pour l'effacer du graphique.";
            Border innerBorder=BorderFactory.createLineBorder(Color.black,3);
            JLabel textLabel=new JLabel("<html><p style=\"text-align:center\">"+text+"</p></html>");
            textLabel.setBorder(innerBorder);
            textLabel.setFont(new Font("Arial",Font.ITALIC,20));

            JPanel panel=new JPanel();
            panel.setLayout(new GridLayout(1,1));
            panel.add(textLabel);
            panel.setSize(250,100);
            panel.setBackground(new Color(208,233,234));

            this.add("Indices", panel);
            this.setSelectedIndex(2);

        }

        this.repaint();
    }

    /**
     * Fonction pour tester si une série est déja sur le graphique
     *
     * @param indicLever Série à tester
     *
     * @return Booléen, vrai si la série est déjà sur le graphique.
     *
     * @since 4.0
     */
    public boolean hasSerie(IndicLever indicLever){
        return _seriesAjoutees.contains(indicLever.getAbreviation());
    }
}