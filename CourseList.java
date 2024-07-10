import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;

public class CourseList {
    LocalDate start = LocalDate.of(2024,9,3);
    LocalDate end = LocalDate.of(2024,12,6);

    private ArrayList<Course> courses;

    
    
    public  Course IFT1015A = new Course("IFT", 1015, 3);
    public  Course IFT2015 = new Course("IFT", 2015, 3);
    public  Course IFT2125 = new Course("IFT", 2125, 3);

    


    public  ArrayList<Course> mathInfoCourses = new ArrayList<>(Arrays.asList(IFT1015A, IFT2015, IFT2125  ));

    public static void main(String[] args) {
        DbManager dbManager = new DbManager();
        CourseList courseList = new CourseList();
        dbManager.addAllCourses(courseList.mathInfoCourses);
    }

    public CourseList() {
        IFT1015A.addSemester(new Semester("A24", start, end, new ArrayList<Period>(Arrays.asList(
            new Period(LocalTime.of(15,30), LocalTime.of(16,30), DayOfWeek.FRIDAY, ClassType.TH, "A"),
            new Period(LocalTime.of(12,30), LocalTime.of(14,30), DayOfWeek.WEDNESDAY, ClassType.TH, "A"),
            new Period(LocalTime.of(16,30), LocalTime.of(17,30), DayOfWeek.FRIDAY, ClassType.TP, "A101"),
            new Period(LocalTime.of(17,30), LocalTime.of(19,30), DayOfWeek.FRIDAY, ClassType.LAB, "A")
        )), new ArrayList<Exam>(Arrays.asList(new Exam(LocalDate.of(2024,5,23), LocalTime.of(8,30), LocalTime.of(11,30), ClassType.FINAL, "A")))));

        IFT2015.addSemester(new Semester("A24", start, end, new ArrayList<Period>(Arrays.asList(
            new Period(LocalTime.of(8,30), LocalTime.of(9,30), DayOfWeek.FRIDAY, ClassType.TH, "A"),
            new Period(LocalTime.of(10,30), LocalTime.of(12,30), DayOfWeek.MONDAY, ClassType.TH, "A"),
            new Period(LocalTime.of(9,30), LocalTime.of(11,30), DayOfWeek.MONDAY, ClassType.TP, "A101")
        )), new ArrayList<Exam>(Arrays.asList(new Exam(LocalDate.of(2024,5,23), LocalTime.of(8,30), LocalTime.of(11,30), ClassType.FINAL, "A")))));

        IFT2125.addSemester(new Semester("A24", start, end, new ArrayList<Period>(Arrays.asList(
            new Period(LocalTime.of(8,30), LocalTime.of(9,30), DayOfWeek.THURSDAY, ClassType.TH, "A"),
            new Period(LocalTime.of(10,30), LocalTime.of(12,30), DayOfWeek.TUESDAY, ClassType.TH, "A"),
            new Period(LocalTime.of(9,30), LocalTime.of(11,30), DayOfWeek.THURSDAY, ClassType.TP, "A101")
        )), new ArrayList<Exam>(Arrays.asList(new Exam(LocalDate.of(2024,5,23), LocalTime.of(8,30), LocalTime.of(11,30), ClassType.FINAL, "A")))));

        // MAT1000.addSemester(new Semester("A24", start, end, new ArrayList<Period>(Arrays.asList(
        //     new Period(LocalTime.of(15,30), LocalTime.of(17,30), DayOfWeek.TUESDAY, ClassType.TH),
        //     new Period(LocalTime.of(15,30), LocalTime.of(17,30), DayOfWeek.THURSDAY, ClassType.TH),
        //     new Period(LocalTime.of(12,30), LocalTime.of(14,30), DayOfWeek.FRIDAY, ClassType.TP)
        // ))));

        // MAT1600.addSemester(new Semester("A24", start, end, new ArrayList<Period>(Arrays.asList(
        //     new Period(LocalTime.of(12,30), LocalTime.of(14,30), DayOfWeek.MONDAY, ClassType.TH),
        //     new Period(LocalTime.of(9,30), LocalTime.of(11,30), DayOfWeek.FRIDAY, ClassType.TP)
        // ))));

        // STT1700.addSemester(new Semester("A24", start, end, new ArrayList<Period>(Arrays.asList(
        //     new Period(LocalTime.of(10,30), LocalTime.of(11,30), DayOfWeek.THURSDAY, ClassType.TH),
        //     new Period(LocalTime.of(10,30), LocalTime.of(12,30), DayOfWeek.MONDAY, ClassType.TH),
        //     new Period(LocalTime.of(13,30), LocalTime.of(15,30), DayOfWeek.WEDNESDAY, ClassType.TP)
        // ))));

        // IFT3700.addSemester(new Semester("A24", start, end, new ArrayList<Period>(Arrays.asList(
        //     new Period(LocalTime.of(16,00), LocalTime.of(18,00), DayOfWeek.WEDNESDAY, ClassType.TH),
        //     new Period(LocalTime.of(15,30), LocalTime.of(16,30), DayOfWeek.MONDAY, ClassType.TH),
        //     new Period(LocalTime.of(16,30), LocalTime.of(18,30), DayOfWeek.MONDAY, ClassType.TP)
        // ))));

        // MAT2412.addSemester(new Semester("A24", start, end, new ArrayList<Period>(Arrays.asList(
        //     new Period(LocalTime.of(15,30), LocalTime.of(16,30), DayOfWeek.WEDNESDAY, ClassType.TH),
        //     new Period(LocalTime.of(12,30), LocalTime.of(14,30), DayOfWeek.MONDAY, ClassType.TH),
        //     new Period(LocalTime.of(15,30), LocalTime.of(17,30), DayOfWeek.THURSDAY, ClassType.TP)
        // ))));

        // STT2700.addSemester(new Semester("A24", start, end, new ArrayList<Period>(Arrays.asList(
        //     new Period(LocalTime.of(10,30), LocalTime.of(12,30), DayOfWeek.TUESDAY, ClassType.TH),
        //     new Period(LocalTime.of(11,30), LocalTime.of(12,30), DayOfWeek.FRIDAY, ClassType.TH),
        //     new Period(LocalTime.of(15,30), LocalTime.of(17,30), DayOfWeek.MONDAY, ClassType.TP)
        // ))));

        // MAT2717.addSemester(new Semester("A24", start, end, new ArrayList<Period>(Arrays.asList(
        //     new Period(LocalTime.of(14,30), LocalTime.of(15,30), DayOfWeek.WEDNESDAY, ClassType.TH),
        //     new Period(LocalTime.of(9,30), LocalTime.of(11,30), DayOfWeek.FRIDAY, ClassType.TH),
        //     new Period(LocalTime.of(8,30), LocalTime.of(10,30), DayOfWeek.THURSDAY, ClassType.TP)
        // ))));

        // IFT1005.addSemester(new Semester("A24", start, end, new ArrayList<Period>(Arrays.asList(
        //     new Period(LocalTime.of(14,30), LocalTime.of(15,30), DayOfWeek.WEDNESDAY, ClassType.TH),
        //     new Period(LocalTime.of(9,30), LocalTime.of(11,30), DayOfWeek.FRIDAY, ClassType.TH),
        //     new Period(LocalTime.of(8,30), LocalTime.of(10,30), DayOfWeek.THURSDAY, ClassType.TP)
        // ))));

    }

    // ift1025, 1065, 1215
    // ift2105
    // mat1400
}
