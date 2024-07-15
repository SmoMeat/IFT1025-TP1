import java.time.DayOfWeek;
import java.time.LocalTime;

/**
 * La classe SchedulePeriod représente une période d'horaire associée à un cours spécifique.
 * Elle est utile dans Schedule pour garder une trace du cours duquel la période est enfant.
 */
public class SchedulePeriod extends Period {
    private Course course;

    /**
     * Construit un nouvel objet SchedulePeriod en utilisant un cours et une période prédéfinis.
     *
     * @param course le cours associé à cette période d'horaire
     * @param period la période
     */
    public SchedulePeriod(Course course, Period period) {
        super(period);
        this.course = course;
    }

    /**
     * Récupère le cours associé à cette période d'horaire.
     *
     * @return le cours associé
     */
    public Course getCourse() {
        return this.course;
    }

    /**
     * Définit le cours associé à cette période d'horaire.
     *
     * @param course le cours à associer
     */
    public void setCourse(Course course) {
        this.course = course;
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
        testSchedulePeriodConstructor();
        testGetCourse();
        testSetCourse();

        System.out.println("Tous les tests de SchedulePeriod ont réussi.");
    }

    /**
     * Teste le constructeur de la classe SchedulePeriod.
     */
    public static void testSchedulePeriodConstructor() {
        Course course = new Course("IFT", 1015, 3);
        Period period = new Period(LocalTime.now(), LocalTime.now(), DayOfWeek.MONDAY, ClassType.TH, "");
        SchedulePeriod schedulePeriod = new SchedulePeriod(course, period);

        assert schedulePeriod instanceof SchedulePeriod  : "La période d'horaire n'est pas bonne";

        System.out.println("testSchedulePeriodConstructor réussi.");
    }

    /**
     * Teste la méthode getCourse() de la classe SchedulePeriod.
     */
    public static void testGetCourse() {
        Course course = new Course("IFT", 1015, 3);
        Period period = new Period(LocalTime.now(), LocalTime.now(), DayOfWeek.MONDAY, ClassType.TH, "");
        SchedulePeriod schedulePeriod = new SchedulePeriod(course, period);

        assert schedulePeriod.getCourse().equals(course) : "Le cours récupéré ne correspond pas";
        
        System.out.println("testGetCourse réussi.");
    }

    /**
     * Teste la méthode setCourse(Course course) de la classe SchedulePeriod.
     */
    public static void testSetCourse() {
        Course course1 = new Course("IFT", 1015, 3);
        Course course2 = new Course("MAT", 1720, 4);
        Period period = new Period(LocalTime.now(), LocalTime.now(), DayOfWeek.MONDAY, ClassType.TH, "");
        SchedulePeriod schedulePeriod = new SchedulePeriod(course1, period);

        schedulePeriod.setCourse(course2);
        
        assert schedulePeriod.getCourse().equals(course2) : "Le cours n'a pas été correctement défini";

        System.out.println("testSetCourse réussi.");
    }
}