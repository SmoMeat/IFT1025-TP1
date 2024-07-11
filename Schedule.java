import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Schedule {
    private class SchedulePeriod extends Period {
        public Course course;

        SchedulePeriod(Course course, Period period) {
            super(period);
            this.course = course;
        }
    }

    private Map<DayOfWeek, List<SchedulePeriod>> schedule;
    private int totalCredits;

    public Schedule() {
        schedule = new HashMap<>();
        for (DayOfWeek day : DayOfWeek.values()) {
            schedule.put(day, new ArrayList<>());
        }
    }

    public Schedule(Schedule other) {
        this();
        for (DayOfWeek day : DayOfWeek.values()) {
            for (SchedulePeriod period : other.schedule.get(day)) {
                schedule.get(day).add(period);
            }
        }
        this.totalCredits = other.totalCredits;
    }

    public Schedule(List<Course> courses, String sessionName) {
        this();
        addManyClasses(courses, sessionName);
    }

    public void addCourse(Course course, String sessionName) {
        // TODO: vérifier conflit d'horaire
        if (course.getSemesterByName(sessionName) == null) {
            return;
        }
        course.getSemesterByName(sessionName).getPeriods().forEach(period -> {
            schedule.get(period.getDayOfWeek()).add(new SchedulePeriod(course, period));
        });
        totalCredits += course.getCredit();
    }

    public void addManyClasses(List<Course> courses, String sessionName) {
        for (Course course : courses) {
            addCourse(course, sessionName);
        }
    }

    public void removeClass(Course course, String sessionName) {
        course.getSemesterByName(sessionName).getPeriods().forEach(period -> {
            schedule.get(period.getDayOfWeek()).remove(period);
        });
        totalCredits -= course.getCredit();
    }

    public List<SchedulePeriod> getClassesForSpecifDay(DayOfWeek day) {
        return schedule.get(day);
    }

    public List<SchedulePeriod> order(DayOfWeek day) {
        List<SchedulePeriod> Speriod = this.schedule.get(day);
        ArrayList<SchedulePeriod> copie = new ArrayList<>();
        ArrayList<SchedulePeriod> result = new ArrayList<>();

        for (SchedulePeriod t : Speriod) {
            copie.add(t);
        }

        SchedulePeriod periodMin = new SchedulePeriod(new Course("IFT", 1015, 3),
                new Period(LocalTime.of(10,30), LocalTime.of(12,30), DayOfWeek.TUESDAY, ClassType.TH, "A"));

        while (!copie.isEmpty()) {
            int Mintime = 1000000;
            for (SchedulePeriod period : copie) {
                int currentTime = 0;
                currentTime = period.getStart().getHour() * 60 + period.getStart().getMinute();

                if (currentTime < Mintime) {
                    Mintime = currentTime;
                    periodMin = period;
                }
            }

            result.add(periodMin);
            copie.remove(periodMin);
        }
        return result;
    }

    public boolean hasConflictDay(DayOfWeek day) {
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

    public boolean hasConflict() {
        for (DayOfWeek day : DayOfWeek.values()) {
            if (this.hasConflictDay(day)) {
                return true;
            }
        }
        return false;
    }

    public static List<Schedule> generateAllPossibleSchedules(ArrayList<Course> availableCourses, String sessionName) {
        int length = availableCourses.size() > 10 ? 10 : availableCourses.size(); // maximum 10 cours dans un horaire
        List<Schedule> result = new ArrayList<>();
        for (int n = 1; n <= length; n++) {
            generateScheduleHelper(availableCourses, n, 0, new ArrayList<>(Collections.nCopies(n, null)),
                    result, sessionName);
        }
        return result;
    }

    // n = nbr de cours à ajouter
    // start: index actuel de la liste
    // selections: les cours actuellement séléctionnés
    // result: liste de toutes les horaires
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

    public static List<Schedule> genarateSuitableSchedules(ArrayList<Course> availableCourses, int minCredits, int
            maxCredits, String sessionName) {
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


    public static Schedule genarateBestSchedule(ArrayList<Course> availableCourses, int creditMin,
                                                int creditMax, String sessionName) {
        ArrayList<Schedule> results = new ArrayList<>();
        Schedule bestSchedule = new Schedule();
        int bestScore = 0;
        for (Schedule schedule : generateAllPossibleSchedules(availableCourses, sessionName)) {
            if (schedule.getTotalCredits() < creditMin || schedule.getTotalCredits() > creditMax)
                continue;

            int currentScore = schedule.getExamScore();

            if (currentScore > bestScore) {
                bestSchedule = schedule;
                bestScore = currentScore;
            }            
        }
        return bestSchedule;
    }

    public int getExamScore() {
        int point = 0;
        int time = 0;

        for (DayOfWeek day : DayOfWeek.values()) {
            List<SchedulePeriod> schedule = this.schedule.get(day);

            // On attribut 5 points s'il n'y a pas de conflits dans la journee
            if (!this.hasConflictDay(day)) {
                point += 5;
            }

            // Si on a plus au moins 1h entre deux cours, on ajoute 2 points
            List<SchedulePeriod> orderSchedule = this.order(day);
            for (int i = 0; i < orderSchedule.size() - 1; i++) {
                if (orderSchedule.get(i).timeBetweenV2(orderSchedule.get(i + 1)) >= 1) {
                    point += 1;
                }
            }


            for (SchedulePeriod Speriod : schedule) {
                time += Speriod.timeOf();
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
        return point;
    }

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

    public void printScheduleGrid() {
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
                    grid[i][dayIndex] = period.course.getAbbreviatedName();
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
        System.out.println("╚═══════╩═════════╩═════════╩═════════╩═════════╩═════════╩═════════╩═════════╝");
    }

    public int getTotalCredits() {
        return this.totalCredits;
    }
}
