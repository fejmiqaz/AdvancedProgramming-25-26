package Aud.Ex2.AnonymousClasses;

public class Test {
    public static void main(String[] args) {
        Operation op1 = new OperationTester();
        System.out.println("Addition: " + op1.apply(5,3));

        Operation op2 = new Operation() {
            @Override
            public Integer apply(Integer first, Integer second) {
                return first * second;
            }
        };
        System.out.println(op2.apply(5,3));

        MessageProvider m1 = new MessageProviderTester();
        System.out.println(m1.getMessage());

        MessageProvider m2 = new MessageProvider() {
            @Override
            public String getMessage() {
                return "Hello from main!";
            }
        };
        System.out.println(m2.getMessage());

        MessageProvider m3 = () -> "Hello from a lambda!";
        System.out.println(m3.getMessage());
    }
}
