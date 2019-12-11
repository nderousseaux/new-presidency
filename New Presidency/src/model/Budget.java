package model;

/**
 * <b><i>Budget</i> est la classe contenant les informations sur le budget alloué au joueur au cours du jeu</b>
 * <p>
 *     Cette classe contient:
 *     <ul>
 *         <li>Le <b>budget restant</b> du tour, soit le champ <b>_remainingBudget</b></li>
 *         <li>Des <b>accesseurs</b></li>
 *         <li>Une <b>redéfinition de la méthode toString()</b></li>
 *     </ul>
 * </p>
 * @see controller.Controller
 * @see Budget#setRemainingBudget(double)
 * @see Budget#getRemainingBudget()
 * @see Budget#toString()
 * @author yoanv
 */
public class Budget {
    private double _remainingBudget;

    /**
     * Constructeur de <b>Budget</b>, instanciant le budget à <b>40 000</b>, automatiquement répartis
     */
    public Budget(){
        _remainingBudget=10000;
    }

    /**Accesseur du budget restant, modifiant sa valeur
     *
     * @param budget Budget restant
     */
    public void setRemainingBudget(double budget){
        _remainingBudget=budget;
    }


    /**Accesseur du budget restant, récupérant la valeur du budget restant
     *
     * @return Budget restant
     */
    public double getRemainingBudget(){
        return _remainingBudget;
    }

    /**Redéfinition de la méthode toString(), renvoyant sous forme de String la valeur du budget restant
     *
     * @return Budget restant, sous forme de String
     */
    @Override public String toString(){
        return Double.toString(_remainingBudget);
    }
}
