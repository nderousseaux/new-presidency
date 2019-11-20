import controller.Controller;
import view.TextualView;
import view.GraphicalView;

<<<<<<< HEAD
import java.io.FileNotFoundException;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws InterruptedException, FileNotFoundException, IOException {
=======
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws InterruptedException, IOException {
>>>>>>> e4f312c2967af156800fe99092d80e90b9005013
        Controller controller=new Controller();
        controller.init();
        GraphicalView affichage = new GraphicalView(controller);

        //affichage.init();
    }
}
