import java.util.ArrayList;

/**
 * @author Tristan Petit et Mathieu Ducharme
 *
 * Cette classe represente un cours qui l'ont peut ajouter à l'horaire des etudiants.
 */
public class Course {
    private String subject; // Matiere (ex: "IFT")
    private int value; // Numero du cours
    private String name; // ex: Calcul 1
    private String description; // ex: Étude des dérivées et intégrales en 3 dimensions
    private int credit; // Nombre de credit
    private ArrayList<Semester> semesters = new ArrayList<>(); // Liste des semestres auxquelles le cours se donne
    private ArrayList<Course> prerequisites = new ArrayList<>(); // Liste des cours préalables


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
     * C'est la fonction qui déterminer l'affichage d'un cours.
     * 
     * @return String - Retourne l'affichage voulu
     */
    @Override
    public String toString() {
        return getAbbreviatedName() + ": " + name + " (" + description + ") " + credit + " crédit avec " + semesters.size() + " semestres enregistrées";
    }

    public void addPrerequisite(Course prerequisite) throws IllegalArgumentException {
        if (prerequisites.contains(prerequisite))
            throw new IllegalArgumentException(getAbbreviatedName() + " a déjà " + prerequisite.getAbbreviatedName() + " comme préalable.");
        prerequisites.add(prerequisite);
    }


/**
 * Cette fonction affiche un cours par son sigle + numéro (ex: IFT1015)
 * 
 * @return String - C'est le sigle + nom du cours
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
     * Cette fonction va chercher le semestre voulu associé au cours parmi la liste des semestres (Attribut semesters)
     * 
     * @param name String C'est le nom du semestre (ex: A24)
     * @return retourne le semestre correspondant s'il existe. Sinon, on retourne null
     */
    public Semester getSemesterByName(String name) {
        for (Semester semester : semesters) {
            if (semester.getName().equals(name))
                return semester;
        }
        return null;
    }

    /**
     * Cette fonction retourne un affichage des touts les semestres du cours
     * 
     * @return String - C'est l'affichage souhaitée
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
     * Cette fonction ajoute un semestre au cours
     * 
     * @param semester Semester C'est le semestre que l'on veut ajouter
     */
    public void addSemester(Semester semester) {
        this.semesters.add(semester);
    }

    /**
     * Cette fonction retire un semestre au cours
     * 
     * @param semester Semester C'est le semestre que l'on veut retirer
     */
    public void removeSemester(Semester semester) {
        this.semesters.remove(semester);
    }

    /**
     * Cette fonction ajoute un semestre au cours
     * 
     * @param semester Semester C'est le semestre que l'on veut retirer
     */
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
