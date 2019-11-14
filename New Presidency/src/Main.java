import controller.Controller;
import view.TextualView;
import view.GraphicalView;

public class Main {
    public static void main(String[] args) throws InterruptedException{
        Controller controller=new Controller();
        controller.init();
        TextualView affichage = new TextualView(controller);

        affichage.init();
    }
}
