import controller.Controller;
import view.TextualView;
import view.GraphicalView;

import java.io.FileNotFoundException;
import java.io.IOException;

/**<b><i>Main</i> est la classe lançant le jeu</b>
 * @see Controller
 * @see GraphicalView
 * @see TextualView
 *
 * @author yvanderspurt ltreyer nderousseaux
 */
public class Main {
    public static void main(String[] args) throws InterruptedException, IOException {

        Controller controller=new Controller();
        controller.init();
        GraphicalView affichage = new GraphicalView(controller);
        //affichage.init();
    }
}
