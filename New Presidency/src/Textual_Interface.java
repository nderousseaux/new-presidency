import java.util.Scanner;

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

        }
    }
    public void showIndicators(){
        for(Indicator i : G_Indicator.getIndicators()){
            System.out.println("    Indicateur "+i.getName()+": Valeur actuelle: "+i.getValue());
        }
    }

    public void showLevers(){

    }

}
