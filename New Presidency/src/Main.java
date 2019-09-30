import java.util.Scanner;

public class Main {
    public static void main(String[] args){
        Controller controller=new Controller();
        Textual_Interface affichage = new Textual_Interface(controller);
        Indicator.initIndicators();
        System.out.println("NEW PRESIDENCY");
        System.out.println("-------------------------");
        Scanner sc=new Scanner(System.in);
        affichage.showRound();

    }
}
