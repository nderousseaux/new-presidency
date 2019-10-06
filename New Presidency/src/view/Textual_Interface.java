package view;

import controller.*;
import model.Indicator;
import model.Informative_Object;
import model.Lever;

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
        System.out.println("Budget restant: "+_controller.getBudget().toString());
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
                System.out.println("-------------------------");
                _controller.exitGame();
                break;
            default:
                System.out.println("Saisie incorrecte");
                showRound();
                break;
        }
    }
    public void showIndicators(){
        Integer i=1;
        for(Indicator indic : _controller.getIndicators()) {
            System.out.println("    "+i+")Indicateur " + indic.getName() + ": Valeur actuelle: " + indic.getValue()+" sur 100");
            i++;
        }

        System.out.println("    Sélectionnez un indicateur: (q pour quitter)");
        Scanner sc= new Scanner(System.in);
        String scVal=sc.nextLine();
        Integer index=null;
        if(scVal=="quitter") //pas fini
            showRound();
        else
            index = Integer.parseInt(scVal)-1;
        Indicator indic = null;

        if(index <_controller.getIndicators().size())
            indic=((ArrayList<Indicator>)_controller.getIndicators()).get(index);
        else {
            System.out.println("Saisie incorrecte");
            showIndicators();
        }
        System.out.println("    Vous avez choisi l'indicateur "+indic.getName());
        System.out.println("    Que souhaitez-vous faire?");
        System.out.println("    1) Obtenir des informations");
        System.out.println("    2) Retour");
        sc = new Scanner(System.in);
        switch (sc.nextInt()){
            case 1:
                showInfos(indic);
                break;
            case 2:
                showIndicators();
                break;
            default:
                System.out.println("Saisie incorrecte");
                showIndicators();
        }
    }

    public void showLevers(){
        Integer i=1;
        for(Lever l : _controller.getLevers()){
            System.out.println("    "+i+") Levier "+l.getName()+": Budget actuel: "+l.getBudget());
            i++;
            /*for(Integer i = 0; i<l.getEffects().size();i++){
                //lister les effets du dictionnaire
                //System.out.println(l.getEffectName(0));
            }*/
        }
        System.out.println("    Sélectionnez un levier (q pour quitter)");
        Scanner sc= new Scanner(System.in);
        Integer index=null;
        String scVal=sc.nextLine();
        System.out.println(scVal);
        if(scVal=="q") {
            showRound();
        }
        else
            index = Integer.parseInt(scVal)-1;
        Lever l =   null;
        if(index<_controller.getLevers().size())
            l=((ArrayList<Lever>)_controller.getLevers()).get(index);
        else{
            System.out.println("Saisie incorrecte");
            showLevers();
        }
        System.out.println("    Vous avez choisi le levier "+l.getName());
        System.out.println("    Que souhaitez-vous faire?");
        System.out.println("    1) Augmenter le budget");
        System.out.println("    2) Réduire le budget");
        System.out.println("    3) Obtenir des informations sur le levier");
        System.out.println("    4) Retour");
        sc=new Scanner(System.in);
        switch(sc.nextLine()) {
            case "1":
                addToBudget(l);
                break;
            case "2":
                removeFromBudget(l);
                break;
            case "3":
                showInfos(l);
                break;
            case "4":
                showLevers();
                break;
            default:
                System.out.println("Saisie incorrecte");
                showLevers();
                break;
        }
    }

    public void addToBudget(Lever l){
        System.out.println("        De combien souhaitez-vous augmenter le budget?");
        Scanner sc = new Scanner(System.in);
        while (!sc.hasNextInt()) {
            System.out.println("        Veuillez saisir une valeur correcte");
            System.out.println("        De combien souhaitez-vous augmenter le budget?");
        }
        if(_controller.addToBudget(l, sc.nextInt())!=0){
            System.out.println("        Budget restant insuffisant!");
            addToBudget(l);
        }
    }

    public void removeFromBudget(Lever l){
        Scanner sc= new Scanner(System.in);
        System.out.println("        De combien souhaitez-vous diminuer le budget?");
        while (!sc.hasNextInt()) {
            System.out.println("        Veuillez saisir une valeur correcte");
            System.out.println("        De combien souhaitez-vous diminuer le budget?");
        }
        if(_controller.removeFromBudget(l, sc.nextInt())!=0){
            System.out.println("        Vous ne pouvez pas retirer autant du budget de ce levier!");
            removeFromBudget(l);
        }
    }

    public void showInfos(Informative_Object obj){
        for(String s : _controller.listInfos(obj)){
            System.out.println("        "+s);
        };
    }

}
