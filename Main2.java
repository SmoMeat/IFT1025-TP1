import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main2 {
    public static void main(String[] args) {
        // Student s1 = new Student("Jean-Claude", "Van Damme", "01");

        // CourseList courseList = new CourseList();

        // // List<Schedule> schedules = Schedule.genarateSuitableSchedules(courseList.mathInfoCourses, 3, 18, "A24");

        // // for (Schedule schedule : schedules) {
        // //     schedule.printScheduleGrid();
        // // }

        // Schedule.genarateBestSchedule(courseList.mathInfoCourses, 3, 16, "A24").printScheduleGrid();

        DbManager db = new DbManager();

        Course course = new Course("IFT", 1035, "IFT1025", "null", 3);
        Period period = new Period(LocalTime.of(9,30), LocalTime.of(11,30), DayOfWeek.MONDAY, ClassType.TP, "A");


        System.out.println( db.getCourseId(course) );
        System.out.println( db.getPeriodId(period) );

    }
}
