package view;
import controller.*;
import model.*;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;

import static java.lang.System.exit;
import static java.lang.Thread.sleep;
//TODO:Avant d'ajouter une série, vérifier si elle n'existe pas déjà
//TODO:Permettre la suppression d'une série si elle existe déjà
//TODO:Permettre l'ajout et suppression d'une série dans les indicateurs
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

    private static class TutoFrame extends JFrame{
        private TutoFrame _nextFrame;
        private TutoFrame _previousFrame;
        private JButton _next;
        private JButton _previous;

        public TutoFrame(String text, Component position, TutoFrame nextFrame, TutoFrame previousFrame){
            _nextFrame=nextFrame;
            _previousFrame=previousFrame;
            this.setUndecorated(true);
            this.setSize(1000,300);
            this.setLayout(new BorderLayout());
            this.setLocationRelativeTo(position);

            JLabel textLabel=new JLabel(text);
            this.add(textLabel,BorderLayout.CENTER);

            JPanel buttons=new JPanel();

            _previous=new JButton(new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    setVisible(false);
                    _previousFrame.setVisible(true);
                }
            });
            _previous.setText("Précédent");
            buttons.add(_previous);

            JButton quit=new JButton(new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    setVisible(false);
                }
            });
            quit.setText("Quitter");
            buttons.add(quit);

            _next = new JButton(new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    setVisible(false);
                    _nextFrame.setVisible(true);
                }
            });
            _next.setText("Suivant");
            buttons.add(_next);

            if(_nextFrame==null)
                _next.setEnabled(false);
            if(_previousFrame==null)
                _previous.setEnabled(false);

            this.add(buttons,BorderLayout.SOUTH);

        }

        public void setPreviousFrame(TutoFrame previousFrame) {
            _previousFrame = previousFrame;
            _previous.setEnabled(true);
        }

        public void setNextFrame(TutoFrame nextFrame){
            _nextFrame=nextFrame;
            _next.setEnabled(true);
        }
    }

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
        _levers.setLayout(new GridLayout(4,1));

        JPanel formationCat=new JPanel();
        formationCat.setLayout(new GridLayout(10,1));
        JLabel formLab=new JLabel("FORMATION");
        formLab.setForeground(Color.RED);
        formationCat.add(formLab);

        JPanel rechercheCat=new JPanel();
        rechercheCat.setLayout(new GridLayout(8,1));
        JLabel rechLab = new JLabel("RECHERCHE");
        rechLab.setForeground(Color.BLUE);
        rechercheCat.add(rechLab);

        JPanel centralCat=new JPanel();
        centralCat.setLayout(new GridLayout(6,1));
        JLabel centLab=new JLabel("CENTRAL");
        centLab.setForeground(Color.GREEN);
        centralCat.add(centLab);

        JPanel constructionCat=new JPanel();
        constructionCat.setLayout(new GridLayout(4,1));
        JLabel constLab=new JLabel("IMMOBILIER");
        constLab.setForeground(Color.ORANGE);
        constructionCat.add(constLab);
        _scrollLevers=new JScrollPane(_levers);
        _scrollLevers.setBounds(20,40,500,610);

        for(Lever l : _controller.getLevers()) {
            //element entier
            JPanel elem=new JPanel();
            elem.setLayout(new GridLayout(1,3));

            //zone texte

            JLabel nom = new JLabel(l.getName());

            //On définit le step spinner pour chaque levier
            int stepSpinner = 50;
            if(l.getAbreviation().equals("LNbTituForm") || l.getAbreviation().equals("LNbContrForm") || l.getAbreviation().equals("LNbTituRech") || l.getAbreviation().equals("LNbContrRech")){
                stepSpinner =1;
            }



            SpinnerModel model=new SpinnerNumberModel(l.getBudget(),0,Double.POSITIVE_INFINITY,stepSpinner);
            JSpinner spinner=new JSpinner(model);
            spinner.addChangeListener(new ChangeListener() {
                @Override
                public void stateChanged(ChangeEvent changeEvent) {
                    try{
                        double test=(double)spinner.getValue();
                        changeLeverBudget(l,spinner);
                    }
                    catch(Exception e){
                        String messageDerreur = "";
                        if(e.getMessage()=="Budget insuffisant"){
                            messageDerreur+="Budget restant : "+ _controller.getBudget().getRemainingBudget() + "\n Impossible d'ajouter autant dans ce levier !";
                        }
                        else if(e.getMessage() == "Trop grand"){
                            messageDerreur+="Budget maximum du levier : "+ l.getMaxBudget() + "\n Impossible d'ajouter autant dans ce levier !";
                        }
                        else if(e.getMessage() == "Trop petit"){
                            messageDerreur+="Budget minimum du levier : "+ l.getMinBudget() + "\n Impossible de réduire autant le budget dans ce levier !";

                        }

                        JOptionPane.showMessageDialog(null, messageDerreur);
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

            //On ajoute la valeur maximale et la valeur minimale
            info+= "<i><b>Valeur minimale :</b> " + l.getMinBudget() + "<br>";
            info+= "<b>Valeur maximale :</b> " + l.getMaxBudget() + "</i>";

            info+="</html>";
            elem.setToolTipText(info);

            elem.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent mouseEvent) {
                    if(!_graphicLine.hasSerie(l))
                        _graphicLine.addSerie(l);
                    else
                        _graphicLine.delSerie(l);
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
            if(l.getCategory().equals("Formation"))
                formationCat.add(elem);
            else if(l.getCategory().equals("Recherche"))
                rechercheCat.add(elem);
            else if(l.getCategory().equals("Central"))
                centralCat.add(elem);
            else if(l.getCategory().equals("Immobilier"))
                constructionCat.add(elem);

        }
        _levers.add(formationCat);
        _levers.add(rechercheCat);
        _levers.add(centralCat);
        _levers.add(constructionCat);
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
            elem.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent mouseEvent) {
                    if(!_graphicLine.hasSerie(i))
                            _graphicLine.addSerie(i);
                    else
                        _graphicLine.delSerie(i);
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
            _graphicLine.debut();

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

        JButton exitButton=new JButton(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                exit(0);
            }
        });
        exitButton.setText("X");
        exitButton.setBackground(Color.RED);
        exitButton.setBorderPainted(true);
        exitButton.setBounds(0,25,20,20);

        JButton resizeButton=new JButton(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                resize();
            }
        });
        resizeButton.setText("□");
        resizeButton.setBackground(Color.ORANGE);
        resizeButton.setBorderPainted(true);
        resizeButton.setBounds(0,25,20,20);

        JPanel buttonsTop=new JPanel();
        buttonsTop.setLayout(new GridLayout(1,2));
        buttonsTop.add(resizeButton);
        buttonsTop.add(exitButton);

        JButton tuto=new JButton(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                tutorial();
            }
        });
        tuto.setText("Tutoriel");
        _pannelTop=new JPanel();
        _pannelTop.setLayout(new BorderLayout());
        _pannelTop.add(_year,BorderLayout.CENTER);
        _pannelTop.add(buttonsTop,BorderLayout.AFTER_LINE_ENDS);
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
        String res=_controller.setLeverBudget(lever,Double.parseDouble(jspinner.getValue().toString()));
        if(res.equals("Ok")){
            removeAllElements();
            updateBudget();
            addAllElements();
            this.setVisible(true);
        }
        else{
            if(res.equals("insufficient budget") || res.equals("insufficient budget (salary * number employees)")){
                throw new IllegalArgumentException("Budget insuffisant");
            }
            else if(res.equals("more than max")){
                throw new IllegalArgumentException("Trop grand");
            }
            else if(res.equals("less than min")){
                throw new IllegalArgumentException("Trop petit");
            }


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

        JButton tuto=new JButton(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                addAllElements();
                tutorial();
            }
        });
        tuto.setText("Afficher le tutoriel");
        JButton noTuto=new JButton(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                addAllElements();
            }
        });
        noTuto.setText("Passer le tutoriel");

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

    /**Procédure d'<b>affichage de l'écran de fin</b>, qui indique la victoire ou la défaite du joueur
     *
     */
    private void exitPage(){
        this.removeAll();
    }

    /** Procédure d'affichage du <b>tutoriel du jeu</b><br>
     * Elle appelle des <b>TutoFrame</b>, une classe interne à GraphicalView, simplement des <b>JFrames avec un template défini</b>
     */
    private void tutorial(){
        ArrayList<TutoFrame> frames=new ArrayList<>();
        TutoFrame f1= new TutoFrame("Bienvenue sur New Presidency!",null,null,null);
        TutoFrame f2=new TutoFrame("Bonne chance!",null,null,f1);
        f1.setNextFrame(f2);

        f1.setVisible(true);
    }

    /** Procédure de <b>redimensionnement de la fenêtre</b><br>
     * Elle peut être en plein écran, ou de taille 1280*800
     */
    private void resize(){
        if(this.getSize().equals(Toolkit.getDefaultToolkit().getScreenSize()))
            this.setSize(1280, 800);
        else
            this.setSize( Toolkit.getDefaultToolkit().getScreenSize());
        this.setLocationRelativeTo(null);
    }
}

