import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main2 {
    public static void main(String[] args) {
        Student s1 = new Student("Jean-Claude", "Van Damme", "01");

        CourseList courseList = new CourseList();

        List<Schedule> schedules = Schedule.genarateSuitableSchedules(courseList.mathInfoCourses, 13, 18, "A24");

        for (Schedule schedule : schedules) {
            schedule.printScheduleGrid();
        }

        Schedule.genarateBestSchedule(courseList.mathInfoCourses,"A24", 14,16).printScheduleGrid();



    }
}
