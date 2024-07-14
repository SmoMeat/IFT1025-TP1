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
}
