import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class Semester {
    private String name; // ex: A24, H25
    private LocalDate start;
    private LocalDate end;
    private ArrayList<Period> periods = new ArrayList<>();
    private ArrayList<Exam> exams = new ArrayList<>();

    /**
     * Construit une nouvelle session universitaire.
     *
     * @param name     l'identifiant de la session, par exemple A24, H25
     * @param start    la date de début de la session
     * @param end      la date de fin de la session
     * @param periods  la liste des périodes dans la session
     * @param exams    la liste des examens dans la session
     */
    public Semester(String name, LocalDate start, LocalDate end, ArrayList<Period> periods, ArrayList<Exam> exams) {
        this.name = name;
        this.start = start;
        this.end = end;
        this.periods = periods;
        this.exams = exams;
    }

    /**
     * Construit une nouvelle session universitaire sans examens et périodes.
     *
     * @param name     l'identifiant de la session, par exemple A24, H25
     * @param start    la date de début de la session
     * @param end      la date de fin de la session
     * @param periods  la liste des périodes dans la session
     */
    public Semester(String name, LocalDate start, LocalDate end) {
        this.name = name;
        this.start = start;
        this.end = end;
    }

    /**
     * Obtient l'identifiant de la session.
     *
     * @return l'identifiant de la session
     */
    public String getName() {
        return name;
    }

    /**
     * Définit l'identifiant de la session.
     *
     * @param semester l'identifiant de la session à définir
     */
    public void setName(String semester) {
        this.name = semester;
    }

    /**
     * Obtient la date de début de la session.
     *
     * @return la date de début de la session
     */
    public LocalDate getStart() {
        return start;
    }

    /**
     * Définit la date de début de la session.
     *
     * @param start la date de début à définir
     */
    public void setStart(LocalDate start) {
        this.start = start;
    }

    /**
     * Obtient la date de fin de la session.
     *
     * @return la date de fin de la session
     */
    public LocalDate getEnd() {
        return end;
    }

    /**
     * Définit la date de fin de la session.
     *
     * @param end la date de fin à définir
     */
    public void setEnd(LocalDate end) {
        this.end = end;
    }

    /**
     * Obtient la liste des périodes dans la session.
     *
     * @return la liste des périodes
     */
    public ArrayList<Period> getPeriods() {
        return periods;
    }

    /**
     * Ajoute à la liste des périodes dans la session une nouvelle période
     *
     * @param period la période à ajouter
     */
    public void addPeriod(Period period) {
        this.periods.add(period);
    }

    /**
     * Ajoute à la liste des périodes dans la session des nouvelles périodes
     *
     * @param periods la liste de périodes à ajouter
     */
    public void addPeriods(ArrayList<Period> periods) {
        this.periods.addAll(periods);
    }

    /**
     * Retire à la liste des périodes dans la session une période
     *
     * @param period la période à enlever
     */
    public void removePeriod(Period period) {
        this.periods.remove(period);
    }

    /**
     * Retire à la liste des périodes dans la session plusieurs périodes
     *
     * @param periods la liste de périodes à enlever
     */
    public void removePeriods(ArrayList<Period>  periods) {
        this.periods.removeAll(periods);
    }

    /**
     * Obtient la liste des périodes dans la session.
     *
     * @return la liste des périodes
     */
    public ArrayList<Exam> getExams() {
        return exams;
    }

    /**
     * Ajoute à la liste des examens dans la session un nouvel examen
     *
     * @param exam l'examen à ajouter
     */
    public void addExam(Exam exam) {
        this.exams.add(exam);
    }

    /**
     * Ajoute à la liste des examens dans la session plusieurs nouveaux examens
     *
     * @param exams la liste d'examens à ajouter
     */
    public void addExams(ArrayList<Exam> exams) {
        this.exams.addAll(exams);
    }

    /**
     * Retire à la liste des examens dans la session un examen
     *
     * @param exam l'examen à enlever
     */
    public void removeExam(Exam exam) {
        this.exams.remove(exam);
    }

    /**
     * Retire à la liste des examens dans la session plusieurs examens
     *
     * @param exams la liste d'examens à enlever
     */
    public void removeExams(ArrayList<Exam> exams) {
        this.exams.removeAll(exams);
    }

    /**
     * Retourne une représentation sous forme de chaîne de caractères des périodes
     * séparées par des virgules.
     *
     * @return une chaîne de caractères représentant les périodes
     */
    public String getPeriodsAsString() {
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < periods.size(); i++) {
            stringBuilder.append(periods.get(i));
            if (i < periods.size() - 1) {
                stringBuilder.append(", ");
            }
        }

        return stringBuilder.toString();
    }

    /**
     * Retourne une représentation sous forme de chaîne de caractères des examens
     * séparées par des virgules.
     *
     * @return une chaîne de caractères représentant les examens
     */
    public String getExamsAsString() {
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < exams.size(); i++) {
            stringBuilder.append(exams.get(i));
            if (i < exams.size() - 1) {
                stringBuilder.append(", ");
            }
        }

        return stringBuilder.toString();
    }

    /**
     * Retourne une représentation sous forme de chaîne de caractères de le semestre.
     *
     * @return une chaîne de caractères représentant le semestre
     */
    public String toString() {
        return name + " de " + start + " à " + end + " avec les périodes " + getPeriodsAsString() + " et les examens " + getExamsAsString();
    }

    /**
     * Méthode principale pour exécuter les tests unitaires.
     *
     * @param args Les arguments de la ligne de commande (non utilisés).
     */
    public static void main(String[] args) {
        runTests();
    }

    /**
     * Méthode principale pour exécuter les tests unitaires.
     */
    public static void runTests() {
        testSemesterConstructorWithPeriodsAndExams();
        testSemesterConstructorWithoutPeriodsAndExams();
        testAddPeriod();
        testAddPeriods();
        testRemovePeriod();
        testRemovePeriods();
        testAddExam();
        testAddExams();
        testRemoveExam();
        testRemoveExams();

        System.out.println("Tous les tests de Semester ont réussi.");
    }

    /**
     * Teste le constructeur de la classe Semester avec périodes et examens.
     */
    public static void testSemesterConstructorWithPeriodsAndExams() {
        LocalDate start = LocalDate.of(2024, 9, 1);
        LocalDate end = LocalDate.of(2024, 12, 31);
        ArrayList<Period> periods = new ArrayList<>();
        periods.add(new Period(LocalTime.now(), LocalTime.now(), DayOfWeek.MONDAY, ClassType.TH, ""));
        ArrayList<Exam> exams = new ArrayList<>();
        exams.add(new Exam(LocalDate.now(), LocalTime.now(), LocalTime.now(), ClassType.TH, ""));

        Semester semester = new Semester("A24", start, end, periods, exams);

        assert semester.getName().equals("A24") : "Identifiant du semestre incorrect";
        assert semester.getStart().equals(start) : "Date de début du semestre incorrecte";
        assert semester.getEnd().equals(end) : "Date de fin du semestre incorrecte";
        assert semester.getPeriods().containsAll(periods) : "Liste des périodes incorrecte";
        assert semester.getExams().containsAll(exams) : "Liste des examens incorrecte";

        System.out.println("testSemesterConstructorWithPeriodsAndExams réussi.");
    }

    /**
     * Teste le constructeur de la classe Semester sans périodes et examens.
     */
    public static void testSemesterConstructorWithoutPeriodsAndExams() {
        LocalDate start = LocalDate.of(2024, 9, 1);
        LocalDate end = LocalDate.of(2024, 12, 31);

        Semester semester = new Semester("A24", start, end);

        assert semester.getName().equals("A24") : "Identifiant du semestre incorrect";
        assert semester.getStart().equals(start) : "Date de début du semestre incorrecte";
        assert semester.getEnd().equals(end) : "Date de fin du semestre incorrecte";
        assert semester.getPeriods().isEmpty() : "Liste des périodes non vide";
        assert semester.getExams().isEmpty() : "Liste des examens non vide";

        System.out.println("testSemesterConstructorWithoutPeriodsAndExams réussi.");
    }

    /**
     * Teste la méthode addPeriod() de la classe Semester.
     */
    public static void testAddPeriod() {
        Semester semester = new Semester("A24", LocalDate.of(2024, 9, 1), LocalDate.of(2024, 12, 31));
        Period period = new Period(LocalTime.now(), LocalTime.now(), DayOfWeek.MONDAY, ClassType.TH, "");

        semester.addPeriod(period);

        assert semester.getPeriods().contains(period) : "La période n'a pas été ajoutée correctement";

        System.out.println("testAddPeriod réussi.");
    }

    /**
     * Teste la méthode addPeriods() de la classe Semester.
     */
    public static void testAddPeriods() {
        Semester semester = new Semester("A24", LocalDate.of(2024, 9, 1), LocalDate.of(2024, 12, 31));
        ArrayList<Period> periods = new ArrayList<>();
        periods.add(new Period(LocalTime.now(), LocalTime.now(), DayOfWeek.MONDAY, ClassType.TH, ""));

        semester.addPeriods(periods);

        assert semester.getPeriods().containsAll(periods) : "Les périodes n'ont pas été ajoutées correctement";

        System.out.println("testAddPeriods réussi.");
    }

    /**
     * Teste la méthode removePeriod() de la classe Semester.
     */
    public static void testRemovePeriod() {
        Semester semester = new Semester("A24", LocalDate.of(2024, 9, 1), LocalDate.of(2024, 12, 31));
        Period period = new Period(LocalTime.now(), LocalTime.now(), DayOfWeek.MONDAY, ClassType.TH, "");
        semester.addPeriod(period);

        semester.removePeriod(period);

        assert !semester.getPeriods().contains(period) : "La période n'a pas été retirée correctement";

        System.out.println("testRemovePeriod réussi.");
    }

    /**
     * Teste la méthode removePeriods() de la classe Semester.
     */
    public static void testRemovePeriods() {
        Semester semester = new Semester("A24", LocalDate.of(2024, 9, 1), LocalDate.of(2024, 12, 31));
        ArrayList<Period> periods = new ArrayList<>();
        periods.add(new Period(LocalTime.now(), LocalTime.now(), DayOfWeek.MONDAY, ClassType.TH, ""));
        semester.addPeriods(periods);

        semester.removePeriods(periods);

        assert semester.getPeriods().isEmpty() : "Les périodes n'ont pas été retirées correctement";

        System.out.println("testRemovePeriods réussi.");
    }

    /**
     * Teste la méthode addExam() de la classe Semester.
     */
    public static void testAddExam() {
        Semester semester = new Semester("A24", LocalDate.of(2024, 9, 1), LocalDate.of(2024, 12, 31));
        Exam exam = new Exam(LocalDate.now(), LocalTime.now(), LocalTime.now(), ClassType.TH, "");

        semester.addExam(exam);

        assert semester.getExams().contains(exam) : "L'examen n'a pas été ajouté correctement";

        System.out.println("testAddExam réussi.");
    }

    /**
     * Teste la méthode addExams() de la classe Semester.
     */
    public static void testAddExams() {
        Semester semester = new Semester("A24", LocalDate.of(2024, 9, 1), LocalDate.of(2024, 12, 31));
        ArrayList<Exam> exams = new ArrayList<>();
        exams.add(new Exam(LocalDate.now(), LocalTime.now(), LocalTime.now(), ClassType.TH, ""));

        semester.addExams(exams);

        assert semester.getExams().containsAll(exams) : "Les examens n'ont pas été ajoutés correctement";

        System.out.println("testAddExams réussi.");
    }

    /**
     * Teste la méthode removeExam() de la classe Semester.
     */
    public static void testRemoveExam() {
        Semester semester = new Semester("A24", LocalDate.of(2024, 9, 1), LocalDate.of(2024, 12, 31));
        Exam exam = new Exam(LocalDate.now(), LocalTime.now(), LocalTime.now(), ClassType.TH, "");
        semester.addExam(exam);

        semester.removeExam(exam);

        assert !semester.getExams().contains(exam) : "L'examen n'a pas été retiré correctement";

        System.out.println("testRemoveExam réussi.");
    }

    /**
     * Teste la méthode removeExams() de la classe Semester.
     */
    public static void testRemoveExams() {
        Semester semester = new Semester("A24", LocalDate.of(2024, 9, 1), LocalDate.of(2024, 12, 31));
        ArrayList<Exam> exams = new ArrayList<>();
        exams.add(new Exam(LocalDate.now(), LocalTime.now(), LocalTime.now(), ClassType.TH, ""));
        semester.addExams(exams);

        semester.removeExams(exams);

        assert semester.getExams().isEmpty() : "Les examens n'ont pas été retirés correctement";

        System.out.println("testRemoveExams réussi.");
    }

}
