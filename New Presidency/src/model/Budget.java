package model;

public class Budget {
    private double _remainingBudget;

    public Budget(){
        _remainingBudget=10000;
    }

    public void setRemainingBudget(double budget){
        _remainingBudget=budget;
    }

    public double getRemainingBudget(){
        return _remainingBudget;
    }

    @Override public String toString(){
        return Double.toString(_remainingBudget);
    }
}
