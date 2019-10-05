package view;

import controller.*;
import model.*;

import javax.crypto.spec.PSource;
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
                System.out.println("Merci d'avoir joué à New Presidency!");
                exit(0);
                break;
            default:
                System.out.println("Saisie incorrecte");
                exit(0);
        }
    }
    public void showIndicators(){
        Integer i=1;
        for(Indicator indic : List_Indicators.getIndicators()) {
            System.out.println("    "+i+")Indicateur " + indic.getName() + ": Valeur actuelle: " + indic.getValue()+" sur 100");
            i++;
        }
        System.out.println("    Sélectionnez un indicateur:");
        Scanner sc= new Scanner(System.in);
        Indicator indic = null;
        if(sc.nextInt()<_controller.getIndicators().size())
            indic=((ArrayList<Indicator>)List_Indicators.getIndicators()).get(sc.nextInt()-1);
        else {
            System.out.println("Saisie incorrecte");
            exit(00);
        }
        System.out.println("    Vous avez choisi l'indicateur "+indic.getName());
        System.out.println("    Que souhaitez-vous faire?");
        System.out.println("    1) Obtenir des informations");
        System.out.println("    2) Quitter");
        sc = new Scanner(System.in);
        switch (sc.nextInt()){
            case 1:
                for(String s : (ArrayList<String>)_controller.listInfos(indic)){
                    System.out.println("    "+s);
                };
                break;
            case 2:
                showRound();
                break;
            default:
                System.out.println("Saisie incorrecte");
                exit(0);
        }
    }

    public void showLevers(){
        Integer i=1;
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
        Lever l =   null;
        if(sc.nextInt()<_controller.getLevers().size())
            l=((ArrayList<Lever>)List_Levers.getLevers()).get(sc.nextInt()-1);
        else{
            System.out.println("Saisie incorrecte");
            exit(0);
        }
        System.out.println("    Vous avez choisi le levier "+l.getName());
        System.out.println("    Que souhaitez-vous faire?");
        System.out.println("    1) Augmenter le budget");
        System.out.println("    2) Réduire le budget");
        System.out.println("    3) Obtenir des informations sur le levier");
        System.out.println("    4) Quitter");
        sc=new Scanner(System.in);
        switch(sc.nextLine()) {
            case "1":
                System.out.println("        De combien souhaitez-vous augmenter le budget?");
                while (!sc.hasNextInt()) {
                    System.out.println("        Veuillez saisir une valeur correcte");
                    System.out.println("        De combien souhaitez-vous augmenter le budget?");
                }
                _controller.addToBudget(l, sc.nextInt());
                break;
            case "2":
                System.out.println("        De combien souhaitez-vous diminuer le budget?");
                while (!sc.hasNextInt()) {
                    System.out.println("        Veuillez saisir une valeur correcte");
                    System.out.println("        De combien souhaitez-vous diminuer le budget?");
                }
                _controller.removeFromBudget(l, sc.nextInt());
                break;
            case "3":
                for(String s : _controller.listInfos(l)){
                    System.out.println("        "+s);
                };
                break;
            case "4":
                showRound();
                break;
            default:
                System.out.println("Saisie incorrecte");
                exit(0);
                break;
        }
    }

}
