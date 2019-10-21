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
        this.setVisible(true);
        this.setTitle("New Presidency");
        this.setLayout(new BorderLayout());

        JPanel year = new JPanel();
        this.getContentPane().add(year,BorderLayout.NORTH);
        year.add(new JTextArea("Année "+_controller.getYear()+" sur "+_controller.getMaxYear()));
        this.add(year);

        JScrollPane levers=new JScrollPane(new TextArea("Levers"));
        this.getContentPane().add(levers,BorderLayout.WEST);
        JTextArea leverList = new JTextArea(_controller.getLevers().size(),1);
        int i=1;
        for(Lever l : _controller.getLevers()){
            levers.add(new Label(l.getName()));
            i++;
        }
        this.add(levers);

    }
}
