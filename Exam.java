import java.time.LocalTime;
import java.util.Date;

public class Exam {
    private Date date = new Date();
    private double time;
    private String type;

    public Exam(Date date, double time, String type) {
        this.date = date;
        this.time = time;
        this.type = type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setTime(double time) {
        this.time = time;
    }

    public Date getDate() {
        return this.date;
    }

    public String getType() {
        return this.type;
    }

    public double getTime() {
        return this.time;
    }

    @Override
    public String toString() {
        String result = "";

        int HStart = (int) this.date.getHours() + (int) this.time;
        LocalTime timeEnd = LocalTime.of(HStart, this.date.getMinutes());
        LocalTime timeStart = LocalTime.of(this.date.getHours(), this.date.getMinutes());

        result += "Examen " + type + " - " + Cours.datePrint(this.date) + " de ";
        result += timeStart.toString() + " à " + timeEnd.toString();
        return result;
    }
}
