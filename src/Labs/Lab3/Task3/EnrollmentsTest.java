package Labs.Lab3.Task3;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

// TODO: Add classes and implement methods

class StudyProgramme {
    private String code;
    private String name;
    private Faculty faculty;
    private int numPublicQuota;
    private int numPrivateQuota;
    private int enrolledInPublicQuota;
    private int enrolledInPrivateQuota;
    private List<Applicant> applicants;

    public StudyProgramme(String code, String name, Faculty faculty, int numPublicQuota, int numPrivateQuota) {
        this.code = code;
        this.name = name;
        this.faculty = faculty;
        this.numPublicQuota = numPublicQuota;
        this.numPrivateQuota = numPrivateQuota;
        this.applicants = new ArrayList<>();
    }

    public void addApplicant(Applicant a){
        applicants.add(a);
    }

    public void calculateEnrollmentNumbers(){
        applicants.forEach(Applicant::calculatePoints);

        applicants.sort((a,b) -> Double.compare(b.getPoints(), a.getPoints()));

        enrolledInPublicQuota = Math.min(numPublicQuota, applicants.size());
        enrolledInPrivateQuota = Math.min(numPrivateQuota, Math.max(0, applicants.size() - enrolledInPublicQuota));
    }

    public int getTotalQuota() { return numPrivateQuota + numPublicQuota; }
    public int getEnrolledTotal() { return enrolledInPrivateQuota + enrolledInPublicQuota; }

    public double getEnrollmentPercentage() {
        if (getTotalQuota() == 0) return 0;
        return (100.0 * getEnrolledTotal()) / getTotalQuota();
    }

    public long countRelevantSubjects(){
        return faculty.getAppropriateSubjects().size();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Name: ").append(name).append("\n");
        sb.append("Public Quota:\n");

        List<Applicant> sorted = new ArrayList<>(applicants);
        sorted.sort((a, b) -> Double.compare(b.getPoints(), a.getPoints()));

        for (int i = 0; i < enrolledInPublicQuota; i++)
            sb.append(sorted.get(i)).append("\n");

        sb.append("Private Quota:\n");
        for (int i = enrolledInPublicQuota; i < enrolledInPublicQuota + enrolledInPrivateQuota; i++)
            sb.append(sorted.get(i)).append("\n");

        sb.append("Rejected:\n");
        for (int i = enrolledInPublicQuota + enrolledInPrivateQuota; i < sorted.size(); i++)
            sb.append(sorted.get(i)).append("\n");

        sb.append("\n");

        return sb.toString();
    }


    public Faculty getFaculty() {
        return faculty;
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

    public Faculty(String shortName) {
        this.shortName = shortName;
        this.appropriateSubjects = new ArrayList<>();
        this.studyProgrammes = new ArrayList<>();
    }

    public void addSubject(String subject){
        appropriateSubjects.add(subject);
    }

    public void addStudyProgramme(StudyProgramme studyProgramme){
        studyProgrammes.add(studyProgramme);
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Faculty: ").append(shortName).append("\n");
        sb.append("Subjects: ").append(appropriateSubjects).append("\n");
        sb.append("Study Programmes:\n");

        List<StudyProgramme> sorted = new ArrayList<>(studyProgrammes);

        sorted.sort((p1, p2) -> {
            int cmp = Long.compare(
                    p1.countRelevantSubjects(),
                    p2.countRelevantSubjects()
            );
            if (cmp != 0) return cmp;

            cmp = Double.compare(
                    p2.getEnrollmentPercentage(),
                    p1.getEnrollmentPercentage()
            );
            if (cmp != 0) return cmp;

            Applicant best1 = p1.getApplicants().stream()
                    .max(Comparator.comparing(Applicant::getPoints)).orElse(null);
            Applicant best2 = p2.getApplicants().stream()
                    .max(Comparator.comparing(Applicant::getPoints)).orElse(null);

            if (best1 == null || best2 == null) return 0;

            return Double.compare(best2.getPoints(), best1.getPoints());
        });

        for (StudyProgramme sp : sorted)
            sb.append(sp);

        return sb.toString();
    }

}

class Applicant {
    private int id;
    private String name;
    private double gpa;
    private List<SubjectWithGrade> subjectsWithGrade;
    private StudyProgramme studyProgramme;
    private double points;

    public Applicant(int id, String name, double gpa, StudyProgramme studyProgramme) {
        this.id = id;
        this.name = name;
        this.gpa = gpa;
        this.subjectsWithGrade = new ArrayList<>();
        this.studyProgramme = studyProgramme;
    }

    public void addSubjectAndGrade(String subject, int grade){
        subjectsWithGrade.add(new SubjectWithGrade(subject,grade));
    }

    public double calculatePoints(){
        double total = gpa * 12;

        for(SubjectWithGrade swg : subjectsWithGrade){
            if(studyProgramme.getFaculty().getAppropriateSubjects().contains(swg.getSubject())){
                total += swg.getGrade() * 2;
            }else{
                total += swg.getGrade() * 1.2;
            }
        }
        this.points = total;
        return total;
    }

    public double getPoints() {
        return points;
    }

    @Override
    public String toString() {
        return String.format(
                "Id: %d, Name: %s, GPA: %.1f - %.1f",
                id, name, gpa, points
        );
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

class Enrollment {
    private Applicant applicant;
    private StudyProgramme studyProgramme;

    public Enrollment(Applicant applicant, StudyProgramme studyProgramme) {
        this.applicant = applicant;
        this.studyProgramme = studyProgramme;
    }

    @Override
    public String toString() {
        return String.format("Enrollment{applicant=%s, studyProgramme=%s, points=%.2f}",
                applicant.getName(), studyProgramme.getCode(), applicant.getPoints());
    }
}


class EnrollmentsIO {

    public static List<Enrollment> readEnrollments(List<StudyProgramme> studyProgrammes, InputStream inputStream) {
        List<Enrollment> list = new ArrayList<>();
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));

        br.lines().forEach(line -> {

            if (line.trim().isEmpty()) return;  // skip empty lines

            String[] parts = line.split(";");
            if (parts.length < 4) return;       // skip invalid lines

            int id = Integer.parseInt(parts[0]);
            String name = parts[1];
            double gpa = Double.parseDouble(parts[2]);

            String studyCode = parts[parts.length - 1];

            StudyProgramme sp = studyProgrammes.stream()
                    .filter(s -> s.getCode().equals(studyCode))
                    .findFirst().orElse(null);

            if (sp == null) return;

            Applicant a = new Applicant(id, name, gpa, sp);

            for (int i = 3; i < parts.length - 1; i++) {
                String[] sg = parts[i].split(":");
                if (sg.length == 2) {
                    a.addSubjectAndGrade(sg[0], Integer.parseInt(sg[1]));
                }
            }

            sp.addApplicant(a);
            list.add(new Enrollment(a, sp)); // optional
        });

        return list;
    }


    public static void printRanked(List<Faculty> faculties) {
        faculties.forEach(f -> System.out.println(f));
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
