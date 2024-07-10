import java.time.LocalDate;
import java.time.LocalTime;

public class Exam extends Period {
    private LocalDate date;

    public Exam(LocalDate date, LocalTime start, LocalTime end, ClassType type, String section) {
        super(start, end, date.getDayOfWeek(), type, section);
        this.date = date;
    }

    public LocalDate getDate() {
        return this.date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Examen " + getType() + " le " + getDate() + " de " + getStart() + " à " + getEnd();
    }
}
