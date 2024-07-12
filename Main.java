import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        //Cours PremierCours = new Cours("IFT",1025, 3);
        //System.out.println(new Date(2024 - 1900, 11, 5));
        //System.out.println(LocalTime.of(13,30));
        //System.out.println(ClassType.TP + "");
        //PremierCours.addDate();
        //PremierCours.addSchedule();
        //PremierCours.addExams();
        //System.out.println(PremierCours);

        //Date date = new Date(124,8,9);
        //System.out.println(date);

        //Exam exam = new Exam(new Date(124, 5,5, 13,30), 3, "Intra");
        //System.out.println(exam);

        //LocalTime a = LocalTime.of(13,30);
       // LocalTime b = LocalTime.of(12,30);

       // System.out.println(a);

        //Schedule a1 = new Schedule(a,LocalTime.of(16,30), 1, ClassType.LAB);
        //Schedule b1 = new Schedule(b, LocalTime.of(14,00), 1,ClassType.TH);

        //System.out.println(a1.timeBetween(b1));

        //System.out.println(a1.inConflict(b1));
        CourseList courseList = new CourseList();

        List<Schedule> schedules = Schedule.genarateSuitableSchedules(courseList.mathInfoCourses, 13, 18, "A24");

        Schedule schedule = schedules.get(0);

        schedule.printScheduleGrid();

        System.out.println(schedule.orderPeriodsForSpecificDay(DayOfWeek.FRIDAY));
    }
}
