package Aud.Ex5;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

interface Quantifiable {
    double amount();
}

class License implements Quantifiable, Comparable<License> {
    private String title;
    private String category;
    private String region;
    private int units;
    private double ratePerUnit;

    public License(String title, String category, String region, int units, double ratePerUnit) {
        this.title = title;
        this.category = category;
        this.region = region;
        this.units = units;
        this.ratePerUnit = ratePerUnit;
    }

    @Override
    public double amount() {
        return units * ratePerUnit;
    }

    @Override
    public int compareTo(License other) {
        int cmp = Double.compare(other.amount(), this.amount());
        if(cmp != 0){
            return cmp;
        }
        cmp = this.category.compareToIgnoreCase(other.category);
        if(cmp != 0){
            return cmp;
        }
        return this.title.compareToIgnoreCase(other.title);
    }

    @Override
    public String toString() {
        return String.format("%s [%s|%s] units=%d rp=%.2f total=%.2f", title, category, region ,units, ratePerUnit, amount());
    }
}
public class LicenseTest {
    public static void main(String[] args) {

        // Create licenses
        License l1 = new License("Golden Hits", "music", "EU", 1000, 0.5);     // income = 500
        License l2 = new License("Wild Nature", "video", "US", 400, 2.0);      // income = 800
        License l3 = new License("Chill Vibes", "music", "APAC", 800, 0.5);    // income = 400
        License l4 = new License("Golden Hits", "music", "US", 1000, 0.5);     // same income+category, title ties

        List<License> list = new ArrayList<>();
        list.add(l1);
        list.add(l2);
        list.add(l3);
        list.add(l4);

        System.out.println("=== BEFORE SORTING ===");
        list.forEach(System.out::println);

        Collections.sort(list); // uses compareTo()

        System.out.println("\n=== AFTER SORTING (DESC by income) ===");
        list.forEach(System.out::println);

        // Test validation exceptions
        System.out.println("\n=== TESTING ILLEGAL ARGUMENTS ===");
        try {
            new License("", "music", "EU", 100, 0.5); // invalid title
        } catch (Exception e) {
            System.out.println("Caught: " + e.getMessage());
        }

        try {
            new License("Bad", "music", "EU", -5, 0.5); // invalid units
        } catch (Exception e) {
            System.out.println("Caught: " + e.getMessage());
        }

        try {
            new License("Bad", "music", "EU", 100, -1.0); // invalid rate
        } catch (Exception e) {
            System.out.println("Caught: " + e.getMessage());
        }
    }
}

