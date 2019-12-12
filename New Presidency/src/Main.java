import controller.Controller;
import view.GraphicalView;

import java.io.FileNotFoundException;
import java.io.IOException;

/**<b><i>Main</i> est la classe lançant le jeu</b>
 * @see Controller
 * @see GraphicalView
 *
 * @author yvanderspurt ltreyer nderousseaux
 */
public class Main {
    public static void main(String[] args) throws InterruptedException, IOException {
        Controller controller=new Controller();
        GraphicalView affichage = new GraphicalView(controller);
    }
}
