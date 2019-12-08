package view;
import controller.*;
import model.*;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import static java.lang.System.exit;

/**
 * <b><i>GraphicalView</i> est la classe de l'interface graphique du jeu</b>
 * <p>
 *     L'interface est une JFrame, comportant d'autres éléments:
 *     <ul>
 *        <li>des <b>JPannels</b> pour les indicateurs, les leviers, le budget et le tour</li>
 *        <li>des <b>JScrollPane</b> permettant d'avoir un ascenceur vertical</li>
 *        <li>des <b>JButton</b> pour afficher les graphiques d'évolution et terminer le tour</li>
 *     </ul>
 *     Le côté flexible de l'interface est amené par le combo de Layout <b>BorderLayout</b> pour la fenêtre principale,
 *     et <b>GridLayout</b> pour le pannel comprenant les indicateurs et les leviers, ainsi que le pannel au bas de l'interface,
 *     comprenant le budget et les boutons.
 * </p>
 *
 * @see Controller
 * @see TextualView
 *
 * @author yvanderspurt
 */
public class GraphicalView extends JFrame {
    private Controller _controller;
    private JPanel _year;
    private JPanel _levers;
    private JScrollPane _scrollLevers;
    private JPanel _indicators;
    private JScrollPane _scrollIndicators;
    private GraphicLine _graphicLine;
    private JPanel _budget;
    private JButton _nextRound;
    private JButton _showGraphic;
    private JPanel _pannelBottom;
    private JPanel _pannelTop;
    private JPanel _pannelCenter;
    /**
     * Constructeur de GraphicalView, appelant des sous-fonctions d'initialisation
     * @param controller Controlleur du jeu
     * @see GraphicalView#init()
     * @see GraphicalView#updateAll()
     * @see GraphicalView#addAllElements()
     */
    public GraphicalView(Controller controller){
        _controller=controller;
        init(); //Initialisation de la fenetre
        updateAll(); //Initialisation des differentes variables
        homepage();
        //addAllElements(); //Ajout des elements de la fenetre et affichage
    }

    /**
     * Procédure d'initialisation/mise à jour du budget restant à investir
     * @see Controller
     */
    private void updateBudget(){

        JLabel labBudget = new JLabel(String.valueOf((int)_controller.getBudget().getRemainingBudget()));
        _budget=new JPanel();
        _budget.add(new JLabel("Budget restant : "));
        _budget.add(labBudget);

    }

    /**
     * Procédure d'initialisation/mise à jour des leviers de gestion<br>
     *     Chaque élément de la liste comporte
     *     <ul>
     *         <li>le <b>nom du levier</b></li>
     *         <li>Le <b>budget du levier</b>, modifiable au clavier ou les flèches</li>
     *         <li>Deux <b>flèches</b> permettant d'augmenter ou diminuer le budget alloué au levier</li>
     *         <li>Des <b>informations pédagogiques</b> sur le levier</li>
     *     </ul>
     * @see Controller
     */
    private void updateLevers(){
        _levers=new JPanel();
        _levers.setLayout(new GridLayout(_controller.getLevers().size(),1));
        _scrollLevers=new JScrollPane(_levers);
        _scrollLevers.setBounds(20,40,500,610);

        _levers.setToolTipText("Leviers");
        for(Lever l : _controller.getLevers()) {
            //element entier
            JPanel elem=new JPanel();
            elem.setLayout(new GridLayout(1,2));

            //zone texte

            JLabel nom = new JLabel(l.getName());

            SpinnerModel model=new SpinnerNumberModel(l.getBudget(),0,Double.POSITIVE_INFINITY,50);
            JSpinner spinner=new JSpinner(model);
            spinner.addChangeListener(new ChangeListener() {
                @Override
                public void stateChanged(ChangeEvent changeEvent) {
                    try{
                        double test=(double)spinner.getValue();
                        changeBudget(l,spinner);
                    }
                    catch(Exception e){
                        spinner.setValue(String.valueOf(l.getBudget()));
                        spinner.setVisible(true);
                    }

                }
            });
            elem.add(nom);
            elem.add(spinner);
            String info="<html>";
            for(String s : l.getInfos()){
                info+=s;
                info+="<br>";
            }
            info+="</html>";
            elem.setToolTipText(info);
            _levers.add(elem);

        }
    }

