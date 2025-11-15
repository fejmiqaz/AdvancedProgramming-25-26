package Exercises;

import java.io.*;
import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

class WindowShape {
    String id;
    List<Integer> sizes;

    public WindowShape(String id, List<Integer> sizes) {
        this.id = id;
        this.sizes = sizes;
    }
}

class ShapesApplication{
    List<WindowShape> windows;
    private int squaresCount;

    public ShapesApplication() {
        this.windows = new ArrayList<>();
        squaresCount = 0;
    }

    public int readCanvases(InputStream is) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        int countSquares = 0;
        String line;

        while((line = br.readLine()) != null){

            String [] parts = line.split("\\s+");
            String id = parts[0];

            List<Integer> sizes = new ArrayList<>();
            for(int i = 1; i < parts.length; i++){
                sizes.add(Integer.parseInt(parts[i]));
                countSquares++;
            }

            windows.add(new WindowShape(id, sizes));
        }
        squaresCount = countSquares;

        // example: 36
        return squaresCount;
    }

    public void printLargestCanvasTo(OutputStream os){
        PrintWriter pw = new PrintWriter(os);

        WindowShape largest = null;
        int maxPerimeter = 0;

        for(WindowShape w : windows){
            int totalPerimeter = 0;
            for(int size : w.sizes){
                totalPerimeter += 4 * size;
            }

            if(totalPerimeter > maxPerimeter){
                maxPerimeter= totalPerimeter;
                largest = w;
            }

        }


        // 364fbe94 14 1556
        if(largest != null){
            pw.printf("%s %d %d%n", largest.id, largest.sizes.size(), maxPerimeter);
        }

        pw.flush();

    }
}

public class Shapes1Test {

    public static void main(String[] args) throws IOException {
        ShapesApplication shapesApplication = new ShapesApplication();

        System.out.println("===READING SQUARES FROM INPUT STREAM===");
        System.out.println(shapesApplication.readCanvases(System.in));
        System.out.println("===PRINTING LARGEST CANVAS TO OUTPUT STREAM===");
        shapesApplication.printLargestCanvasTo(System.out);

    }
}