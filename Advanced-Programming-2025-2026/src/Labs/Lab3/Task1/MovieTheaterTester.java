package Labs.Lab3.Task1;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

class Movie{
    private String title;
    private String genre;
    private int year;
    private double avgRating;

    public Movie(String title, String genre, Integer year, Double avgRating) {
        this.title = title;
        this.genre = genre;
        this.year = year;
        this.avgRating = avgRating;
    }

    public String getTitle() {
        return title;
    }

    public String getGenre() {
        return genre;
    }

    public Integer getYear() {
        return year;
    }

    public Double getAvgRating() {
        return avgRating;
    }

    @Override
    public String toString() {
        return String.format("%s, %s, %d, %.2f", title, genre, year,  avgRating);
    }
}

class GenreAndTitleComparator implements Comparator<Movie>{

    @Override
    public int compare(Movie o1, Movie o2) {
        int genreCompare = o1.getGenre().compareToIgnoreCase(o2.getGenre());
        if(genreCompare == 0){
            return o1.getTitle().compareToIgnoreCase(o2.getTitle());
        }
        return genreCompare;
    }
}

class YearAndTitleComparator implements Comparator<Movie>{

    @Override
    public int compare(Movie o1, Movie o2) {
        int yearCompare = o1.getYear().compareTo(o2.getYear());
        if(yearCompare == 0){
            return o1.getTitle().compareToIgnoreCase(o2.getTitle());
        }
        return yearCompare;
    }
}

class RatingAndTitleComparator implements Comparator<Movie>{

    @Override
    public int compare(Movie o1, Movie o2) {


        int ratingCompare = Double.compare(o2.getAvgRating(), o1.getAvgRating());
        if(ratingCompare == 0){
            return o1.getTitle().compareToIgnoreCase(o2.getTitle());
        }
        return ratingCompare;
    }
}

class MovieTheater {
    private List<Movie> movies;
    private int size = 0;

    public MovieTheater() {
        this.movies = new ArrayList<>(size);
        this.size++;
    }

    public void readMovies(InputStream is) throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(is))){
            int n = Integer.parseInt(br.readLine().trim());

            for(int i = 0; i < n; i++){
                String title = br.readLine().trim();
                String genre = br.readLine().trim();
                int year = Integer.parseInt(br.readLine().trim());

                String [] ratingParts = br.readLine().trim().split(" ");
                double sum = 0.0;

                for(String r: ratingParts){
                    sum += Integer.parseInt(r);
                }

                double avg = sum / ratingParts.length;

                movies.add(new Movie(title, genre, year, avg));

            }
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

    public void printByGenreAndTitle(){
        List<Movie> sorted = new ArrayList<>(movies);
        sorted.sort(new GenreAndTitleComparator());
        sorted.forEach(System.out::println);
    }

    public void printByYearAndTitle(){
        List<Movie> sorted = new ArrayList<>(movies);
        sorted.sort(new YearAndTitleComparator());
        sorted.forEach(System.out::println);
    }

    public void printByRatingAndTitle(){
        List<Movie> sorted = new ArrayList<>(movies);
        sorted.sort(new RatingAndTitleComparator());
        sorted.forEach(System.out::println);
    }

}


public class MovieTheaterTester {
    public static void main(String[] args) {
        MovieTheater mt = new MovieTheater();
        try {
            mt.readMovies(System.in);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return;
        }
        System.out.println("SORTING BY RATING");
        mt.printByRatingAndTitle();
        System.out.println("\nSORTING BY GENRE");
        mt.printByGenreAndTitle();
        System.out.println("\nSORTING BY YEAR");
        mt.printByYearAndTitle();
    }
}