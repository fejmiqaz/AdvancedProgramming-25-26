package Aud.Ex2.CalculatorV2;

import Aud.Ex1.Calculator.UnknownOperatorException;

public class OperationFactory {
    private double result;

    private static final char PLUS = '+';
    private static final char MINUS = '-';
    private static final char MULTIPLY = '*';
    private static final char DIVIDE = '/';

    public static final Operation ADD = (r, v) -> r + v;
    public static final Operation SUBTRACT = (r, v) -> r - v;
    public static final Operation MULTIPLY_OP = (r, v) -> r * v;
    public static final Operation DIVIDE_OP = (r, v) -> r / v;


    public static Operation getOperation(char operator) throws UnknownOperatorException {
        if (operator == PLUS){
            return ADD;
        }else if(operator == MINUS){
            return SUBTRACT;
        }else if(operator == MULTIPLY){
            return MULTIPLY_OP;
        }else if(operator == DIVIDE){
            return DIVIDE_OP;
        }else{
            throw new Aud.Ex2.CalculatorV2.UnknownOperatorException(operator);
        }
    }

}