    /**
     * Procédure d'initialisation/mise à jour des indicateurs<br>
     *     Chaque élément de la liste comporte
     *     <ul>
     *         <li>Le <b>nom de l'indicateur</b></li>
     *         <li>Le <b>pourcentage de complétion de l'indicateur</b></li>
     *     </ul>
     * @see Controller
     */
    private void updateIndics(){
        _indicators=new JPanel();
        _indicators.setLayout(new GridLayout(_controller.getIndicators().size(),1));

        _scrollIndicators=new JScrollPane(_indicators);
        _scrollIndicators.setBounds(600,40,500,610);
        _scrollIndicators.setPreferredSize(new Dimension(500,610));

        //_indicators.setToolTipText("Indicateurs");

        for(Indicator i : _controller.getIndicators()){
            //element entier

            JPanel elem=new JPanel();

            elem.setLayout(new GridLayout(1,2));


            //zone texte

            JLabel nom = new JLabel(i.getName());

            //zone valeur
            JPanel zoneval=new JPanel();
            JLabel val = new JLabel((int)i.getValue()+"%");
            zoneval.add(val);
            elem.add(nom);
            elem.add(zoneval);

            String info="<html>";
            for(String s : i.getInfos()){
                info+=s;
                info+="<br>";
            }
            info+="</html>";
            elem.setToolTipText(info);
            _indicators.add(elem);
        }
    }

    /**Procédure d'initialisation/mise à jour du tour
     * @see Controller
     */
    private void updateYear(){
        _year = new JPanel();
        JTextArea textArea= new JTextArea("Année "+_controller.getYear()+" sur "+_controller.getMaxYear());
        textArea.setFocusable(false);
        _year.add(textArea);
    }

    /**Procédure de fin de tour<br>
     * Avant le tour d'après, un graphique s'affiche, récapitulant la <b>répartion du budget du tour</b>
     * @see Controller
     * @see GraphicPie
     */
    private void endOfRound(){
        if(_controller.getYear()<=_controller.getMaxYear()) {
            removeAllElements();
            updateAll();
            addAllElements();
            this.setVisible(true);
        }
        else
            exit(0);
    }

