import java.time.DayOfWeek;
import java.time.LocalTime;

public class Period {
    private LocalTime start;
    private LocalTime end;
    private DayOfWeek dayOfWeek;
    private ClassType type; // Le type du cours (Th, Tp ou Lab)
    private String section;

    public Period(LocalTime start, LocalTime end, DayOfWeek dayOfWeek, ClassType type, String section) {
        this.start = start;
        this.end = end;
        this.dayOfWeek = dayOfWeek; //dayOfWeek % 7;
        this.type = type;
        this.section = section;
    }

    public Period(Period other) {
        this.start = other.getStart();
        this.end = other.getEnd();
        this.dayOfWeek = other.getDayOfWeek();
        this.type = other.getType();
        this.section = other.getSection();
    }

    // Getters et Setters des differents attributs
    public void setStart(LocalTime start) {
        this.start = start;
    }

    public void setEnd(LocalTime end) {
        this.end = end;
    }

    public void setDayOfWeek(int dayOfWeek) {
        this.dayOfWeek = DayOfWeek.of(dayOfWeek % 7); // modulo 7 to handle error
    }

    public void setType(ClassType type) {
        this.type = type;
    }

    public LocalTime getStart() {
        return this.start;
    }

    public LocalTime getEnd() {
        return this.end;
    }

    public DayOfWeek getDayOfWeek() {
        return this.dayOfWeek;
    }

    public ClassType getType() {
        return this.type;
    }

    @Override
    public String toString() {
        String result = "";
        String jours = "Dimanche";
        result += this.type + ": ";

        switch (this.dayOfWeek) {
            case DayOfWeek.MONDAY:
                jours = "Lundi";
                break;
            case DayOfWeek.TUESDAY:
                jours = "Mardi";
                break;
            case DayOfWeek.WEDNESDAY:
                jours = "Mercredi";
                break;
            case DayOfWeek.THURSDAY:
                jours = "Jeudi";
                break;
            case DayOfWeek.FRIDAY:
                jours = "Vendredi";
                break;
            case DayOfWeek.SATURDAY:
                jours = "Samedi";
                break;
        }
        result += jours + " de " + this.start.toString() + " à " + this.end.toString();
        return result;
    }

    /**
     * Cette fonction calcul le temps entre 2 sceance de cours en nombre d'heures relativement
     * a l'instance actuel. Par exemple, si le cours actuel finit à 13h et celui en parametre
     * finit 14h ça retourne 1.0. Si lec cours actuel finit à 13h et le second fint à 12h ça
     * retourne -1.
     *
     * @param autre Schedule - C'est la sceance avec laquelle on compare
     * @return double - On renvoit le nombre d'heure entre les sceances
     */
    public double timeBetween(Period autre) {
        // On calcule d'abord l'ecart d'heure selon le jour
        double result = (autre.getDayOfWeek().getValue() - this.dayOfWeek.getValue()) * 24;

        //On prend en compte les heures et minute
        double hourThis = this.end.getHour();
        double minuteThis = this.end.getMinute() / 60;

        double hourTotEnd = hourThis + minuteThis;

        double hourAutre = autre.end.getHour();
        double minuteAutre = autre.end.getMinute() / 60;

        double hourTotStart = hourAutre + minuteAutre;

        result += (hourTotStart - hourTotEnd);

        return result;
    }

    // Fin vs debut
    public double timeBetweenV2(Period autre) {
        // On calcule d'abord l'ecart d'heure selon le jour
        double result = (autre.getDayOfWeek().getValue() - this.dayOfWeek.getValue()) * 24;

        //On prend en compte les heures et minute
        double hourThis = this.end.getHour();
        double minuteThis = this.end.getMinute() / 60;

        double hourTotEnd = hourThis + minuteThis;

        double hourAutre = autre.start.getHour();
        double minuteAutre = autre.start.getMinute() / 60;

        double hourTotStart = hourAutre + minuteAutre;

        result += (hourTotStart - hourTotEnd);

        return result;
    }
    

    /**
     * Cette fonction
     *
     * @return
     */
    public double timeOf() {
        double HEnd = this.end.getHour();
        double MEnd = this.end.getMinute() / 60;
        double HStart = this.start.getHour();
        double MStart = this.start.getMinute() / 60;

        return (HEnd + MEnd) - (HStart + MStart);
    }

    /**
     * Cette fonction verifie si 2 sceances sont en conflit d'horaire
     *
     * @param autre Schedule - L'autre sceance avec laquelle on compare
     *
     * @return boolean - On retourne un booleen indiquant s'il y a un conflit
     */
    public boolean inConflict(Period autre) {
        if (this.timeBetween(autre) == 0)
            return true;
        if (Math.abs(this.timeBetween(autre)) >= Math.max(this.timeOf(), autre.timeOf())) {
            return false;
        }
        if (this.timeBetween(autre) < 0) {
            if ((this.start.getHour() + this.start.getMinute() / 60) < (autre.end.getHour() +
                    autre.end.getMinute() / 60)) {
                return true;
            } else {
                return false;
            }
        } else {
            if ((autre.start.getHour() + autre.start.getMinute() / 60) < (this.end.getHour() +
                    this.end.getMinute() / 60)) {
                return true;
            } else {
                return false;
            }
        }
    }

    public String getSection() {
        return this.section;
    }

    public void setSection(String section) {
        this.section = section;
    }
    
}
