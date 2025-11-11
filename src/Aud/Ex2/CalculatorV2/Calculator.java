package Aud.Ex2.CalculatorV2;

public class Calculator {
    private double result;

    public Calculator(){
        result = 0.0;
    }

    public double getResult(){
        return result;
    }

    public String execute(char operator, double value) throws UnknownOperatorException {
        Operation op = OperationFactory.getOperation(operator);
        result = op.apply(result, value);
        return String.format("result %c %f = %f", operator, value, result);
    }

    @Override
    public String toString() {
        return String.format("updated result %f", result);
    }
}
