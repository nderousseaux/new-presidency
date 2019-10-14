package model;

public class Budget {
    private Integer _remainingBudget;

    public Budget(){
        _remainingBudget=10000;
    }

    public void setRemainingBudget(Integer budget){
        _remainingBudget=budget;
    }

    public Integer getRemainingBudget(){
        return _remainingBudget;
    }

    @Override public String toString(){
        return _remainingBudget.toString();
    }
}
