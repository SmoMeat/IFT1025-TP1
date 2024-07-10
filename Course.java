import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 * @author Tristan Petit et Mathieu Ducharme
 *
 * Cette classe represente un cours qui l'ont peut ajouter a l'horaire des etudiants.
 */
public class Course {
    private int number; // Numero du cours
    private String subject; // Matiere (ex: "IFT")
    private int credits; // Nombre de credit
    private ArrayList<Semester> semesters = new ArrayList<>();
    private ArrayList<Exam> exams = new ArrayList<>();
    private ArrayList<Course> prerequisites = new ArrayList<>();
    private String section;
    // add name of course


    /**
     * Ce sont les constructeurs du cours. La liste de cours préalables est optionnelle.
     */
    public Course(String subject, int number, int credit, String section) {
        this.number = number;
        this.subject = subject;
        this.credits = credit;
        this.section = section;
    }

    public Course(String subject, int number, int credit, String section, ArrayList<Course> prerequisites) {
        this.number = number;
        this.subject = subject;
        this.credits = credit;
        this.section = section;
        this.prerequisites = prerequisites;
    }

    @Override
    public String toString() {
        return getName();
        // String result = "";
        // result += this.subject + this.number + " - " + this.credits + " crédit\n";
        // result += "Date de debut: " + this.start + "\n";
        // result += "Date de fin: " + this.start + "\n";

        // for (Period horaire : periods) {
        //     result += horaire.toString() + "\n";
        // }

        // for (Exam exam : exams) {
        //     result += exam.toString() + "\n";
        // }
        // return result;
    }

    // public void addSchedule() {
    //     Period horaire;

    //     Scanner scan = new Scanner(System.in);
    //     boolean other = true;

    //     while (other) {
    //         System.out.println("Est-ce un Th, Tp ou Lab? (Th par defaut)");
    //         String type = scan.next();

    //         System.out.println("Quel Jours? (0 = Dimanche et 6 = Samedi");
    //         int day = scan.nextInt() % 7;

    //         System.out.println("Heure de debut?");
    //         int HStart = scan.nextInt();

    //         System.out.println("Minute de debut?");
    //         int MStart = scan.nextInt();

    //         System.out.println("Heure de fin?");
    //         int HEnd = scan.nextInt();

    //         System.out.println("Minute de fin?");
    //         int MEnd = scan.nextInt();

    //         System.out.println("Finit? (oui/non et oui par defaut)");
    //         String fini = scan.next();

    //         if (fini.compareTo("non") != 0) {
    //             other = false;
    //         }

    //         LocalTime start = LocalTime.of(HStart, MStart);
    //         LocalTime end = LocalTime.of(HEnd, MEnd);

    //         if (type.equals("Tp")) {
    //             horaire = new Period(start, end, day, ClassType.TP);
    //         } else if (type.equals("Lab")) {
    //             horaire = new Period(start, end, day, ClassType.LAB);
    //         } else {
    //             horaire = new Period(start, end, day, ClassType.TH);
    //         }
    //         periods.add(horaire);
    //     }
    // }

    // public void addExams() {
    //     boolean finish = true;
    //     Scanner scan = new Scanner(System.in);

    //     while (finish) {
    //         LocalDate date = new Date(0,0,0,0,0);

    //         System.out.println("Entrer la date de l'examen");
    //         String dateS = scan.next();
    //         date = stringToDate(dateS);

    //         System.out.println("L'heure de debut");
    //         int HStart = scan.nextInt();
    //         date.setHours(HStart);

    //         System.out.println("Minute de debut");
    //         int MStart = scan.nextInt();
    //         date.setMinutes(MStart);

    //         System.out.println("Durée de l'examen");
    //         double duree = scan.nextInt();

    //         System.out.println("Intra ou Final? (defaut intra)");
    //         String typeE = scan.next();

    //         if (typeE.compareTo("Final") != 0) {
    //             typeE = "Intra";
    //         }

    //         exams.add(new Exam(date, duree, typeE));

    //         System.out.println("Finit? (oui/non et oui par defaut)");
    //         String fini = scan.next();

    //         if (fini.compareTo("non") != 0) {
    //             finish = false;
    //         }

    //     }
    // }

    public void addExam(Exam exam) {
        exams.add(exam);
    }



    public void addPrerequisite(Course prerequisite) throws IllegalArgumentException {
        if (prerequisites.contains(prerequisite))
            throw new IllegalArgumentException(getName() + " a déjà " + prerequisite.getName() + " comme préalable.");
        prerequisites.add(prerequisite);
    }

    public String getName() {
        return this.subject + this.number;
    }


    //Getters et Setters
    public int getNumber() {
        return this.number;
    }

    public String getSubject(String subject) {
        return this.subject;
    }

    public int getCredits() {
        return this.credits;
    }

    public ArrayList<Semester> getSemesters() {
        return this.semesters;
    }

    public Semester getSemesterByName(String name) {
        for (Semester semester : semesters) {
            if (semester.getName().equals(name))
                return semester;
        }
        return null;
    }

    public void addSemester(Semester semester) {
        this.semesters.add(semester);
    }

    public ArrayList<Exam> getExams() {
        return this.exams;
    }

    public String getSection() {
        return this.section;
    }

    public ArrayList<Course> getPrerequisites() {
        return this.prerequisites;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public void setSection(String section) {
        this.section = section;
    }

}
