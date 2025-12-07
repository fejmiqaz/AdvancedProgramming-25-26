package Labs.Lab4.Task2;

import java.util.*;

class StudentAlreadyExistsException extends Exception {
    private Student student;

    public StudentAlreadyExistsException(Student student){
        this.student = student;
    }

    public String getMessage(){

        return String.format("Student with ID %s already exists", student.getId());
    }

}

class Student {
    private String id;
    private List<Integer> grades;

    public Student(String id, List<Integer> grades) {
        this.id = id;
        this.grades = grades;
    }

    public void addGradeToStudent(int grade){
        grades.add(grade);
    }

    public List<Integer> getGrades() {
        return grades;
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return String.format("Student{id='%s', grades=%s}", id,grades);
    }
}

class Faculty{
    private Map<String, Student> students;

    public Faculty() {
        this.students = new HashMap<String, Student>();
    }

    public void addStudent(String id, List<Integer> grades) throws StudentAlreadyExistsException {
        if(students.containsKey(id)){
            throw new StudentAlreadyExistsException(students.get(id));
        }
        students.put(id,new Student(id,grades));
    }

    public void addGrade(String id, int grade){
        students.get(id).addGradeToStudent(grade);
    }

    public Set<Student> getStudentsSortedByAverageGrade(){
        Comparator<Student> comparison = (s1, s2) -> {
            double avg1 = s1.getGrades().stream().mapToInt(i ->i).average().orElse(0);
            double avg2 = s2.getGrades().stream().mapToInt(i -> i).average().orElse(0);

            int rez = Double.compare(avg2 , avg1);
            if(rez != 0){
                return rez;
            }

            int passed1 = s1.getGrades().size();
            int passed2 = s1.getGrades().size();

            int cmpPassed = Integer.compare(passed2, passed1);
            if (cmpPassed!=0) return cmpPassed;

            return s1.getId().compareTo(s2.getId());
        };

        TreeSet<Student> sorted = new TreeSet<>(comparison);

        sorted.addAll(students.values());
        return sorted;
    }

    public Set<Student> getStudentsSortedByCoursesPassed(){
        Comparator<Student> comparison = (s1,s2) -> {
            int passed1 = s1.getGrades().size();
            int passed2 = s2.getGrades().size();

            int cmpPassed = Integer.compare(passed2, passed1);
            if (cmpPassed!=0) return cmpPassed;

            double avg1 = s1.getGrades().stream().mapToInt(i ->i).average().orElse(0);
            double avg2 = s2.getGrades().stream().mapToInt(i -> i).average().orElse(0);

            int rez = Double.compare(avg2 , avg1);
            if(rez != 0){
                return rez;
            }

            return s2.getId().compareTo(s1.getId());
        };

        TreeSet<Student> sorted = new TreeSet<>(comparison);
        sorted.addAll(students.values());

        return sorted;
    }

    public Set<Student> getStudentsSortedByMaxGrade(){
        Comparator<Student> comparison = (s1,s2) -> {
            int maxGrade1 = s1.getGrades().stream().max(Integer::compareTo).orElse(0);
            int maxGrade2 = s2.getGrades().stream().max(Integer::compareTo).orElse(0);

            int maxGradeComparison = Integer.compare(maxGrade2, maxGrade1);

            if(maxGradeComparison != 0) return maxGradeComparison;

            double avg1 = s1.getGrades().stream().mapToInt(i -> i).average().orElse(0);
            double avg2 = s2.getGrades().stream().mapToInt(i -> i).average().orElse(0);

            int averageComparison = Double.compare(avg2, avg1);

            if(averageComparison != 0) return averageComparison;

            return s2.getId().compareTo(s1.getId());
        };

        TreeSet<Student> sorted = new TreeSet<>(comparison);
        sorted.addAll(students.values());
        return sorted;
    }
}

public class SetsTest {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Faculty faculty = new Faculty();

        while (true) {
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("exit")) {
                break;
            }

            String[] tokens = input.split("\\s+");
            String command = tokens[0];

            switch (command) {
                case "addStudent":
                    String id = tokens[1];
                    List<Integer> grades = new ArrayList<>();
                    for (int i = 2; i < tokens.length; i++) {
                        grades.add(Integer.parseInt(tokens[i]));
                    }
                    try {
                        faculty.addStudent(id, grades);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;

                case "addGrade":
                    String studentId = tokens[1];
                    int grade = Integer.parseInt(tokens[2]);
                    faculty.addGrade(studentId, grade);
                    break;

                case "getStudentsSortedByAverageGrade":
                    System.out.println("Sorting students by average grade");
                    Set<Student> sortedByAverage = faculty.getStudentsSortedByAverageGrade();
                    for (Student student : sortedByAverage) {
                        System.out.println(student);
                    }
                    break;

                case "getStudentsSortedByCoursesPassed":
                    System.out.println("Sorting students by courses passed");
                    Set<Student> sortedByCourses = faculty.getStudentsSortedByCoursesPassed();
                    for (Student student : sortedByCourses) {
                        System.out.println(student);
                    }
                    break;

                default:
                    break;
            }
        }

        scanner.close();
    }
}
