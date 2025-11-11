package Aud.Ex2.CourseAndStudents;

import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class Course {
    private Student [] students;
    private int size;
    private String title;

    public Course(String title, int capacity) {
        this.title = title;
        this.students = new Student[capacity];
    }

    public void enroll(Supplier<Student> supplier){
        for(int i = 0; i < students.length; i++){
            students[i++] = supplier.get();
        }
    }

    public void forEach(Consumer<Student> consumer){
        for(int i = 0; i < students.length; i++){
            consumer.accept(students[i]);
        }
    }
    public Integer count(Predicate<Student> condition){
        int counter = 0 ;
        for(int i = 0; i < students.length; i++){
            if (condition.test(students[i])){
                counter++;
            }
        }
        return counter;
    }

    public Student findFirst(Predicate<Student> condition){
        for(int i = 0; i < students.length; i++){
            if(condition.test(students[i])){
                return students[i];
            }
        }
        return null;
    }

    public Student [] filter(Predicate<Student> condition){
        Student [] newArray = new Student[size];
        for(int i = 0; i < students.length; i++){
            if(condition.test(students[i])){
                newArray[++i] = students[i];
            }
        }
        return newArray;
    }

}
