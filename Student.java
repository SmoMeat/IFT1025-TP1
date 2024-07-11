import java.util.ArrayList;
import java.util.List;

public class Student {
    private String firstname;
    private String lastname;
    private String matricule;
    private Schedule schedule = new Schedule();

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


    public void addCourse(Course course, String sessionName) {
        schedule.addCourse(course, sessionName);
    }

    public void generateBestSchedule(ArrayList<Course> availableCourses, String semesterName) {
        for (Course course : availableCourses) {
            availableCourses.remove(course);
        }
        List<Schedule> schedules =  Schedule.genarateSuitableSchedules(availableCourses, 14, 16, semesterName);
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

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

    @Override
    public String toString() {
        return firstname + " " +  lastname + " (" + matricule + ")";
    }
}