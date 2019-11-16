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


public class GraphicalView extends JFrame {
    private Controller _controller;
    private JPanel _year;
    private JPanel _levers;
    private JPanel _indicators;
    private JPanel _budget;

    public GraphicalView(Controller controller){
        _controller=controller;
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(1200,850);

        this.setTitle("New Presidency");
        this.setLayout(new BorderLayout());

        //budget
        JLabel labBudget = new JLabel(String.valueOf(_controller.getBudget().getRemainingBudget()));
        _budget=new JPanel();
        _budget.add(new JLabel("Budget restant : "));
        _budget.add(labBudget);

        //annee
        _year = new JPanel();
        _year.add(new JTextArea("Année "+_controller.getYear()+" sur "+_controller.getMaxYear()));


        //leviers
        _levers=new JPanel();
        JScrollPane scrollLevers=new JScrollPane(_levers);
        _levers.setLayout(new GridLayout(_controller.getLevers().size(),1));
        scrollLevers.setBounds(20,40,500,610);

        _levers.setToolTipText("Leviers");
        for(Lever l : _controller.getLevers()) {
            //element entier
            JPanel elem=new JPanel();
            elem.setLayout(new GridLayout(1,2));
            elem.setSize(scrollLevers.getSize());

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


            _levers.add(elem);

        }

        _indicators=new JPanel();
        JScrollPane scrollIndicators=new JScrollPane(_indicators);
        _indicators.setLayout(new GridLayout(_controller.getIndicators().size(),1));
        scrollIndicators.setBounds(600,40,500,610);

        _indicators.setToolTipText("Indicateurs");

        for(Indicator i : _controller.getIndicators()){
            //element entier

            JPanel elem=new JPanel();
            elem.setLayout(new GridLayout(1,2));
            elem.setSize(scrollLevers.getSize());

            //zone texte

            JLabel nom = new JLabel(i.getName());

            //zone valeur
            JPanel zoneval=new JPanel();
            JLabel val = new JLabel(String.valueOf((int)i.getValue())+"%");
            zoneval.add(val);
            elem.add(nom);
            elem.add(zoneval);
            _indicators.add(elem);
        }

        this.getContentPane().add(_year,BorderLayout.NORTH);
        this.getContentPane().add(scrollLevers);
        this.getContentPane().add(scrollIndicators);
        this.getContentPane().add(_budget,BorderLayout.SOUTH);
        this.getContentPane().add(_year);
        this.setVisible(true);
    }

    private void majBudget(){
        this.remove(_budget);
        JLabel labBudget = new JLabel(String.valueOf(_controller.getBudget().getRemainingBudget()));

        _budget=new JPanel();
        _budget.add(new JLabel("Budget restant : "));
        _budget.add(labBudget);
        this.getContentPane().add(_budget,BorderLayout.SOUTH);
        this.setVisible(true);
    }
}
