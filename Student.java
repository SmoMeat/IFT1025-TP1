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
}
