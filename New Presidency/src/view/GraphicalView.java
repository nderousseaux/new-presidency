package view;


import controller.Controller;
import model.Indicator;
import model.Lever;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import static java.lang.System.exit;


public class GraphicalView extends JFrame {
    private Controller _controller;
    private JPanel _year;
    private JPanel _levers;
    private JScrollPane _scrollLevers;
    private JPanel _indicators;
    private JScrollPane _scrollIndicators;
    private JPanel _panelIndicLevers;
    private JPanel _budget;
    private JButton _nextRound;
    private JButton _showGraphic;
    private JPanel _pannelBottom;

    public GraphicalView(Controller controller){
        _controller=controller;
        init(); //Initialisation de la fenetre
        updateAll(); //Initialisation des differentes variables
        addAllElements(); //Ajout des elements de la fenetre
        this.setVisible(true);
    }

    private void updateBudget(){
        JLabel labBudget = new JLabel(String.valueOf(_controller.getBudget().getRemainingBudget()));
        _budget=new JPanel();
        _budget.add(new JLabel("Budget restant : "));
        _budget.add(labBudget);
    }

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
            //elem.setSize(_scrollLevers.getSize());

            //zone texte

            JLabel nom = new JLabel(l.getName());

            //zone valeur
            JPanel zoneval=new JPanel();
            zoneval.setLayout(new FlowLayout());
            JTextField val = new JTextField();
            val.setColumns(6);
            val.setText(String.valueOf((int)l.getBudget()));
            zoneval.add(val);

            val.addKeyListener(new KeyListener() {
                @Override
                public void keyTyped(KeyEvent keyEvent) {
                    if((int)keyEvent.getKeyChar() < 0 || (int)keyEvent.getKeyChar()>9){

                    }
                }

                @Override
                public void keyPressed(KeyEvent keyEvent) {

                }

                @Override
                public void keyReleased(KeyEvent keyEvent) {

                }
            });

            //zone fleches
            JPanel zonefleches=new JPanel();
            zonefleches.setLayout(new BorderLayout());

            //fleches
            JButton haut=new JButton("↑");
            JButton bas=new JButton("↓");
            haut.addActionListener(new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    if(_controller.addToBudget(l,50.0)==0) { //s'il reste assez d'argent sur le budget principal
                        val.setText(String.valueOf((int)l.getBudget()));
                        updateBudget();
                    }
                }
            });
            bas.addActionListener(new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    if(_controller.removeFromBudget(l,50.0)==0) { //si le budget alloué n'est pas déjà nul ou inférieur à 50
                        val.setText(String.valueOf((int)l.getBudget()));
                        updateBudget();
                    }
                }
            });
            zonefleches.add(haut,BorderLayout.NORTH);
            zonefleches.add(bas,BorderLayout.SOUTH);
            zoneval.add(zonefleches);

            elem.add(nom);
            elem.add(zoneval);
            elem.setToolTipText(((ArrayList<String>)l.getInfos()).get(0));
            _levers.add(elem);

        }
    }

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
            elem.setToolTipText(((ArrayList<String>)i.getInfos()).get(0));
            _indicators.add(elem);
        }
    }

    private void updateYear(){
        _year = new JPanel();
        JTextArea textArea= new JTextArea("Année "+_controller.getYear()+" sur "+_controller.getMaxYear());
        textArea.setFocusable(false);
        _year.add(textArea);
    }

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

    private void addAllElements(){
        //Pannel des leviers/indicateurs
        _panelIndicLevers=new JPanel();
        _panelIndicLevers.setLayout(new GridLayout(1,2));
        _nextRound = new JButton(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                _controller.endOfRound();
                endOfRound();
            }
        });
        //Ajout des indicateurs et leviers
        _panelIndicLevers.add(_scrollLevers);
        _panelIndicLevers.add(_scrollIndicators);

        //Ajout du pannel entier
        this.getContentPane().add(_panelIndicLevers,BorderLayout.CENTER);

        //Creation du bouton de tour suivant
        _nextRound.setText("Passer au tour suivant");

        //Creation du bouton de visualisation des graphiques d'evolution
        _showGraphic = new JButton(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                _controller.showGraphicLine();
            }
        });
        _showGraphic.setText("Afficher l'évolution des indicateurs");

        //Ajout de l'annee courante
        this.getContentPane().add(_year,BorderLayout.NORTH);

        _pannelBottom=new JPanel();
        _pannelBottom.setLayout(new GridLayout(1,3));
        _pannelBottom.add(_budget);
        _pannelBottom.add(_showGraphic);
        _pannelBottom.add(_nextRound);

        this.getContentPane().add(_pannelBottom,BorderLayout.SOUTH);
    }

    private void removeAllElements(){
        _panelIndicLevers.remove(_scrollIndicators);
        _panelIndicLevers.remove(_scrollLevers);
        this.remove(_panelIndicLevers);
        _pannelBottom.remove(_budget);
        _pannelBottom.remove(_nextRound);
        _pannelBottom.remove(_showGraphic);
        this.remove(_pannelBottom);
        this.remove(_year);
    }

    private void updateAll(){
        updateLevers();
        updateIndics();
        updateBudget();
        updateYear();
    }

    private void init(){
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(1200,850);
        this.setTitle("New Presidency");
        this.setLayout(new BorderLayout());
        this.setResizable(false);
    }
}
