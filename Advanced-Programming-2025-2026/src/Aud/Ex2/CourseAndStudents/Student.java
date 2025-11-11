package Aud.Ex2.CourseAndStudents;

class InvalidGradeException extends Throwable {
    public String getMessage(){
        return "The grade should be in between 5-10!";
    }
}

public class Student {
    private final String index;
    private String name;
    private Integer grade;
    private Integer attendance;

    public Student(String index, String name, Integer grade, Integer attendance) throws InvalidGradeException{
        this.index = index;
        this.name = name;
        if(grade > 5 && grade <= 10){
            this.grade = grade;
        }else {
            throw new InvalidGradeException();
        }
        this.attendance = attendance;
    }

    @Override
    public String toString() {
        return "Student{" +
                "index='" + index + '\'' +
                ", name='" + name + '\'' +
                ", grade=" + grade +
                ", attendance=" + attendance +
                '}';
    }
}
