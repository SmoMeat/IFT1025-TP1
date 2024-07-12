import java.time.DayOfWeek;
import java.time.LocalTime;

/**
 * @author Tristan Petit et Mathieu Ducharme
 * 
 * Cette classe correspond aux périodes de cours qui seront des attributs des semestres pour les cours 
 */
public class Period {
    private LocalTime start; // Heure de début du cours
    private LocalTime end; // Heure de fin du cours
    private DayOfWeek dayOfWeek; // Jours de la semaine du cours
    private ClassType type; // Le type du cours (Th, Tp ou Lab)
    private String section; // La section du cours (A, B,...)

    /**
     * C'est le constructeur de la classe Period
     * 
     * @param start Heure de début du cours
     * @param end Heure de fin du cours
     * @param dayOfWeek Jours de la semaine du cours
     * @param type Le type du cours (Th, Tp ou Lab)
     * @param section La section du cours (A, B,...)
     */
    public Period(LocalTime start, LocalTime end, DayOfWeek dayOfWeek, ClassType type, String section) {
        this.start = start;
        this.end = end;
        this.dayOfWeek = dayOfWeek; //dayOfWeek % 7;
        this.type = type;
        this.section = section;
    }

    // Constructeur avec une periode existante
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

    public void setSection(String section) {
        this.section = section;
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

    public String getSection() {
        return this.section;
    }

    /** 
     * C'est l'affichage souhaitée lors d'un print (ex:TH: Mardi de 11:30 à 13:30)
     * 
     * @return String - L'affichage souhaitée
     * */ 
    @Override
    public String toString() {
        String result = "";
        String jours = "Dimanche";
        result += this.type + ": ";

        // On attribut les noms des jours de la semaine selon la valeur de l'attribut dayOfWeek
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
     * Cette fonction calcul le temps entre 2 scéances de cours en nombre d'heures relativement
     * a l'instance actuel. Par exemple, si le cours actuel finit à 13h et celui en parametre
     * finit 14h ça retourne 1.0. Si le cours actuel finit à 13h et le second fint à 12h ça
     * retourne -1.
     *
     * @param autre Schedule - C'est la scéance avec laquelle on compare
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

    /**
     * Cette fonction est la même que le précédente, mais elle calcul le temps entre la fin de la periode de
     * l'instance actuel et le début de la periode de "autre". (À la place du temps entre les 2 fins de cours)
     * 
     * @param autre Schedule - C'est la scéance avec laquelle on compare
     * @return double - On renvoit le nombre d'heure entre les sceances
     */
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
     * Cette fonction donne la durée de la période actuelle en heure
     *
     * @return double - Le temps de la période en heure
     */
    public double timeOf() {
        double HEnd = this.end.getHour();
        double MEnd = this.end.getMinute() / 60;
        double HStart = this.start.getHour();
        double MStart = this.start.getMinute() / 60;

        return (HEnd + MEnd) - (HStart + MStart);
    }

    /**
     * Cette fonction verifie si 2 scéances sont en conflit d'horaire
     *
     * @param autre Schedule - L'autre scéance avec laquelle on compare
     *
     * @return boolean - On retourne un booleen indiquant s'il y a un conflit
     */
    public boolean inConflict(Period autre) {
        // Si on finit en même temps, il y a forcément un conflit
        if (this.timeBetween(autre) == 0)
            return true;

        // Si il le temps entre les fins de période est plus grand que le maximum des temps des périodes, alors
        // il n'y a pas de conflit 
        if (Math.abs(this.timeBetween(autre)) >= Math.max(this.timeOf(), autre.timeOf())) {
            return false;
        }
        // Si c'est négatif, la période actuel finit plus tard
        if (this.timeBetween(autre) < 0) {
            // On vérifie si la période débute avant que l'autre finit
            if ((this.start.getHour() + this.start.getMinute() / 60) < (autre.end.getHour() +
                    autre.end.getMinute() / 60)) {
                return true;
            } else {
                return false;
            }
        } else {
            // même chose mais avec la période actuel qui se termine en premier
            if ((autre.start.getHour() + autre.start.getMinute() / 60) < (this.end.getHour() +
                    this.end.getMinute() / 60)) {
                return true;
            } else {
                return false;
            }
        }
    }    
}

