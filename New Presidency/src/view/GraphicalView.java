package view;
import controller.*;
import model.*;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;
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
    private GraphicPie _graphicPie;
    private JPanel _budget;
    private JButton _nextRound;
    private JPanel _pannelBottom;
    private JPanel _pannelTop;
    private JPanel _pannelCenter;
    /**
     * Constructeur de <b>GraphicalView</b>, appelant des <b>sous-fonctions d'initialisation</b>
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
     * Procédure d'<b>initialisation/mise à jour</b> du budget restant à investir
     * @see Controller
     */
    private void updateBudget(){

        JLabel labBudget = new JLabel(String.valueOf((int)_controller.getBudget().getRemainingBudget()));
        _budget=new JPanel();
        _budget.add(new JLabel("Budget restant : "));
        _budget.add(labBudget);

    }

    /**
     * Procédure d'<b>initialisation/mise à jour</b> des <b>leviers de gestion</b><br>
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

        for(Lever l : _controller.getLevers()) {
            //element entier
            JPanel elem=new JPanel();
            elem.setLayout(new GridLayout(1,3));

            //zone texte

            JLabel nom = new JLabel(l.getName());

            SpinnerModel model=new SpinnerNumberModel(l.getBudget(),0,Double.POSITIVE_INFINITY,50);
            JSpinner spinner=new JSpinner(model);
            spinner.addChangeListener(new ChangeListener() {
                @Override
                public void stateChanged(ChangeEvent changeEvent) {
                    try{
                        double test=(double)spinner.getValue();
                        changeLeverBudget(l,spinner);
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

            elem.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent mouseEvent) {
                    _graphicLine.addSerie(l.getName());
                }

                @Override
                public void mousePressed(MouseEvent mouseEvent) {

                }

                @Override
                public void mouseReleased(MouseEvent mouseEvent) {

                }

                @Override
                public void mouseEntered(MouseEvent mouseEvent) {

                }

                @Override
                public void mouseExited(MouseEvent mouseEvent) {

                }
            });
            _levers.add(elem);

        }
    }

    /**
     * Procédure d'<b>initialisation/mise à jour</b> des <b>indicateurs de réussite</b><br>
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

        for(Indicator i : _controller.getIndicators()){
            //element entier

            JPanel elem=new JPanel();

            elem.setLayout(new GridLayout(1,2));


            //zone texte

            JLabel nom = new JLabel(i.getName());

            //zone valeur
            JPanel zoneval=new JPanel();
            JLabel val = new JLabel(String.valueOf((int)i.getValue()));
            //On ne met pas de pourcent pour les nombres entiers (nombre de prix Nobel, étudiants...)
            if(!i.getAbreviation().substring(0,3).equals("INb")) {
                val.setText(val.getText() + "%");
            }
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

    /**Procédure d'<b>initialisation/mise à jour du tour</b>
     * @see Controller
     */
    private void updateYear(){
        _year = new JPanel();
        JTextArea textArea= new JTextArea("Année "+_controller.getYear()+" sur "+_controller.getMaxYear());
        textArea.setFocusable(false);
        _year.add(textArea);
    }

    /**Procédure de <b>fin de tour</b><br>
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

    /**Procédure de <b>mise en place des éléments</b> de la fenêtre principale
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
        if(_controller.getYear()==1)
            _graphicLine.setVisible(false);

        //Initialisation du graphique de répartition du budget du tour

        _graphicPie=new GraphicPie(_controller.getLevers(),_controller.getBudget().getRemainingBudget());

        //Ajout au pannel central
        _pannelCenter.add(_graphicLine);
        _pannelCenter.add(_graphicPie);

        //Ajout du pannel entier
        this.getContentPane().add(_pannelCenter,BorderLayout.CENTER);

        //Creation du bouton de tour suivant
        _nextRound.setText("Passer au tour suivant");

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
        _pannelBottom.add(_nextRound);

        this.getContentPane().add(_pannelBottom,BorderLayout.SOUTH);
        this.getContentPane().add(_pannelTop,BorderLayout.NORTH);
        this.setVisible(true);
    }

    /** Procédure de <b>retrait des éléments</b> de la fenêtre principale (permet le <b>rafraichissement des variables</b>, comme le budget)
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

    /**Procédure d'<b>appel de toutes les initialisations/mises à jour</b> de tous les éléments de la fenêtre principale
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

    /** Procédure de <b>définition de la taille</b>, du <b>nom</b> et du <b>Layout</b> de la fenêtre principale. Appelé une unique fois au début du jeu
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

    /** Procédure de <b>communication à un levier donné</b> d'une <b>volonté de modification</b> (qui peut échouer selon l'action) sur le <b>budget alloué</b>
     *
     * @param lever Levier dont le budget doit changer
     * @param jspinner JSpinner contenant la valeur du budget, entrée par l'utilisateur
     *
     * @see GraphicalView#removeAllElements()
     * @see GraphicalView#updateBudget()
     * @see GraphicalView#addAllElements()
     * @see Controller
     */
    private void changeLeverBudget(Lever lever, JSpinner jspinner){
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
        _graphicPie=_graphicPie.refresh(_controller.getLevers(),_controller.getBudget().getRemainingBudget());
    }

    /** Procédure d'<b>affichage de l'écran d'accueil</b>, qui propose basiquement le tutoriel au joueur
     *
     * @see GraphicalView#tutorial()
     *
     */
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

    private void tutorial(){

    }
}

