import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * La classe Schedule représente un emploi du temps pour une semestre spécifique.
 * Elle contient un tableau des jours de la semaine avec une liste de périodes d'horaire (SchedulePeriod).
 */
public class Schedule {
    private Map<DayOfWeek, List<SchedulePeriod>> schedule;
    private String semesterName = "A00";

    /**
     * Constructeur par défaut qui initialise un emploi du temps vide pour chaque jour de la semaine.
     */
    public Schedule() {
        schedule = new HashMap<>();
        for (DayOfWeek day : DayOfWeek.values()) {
            schedule.put(day, new ArrayList<>());
        }
    }

    /**
     * Constructeur qui initialise un emploi du temps vide pour chaque jour de la semaine et définit le nom du semestre.
     *
     * @param semesterName le nom du semestre
     */
    public Schedule(String semesterName) {
        schedule = new HashMap<>();
        for (DayOfWeek day : DayOfWeek.values()) {
            schedule.put(day, new ArrayList<>());
        }
        this.semesterName = semesterName;
    }

    /**
     * Constructeur qui crée une copie d'un autre Schedule.
     *
     * @param other l'emploi du temps à copier
     */
    public Schedule(Schedule other) {
        this(other.getSemesterName());
        for (DayOfWeek day : DayOfWeek.values()) {
            for (SchedulePeriod period : other.schedule.get(day)) {
                schedule.get(day).add(period);
            }
        }
    }

    /**
     * Constructeur qui initialise un Schedule à partir d'une liste de cours et d'un nom de semestre.
     *
     * @param courses      la liste des cours à ajouter
     * @param semesterName le nom du semestre
     */
    public Schedule(List<Course> courses, String semesterName) {
        this(semesterName);
        addManyClasses(courses, semesterName);
    }

    /**
     * Ajoute un cours à l'emploi du temps pour une session donnée.
     *
     * @param course      le cours à ajouter
     * @param sessionName le nom de la session
     */
    public void addCourse(Course course, String sessionName) {
        if (course.getSemesterByName(sessionName) == null)
            return;
        
        course.getSemesterByName(sessionName).getPeriods().forEach(period -> {
            schedule.get(period.getDayOfWeek()).add(new SchedulePeriod(course, period));
        });
    }

    public void addManyClasses(List<Course> courses, String sessionName) {
        for (Course course : courses) {
            addCourse(course, sessionName);
        }
    }

    /**
     * Ajoute plusieurs cours à l'emploi du temps pour une session donnée.
     *
     * @param courses     la liste des cours à ajouter
     * @param sessionName le nom de la session
     */
    public void removeClass(Course course, String sessionName) {
        course.getSemesterByName(sessionName).getPeriods().forEach(period -> {
            schedule.get(period.getDayOfWeek()).remove(period);
        });
    }

    /**
     * Récupère les périodes d'horaire pour un jour spécifique.
     *
     * @param day le jour de la semaine
     * @return la liste des périodes d'horaire pour le jour spécifié
     */
    public List<SchedulePeriod> getClassesForSpecificDay(DayOfWeek day) {
        return schedule.get(day);
    }

    /**
     * Trie et retourne les périodes d'horaire pour un jour spécifique par ordre croissant de début.
     *
     * @param day le jour de la semaine
     * @return la liste triée des périodes d'horaire pour le jour spécifié
     */
    public List<SchedulePeriod> orderPeriodsForSpecificDay(DayOfWeek day) {
        List<SchedulePeriod> schedulePeriods = getClassesForSpecificDay(day);
        ArrayList<SchedulePeriod> copie = new ArrayList<>();
        ArrayList<SchedulePeriod> result = new ArrayList<>();

        for (SchedulePeriod schedulePeriod : schedulePeriods) {
            copie.add(schedulePeriod);
        }

        SchedulePeriod earliestPeriod = new SchedulePeriod(
            new Course("IFT", 1015, 3),
            new Period(LocalTime.of(10,30), LocalTime.of(12,30), DayOfWeek.TUESDAY, ClassType.TH, "A")
        );

        while (!copie.isEmpty()) {
            int minimumTime = 1440;
            for (SchedulePeriod period : copie) {
                int currentTime = period.getStart().getHour() * 60 + period.getStart().getMinute();

                if (currentTime <= minimumTime) {
                    minimumTime = currentTime;
                    earliestPeriod = period;
                }
            }

            result.add(earliestPeriod);
            copie.remove(earliestPeriod);
        }
        return result;
    }

