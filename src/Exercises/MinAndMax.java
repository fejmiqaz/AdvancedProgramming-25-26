package Exercises;

import java.util.Scanner;

class MinMax<T extends Comparable<T>> {
    private T min;
    private T max;
    private int total;      // total elements seen
    private int minCount;   // how many equal to current min
    private int maxCount;   // how many equal to current max

    public MinMax() {
        this.total = 0;
        this.minCount = 0;
        this.maxCount = 0;
    }

    public void update(T element) {
        if (total == 0) {
            min = element;
            max = element;
            minCount = 1;
            maxCount = 1;
            total = 1;
            return;
        }

        total++;

        int cmpMin = element.compareTo(min);
        if (cmpMin < 0) {
            min = element;
            minCount = 1; // this element is the first of the new min
        } else if (cmpMin == 0) {
            minCount++;
        }

        int cmpMax = element.compareTo(max);
        if (cmpMax > 0) {
            max = element;
            maxCount = 1; // this element is the first of the new max
        } else if (cmpMax == 0) {
            maxCount++;
        }

        // If min and max are equal (all elements same so far),
        // both counts should equal total to avoid double-subtracting later.
        if (min.equals(max)) {
            minCount = total;
            maxCount = total;
        }
    }

    public T min() { return total == 0 ? null : min; }
    public T max() { return total == 0 ? null : max; }

    @Override
    public String toString() {
        if (total == 0) return "null null 0";
        int different = min.equals(max) ? 0 : Math.max(0, total - minCount - maxCount);
        return String.format("%s %s %d", min, max, different) + "\n";
    }
}

public class MinAndMax {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();

        // Read n strings
        MinMax<String> strings = new MinMax<>();
        for (int i = 0; i < n; ++i) {
            strings.update(scanner.next());
        }
        System.out.println(strings);

        // Read n integers
        MinMax<Integer> ints = new MinMax<>();
        for (int i = 0; i < n; ++i) {
            ints.update(scanner.nextInt());
        }
        System.out.println(ints);
    }
}
