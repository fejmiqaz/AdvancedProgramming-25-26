package Aud.Ex2.AnonymousClasses;

public class OperationTester implements Operation{

    @Override
    public Integer apply(Integer first, Integer second) {
        return first + second;
    }
}
