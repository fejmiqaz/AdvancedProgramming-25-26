package Exercises;

import java.util.Arrays;
import java.util.Scanner;

public class TripleTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int a = scanner.nextInt();
        int b = scanner.nextInt();
        int c = scanner.nextInt();
        Triple<Integer> tInt = new Triple<Integer>(a, b, c);
        System.out.printf("%.2f\n", tInt.max());
        System.out.printf("%.2f\n", tInt.avarage());
        tInt.sort();
        System.out.println(tInt);
        float fa = scanner.nextFloat();
        float fb = scanner.nextFloat();
        float fc = scanner.nextFloat();
        Triple<Float> tFloat = new Triple<Float>(fa, fb, fc);
        System.out.printf("%.2f\n", tFloat.max());
        System.out.printf("%.2f\n", tFloat.avarage());
        tFloat.sort();
        System.out.println(tFloat);
        double da = scanner.nextDouble();
        double db = scanner.nextDouble();
        double dc = scanner.nextDouble();
        Triple<Double> tDouble = new Triple<Double>(da, db, dc);
        System.out.printf("%.2f\n", tDouble.max());
        System.out.printf("%.2f\n", tDouble.avarage());
        tDouble.sort();
        System.out.println(tDouble);
    }
}
// vasiot kod ovde
// class Triple

class Triple<T extends Number>{
    T first;
    T second;
    T third;

    public Triple(T first, T second, T third) {
        this.first = first;
        this.second = second;
        this.third = third;
    }

    double max(){
        T max = first;
        if(second.doubleValue() > max.doubleValue()){
            max = second;
        }
        if(third.doubleValue() > max.doubleValue()){
            max = third;
        }
        return max.doubleValue();
    }

    double avarage(){
        return (first.doubleValue() + second.doubleValue() + third.doubleValue()) / 3;
    }

    void sort(){
        if(first.doubleValue() > second.doubleValue()){
            T temp = first;
            first = second;
            second = temp;
        }

        if(second.doubleValue() > third.doubleValue()){
            T temp = second;
            second = third;
            third = temp;
        }
        if(first.doubleValue() > second.doubleValue()){
            T temp = first;
            first = second;
            second = temp;
        }
    }

    @Override
    public String toString() {
        return String.format("%.2f %.2f %.2f", first.doubleValue(), second.doubleValue(), third.doubleValue());
    }
}

