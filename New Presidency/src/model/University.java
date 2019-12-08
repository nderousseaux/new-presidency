package model;

public class University {
    private String _name;
    private Integer _successRate;

    public University(String name, Integer successRate){
        _name=name;
        _successRate=successRate;
    }

    public String getName(){
        return _name;
    }

    public Integer getSuccessRate(){
        return _successRate;
    }

}
