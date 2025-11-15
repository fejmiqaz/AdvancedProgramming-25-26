package Exercises;


import java.io.*;
import java.util.ArrayList;
import java.util.List;

class Canvas {
    String id;
    List<String> types;
    List<Integer> sizes;

    public Canvas(String id, List<String> types, List<Integer> sizes) {
        this.id = id;
        this.types = types;
        this.sizes = sizes;
    }
}

class InvalidCanvasException extends RuntimeException {
    String id;
    double maxArea;

    public InvalidCanvasException(String id, double maxArea) {
        super(String.format("Canvas %s has a shape with area larger than %.2f", id, maxArea));
        this.maxArea = maxArea;
        this.id = id;
    }
}

class ShapesApplication2 {

    double maxArea;
    List<Canvas> canvas;

    public ShapesApplication2(double maxArea) {
        this.maxArea = maxArea;
        this.canvas = new ArrayList<>();
    }

    public void readCanvases(InputStream inputStream) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        while ((line = br.readLine()) != null) {

            try {
                String[] parts = line.split("\\s+");
                String id = parts[0];

                List<String> types = new ArrayList<>();
                List<Integer> sizes = new ArrayList<>();
                for (int i = 1; i < parts.length; i += 2) {
                    types.add(parts[i]);
                    sizes.add(Integer.parseInt(parts[i + 1]));
                }

                for (int i = 0; i < types.size(); i++) {
                    String t = types.get(i);
                    int size = sizes.get(i);
                    double area = 0.0;

                    if (t.equals("S")) {
                        area = size * size;
                    } else if (t.equals("C")) {
                        area = Math.PI * size * size;
                    }

                    if (area > maxArea) {
                        throw new InvalidCanvasException(id, maxArea);
                    }
                }

                canvas.add(new Canvas(id, types, sizes));
            } catch (InvalidCanvasException e){
                System.out.println(e.getMessage());
            }
        }
    }

    private double totalArea(Canvas c) {
        double sum = 0;
        for (int i = 0; i < c.types.size(); i++) {
            String t = c.types.get(i);
            int size = c.sizes.get(i);

            if (t.equals("S")) {
                sum += size * size;
            } else {
                sum += Math.PI * size * size;
            }
        }
        return sum;
    }

    public void printCanvases(OutputStream os) {
        PrintWriter pw = new PrintWriter(os);

        canvas.sort((c1, c2) -> Double.compare
                (totalArea(c2), totalArea(c1)
                ));

        for (Canvas c : canvas) {
            int total_shapes = c.types.size();
            int total_circles = 0;
            int total_squares = 0;

            double min_area = Double.MAX_VALUE;
            double max_area = Double.MIN_VALUE;
            double sum_area = 0.0;

            for (int i = 0; i < c.types.size(); i++) {
                String type = c.types.get(i);
                int size = c.sizes.get(i);

                double area = 0.0;
                if (type.equals("S")) {
                    total_squares++;
                    area = size * size;
                } else {
                    total_circles++;
                    area = Math.PI * size * size;
                }
                sum_area += area;
                min_area = Math.min(min_area, area);
                max_area = Math.max(max_area, area);
            }

            double avg_area = sum_area / total_shapes;

            // 5e28f402 11 5 6 100.00 2642.08 1007.35

            pw.printf("%s %d %d %d %.2f %.2f %.2f%n", c.id, total_shapes, total_circles, total_squares, min_area, max_area, avg_area);

        }
        pw.flush();
    }

}

public class Shapes2Test {

    public static void main(String[] args) throws IOException {

        ShapesApplication2 shapesApplication = new ShapesApplication2(10000);

        System.out.println("===READING CANVASES AND SHAPES FROM INPUT STREAM===");
        shapesApplication.readCanvases(System.in);


        System.out.println("===PRINTING SORTED CANVASES TO OUTPUT STREAM===");
        shapesApplication.printCanvases(System.out);


    }
}