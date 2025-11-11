package Labs.Lab3.Task3;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Array;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

// TODO: Add classes and implement methods

class Applicant {
    private int id;
    private String name;
    private double gpa;
    private List<SubjectWithGrade> subjectsWithGrade;
    private int size =0;
    private StudyProgramme studyProgramme;

    public Applicant(int id, String name, double gpa, StudyProgramme studyProgramme) {
        this.id = id;
        this.name = name;
        this.gpa = gpa;
        this.studyProgramme = studyProgramme;
        subjectsWithGrade = new ArrayList<>(size++);
    }

    public void addSubjectAndGrade(String subject, int grade){
        subjectsWithGrade.add(new SubjectWithGrade(subject, grade));
    }

    public int calculatePoints(){
        double totalPoints = gpa * 12.0;

        Faculty faculty = studyProgramme.getFa

    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getGpa() {
        return gpa;
    }

    public List<SubjectWithGrade> getSubjectsWithGrade() {
        return subjectsWithGrade;
    }

    public StudyProgramme getStudyProgramme() {
        return studyProgramme;
    }
}

class StudyProgramme {
    private String code;
    private String name;
    private int numPublicQuota;
    private int numPrivateQuota;
    private int enrolledInPublicQuota;
    private int enrolledInPrivateQuota;
    private List<Applicant> applicants;
    private int size = 0;

    public StudyProgramme(String code, String name, int numPublicQuota, int numPrivateQuota, int enrolledInPublicQuota, int enrolledInPrivateQuota) {
        this.code = code;
        this.name = name;
        this.numPublicQuota = numPublicQuota;
        this.numPrivateQuota = numPrivateQuota;
        this.enrolledInPublicQuota = enrolledInPublicQuota;
        this.enrolledInPrivateQuota = enrolledInPrivateQuota;
        this.applicants = new ArrayList<>(size);
        size++;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public int getNumPublicQuota() {
        return numPublicQuota;
    }

    public int getNumPrivateQuota() {
        return numPrivateQuota;
    }

    public int getEnrolledInPublicQuota() {
        return enrolledInPublicQuota;
    }

    public int getEnrolledInPrivateQuota() {
        return enrolledInPrivateQuota;
    }

    public List<Applicant> getApplicants() {
        return applicants;
    }
}

class Faculty {
    private String shortName;
    private List<String> appropriateSubjects;
    private List<StudyProgramme> studyProgrammes;
    private int sizeAppropriateSubjects = 0;
    private int sizeStudyProgrammes = 0;

    public Faculty(String shortName) {
        this.shortName = shortName;
        appropriateSubjects = new ArrayList<>(sizeAppropriateSubjects++);
        studyProgrammes = new ArrayList<>(sizeStudyProgrammes++);
    }

    public void addSubject(String s){
        appropriateSubjects.add(s);
    }

    public void addStudyProgramme(StudyProgramme sp){
        studyProgrammes.add(sp);
    }

    public String getShortName() {
        return shortName;
    }

    public List<String> getAppropriateSubjects() {
        return appropriateSubjects;
    }

    public List<StudyProgramme> getStudyProgrammes() {
        return studyProgrammes;
    }
}

class SubjectWithGrade
{
    private String subject;
    private int grade;
    public SubjectWithGrade(String subject, int grade) {
        this.subject = subject;
        this.grade = grade;
    }
    public String getSubject() {
        return subject;
    }
    public int getGrade() {
        return grade;
    }
}

class EnrollmentsIO {
    public static void printRanked(List<Faculty> faculties) {
    }

    public static List<Enrollment> readEnrollments(List<StudyProgramme> studyProgrammes, InputStream inputStream) {
    }
}

public class EnrollmentsTest {

    public static void main(String[] args) {
        Faculty finki = new Faculty("FINKI");
        finki.addSubject("Mother Tongue");
        finki.addSubject("Mathematics");
        finki.addSubject("Informatics");

        Faculty feit = new Faculty("FEIT");
        feit.addSubject("Mother Tongue");
        feit.addSubject("Mathematics");
        feit.addSubject("Physics");
        feit.addSubject("Electronics");

        Faculty medFak = new Faculty("MEDFAK");
        medFak.addSubject("Mother Tongue");
        medFak.addSubject("English");
        medFak.addSubject("Mathematics");
        medFak.addSubject("Biology");
        medFak.addSubject("Chemistry");

        StudyProgramme si = new StudyProgramme("SI", "Software Engineering", finki, 4, 4);
        StudyProgramme it = new StudyProgramme("IT", "Information Technology", finki, 2, 2);
        finki.addStudyProgramme(si);
        finki.addStudyProgramme(it);

        StudyProgramme kti = new StudyProgramme("KTI", "Computer Technologies and Engineering", feit, 3, 3);
        StudyProgramme ees = new StudyProgramme("EES", "Electro-energetic Systems", feit, 2, 2);
        feit.addStudyProgramme(kti);
        feit.addStudyProgramme(ees);

        StudyProgramme om = new StudyProgramme("OM", "General Medicine", medFak, 6, 6);
        StudyProgramme nurs = new StudyProgramme("NURS", "Nursing", medFak, 2, 2);
        medFak.addStudyProgramme(om);
        medFak.addStudyProgramme(nurs);

        List<StudyProgramme> allProgrammes = new ArrayList<>();
        allProgrammes.add(si);
        allProgrammes.add(it);
        allProgrammes.add(kti);
        allProgrammes.add(ees);
        allProgrammes.add(om);
        allProgrammes.add(nurs);

        EnrollmentsIO.readEnrollments(allProgrammes, System.in);

        List<Faculty> allFaculties = new ArrayList<>();
        allFaculties.add(finki);
        allFaculties.add(feit);
        allFaculties.add(medFak);

        allProgrammes.stream().forEach(StudyProgramme::calculateEnrollmentNumbers);

        EnrollmentsIO.printRanked(allFaculties);

    }


}
