import java.time.LocalTime;
import java.util.Date;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * @author Tristan Petit et Mathieu Ducharme
 *
 * Cette classe represente un cours qui l'ont peut ajouter a l'horaire des etudiants.
 */
public class Cours {
    private int number; // Numero du cours
    private String subject; // Matiere (ex: "IFT")
    private int credit; // Nombre de credit
    private ArrayList<Schedule> schedule = new ArrayList<>(); // Horaire d'une semaine type
    private Date start = new Date(); // Date de debut du cours
    private Date end = new Date(); // Date de fin du cours
    private ArrayList<Exam> exams = new ArrayList<>();

    //Getters et Setters
    public int getNumber() {
        return this.number;
    }

    public String getSubject(String subject) {
        return this.subject;
    }

    public int getCredit() {
        return this.credit;
    }

    public ArrayList<Schedule> getSchedule() {
        return this.schedule;
    }

    public Date getStart() {
        return this.start;
    }

    public Date getEnd() {
        return this.end;
    }

    public ArrayList<Exam> getExams() {
        return this.exams;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public void setEnd(Date end) {
        this.end = end;
    }


    private static Date stringToDate(String date) {
        Date result = new Date();
        String[] tabStart = date.split("-");

        result.setDate(Integer.parseInt(tabStart[0]));
        result.setMonth(Integer.parseInt(tabStart[1]));
        result.setYear(Integer.parseInt(tabStart[2]) - 1900);

        return result;
    }

    /**
     * C'est le constructeur du cours. L'usager entre la date de debut et de fin de la forme: JJ-MM-AAAA
     */
    public Cours(String subject, int number, int credit) {
        this.number = number;
        this.subject = subject;
        this.credit = credit;
    }

    public static String datePrint(Date date) {
        String result = "";
        result += date.toString().substring(0,10) + date.toString().substring(date.toString().length() - 5);
        return result;
    }

    @Override
    public String toString() {
        String result = "";
        result += this.subject + this.number + " - " + this.credit + " crédit\n";
        result += "Date de debut: " + datePrint(this.start) + "\n";
        result += "Date de fin: " + datePrint(this.start) + "\n";

        for (Schedule horaire : schedule) {
            result += horaire.toString() + "\n";
        }

        for (Exam exam : exams) {
            result += exam.toString() + "\n";
        }
        return result;
    }

    public void addDate() {
        Scanner scan = new Scanner(System.in);

        System.out.println("Entrer la date de début (JJ-MM-AAAA)");
        String startS = scan.next();
        this.start = stringToDate(startS);

        System.out.println("Entrer la date de fin (JJ-MM-AAAA)");
        String endS = scan.next();
        this.end = stringToDate(endS);
    }

    public void addSchedule() {
        Schedule horaire;

        Scanner scan = new Scanner(System.in);
        boolean other = true;

        while (other) {
            System.out.println("Est-ce un Th, Tp ou Lab? (Th par defaut)");
            String type = scan.next();

            System.out.println("Quel Jours? (0 = Dimanche et 6 = Samedi");
            int day = scan.nextInt() % 7;

            System.out.println("Heure de debut?");
            int HStart = scan.nextInt();

            System.out.println("Minute de debut?");
            int MStart = scan.nextInt();

            System.out.println("Heure de fin?");
            int HEnd = scan.nextInt();

            System.out.println("Minute de fin?");
            int MEnd = scan.nextInt();

            System.out.println("Finit? (oui/non et oui par defaut)");
            String fini = scan.next();

            if (fini.compareTo("non") != 0) {
                other = false;
            }

            LocalTime start = LocalTime.of(HStart, MStart);
            LocalTime end = LocalTime.of(HEnd, MEnd);

            if (type.equals("Tp")) {
                horaire = new Schedule(start, end, day, ClassType.TP);
            } else if (type.equals("Lab")) {
                horaire = new Schedule(start, end, day, ClassType.LAB);
            } else {
                horaire = new Schedule(start, end, day, ClassType.TH);
            }
            schedule.add(horaire);
        }
    }

    public void addExams() {
        boolean finish = true;
        Scanner scan = new Scanner(System.in);

        while (finish) {
            Date date = new Date(0,0,0,0,0);

            System.out.println("Entrer la date de l'examen");
            String dateS = scan.next();
            date = stringToDate(dateS);

            System.out.println("L'heure de debut");
            int HStart = scan.nextInt();
            date.setHours(HStart);

            System.out.println("Minute de debut");
            int MStart = scan.nextInt();
            date.setMinutes(MStart);

            System.out.println("Durée de l'examen");
            double duree = scan.nextInt();

            System.out.println("Intra ou Final? (defaut intra)");
            String typeE = scan.next();

            if (typeE.compareTo("Final") != 0) {
                typeE = "Intra";
            }

            exams.add(new Exam(date, duree, typeE));

            System.out.println("Finit? (oui/non et oui par defaut)");
            String fini = scan.next();

            if (fini.compareTo("non") != 0) {
                finish = false;
            }

        }
    }
}
