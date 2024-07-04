import java.time.LocalDate;
import java.util.ArrayList;

public class Semester {
    private String name; // ex: A24, H25
    private LocalDate start;
    private LocalDate end;
    private ArrayList<Period> periods;

    /**
     * Construit une nouvelle session universitaire.
     *
     * @param name     l'identifiant de la session, par exemple A24, H25
     * @param start    la date de début de la session
     * @param end      la date de fin de la session
     * @param periods  la liste des périodes dans la session
     */
    public Semester(String name, LocalDate start, LocalDate end, ArrayList<Period> periods) {
        this.name = name;
        this.start = start;
        this.end = end;
        this.periods = periods;
    }

    /**
     * Obtient l'identifiant de la session.
     *
     * @return l'identifiant de la session
     */
    public String getName() {
        return name;
    }

    /**
     * Définit l'identifiant de la session.
     *
     * @param semester l'identifiant de la session à définir
     */
    public void setName(String semester) {
        this.name = semester;
    }

    /**
     * Obtient la date de début de la session.
     *
     * @return la date de début de la session
     */
    public LocalDate getStart() {
        return start;
    }

    /**
     * Définit la date de début de la session.
     *
     * @param start la date de début à définir
     */
    public void setStart(LocalDate start) {
        this.start = start;
    }

    /**
     * Obtient la date de fin de la session.
     *
     * @return la date de fin de la session
     */
    public LocalDate getEnd() {
        return end;
    }

    /**
     * Définit la date de fin de la session.
     *
     * @param end la date de fin à définir
     */
    public void setEnd(LocalDate end) {
        this.end = end;
    }

    /**
     * Obtient la liste des périodes dans la session.
     *
     * @return la liste des périodes
     */
    public ArrayList<Period> getPeriods() {
        return periods;
    }

    /**
     * Ajoute à la liste des périodes dans la session une nouvelle période
     *
     * @param period la période à ajouter
     */
    public void addPeriods(Period period) {
        this.periods.add(period);
    }

    /**
     * Retire à la liste des périodes dans la session une nouvelle période
     *
     * @param periods la période à enlever
     */
    public void removePeriods(Period period) {
        this.periods.remove(period);
    }
}
