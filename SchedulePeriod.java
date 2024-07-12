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
}