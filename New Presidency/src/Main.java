import controller.Controller;
import view.TextualView;
import view.GraphicalView;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws InterruptedException, IOException {
        Controller controller=new Controller();
        controller.init();
        TextualView affichage = new TextualView(controller);

        affichage.init();
    }
}
