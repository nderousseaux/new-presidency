import controller.Controller;
import view.TextualView;

public class Main {
    public static void main(String[] args) throws InterruptedException{
        Controller controller=new Controller();
        TextualView affichage = new TextualView(controller);
        controller.init();
        affichage.init();
    }
}
