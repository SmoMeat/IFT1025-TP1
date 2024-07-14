import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;

public class Student {
    private String firstname;
    private String lastname;
    private String matricule;
    private Schedule schedule = new Schedule();

    public Student() {}

    /**
     * Construit un nouvel étudiant
     * 
     * @param firstname le prénom de l'étudiant
     * @param lastname le nom de famille de l'étudiant
     * @param matricule le matricule de l'étudiant
     */
    public Student(String firstname, String lastname, String matricule) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.matricule = matricule;
    }

    /**
     * Ajoute un cours à l'horaire de l'étudiant pour une session spécifique.
     *
     * @param course      le cours à ajouter
     * @param sessionName le nom de la session
     */
    public void addCourse(Course course, String sessionName) {
        schedule.addCourse(course, sessionName);
    }

    /**
     * Change l'horaire de l'étudiant pour un autre.
     *
     * @param schedule l'horaire à changer
     */
    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    /**
     * Renvoie l'horaire de l'étudiant.
     *
     * @return l'horaire de l'étudiant
     */
    public Schedule getSchedule() {
        return this.schedule;
    }

    /**
     * Renvoie le prénom de l'étudiant
     * 
     * @return le prénom de l'étudiant
     */
    public String getFirstname() {
        return firstname;
    }

    /**
     * Modifie le prénom de l'étudiant
     * 
     * @param firstname le nouveau prénom de l'étudiant
     */
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    /**
     * Renvoie le nom de famille de l'étudiant
     * 
     * @return le nom de famille de l'étudiant
     */
    public String getLastname() {
        return lastname;
    }

    /**
     * Modifie le nom de famille de l'étudiant
     * 
     * @param lastname le nouveau nom de famille de l'étudiant
     */
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    /**
     * Renvoie le matricule de l'étudiant
     * 
     * @return le matricule de l'étudiant
     */
    public String getMatricule() {
        return matricule;
    }

    /**
     * Modifie le matricule de l'étudiant
     * 
     * @param matricule le nouveau matricule de l'étudiant
     */
    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    /**
     * Retourne une représentation sous forme de chaîne de caractères de l'étudiant
     *
     * @return une chaîne de caractères représentant l'étudiant
     */
    @Override
    public String toString() {
        return firstname + " " +  lastname + " (" + matricule + ")";
    }

    /**
     * Méthode principale pour exécuter les tests unitaires de la classe Student.
     *
     * @param args Les arguments de la ligne de commande (non utilisés).
     */
    public static void main(String[] args) {
        runTests();
    }

    /**
     * Méthode principale pour exécuter les tests unitaires de la classe Student.
     */
    public static void runTests() {
        testStudentConstructor();
        testSettersAndGetters();
        testAddCourse();
        testToString();

        System.out.println("Tous les tests de Student ont réussi.");
    }

    /**
     * Teste le constructeur de la classe Student.
     */
    public static void testStudentConstructor() {
        Student student = new Student("John", "Doe", "12345");
        assert student.getFirstname().equals("John") : "Prénom incorrect";
        assert student.getLastname().equals("Doe") : "Nom de famille incorrect";
        assert student.getMatricule().equals("12345") : "Matricule incorrect";
        System.out.println("testStudentConstructor réussi.");
    }

    /**
     * Teste les méthodes setters et getters de la classe Student.
     */
    public static void testSettersAndGetters() {
        Student student = new Student();
        student.setFirstname("Jane");
        student.setLastname("Smith");
        student.setMatricule("54321");

        assert student.getFirstname().equals("Jane") : "Méthode setter/getter du prénom incorrecte";
        assert student.getLastname().equals("Smith") : "Méthode setter/getter du nom de famille incorrecte";
        assert student.getMatricule().equals("54321") : "Méthode setter/getter du matricule incorrecte";
        System.out.println("testSettersAndGetters réussi.");
    }

    /**
     * Teste l'ajout d'un cours à l'horaire de la classe Student.
     */
    public static void testAddCourse() {
        Student student = new Student("John", "Doe", "12345");
        Course course = new Course("IFT", 1015, 3);
        course.addSemester(new Semester("A24", LocalDate.now(), LocalDate.now(), new ArrayList<Period>(
            Arrays.asList(new Period(LocalTime.of(15,30), LocalTime.of(16,30), DayOfWeek.FRIDAY, ClassType.TH, "A"))), new ArrayList<Exam>()
        ));
        student.addCourse(course, "A24");

        Schedule schedule = student.getSchedule();
        assert schedule.getCourses().contains(course) : "Le cours n'a pas été ajouté correctement";
        System.out.println("testAddCourse réussi.");
    }

    /**
     * Teste la méthode toString de la classe Student.
     */
    public static void testToString() {
        Student student = new Student("John", "Doe", "12345");
        assert student.toString().equals("John Doe (12345)") : "Méthode toString incorrecte";
        System.out.println("testToString réussi.");
    }
}