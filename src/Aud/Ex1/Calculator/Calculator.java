package Aud.Ex1.Calculator;

public class Calculator {
    private double result;

    private static final String PLUS = "+";
    private static final String MINUS = "-";
    private static final String MULTIPLY = "*";
    private static final String DIVIDE = "/";

    public Calculator() {
        this.result = 0.0;
    }

    public void operate(String operator, Integer value){
        if(operator.equals(PLUS)){
            result += value;
        }else if(operator.equals(MINUS)){
            result -= value;
        }else if(operator.equals(MULTIPLY)){
            result *= value;
        }else if(operator.equals(DIVIDE)){
            result /= value;
        }
    }

    public double getResult() {
        return result;
    }
}
