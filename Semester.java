import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Semester {
    private String name; // ex: A24, H25
    private LocalDate start;
    private LocalDate end;
    private ArrayList<Period> periods = new ArrayList<>();
    private ArrayList<Exam> exams = new ArrayList<>();

    /**
     * Construit une nouvelle session universitaire.
     *
     * @param name     l'identifiant de la session, par exemple A24, H25
     * @param start    la date de début de la session
     * @param end      la date de fin de la session
     * @param periods  la liste des périodes dans la session
     * @param exams    la liste des examens dans la session
     */
    public Semester(String name, LocalDate start, LocalDate end, ArrayList<Period> periods, ArrayList<Exam> exams) {
        this.name = name;
        this.start = start;
        this.end = end;
        this.periods = periods;
        this.exams = exams;
    }

    /**
     * Construit une nouvelle session universitaire sans examens et périodes.
     *
     * @param name     l'identifiant de la session, par exemple A24, H25
     * @param start    la date de début de la session
     * @param end      la date de fin de la session
     * @param periods  la liste des périodes dans la session
     */
    public Semester(String name, LocalDate start, LocalDate end) {
        this.name = name;
        this.start = start;
        this.end = end;
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
    public void addPeriod(Period period) {
        this.periods.add(period);
    }

    /**
     * Ajoute à la liste des périodes dans la session des nouvelles périodes
     *
     * @param periods la liste de périodes à ajouter
     */
    public void addPeriods(ArrayList<Period> periods) {
        this.periods.addAll(periods);
    }

    /**
     * Retire à la liste des périodes dans la session une période
     *
     * @param period la période à enlever
     */
    public void removePeriod(Period period) {
        this.periods.remove(period);
    }

    /**
     * Retire à la liste des périodes dans la session plusieurs périodes
     *
     * @param periods la liste de périodes à enlever
     */
    public void removePeriods(ArrayList<Period>  periods) {
        this.periods.removeAll(periods);
    }

    /**
     * Obtient la liste des périodes dans la session.
     *
     * @return la liste des périodes
     */
    public ArrayList<Exam> getExams() {
        return exams;
    }

    /**
     * Ajoute à la liste des examens dans la session un nouvel examen
     *
     * @param exam l'examen à ajouter
     */
    public void addExam(Exam exam) {
        this.exams.add(exam);
    }

    /**
     * Ajoute à la liste des examens dans la session plusieurs nouveaux examens
     *
     * @param exams la liste d'examens à ajouter
     */
    public void addExams(ArrayList<Exam> exams) {
        this.exams.addAll(exams);
    }

    /**
     * Retire à la liste des examens dans la session un examen
     *
     * @param exam l'examen à enlever
     */
    public void removeExam(Exam exam) {
        this.exams.remove(exam);
    }

    /**
     * Retire à la liste des examens dans la session plusieurs examens
     *
     * @param exams la liste d'examens à enlever
     */
    public void removeExams(ArrayList<Exam> exams) {
        this.exams.removeAll(exams);
    }

    public String getPeriodsAsString() {
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < periods.size(); i++) {
            stringBuilder.append(periods.get(i));
            if (i < periods.size() - 1) {
                stringBuilder.append(", ");
            }
        }

        return stringBuilder.toString();
    }

    public String getExamsAsString() {
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < exams.size(); i++) {
            stringBuilder.append(exams.get(i));
            if (i < exams.size() - 1) {
                stringBuilder.append(", ");
            }
        }

        return stringBuilder.toString();
    }

    public String toString() {
        return name + " de " + start + " à " + end + " avec les périodes " + getPeriodsAsString() + " et les examens " + getExamsAsString();
        // return name + " de " + start + " à " + end + " avec " + periods.size() + " périodes et " + exams.size() + " examens";
    }
}
