public class Controller {
    G_Indicator g_indicator;
    G_Lever g_lever;

    public static void main(String[] args){
        Indicator.initIndicators();
        //Lever.initLevers();

    }

    public void tour(){
        //tour de jeu...

        G_Indicator.updateAll();
    }
}
