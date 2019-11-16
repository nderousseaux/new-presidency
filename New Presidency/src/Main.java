import controller.Controller;
import view.TextualView;
import view.GraphicalView;

import view.Graphic;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws InterruptedException{
        //Controller controller=new Controller();
        //GraphicalView affichage = new GraphicalView(controller);
        //controller.init();
        //affichage.init();


        double[] xData = new double[] { 0.0, 1.0, 2.0 };
        double[] yData = new double[] { 2.0, 1.0, 0.0 };


        new Graphic("test", "x", "y", "Titre", xData, yData);
    }
}
