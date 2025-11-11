package Aud.Ex1.Calculator;

import java.util.Scanner;

public class Tester {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Calculator is on");
        Calculator c = new Calculator();
        while(true){
            String [] parts = sc.nextLine().split(" ");
            String operator = parts[0];
            Integer value = Integer.parseInt(parts[1]);
            if(operator.equals("R")){
                return;
            }
            if(!(operator.equals("+") || operator.equals("-") || operator.equals("*") || sc.next().equals("/"))){
                throw new UnknownOperatorException(operator);
            }

            try {
                System.out.println(c.getResult());
                c.operate(operator, value);

            } catch (UnknownOperatorException e){
                System.out.println(e.getMessage());
            }


        }
    }
}
