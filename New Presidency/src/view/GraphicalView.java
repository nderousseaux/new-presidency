package view;


import controller.Controller;
import model.Lever;

import javax.swing.*;
import java.awt.*;


public class GraphicalView extends JFrame {
    private Controller _controller;

    public GraphicalView(Controller controller){
        _controller=controller;

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(1200,850);

        this.setTitle("New Presidency");
        this.setLayout(new BorderLayout());

        JPanel year = new JPanel();

        year.add(new JTextArea("Année "+_controller.getYear()+" sur "+_controller.getMaxYear()));
        this.getContentPane().add(year,BorderLayout.NORTH);


        JPanel leverpan=new JPanel();



        leverpan.setLayout(new GridLayout(_controller.getLevers().size(),100));
        this.getContentPane().add(leverpan,BorderLayout.WEST);
        int i=1;
        for(Lever l : _controller.getLevers()) {
            leverpan.add(new JTextArea(l.getName()),i);
            i++;
        }
        JScrollPane scroll=new JScrollPane(leverpan,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.setBounds(20,40,500,610);
        this.getContentPane().add(scroll,BorderLayout.WEST);
        this.getContentPane().add(leverpan, BorderLayout.WEST);


        this.add(year);

        this.setVisible(true);
    }
}
