import java.time.LocalDate;
import java.time.LocalTime;

/**
 * @author Tristan Petit et Mathieu Ducharme
 * 
 * Cette classe correspond aux examens des différents semestres associés aux cours. elle hérite
 * de la classe "Period".
 */
public class Exam extends Period {
    private LocalDate date; // Date de l'examen

    /**
     * C'est le constructeur de la classe
     * 
     * @param date Date de l'examen
     * @param start heure du début de l'examen
     * @param end Heure de fin de l'examen
     * @param type Type de l'examen (INTRA, FINAL, QUIZ)
     * @param section Section de l'examen (A,B,...)
     */
    public Exam(LocalDate date, LocalTime start, LocalTime end, ClassType type, String section) {
        super(start, end, date.getDayOfWeek(), type, section);
        this.date = date;
    }

    //Getter et setter
    public LocalDate getDate() {
        return this.date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
