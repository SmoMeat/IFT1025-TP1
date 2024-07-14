import java.time.LocalDate;
import java.util.ArrayList;

/**
 * @author Tristan Petit et Mathieu Ducharme
 *
 * Cette classe represente un cours qui l'ont peut ajouter a l'horaire des etudiants.
 */
public class Course {
    private String subject; // Matiere (ex: "IFT")
    private int value; // Numero du cours
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
        this.subject = subject;
        this.value = value;
        this.name = subject + value;
        this.description = "Description vide";
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
        this.subject = subject;
        this.value = value;
        this.credit = credit;
        this.prerequisites = prerequisites;
    }

    /**
     * Cette fonction gère l'affichage du cours lors d'un print
     * 
     * @return String - Retourne le format d'affichage voulu
     */
    @Override
    public String toString() {
        return getAbbreviatedName() + ": " + name + " (" + description + ") " + credit + " crédit avec " + semesters.size() + " semestres enregistrées";
    }

    /**
     * Cette fonction permet d'ajouter des cours préalables
     * 
     * @param prerequisite Course - Cours préalable à ajouter
     * @throws IllegalArgumentException
     */
    public void addPrerequisite(Course prerequisite) throws IllegalArgumentException {
        if (prerequisites.contains(prerequisite))
            throw new IllegalArgumentException(getAbbreviatedName() + " a déjà " + prerequisite.getAbbreviatedName() + " comme préalable.");
        prerequisites.add(prerequisite);
    }

    /**
     * Cette fonction permet d'obtenir le nom du cours sous la forme "IFT1015" par exemple
     * 
     * @return String - Le format du nom du cours 
     */
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

    public ArrayList<Course> getPrerequisites() {
        return this.prerequisites;
    }

    /**
     * Cette fonction permet d'obtenir le semestre souhaité par son nom
     * 
     * @param name String - nom du semestre
     * 
     * @return Semester - Le semestre souhaité
     */
    public Semester getSemesterByName(String name) {
        for (Semester semester : semesters) {
            if (semester.getName().equals(name))
                return semester;
        }
        return null;
    }

    /**
     * Cette fonction permet d'obetnir tous les semestres associés au cours
     * 
     * @return String - L'énumération des semestres du cours
     */
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

    /**
     * Cette fonction permet d'ajouter un semestre à la liste des semestres du cours
     * 
     * @param semester Semestre - C'est le semestre à ajouter
     */
    public void addSemester(Semester semester) {
        this.semesters.add(semester);
    }

    /**
     * Cette fonction permet de retirer un semestre à la liste des semestres du cours
     * 
     * @param semester Semestre - C'est le semestre à retirer
     */
    public void removeSemester(Semester semester) {
        this.semesters.remove(semester);
    }

    /**
     * Cette fonction permet d'ajouter plusieurs semestre à la fois à la liste des 
     * semestres du cours
     * 
     * @param semester ArrayList<Semestre> - C'est la liste de semestres à ajouter
     */
    public void addSemesters(ArrayList<Semester> semesters) {
        this.semesters.addAll(semesters);
    }

    /**
     * Cette fonction vérifie si deux cours sont égaux
     * 
     * @param other Object - Object avec lequel on vérifie si le cours est le même
     * 
     * @return boolean - C'est la valeur indiquant si les cours sont les mêmes ou non
     */
    @Override
    public boolean equals(Object other) {
        Course otherCourse = (Course) other;

        if (!subject.equals(otherCourse.getSubject()))
            return false;
        if (value != otherCourse.getValue())
            return false;        
        if (!name.equals(otherCourse.getName()))
            return false;
        if (!description.equals(otherCourse.getDescription()))
            return false;
        if (credit != otherCourse.getCredit())
            return false;
        if (!semesters.equals(otherCourse.getSemesters()))
            return false;

        return true;
    }



    /**
     * Méthode principale pour exécuter les tests unitaires.
     *
     * @param args Les arguments de la ligne de commande (non utilisés).
     */
    public static void main(String[] args) {
        testCourseConstructor();
        testCoursePrerequisites();
        testCourseSemesters();

        System.out.println("Tous les tests de Course ont réussi.");
    }

    /**
     * Teste les constructeurs de la classe Course.
     */
    public static void testCourseConstructor() {
        Course course1 = new Course("IFT", 1015, 3);
        Course course2 = new Course("MAT", 1720, "Probabilité", "Espace de probabilité. Analyse combinatoire. Probabilité conditionnelle.", 4);

        assert course1.getSubject().equals("IFT") : "Sujet incorrect pour course1";
        assert course1.getValue() == 1015 : "Valeur incorrecte pour course1";
        assert course1.getName().equals("IFT1015") : "Nom incorrect pour course1";
        assert course1.getDescription().equals("Description vide") : "Description incorrecte pour course1";
        assert course1.getCredit() == 3 : "Crédits incorrects pour course1";

        assert course2.getSubject().equals("MAT") : "Sujet incorrect pour course2";
        assert course2.getValue() == 1720 : "Valeur incorrecte pour course2";
        assert course2.getName().equals("Probabilité") : "Nom incorrect pour course2";
        assert course2.getDescription().equals("Espace de probabilité. Analyse combinatoire. Probabilité conditionnelle.") : "Description incorrecte pour course2";
        assert course2.getCredit() == 4 : "Crédits incorrects pour course2";

        System.out.println("testCourseConstructor réussi.");
    }

    /**
     * Teste l'ajout et la vérification des prérequis pour la classe Course.
     */
    public static void testCoursePrerequisites() {
        Course course1 = new Course("IFT", 1015, 3);
        Course course2 = new Course("MAT", 1348, 4);

        course1.addPrerequisite(course2);

        ArrayList<Course> prerequisites = course1.getPrerequisites();
        assert prerequisites.size() == 1 : "Nombre de prérequis incorrect pour course1";
        assert prerequisites.get(0).equals(course2) : "Prérequis incorrect pour course1";

        try {
            course1.addPrerequisite(course2); // Cette ligne devrait lever une exception
            System.out.println("Erreur : Exception attendue pour l'ajout de prérequis déjà existant.");
        } catch (IllegalArgumentException e) {
            System.out.println("testCoursePrerequisites réussi."); // Exception attendue donc le test est réussi
        }
    }

    /**
     * Teste l'ajout et la gestion des semestres pour la classe Course.
     */
    public static void testCourseSemesters() {
        Course course = new Course("IFT", 1015, 3);
        Semester semester1 = new Semester("A24", LocalDate.of(2024, 9, 1), LocalDate.of(2024, 12, 20));
        Semester semester2 = new Semester("H25", LocalDate.of(2025, 1, 7), LocalDate.of(2025, 4, 30));

        course.addSemester(semester1);
        course.addSemester(semester2);

        ArrayList<Semester> semesters = course.getSemesters();
        assert semesters.size() == 2 : "Nombre de semestres incorrect pour course";
        assert semesters.contains(semester1) : "Semestre 1 manquant pour course";
        assert semesters.contains(semester2) : "Semestre 2 manquant pour course";

        course.removeSemester(semester1);

        semesters = course.getSemesters();
        assert semesters.size() == 1 : "Nombre de semestres incorrect après suppression pour course";
        assert !semesters.contains(semester1) : "Semestre 1 encore présent après suppression pour course";

        ArrayList<Semester> newSemesters = new ArrayList<>();
        newSemesters.add(new Semester("A24", LocalDate.of(2024, 9, 1), LocalDate.of(2024, 12, 20)));
        newSemesters.add(new Semester("H25", LocalDate.of(2025, 1, 7), LocalDate.of(2025, 4, 30)));
        course.addSemesters(newSemesters);

        semesters = course.getSemesters();
        assert semesters.size() == 3 : "Nombre de semestres incorrect après ajout multiple pour course";
        assert semesters.containsAll(newSemesters) : "Semestres ajoutés incorrects pour course";

        System.out.println("testCourseSemesters réussi.");
    }
}