    /**Procédure de mise en place des éléments de la fenêtre principale
     * @see GraphicalView#removeAllElements
      */
    private void addAllElements(){
        this.getContentPane().removeAll();
        //Pannel des leviers/indicateurs
        _pannelCenter=new JPanel();
        _pannelCenter.setLayout(new GridLayout(2,2));
        _nextRound = new JButton(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                _controller.endOfRound();
                endOfRound();
            }
        });

        //Ajout des indicateurs et leviers
        _pannelCenter.add(_scrollLevers);
        _pannelCenter.add(_scrollIndicators);

        //Initialisation du graphique de suivi

        _graphicLine=new GraphicLine(_controller.getStateList());

        //Ajout au pannel central
        _pannelCenter.add(_graphicLine);

        //Ajout du pannel entier
        this.getContentPane().add(_pannelCenter,BorderLayout.CENTER);

        //Creation du bouton de tour suivant
        _nextRound.setText("Passer au tour suivant");

        //Creation du bouton de visualisation des graphiques d'evolution
        _showGraphic = new JButton(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                _controller.showGraphicLine();
            }
        });
        _showGraphic.setText("Afficher l'évolution des indicateurs et des leviers");
        if(_controller.getYear()==1)
            _showGraphic.setEnabled(false);
        //Ajout de l'annee courante
        this.getContentPane().add(_year,BorderLayout.NORTH);

        JButton exit=new JButton(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                exit(0);
            }
        });
        exit.setText("X");
        exit.setBackground(Color.RED);
        exit.setBorderPainted(true);
        exit.setBounds(0,25,20,20);

        JButton tuto=new JButton();
        tuto.setText("Tutoriel");
        tuto.setEnabled(false);
        _pannelTop=new JPanel();
        _pannelTop.setLayout(new BorderLayout());
        _pannelTop.add(_year,BorderLayout.CENTER);
        _pannelTop.add(exit,BorderLayout.AFTER_LINE_ENDS);
        _pannelTop.add(tuto,BorderLayout.BEFORE_LINE_BEGINS);
        _pannelBottom=new JPanel();
        _pannelBottom.setLayout(new GridLayout(1,3));
        _pannelBottom.add(_budget);
        _pannelBottom.add(_showGraphic);
        _pannelBottom.add(_nextRound);

        this.getContentPane().add(_pannelBottom,BorderLayout.SOUTH);
        this.getContentPane().add(_pannelTop,BorderLayout.NORTH);
        this.setVisible(true);
    }

    /** Procédure de retrait des éléments de la fenêtre principale (permet le rafraichissement des variables, comme le budget)
     * @see GraphicalView#addAllElements()
     * @see GraphicalView#updateBudget()
     *
     */
    private void removeAllElements(){
        _pannelCenter.removeAll();
        this.remove(_pannelCenter);
        _pannelBottom.removeAll();
        this.remove(_pannelBottom);
        _pannelTop.removeAll();
        this.remove(_pannelTop);
    }

    /**Procédure d'appel de toutes les initialisations/mises à jour de tous les éléments de la fenêtre principale
     *
     * @see GraphicalView#updateLevers()
     * @see GraphicalView#updateIndics()
     * @see GraphicalView#updateYear()
     * @see GraphicalView#updateBudget()
     */
    private void updateAll(){
        updateLevers();
        updateIndics();
        updateBudget();
        updateYear();
    }

    /** Procédure de définition de la taille, du nom et du Layout de la fenêtre principale. Appelé une unique fois au début du jeu
     *
     * @see BorderLayout
     */
    private void init(){
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setUndecorated(true);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setSize(screenSize);

        this.setTitle("New Presidency");
        this.setLayout(new BorderLayout());
        this.setResizable(false);
    }

    /** Procédure de communication à un levier donné d'une volonté de modification (qui peut échouer selon l'action) sur le budget alloué
     *
     * @param lever Levier dont le budget doit changer
     * @param jspinner JSpinner contenant la valeur du budget, entrée par l'utilisateur
     *
     * @see GraphicalView#removeAllElements()
     * @see GraphicalView#updateBudget()
     * @see GraphicalView#addAllElements()
     * @see Controller
     */
    private void changeBudget(Lever lever, JSpinner jspinner){
        Integer res=_controller.setLeverBudget(lever,(double)jspinner.getValue());
        if(res==0){
            removeAllElements();
            updateBudget();
            addAllElements();
            this.setVisible(true);
        }
        else{
            jspinner.setValue(String.valueOf(lever.getBudget()));
        }
    }

    private void homepage(){
        JPanel content = new JPanel();
        content.setLayout(new GridBagLayout());
        JLabel title=new JLabel("Bienvenue sur New Presidency!");
        title.setFont(new Font("Arial",Font.BOLD,24));

        JButton tuto=new JButton("Lancer le tutoriel");
        JButton noTuto=new JButton(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                addAllElements();
            }
        });
        noTuto.setText("Passer le tutoriel");
        tuto.setEnabled(false);

        JPanel buttons=new JPanel();
        buttons.setLayout(new GridLayout(1,2));
        buttons.add(tuto);
        buttons.add(noTuto);

        GridBagConstraints cTitle=new GridBagConstraints();
        cTitle.fill = GridBagConstraints.HORIZONTAL;

        GridBagConstraints cButtons=new GridBagConstraints();
        cButtons.fill=GridBagConstraints.HORIZONTAL;
        cButtons.gridy=1;

        content.add(title,cTitle);
        content.add(buttons,cButtons);

        this.add(content,BorderLayout.CENTER);

        this.setVisible(true);
    }

    private void exitPage(){
        this.removeAll();
    }
}

