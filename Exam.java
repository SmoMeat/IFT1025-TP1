import java.time.LocalDate;
import java.time.LocalTime;

/**
 * @author Tristan Petit et Mathieu Ducharme
 * 
 * Cette classe correspond aux examens des différents semestres associés aux cours. elle hérite
 * de la classe "Period".
 */
public class Exam extends Period {
    private LocalDate date; // Date de l'examen

    /**
     * C'est le constructeur de la classe
     * 
     * @param date Date de l'examen
     * @param start heure du début de l'examen
     * @param end Heure de fin de l'examen
     * @param type Type de l'examen (INTRA, FINAL, QUIZ)
     * @param section Section de l'examen (A,B,...)
     */
    public Exam(LocalDate date, LocalTime start, LocalTime end, ClassType type, String section) {
        super(start, end, date.getDayOfWeek(), type, section);
        this.date = date;
    }

    //Getter et setter
    public LocalDate getDate() {
        return this.date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
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
        testExamConstructor();
        testExamDate();
        testExamInheritedMethods();

        System.out.println("Tous les tests de Exam ont réussi.");
    }

    /**
     * Teste le constructeur de la classe Exam.
     */
    public static void testExamConstructor() {
        LocalDate date = LocalDate.of(2024, 7, 15);
        LocalTime start = LocalTime.of(9, 0);
        LocalTime end = LocalTime.of(11, 0);
        ClassType type = ClassType.FINAL;
        String section = "A";

        Exam exam = new Exam(date, start, end, type, section);

        assert exam.getStart().equals(start) : "Heure de début incorrecte";
        assert exam.getEnd().equals(end) : "Heure de fin incorrecte";
        assert exam.getDayOfWeek().equals(date.getDayOfWeek()) : "Jour de la semaine incorrect";
        assert exam.getType().equals(type) : "Type d'examen incorrect";
        assert exam.getSection().equals(section) : "Section d'examen incorrecte";
        assert exam.getDate().equals(date) : "Date d'examen incorrecte";

        System.out.println("testExamConstructor réussi.");
    }

    /**
     * Teste les accesseurs (getters/setters) de la date de la classe Exam.
     */
    public static void testExamDate() {
        LocalDate date = LocalDate.of(2024, 7, 15);
        LocalTime start = LocalTime.of(9, 0);
        LocalTime end = LocalTime.of(11, 0);
        ClassType type = ClassType.FINAL;
        String section = "A";

        Exam exam = new Exam(date, start, end, type, section);

        LocalDate newDate = LocalDate.of(2024, 7, 20);
        exam.setDate(newDate);

        assert exam.getDate().equals(newDate) : "Modification de la date incorrecte";

        System.out.println("testExamDate réussi.");
    }

    /**
     * Teste les méthodes héritées de la classe Period dans la classe Exam.
     */
    public static void testExamInheritedMethods() {
        LocalDate date = LocalDate.of(2024, 7, 15);
        LocalTime start = LocalTime.of(9, 0);
        LocalTime end = LocalTime.of(11, 0);
        ClassType type = ClassType.FINAL;
        String section = "A";

        Exam exam = new Exam(date, start, end, type, section);

        String expectedToString = "FINAL: Lundi de 09:00 à 11:00";
        assert exam.toString().equals(expectedToString) : "Méthode toString incorrecte";

        double timeOf = exam.timeOf();
        assert timeOf == 2.0 : "Méthode timeOf incorrecte";

        System.out.println("testExamInheritedMethods réussi.");
    }
}

