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
    private JPanel _budget;

    public GraphicalView(Controller controller){
        _controller=controller;
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(1200,850);
        this.setTitle("New Presidency");
       // this.setLayout(new BorderLayout());

        majYear();
        majIndics();
        majLevers();
        majBudget();


        JButton nextTour = new JButton(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                _controller.endOfRound();
                endOfRound();
            }
        });

        nextTour.setText("Passer au tour suivant");
        nextTour.setBounds(500,700,200,50);

        JButton showGraphic = new JButton(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                _controller.showGraphicLine();
            }
        });

        showGraphic.setText("Afficher l'évolution des indicateurs");
        showGraphic.setBounds(20,700,200,50);
        this.getContentPane().add(nextTour);
        this.getContentPane().add(showGraphic);
        this.add(_scrollIndicators,BorderLayout.EAST);
        this.add(_scrollLevers);
        this.add(_budget,BorderLayout.SOUTH);
        this.add(_year,BorderLayout.NORTH);
        this.setResizable(false);
        this.setVisible(true);
    }

    private void majBudget(){
        JLabel labBudget = new JLabel(String.valueOf(_controller.getBudget().getRemainingBudget()));
        if(_budget!=null)
            this.remove(_budget);
        _budget=new JPanel();
        _budget.add(new JLabel("Budget restant : "));
        _budget.add(labBudget);
        this.add(_budget,BorderLayout.SOUTH);
        this.setVisible(true);
    }

    private void majLevers(){
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
            /*
            val.addKeyListener(new KeyListener() {
                @Override
                public void keyTyped(KeyEvent keyEvent) {
                    if(keyEvent.getKeyChar()<0 || keyEvent.getKeyChar() >9 )
                }

                @Override
                public void keyPressed(KeyEvent keyEvent) {

                }

                @Override
                public void keyReleased(KeyEvent keyEvent) {

                }
            });
            */
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
                        majBudget();
                    }
                }
            });
            bas.addActionListener(new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    if(_controller.removeFromBudget(l,50.0)==0) { //si le budget alloué n'est pas déjà nul ou inférieur à 50
                        val.setText(String.valueOf((int)l.getBudget()));
                        majBudget();
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

    private void majIndics(){
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

    private void majYear(){
        //annee
        _year = new JPanel();
        _year.add(new JTextArea("Année "+_controller.getYear()+" sur "+_controller.getMaxYear()));

    }

    private void endOfRound(){
        if(_controller.getYear()<=_controller.getMaxYear()) {
            this.remove(_scrollLevers);
            this.remove(_levers);
            majLevers();
            this.remove(_indicators);
            this.remove(_scrollIndicators);
            majIndics();
            this.remove(_budget);
            majBudget();
            this.remove(_year);
            majYear();

            this.getContentPane().add(_scrollIndicators,BorderLayout.EAST);
            this.getContentPane().add(_scrollLevers);
            this.getContentPane().add(_budget, BorderLayout.SOUTH);
            this.getContentPane().add(_year, BorderLayout.NORTH);
            this.setVisible(true);
        }
        else
            exit(0);
    }
}
