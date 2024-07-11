import java.util.ArrayList;

/**
 * @author Tristan Petit et Mathieu Ducharme
 *
 * Cette classe represente un cours qui l'ont peut ajouter a l'horaire des etudiants.
 */
public class Course {
    private int value; // Numero du cours
    private String subject; // Matiere (ex: "IFT")
    private String name; // ex: Calcul 1
    private String description; // ex: Étude des dérivées et intégrales en 3 dimensions
    private int credit; // Nombre de credit
    private ArrayList<Semester> semesters = new ArrayList<>();
    private ArrayList<Course> prerequisites = new ArrayList<>();


    /**
     * Ce sont les constructeurs du cours. La liste de cours préalables est optionnelle.
     */
    public Course() {}
    
    public Course(String subject, int value, int credit) {
        this.value = value;
        this.subject = subject;
        this.credit = credit;
    }

    public Course(String subject, int value, String name, String description, int credit) {
        this.subject = subject;
        this.value = value;
        this.name = name;
        this.description = description;
        this.credit = credit;
    }

    public Course(String subject, int value, int credit, ArrayList<Course> prerequisites) {
        this.value = value;
        this.subject = subject;
        this.credit = credit;
        this.prerequisites = prerequisites;
    }

    @Override
    public String toString() {
        return getAbbreviatedName() + ": " + name + " (" + description + ") " + credit + " crédit avec " + semesters.size() + " semestres enregistrées";
        // return getAbbreviatedName();
        // String result = "";
        // result += this.subject + this.value + " - " + this.credits + " crédit\n";
        // result += "Date de debut: " + this.start + "\n";
        // result += "Date de fin: " + this.start + "\n";

        // for (Period horaire : se) {
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

    public void addPrerequisite(Course prerequisite) throws IllegalArgumentException {
        if (prerequisites.contains(prerequisite))
            throw new IllegalArgumentException(getAbbreviatedName() + " a déjà " + prerequisite.getAbbreviatedName() + " comme préalable.");
        prerequisites.add(prerequisite);
    }

    public String getAbbreviatedName() {
        return this.subject + this.value;
    }


    //Getters et Setters
    public int getValue() {
        return this.value;
    }

    public void setValue(int number) {
        this.value = number;
    }

    public String getSubject() {
        return this.subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCredit() {
        return this.credit;
    }

    public void setCredit(int credits) {
        this.credit = credits;
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

    public String getSemestersAsString() {
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < semesters.size(); i++) {
            stringBuilder.append(semesters.get(i).getName());
            if (i < semesters.size() - 1) {
                stringBuilder.append(", ");
            }
        }

        return stringBuilder.toString();
    }

    public void addSemester(Semester semester) {
        this.semesters.add(semester);
    }

    public void removeSemester(Semester semester) {
        this.semesters.remove(semester);
    }

    public void addSemesters(ArrayList<Semester> semesters) {
        this.semesters.addAll(semesters);
    }

    // pas utilisé !!!
    // public void removeSemesters(ArrayList<Semester> semesters) {
    //     this.semesters.removeAll(semesters);
    // }

    public ArrayList<Course> getPrerequisites() {
        return this.prerequisites;
    }
}
