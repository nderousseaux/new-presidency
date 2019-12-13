package view;
import controller.*;
import model.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;

import static java.lang.System.exit;
import static java.lang.System.in;
import static java.lang.Thread.sleep;

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
//TODO:Readme : ajouter les liens, et les instructions d'installation
//TODO:Wiki : détailler les objectifs pédagogique, détailler les différents scénario
//TODO:Milestone : les ranger, les mettre à jour et modifier excel avancée des taches
//TODO:Doc et com de code.
//TODO:Installeur
//TODO:Affichage des message d'erreur en boucle
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
    private String _scenario;

    private ImageIcon _background;
    private JFrame _backFrame;


    private static class TutoFrame extends JFrame{
        private TutoFrame _nextFrame;
        private TutoFrame _previousFrame;
        private JButton _next;
        private JButton _previous;

        public TutoFrame(String text, Component position, TutoFrame nextFrame, TutoFrame previousFrame){
            _nextFrame=nextFrame;
            _previousFrame=previousFrame;
            this.setSize(300,250);
            this.setLayout(new BorderLayout());
            this.setLocationRelativeTo(position);
            this.setUndecorated(true);
            this.setAlwaysOnTop(true);
            this.addFocusListener(new FocusListener() {
                @Override
                public void focusGained(FocusEvent focusEvent) {

                }

                @Override
                public void focusLost(FocusEvent focusEvent) {
                    dispose();
                }
            });
            Border innerBorder=BorderFactory.createLineBorder(Color.black,3);
            JLabel textLabel=new JLabel("<html><p style=\"text-align:center\">"+text+"</p></html>");
            textLabel.setBorder(innerBorder);
            textLabel.setFont(new Font("Arial",Font.ITALIC,20));

            JPanel panel=new JPanel();
            panel.setLayout(new GridLayout(1,1));
            panel.add(textLabel);
            panel.setSize(250,100);
            panel.setBackground(new Color(208,233,234));

            this.add(panel,BorderLayout.CENTER);

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
    public GraphicalView(Controller controller) throws IOException {
        _controller=controller;
        _background= new ImageIcon("Background.png");
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
                            messageDerreur+="Budget restant : "+ (int)_controller.getBudget().getRemainingBudget() + "\n Impossible d'ajouter autant dans ce levier !";
                        }
                        else if(e.getMessage() == "Trop grand"){
                            messageDerreur+="Valeur maximum du levier : "+ (int)l.getMaxBudget() + "\n Impossible d'ajouter autant dans ce levier !";
                        }
                        else if(e.getMessage() == "Trop petit"){
                            messageDerreur+="Valeur minimum du levier : "+ (int)l.getMinBudget() + "\n Impossible de réduire autant le budget dans ce levier !";

                        }

                        setAlwaysOnTop(false);

                        JOptionPane.showMessageDialog(null, messageDerreur);

                        setAlwaysOnTop(true);
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

            if(_controller.getYear() != 1){
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
            }
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
            if(!i.getAbreviation().substring(0,3).equals("INb") && !i.getName().equals("Charge de travail")) {
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
            if(_controller.getYear() != 1){
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
            }

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

        if(_controller.fin(_scenario).size() ==0) {
            removeAllElements();
            updateAll();
            addAllElements();
            this.setVisible(true);
        }
        else {

            endPage();
        }
    }

    private void messageEnd(ArrayList<String> fin){
        TutoFrame scenar;
        //Si c'est une victoire
        if((fin.get(0)) == "VCondi"){
            scenar= new TutoFrame("Vous avez gagné !! <br> Vous avez remplis vos objectifs avant la fin du jeu. <br> Félicitations !",null,null,null);
        }
        //Si c'est une défaite
        else{
            //Si c'est à cause de l'année
            if(fin.get(0) == "DYear"){
                scenar= new TutoFrame("Vous avez perdu !! <br> Vous n'avez pas réussi à remplir vos objectifs avant la fin du jeu. <br> Courage, vous allez y arriver !",null,null,null);
            }
            else{
                scenar = new TutoFrame("Vous avez perdu !! <br> Vous avez fait descendre l'indicateur \" "+ _controller.getIndicator(fin.get(0).substring(1, fin.get(0).length()-1)).getName()+" \" trop bas <br> Réessayez !",null,null,null);
            }
        }
        scenar.setVisible(true);
    }

    /** Procédure d'<b>affichage de l'écran d'accueil</b>, qui propose basiquement le tutoriel au joueur
     *
     * @see GraphicalView#tutorial(Boolean)
     *
     */
    private void endPage(){
        removeAllElements();
        //Ajout du fond d'écran
        addBackground();

        //Création du panel contenant les éléments
        JPanel content = new JPanel();
        content.setLayout(new GridBagLayout());

        //Création du titre
        JLabel title=new JLabel("Merci d'avoir joué !");
        title.setFont(new Font("Arial",Font.BOLD,24));
        title.setForeground(Color.WHITE);


        //Création du bouton permettant de voir le tutoriel
        JButton tuto=new JButton(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                exit(0);
            }
        });
        tuto.setText("Quitter le jeu");

        //Création du bouton permettant de passer le tutoriel
        JButton noTuto=new JButton(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                removeAllElements();
                try {
                    _controller = new Controller();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                _background= new ImageIcon("Background.png");
                content.setVisible(false);
                updateAll();
                removeAllElements();
                homepage();

            }
        });
        noTuto.setText("Rejouer");

        //Création du panel comprenant les boutons
        JPanel buttons=new JPanel();
        buttons.setLayout(new GridLayout(2,1));
        JPanel haut = new JPanel();
        haut.add(tuto);
        haut.add(noTuto);
        buttons.add(haut);
        haut.setBackground(new Color(0,0,0,0));
        buttons.setBackground(new Color(0,0,0,0));
        //Définition des contraintes de taille des composants
        //Contraintes du titre
        GridBagConstraints cTitle=new GridBagConstraints();
        cTitle.fill = GridBagConstraints.HORIZONTAL;

        //Contraintes du panel des boutons
        GridBagConstraints cButtons=new GridBagConstraints();
        cButtons.fill=GridBagConstraints.HORIZONTAL;
        cButtons.gridy=1;

        //Ajout au panel du contenu du titre et des boutons, en respectant les contraintes
        content.add(title,cTitle);
        content.add(buttons,cButtons);
        //Ajout du panel de contenu dans la JFrame
        content.setOpaque(false);


        this.add(content,BorderLayout.CENTER);
        this.setBackground(new Color(0,0,0,0));

        this.setAlwaysOnTop(true);
        this.setVisible(true);
        messageEnd(_controller.fin((_scenario)));
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
        buttonsTop.setLayout(new GridLayout(1,3));

        buttonsTop.add(resizeButton);
        buttonsTop.add(exitButton);
        JPanel buttonsTopGauche=new JPanel();
        buttonsTopGauche.setLayout(new GridLayout(1,2));

        JButton tuto=new JButton(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                tutorial(false);
            }
        });
        tuto.setText("Tutoriel");

        JButton scenar = new JButton(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                scenar();
            }
        });
        scenar.setText("Scénario");
        buttonsTopGauche.add(tuto);
        buttonsTopGauche.add(scenar);

        _pannelTop=new JPanel();
        _pannelTop.setLayout(new BorderLayout());
        _pannelTop.add(_year,BorderLayout.CENTER);
        _pannelTop.add(buttonsTop,BorderLayout.AFTER_LINE_ENDS);
        _pannelTop.add(buttonsTopGauche,BorderLayout.BEFORE_LINE_BEGINS);


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
     * @see GraphicalView#tutorial(Boolean)
     *
     */
    private void homepage(){
        //Ajout du fond d'écran
        addBackground();

        //Création du panel contenant les éléments
        JPanel content = new JPanel();
        content.setLayout(new GridBagLayout());

        //Création du titre
        JLabel title=new JLabel("Bienvenue sur New Presidency!");
        title.setFont(new Font("Arial",Font.BOLD,24));
        title.setForeground(Color.WHITE);

        ArrayList<String> scenarios = _controller.getTextScenarios();
        Object[] elements = scenarios.toArray();
        JComboBox scenar=new JComboBox(elements);

        //Création du bouton permettant de voir le tutoriel
        JButton tuto=new JButton(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                _scenario = (String)scenar.getSelectedItem();
                _controller.setIndicatorFunctScenar(_scenario);
                updateAll();
                addAllElements();
                tutorial(true);

            }
        });
        tuto.setText("Afficher le tutoriel");

        //Création du bouton permettant de passer le tutoriel
        JButton noTuto=new JButton(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                _scenario = (String)scenar.getSelectedItem();
                _controller.setIndicatorFunctScenar(_scenario);
                updateAll();
                addAllElements();
                scenar();

            }
        });
        noTuto.setText("Passer le tutoriel");

        //Création du panel comprenant les boutons
        JPanel buttons=new JPanel();
        buttons.setLayout(new GridLayout(2,1));
        JPanel haut = new JPanel();
        haut.add(tuto);
        haut.add(noTuto);
        buttons.add(haut);
        buttons.add(scenar);
        haut.setBackground(new Color(0,0,0,0));
        buttons.setBackground(new Color(0,0,0,0));
        //Définition des contraintes de taille des composants
        //Contraintes du titre
        GridBagConstraints cTitle=new GridBagConstraints();
        cTitle.fill = GridBagConstraints.HORIZONTAL;

        //Contraintes du panel des boutons
        GridBagConstraints cButtons=new GridBagConstraints();
        cButtons.fill=GridBagConstraints.HORIZONTAL;
        cButtons.gridy=1;

        //Ajout au panel du contenu du titre et des boutons, en respectant les contraintes
        content.add(title,cTitle);
        content.add(buttons,cButtons);
        //Ajout du panel de contenu dans la JFrame
        content.setOpaque(false);


        this.add(content,BorderLayout.CENTER);
        this.setBackground(new Color(0,0,0,0));

        this.setAlwaysOnTop(true);
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
     *
     *
     * @param scenarioALaFin Booléen, vaut vrai si il faut lancer la description du scénario à la fin.
     */
    private void tutorial(Boolean scenarioALaFin){
        TutoFrame fDeb= new TutoFrame("Bienvenue sur <b>New Presidency</b>!",null,null,null);
        TutoFrame f2=new TutoFrame("Dans ce jeu sérieux de <b>gestion budgétaire</b>, vous êtes <b>le président de l'université de Strasbourg</b> et votre but sera d'<b>atteindre les objectifs</b> fixés par le <b>scénario</b> que vous avez choisi, avant la fin des tours",null,null,fDeb);
        TutoFrame f3=new TutoFrame("Pour cela, vous devrez <b>manipuler le budget</b> de ce qu'on appelle des <b>leviers de gestion</b>",null,null,f2);
        TutoFrame f4=new TutoFrame("Les voici<br>Ils sont séparés en 4 catégories: <ul><li><b>Formation</b></li><li><b>Recherche</b></li><li><b>Central</b></li><li><b>Immobilier</b></li></ul>",_scrollLevers,null,f3);
        TutoFrame f5=new TutoFrame("Vous pouvez consulter les <b>informations utiles</b> de chaque levier en les survolant",_scrollLevers,null,f4);
        TutoFrame f6=new TutoFrame("En <b>modifiant le budget de ces leviers</b>, vous compléterez ce qu'on appelle des <b>indicateurs de réussite</b>",null,null,f5);
        TutoFrame f7=new TutoFrame("Les voici<br>Selon le <b>scénario choisi</b>, tâchez de <b>compléter les bons indicateurs de réussite</b>",_scrollIndicators,null,f6);
        TutoFrame f8=new TutoFrame("Comme les leviers, vous pouvez <b>consulter les informations</b> de chaque indicateur (notamment ce par quoi ils sont influencés) au survol",_scrollIndicators,null,f7);
        TutoFrame f9=new TutoFrame("Vous avez à votre disposition deux <b>graphiques de suivi de gestion</b>",null,null,f8);
        TutoFrame f10=new TutoFrame("Le premier, ici, permet de voir la <b>répartition du budget</b> que vous avez à disposition à ce tour",_graphicPie,null,f9);
        TutoFrame f11=new TutoFrame("Le second, ici, permet de voir l'<b>évolution</b> des <b>budgets alloués</b> pour les <b>leviers</b>, et des <b>niveaux de complétions</b> pour les <b>indicateurs</b>",_graphicLine,null,f10);
        TutoFrame f12=new TutoFrame("Il n'est disponible qu'à partir du deuxième tour, changez un ou plusieurs budgets de leviers, et cliquez sur <b>Passer au tour suivant</b>!",_graphicLine,null,f11);
        TutoFrame fFin=new TutoFrame("Bonne chance!",null,null,f12);
        fDeb.setNextFrame(f2);
        f2.setNextFrame(f3);
        f3.setNextFrame(f4);
        f4.setNextFrame(f5);
        f5.setNextFrame(f6);
        f6.setNextFrame(f7);
        f7.setNextFrame(f8);
        f8.setNextFrame(f9);
        f9.setNextFrame(f10);
        f10.setNextFrame(f11);
        f11.setNextFrame(f12);


        Collection<String> infos = _controller.getInfoScenario(_scenario);
        String infosString = "";
        for (String s:infos) {
            infosString+= s;
            infosString+= "\n";
        }

        TutoFrame scenar= new TutoFrame(infosString,null,null,null);
        if(scenarioALaFin)fFin.setNextFrame(scenar);
        this.setAlwaysOnTop(false);
        fDeb.setVisible(true);
    }

    private void scenar(){

        Collection<String> infos = _controller.getInfoScenario(_scenario);
        String infosString = "";
        for (String s:infos) {
            infosString+= s;
            infosString+= "\n";
        }

        TutoFrame scenar= new TutoFrame(infosString,null,null,null);


        scenar.setVisible(true);
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

    /**Procédure d'initialisation du <b>Background</b> du jeu, une JFrame
     *
     */
    private void addBackground(){
        Image img=_background.getImage();
        Image temp=img.getScaledInstance(this.getWidth(),this.getHeight(),Image.SCALE_SMOOTH);
        _background=new ImageIcon(temp);

        _backFrame=new JFrame();
        _backFrame.setSize(this.getSize());
        _backFrame.setUndecorated(true);
        _backFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        JPanel panelBack=new JPanel();
        JLabel back=new JLabel(_background);
        back.setBounds(0,0,this.getWidth(),this.getHeight());
        back.setOpaque(true);
        panelBack.add(back);
        _backFrame.add(panelBack,BorderLayout.CENTER);

        _backFrame.setEnabled(false);
        _backFrame.setFocusable(false);
        _backFrame.setVisible(true);
    }
}

