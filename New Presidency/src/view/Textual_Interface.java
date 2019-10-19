package view;

import controller.*;
import model.Indicator;
import model.IndicLever;
import model.Lever;

import java.util.ArrayList;
import java.util.Scanner;

import static java.lang.System.exit;
import static java.lang.System.setOut;

public class Textual_Interface {
    private Controller _controller;

    public Textual_Interface(Controller controller){
        _controller=controller;
    }

    public void showRound() throws InterruptedException {
        Scanner sc = new Scanner(System.in);
        System.out.println("-------------------------");
        System.out.println("Année : "+_controller.getYear()+" sur " + _controller.getMaxYear());
        System.out.println("Budget restant: "+_controller.getBudget().toString());
        System.out.println("Menus:");
        System.out.println("1) Indicateurs");
        System.out.println("2) Leviers");
        System.out.println("3) Conclure le tour");
        System.out.println("4) Revoir le tutoriel");
        System.out.println("5) Quitter");
        switch (sc.nextInt()) {
            case 1:
                System.out.println("-------------------------");
                showIndicators();
                showRound();
                break;
            case 2:
                System.out.println("-------------------------");
                showLevers();
                showRound();
                break;
            case 3:
                System.out.println("-------------------------");
                _controller.endOfRound();
                if(_controller.getYear()<=_controller.getMaxYear())
                    showRound();
                else
                    System.out.println("Fin du jeu!");
                break;
            case 4:
                System.out.println("-------------------------");
                showTuto();
                showRound();
                break;
            case 5:
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
    public void showIndicators() throws InterruptedException{
        Integer i=1;
        for(Indicator indic : _controller.getIndicators()) {
            System.out.println("    "+i+")Indicateur " + indic.getName() + ": Valeur actuelle: " + indic.getValue() +" sur 100");
            i++;
        }

        System.out.println("    Sélectionnez un indicateur: (-1 pour quitter)");
        Scanner sc= new Scanner(System.in);
        Integer index=sc.nextInt();
        if(index==-1) {
            showRound();
            exit(0);
        }
        else
            index = index-1;
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
                listInfos(indic);
                showIndicators();
                break;
            case 2:
                showIndicators();
                break;
            default:
                System.out.println("Saisie incorrecte");
                showIndicators();
        }
    }

    public void showLevers() throws InterruptedException{
        Integer i=1;
        for(Lever l : _controller.getLevers()){
            System.out.println("    "+i+") Levier "+l.getName()+": Budget actuel: "+l.getBudget());
            i++;
        }
        System.out.println("    Sélectionnez un levier (-1 pour quitter)");
        Scanner sc= new Scanner(System.in);
        Integer index=sc.nextInt();
        if(index==-1) {
            showRound();
        }
        else
            index = index-1;
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
                showLevers();
                break;
            case "2":
                removeFromBudget(l);
                showLevers();
                break;
            case "3":
                listInfos(l);
                showLevers();
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

    public void listInfos(IndicLever obj) throws InterruptedException{
        for(String s : _controller.listInfos(obj)){
            System.out.println(s);
            Thread.sleep(1500);
        }
    }

    public void init() throws InterruptedException{
        System.out.println("NEW PRESIDENCY");
        System.out.println("-------------------------");
        try {
            showTuto();
        }
        catch (InterruptedException e){
            System.out.println(e.getMessage());
        }
        showRound();
    }

    public void showTuto() throws InterruptedException{
        System.out.println("Bienvenue sur New Presidency et encore félicitations pour votre nomination à la tête de l'université!");
        Thread.sleep(2500);
        System.out.println("Votre but désormais: gérer le budget de pleins de domaines de l'université: les leviers de gestion");
        Thread.sleep(2500);
        System.out.println("Ces leviers influenceront plusieurs aspects de votre université: les indicateurs de réussite");
        Thread.sleep(2500);
        System.out.println("Vous pouvez consulter en permanence l'avancement de chaque indicateur, et le budget de chaque levier");
        Thread.sleep(2500);
        System.out.println("Des informations sont également disponibles pour chacun des leviers et chacun des indicateurs, n'hésitez pas à les consulter");
        Thread.sleep(3000);
        System.out.println("Vous gagnerez la partie si les indicateurs atteignent un certain niveau, mais attention!");
        Thread.sleep(2000);
        System.out.println("Laissez de côté un indicateur, et vous perdrez!");
        Thread.sleep(1500);
        System.out.println("Bonne partie!");
        Thread.sleep(1000);
        System.out.println("");
    }
}
