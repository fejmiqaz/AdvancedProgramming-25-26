package Exercises;

import java.util.Scanner;

public class GenericFractionTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        double n1 = scanner.nextDouble();
        double d1 = scanner.nextDouble();
        float n2 = scanner.nextFloat();
        float d2 = scanner.nextFloat();
        int n3 = scanner.nextInt();
        int d3 = scanner.nextInt();
        try {
            GenericFraction<Double, Double> gfDouble = new GenericFraction<Double, Double>(n1, d1);
            GenericFraction<Float, Float> gfFloat = new GenericFraction<Float, Float>(n2, d2);
            GenericFraction<Integer, Integer> gfInt = new GenericFraction<Integer, Integer>(n3, d3);
            System.out.printf("%.2f\n", gfDouble.toDouble());
            System.out.println(gfDouble.add(gfFloat));
            System.out.println(gfInt.add(gfFloat));
            System.out.println(gfDouble.add(gfInt));
            gfInt = new GenericFraction<Integer, Integer>(n3, 0);
        } catch(ZeroDenominatorException e) {
            System.out.println(e.getMessage());
        }

        scanner.close();
    }

}

// вашиот код овде

class ZeroDenominatorException extends RuntimeException{
    public ZeroDenominatorException(String message) {
        super(message);
    }
}

class GenericFraction<T extends Number, U extends Number> {
    T numerator;
    U denominator;

    public GenericFraction(T numerator, U denominator) {
        this.numerator = numerator;
        if(denominator.intValue() == 0){
            throw new ZeroDenominatorException("Denominator cannot be zero");
        }else{
            this.denominator = denominator;
        }
    }

    private int gcd(int a, int b) {
        if (b == 0) return a;
        return gcd(b, a % b);
    }

    GenericFraction<Double, Double> add(GenericFraction<? extends Number, ? extends Number> gf){
        double a = this.numerator.doubleValue();
        double b = this.denominator.doubleValue();
        double c = gf.numerator.doubleValue();
        double d = gf.denominator.doubleValue();

        double newNumerator = (a*d) + (c*b);
        double newDenominator = (b*d);

        // reduce the fraction
        int num = (int)newNumerator;
        int den = (int)newDenominator;

        int g = gcd(num, den);

        newNumerator = (double) num / g;
        newDenominator = (double) den / g;

        return new GenericFraction<>(newNumerator, newDenominator);
    }

    double toDouble(){
        return numerator.doubleValue()/denominator.doubleValue();
    }

    @Override
    public String toString() {
        return String.format("%.2f / %.2f", numerator.doubleValue(), denominator.doubleValue());
    }
}
