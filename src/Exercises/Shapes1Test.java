package Exercises;

import java.io.IOException;
import java.io.InputStream;


class ShapesApplication {
    Window[] windows;
    int size;
    public ShapesApplication(){
        windows = new Window[size];
        size = 0;
    }
    int readCanvases(InputStream inputStream) throws IOException {

        while(inputStream != null){
            String [] parts = inputStream;
        }
    }
}

public class Shapes1Test {

    public static void main(String[] args) {
        ShapesApplication shapesApplication = new ShapesApplication();

        System.out.println("===READING SQUARES FROM INPUT STREAM===");
        System.out.println(shapesApplication.readCanvases(System.in));
        System.out.println("===PRINTING LARGEST CANVAS TO OUTPUT STREAM===");
        shapesApplication.printLargestCanvasTo(System.out);

    }
}