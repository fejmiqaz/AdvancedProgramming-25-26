package Exams.Midterm1.EntryTask;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

class Student implements Comparable<Student>{
    String index;
    List<String> answers;
    int counter;

    public Student(String index, List<String> answers, int counter) {
        this.index = index;
        this.answers = answers;
        this.counter = counter;
    }

    @Override
    public int compareTo(Student other) {
        return Integer.compare(other.counter, this.counter);
    }

    @Override
    public String toString() {
        return String.format("%s %d", index, counter);
    }
}

class Qualifier {
    List<String> correctAnswers;
    List<Student> students;

    public Qualifier(List<String> correctAnswers, List<Student> students) {
        this.correctAnswers = correctAnswers;
        this.students = students;
    }

    public void evaluate(InputStream is, OutputStream os) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(is));

        String [] firstLine = br.readLine().split(";");
        correctAnswers.addAll(Arrays.asList(firstLine));

        String line;
        while((line = br.readLine()) != null){
            String[] parts = line.split("\\s+");
            String idx = parts[0];
            List<String> answers = new ArrayList<>();
            for(int i = 1; i < parts.length; i++){
                answers.add(parts[i]);
            }

            int counter = 0;
            for(int i = 0; i < correctAnswers.size(); i++){
                if(answers.get(i).equals(correctAnswers.get(i))){
                    counter++;
                }
            }

            students.add(new Student(idx, answers, counter));
        }

        Collections.sort(students); // LEARN THIS

        PrintWriter pw = new PrintWriter(os);
        students.stream().forEach(pw::println);
        pw.flush();

    }

}

public class QualifierTest {
    public static void main(String[] args) throws IOException {
        String input =
                "A;B;C;A;B\n" +  // Correct answers (first line)
                        "3321 A A C A B\n" +
                        "1122 A B D B B\n" +
                        "5533 A B C A A\n";

        InputStream is = new ByteArrayInputStream(input.getBytes());
        OutputStream os = System.out;

        Qualifier qualifier = new Qualifier(new ArrayList<>(), new ArrayList<>());

        qualifier.evaluate(is, os);
    }
}