    /**
     * Ajoute une période (SchedulePeriod) à l'horaire existant.
     *
     * @param schedulePeriod la période à ajouter à l'horaire
     */
    public void addSchedulePeriod(SchedulePeriod schedulePeriod) {
        schedule.get(schedulePeriod.getDayOfWeek()).add(schedulePeriod);
    }

    /**
     * Retourne le nom du semestre.
     *
     * @return le nom du semestre
     */
    public String getSemesterName() {
        return this.semesterName;
    }

    /**
     * Définit le nom du semestre.
     *
     * @param semesterName le nom du semestre à changer
     */
    public void setSemesterName(String semesterName) {
        this.semesterName = semesterName;
    }

    /**
     * Retourne toutes les périodes de l'horaire.
     *
     * @return une liste de toutes les périodes de l'horaire
     */
    public ArrayList<SchedulePeriod> getSchedulePeriods() {
        ArrayList<SchedulePeriod> schedulePeriods = new ArrayList<>();

        for (DayOfWeek dayOfWeek: DayOfWeek.values()) {
            for (SchedulePeriod schedulePeriod : schedule.get(dayOfWeek)) {
                schedulePeriods.add(schedulePeriod);
            }
        }

        return schedulePeriods;
    }

    /**
     * Retourne tous les cours de l'horaire.
     *
     * @return une liste de tous les cours de l'horaire
     */
    public ArrayList<Course> getCourses() {
        ArrayList<Course> courses = new ArrayList<>();
        
        for (DayOfWeek dayOfWeek : DayOfWeek.values()) {
            for (SchedulePeriod schedulePeriod : schedule.get(dayOfWeek)) {
                if (!courses.contains(schedulePeriod.getCourse()))
                    courses.add(schedulePeriod.getCourse());
            }
        }

        return courses;
    }

    /**
     * Retourne tous les examens des cours de l'horaire pour le semestre courant.
     *
     * @return une liste de tous les examens
     */
    public ArrayList<Exam> getExams() {
        ArrayList<Exam> exams = new ArrayList<>();
        getCourses().forEach(course -> exams.addAll(course.getSemesterByName(semesterName).getExams()));
        return exams;
    }

    /**
     * Vérifie s'il y a un conflit entre les examens.
     *
     * @return true s'il y a un conflit entre les examens, false sinon
     */
    public boolean hasExamsConflict() {
        for (Exam exam1 : getExams()) {
            for (Exam exam2 : getExams()) {
                if (!exam1.getDate().equals(exam2.getDate()))
                    continue;

                if (exam1.inConflict(exam2))
                    return true;
            }
        }
        return false;
    }

    /**
     * Calcule le score basé sur le temps entre les examens.
     * Équivaut au nombre d'heures total entre tous les examens.
     *
     * @return le score basé sur le temps entre les examens
     */
    public double getTimeBetweenExamsScore() {
        double value = 0;
        for (Exam exam1 : getExams()) {
            for (Exam exam2 : getExams()) {
                double timeBetween = exam1.timeBetweenV2(exam2);

                if (timeBetween >= 0 )
                    value += timeBetween;
            }
        }
        return value;
    }

