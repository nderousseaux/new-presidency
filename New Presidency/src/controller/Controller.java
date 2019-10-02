package controller;

import model.G_Indicator;
import model.G_Lever;

public class Controller {
    G_Indicator g_indicator;
    G_Lever g_lever;

    public void tour(){
        //tour de jeu...

        G_Indicator.updateAll();
    }
}
