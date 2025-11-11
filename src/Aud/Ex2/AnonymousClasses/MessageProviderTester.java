package Aud.Ex2.AnonymousClasses;

public class MessageProviderTester implements MessageProvider{

    @Override
    public String getMessage() {
        return "Hello from a regular class!";
    }
}
