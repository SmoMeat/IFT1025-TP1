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

    @Override
    public String toString() {
        return getAbbreviatedName() + ": " + name + " (" + description + ") " + credit + " crédit avec " + semesters.size() + " semestres enregistrées";
    }

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
