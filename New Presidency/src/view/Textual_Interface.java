package view;

import controller.*;
import model.*;

import java.util.ArrayList;
import java.util.Scanner;

import static java.lang.System.exit;

public class Textual_Interface {
    private Controller _controller;

    public Textual_Interface(Controller controller){
        _controller=controller;
    }

    public void showRound() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Menus:");
        System.out.println("1) Indicateurs");
        System.out.println("2) Leviers");
        System.out.println("3) Conclure le tour");
        System.out.println("4) Quitter");
        switch (sc.nextLine()) {
            case "1":
                showIndicators();
                showRound();
                break;
            case "2":
                showLevers();
                showRound();
                break;
            case "3":
                _controller.endOfRound();
                break;
            case "4":
                exit(0);
                break;
        }
    }
    public void showIndicators(){
        for(Indicator i : List_Indicators.getIndicators()) {
            System.out.println("    Indicateur " + i.getName() + ": Valeur actuelle: " + i.getValue()+" sur 100");
        }
    }

    public void showLevers(){
        Integer i=0;
        for(Lever l : List_Levers.getLevers()){
            System.out.println("    "+i+") Levier "+l.getName()+": Budget actuel: "+l.getBudget());
            i++;
            /*for(Integer i = 0; i<l.getEffects().size();i++){
                //lister les effets du dictionnaire
                //System.out.println(l.getEffectName(0));
            }*/
        }
        System.out.println("    Sélectionnez un levier");
        Scanner sc= new Scanner(System.in);
        Lever l=List_Levers.getLevers().get(sc.nextInt());
        System.out.println("    Vous avez choisi le levier "+l.getName());
        System.out.println("    Que souhaitez-vous faire?");
        System.out.println("    1) Augmenter le budget");
        System.out.println("    2) Réduire le budget");
        System.out.println("    3) Obtenir des informations sur le levier");
        System.out.println("    4) Quitter");
        switch(sc.nextLine()) {
            case "1":
                System.out.println("    De combien souhaitez-vous augmenter le budget?");
                while (!sc.hasNextInt()) {
                    System.out.println("    Veuillez saisir une valeur correcte");
                    System.out.println("    De combien souhaitez-vous augmenter le budget?");
                }
                _controller.addToBudget(l, sc.nextInt());
                break;
            case "2":
                System.out.println("    De combien souhaitez-vous diminuer le budget?");
                while (!sc.hasNextInt()) {
                    System.out.println("    Veuillez saisir une valeur correcte");
                    System.out.println("    De combien souhaitez-vous diminuer le budget?");
                }
                _controller.removeFromBudget(l, sc.nextInt());
                break;
            case "3":
                //System.out.println(l.getInfos());
                System.out.println("");
                break;
            case "4":
                showRound();
                break;
        }
    }

}