    /**
     * Vérifie s'il y a un conflit dans l'horaire pour un jour donné.
     *
     * @param day le jour à vérifier.
     * @return true s'il y a un conflit, false sinon.
     */
    public boolean hasConflictOnSpecificDay(DayOfWeek day) {
        List<SchedulePeriod> periods = schedule.get(day);
        for (int i = 0; i < periods.size(); i++) {
            for (int j = i + 1; j < periods.size(); j++) {
                if (periods.get(i).inConflict(periods.get(j))) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Vérifie s'il y a un conflit dans l'horaire.
     *
     * @return true s'il y a un conflit, false sinon.
     */
    public boolean hasConflict() {
        for (DayOfWeek day : DayOfWeek.values()) {
            if (this.hasConflictOnSpecificDay(day)) {
                return true;
            }

        }
        return false;
    }

    /**
     * Génère toutes les combinaisons possibles d'horaires basées sur les cours
     * donnés pour une certaine session.
     *
     * @param availableCourses les cours disponibles
     * @param sessionName      le nom de la session
     * @return une liste de toutes les combinaisons possibles d'horaires
     */
    public static List<Schedule> generateAllPossibleSchedules(ArrayList<Course> availableCourses, String sessionName) {
        int length = availableCourses.size() > 10 ? 10 : availableCourses.size(); // maximum 10 cours dans un horaire
        List<Schedule> result = new ArrayList<>();
        for (int n = 1; n <= length; n++) {
            generateScheduleHelper(availableCourses, n, 0, new ArrayList<>(Collections.nCopies(n, null)),
                    result, sessionName);
        }
        return result;
    }

    /**
     * Fonction d'aide récursive pour générer les combinaisons d'horaires.
     *
     * @param list        les cours disponibles pour générer l'horaire
     * @param n           le nombre de cours à ajouter
     * @param start       l'index actuel de la liste de cours admissibles
     * @param selections  les cours actuellement sélectionnés
     * @param result      la liste de toutes les horaires générées jusqu'à présent
     * @param sessionName le nom de la session
     */
    private static void generateScheduleHelper(List<Course> list, int n, int start, List<Course> selections,
                                               List<Schedule> result, String sessionName) {
        if (n == 0) {
            result.add(new Schedule(selections, sessionName));
        } else {
            for (int i = start; i <= list.size() - n; i++) {
                selections.set(selections.size() - n, list.get(i));
                generateScheduleHelper(list, n - 1, i + 1, selections, result, sessionName);
            }
        }
    }

    /**
     * Génère une liste d'horaires possibles respectant un certain nombre de crédits
     * minimum et maximum et sans conflits d'horaire.
     *
     * @param availableCourses les cours disponibles
     * @param minCredits       le nombre minimum de crédits
     * @param maxCredits       le nombre maximum de crédits
     * @param sessionName      le nom de la session
     * @return une liste d'horaires possibles respectant les contraintes
     */
    public static List<Schedule> genarateSuitableSchedules(ArrayList<Course> availableCourses, int minCredits, int maxCredits, String sessionName) {
        ArrayList<Schedule> results = new ArrayList<>();
        for (Schedule schedule : generateAllPossibleSchedules(availableCourses, sessionName)) {
            if (schedule.getTotalCredits() < minCredits || schedule.getTotalCredits() > maxCredits)
                continue;
            if (schedule.hasConflict())
                continue;


            results.add(schedule);
        }
        return results;
    }

    /**
     * Génère le meilleur horaires possible respectant un certain nombre de crédits
     * minimum et maximum et sans conflits d'horaire.
     *
     * @param availableCourses les cours disponibles
     * @param creditMin        le nombre minimum de crédits
     * @param creditMax        le nombre maximum de crédits
     * @param sessionName      le nom de la session
     * @return le meilleur horaire possible
     */
    public static Schedule genarateBestSchedule(ArrayList<Course> availableCourses, int creditMin, int creditMax, String sessionName) {
        Schedule bestSchedule = new Schedule();
        double bestScore = 0;
        for (Schedule schedule : generateAllPossibleSchedules(availableCourses, sessionName)) {
            if (schedule.getTotalCredits() < creditMin || schedule.getTotalCredits() > creditMax)
                continue;

            double currentScore = schedule.getScheduleScore();

            if (currentScore > bestScore) {
                bestSchedule = schedule;
                bestScore = currentScore;
            }            
        }
        return bestSchedule;
    }

    /**
     * Calcule le score de l'horaire en fonction de plusieurs critères.
     *
     * @return le score de l'horaire
     */
    public double getScheduleScore() {
        double point = 0;
        double time = 0;

        for (DayOfWeek day : DayOfWeek.values()) {
            List<SchedulePeriod> schedule = this.schedule.get(day);

            // On attribut 5 points s'il n'y a pas de conflits dans la journee
            if (!this.hasConflictOnSpecificDay(day)) {
                point += 5;
            }

            // Si on a plus au moins 1h entre deux cours, on ajoute 2 points
            List<SchedulePeriod> orderSchedule = this.orderPeriodsForSpecificDay(day);
            for (int i = 0; i < orderSchedule.size() - 1; i++) {
                if (orderSchedule.get(i).timeBetweenV2(orderSchedule.get(i + 1)) >= 1) {
                    point += 1;
                }
            }


            for (SchedulePeriod schedulePeriod : schedule) {
                time += schedulePeriod.timeOf();
            }
            /* Si dans la journee, on compte plus de 10 heures de cours, on n'attribut aucun point.
            Sinon, on attribut
            1 point pour 10h
            2 points pour 9h
            ...
            5 points pour 6h
            6 points pour 5h a 1h
            10 points pour 0h (journee de conge)
             */
            if (time > 10) {
                continue;
            }
            if (time > 5) {
                point += 11 - time;
                continue;
            }
            if (time == 0) {
                point += 10;
            }

            point += 6;
        }

        return point + getTimeBetweenExamsScore() * 0.05;
    }

    /**
     * Retourne une représentation en chaîne de caractères de l'horaire.
     *
     * @return une chaîne de caractères représentant l'horaire
     */
    @Override
    public String toString() {
        String str = "";
        for (DayOfWeek day : DayOfWeek.values()) {
            str += day + ":\n";
            for (Period classDetails : schedule.get(day)) {
                str += "\t" + classDetails + "\n";
            }
        }
        return str;
    }

    /**
     * Affiche une grille de l'horaire dans la console.
     */
    public void printScheduleGrid() {
        if (this.schedule != null) {
            LocalTime startHour = LocalTime.of(8, 0);
            LocalTime endHour = LocalTime.of(20, 0);
            int rows = (endHour.getHour() - startHour.getHour()) * 2;
            String[][] grid = new String[rows + 1][7];

            for (int i = 0; i < rows; i++) {
                Arrays.fill(grid[i], " ");
            }

            for (DayOfWeek day : DayOfWeek.values()) {
                for (SchedulePeriod period : schedule.get(day)) {
                    int dayIndex = day.getValue() - 1;
                    int startSlot = (period.getStart().getHour() - startHour.getHour()) * 2 +
                            (period.getStart().getMinute() / 30);
                    int endSlot = (period.getEnd().getHour() - startHour.getHour()) * 2 +
                            (period.getEnd().getMinute() / 30);
                    for (int i = startSlot; i < endSlot; i++) {
                        grid[i][dayIndex] = period.getCourse().getAbbreviatedName();
                    }
                }
            }

            System.out.println("╔═══════╦═════════╦═════════╦═════════╦═════════╦═════════╦═════════╦═════════╗");
            System.out.println("║ " + String.format("%02d", getTotalCredits()) + " cr ║   Mon   ║   Tue   ║   Wed   ║   " +
                    "Thu   ║   Fri   ║   Sat   ║   Sun   ║");
            System.out.println("╠═══════╬═════════╬═════════╬═════════╬═════════╬═════════╬═════════╬═════════╣");
            for (int i = 0; i < rows; i++) {
                String time = String.format("║ %02d:%02d", startHour.getHour() + (i / 2), (i % 2) * 30);
                System.out.print(time + " ║ ");
                for (int j = 0; j < 7; j++) {
                    System.out.print(grid[i][j].length() > 1 ? grid[i][j] + " ║ " : "        ║ ");
                }
                System.out.println();
            }

            System.out.println("╠═══════╩═════════╩═════════╩═════════╩═════════╩═════════╩═════════╩═════════╣");

            for (Exam exam : getExams()) {
                String examString = exam.toString();

                if (examString.length() > 75)
                    examString = examString.substring(0, 75);
                if (examString.length() < 75)
                    examString = String.format("%-75s", examString);

                System.out.println("║ " + examString + " ║");
            }

            System.out.println("╚═════════════════════════════════════════════════════════════════════════════╝");
        }
    }

    /**
     * Calcule le nombre total de crédits de l'horaire.
     *
     * @return le nombre total de crédits
     */
    public int getTotalCredits() {
        int totalCredits = 0;

        for (Course course : getCourses()) {
            totalCredits += course.getCredit();
        }

        return totalCredits;
    }
}
