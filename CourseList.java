import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;

public class CourseList {
    LocalDate start = LocalDate.of(2024,9,3);
    LocalDate end = LocalDate.of(2024,12,6);

    private ArrayList<Course> courses;

    
    
    public Course IFT1015A = new Course("IFT", 1015, 3, "A");
    public Course IFT1015B = new Course("IFT", 1015, 3, "B");
    public Course IFT1575A = new Course("IFT", 1575, 3, "A");
    public Course IFT1575B = new Course("IFT", 1575, 3, "B");
    public Course IFT2015 = new Course("IFT", 2015, 3, "A");
    public Course IFT2125 = new Course("IFT", 2125, 3, "A");
    public Course MAT1000 = new Course("MAT", 1000, 4, "A");
    public Course MAT1600 = new Course("MAT", 1600, 4, "A");
    public Course STT1700 = new Course("STT", 1700, 3, "A");
    // orientation sciences des données
    public Course IFT3700 = new Course("IFT", 3700, 3, "A");
    public Course MAT2412 = new Course("MAT", 2412, 3, "A");
    public Course STT2400 = new Course("STT", 2400, 3, "A");
    public Course STT2700 = new Course("STT", 2700, 3, "A");
    // stats:
    public Course MAT2717 = new Course("MAT", 2717, 3, "A");
    // à ajouter...
    // info:
    public Course IFT1005 = new Course("IFT", 1005, 3, "A");

    public ArrayList<Course> mathInfoCourses = new ArrayList<>(Arrays.asList(IFT1015A, IFT1015B, IFT1575A, IFT1575B, IFT2015, IFT2125, MAT1000, MAT1600, STT1700,
                                                                             IFT3700, MAT2412, MAT2717, IFT1005  ));


    public CourseList() {
        IFT1015A.addSemester(new Semester("A24", start, end, new ArrayList<Period>(Arrays.asList(
            new Period(LocalTime.of(15,30), LocalTime.of(16,30), DayOfWeek.FRIDAY, ClassType.TH),
            new Period(LocalTime.of(12,30), LocalTime.of(14,30), DayOfWeek.WEDNESDAY, ClassType.TH),
            new Period(LocalTime.of(16,30), LocalTime.of(17,30), DayOfWeek.FRIDAY, ClassType.TP),
            new Period(LocalTime.of(17,30), LocalTime.of(19,30), DayOfWeek.FRIDAY, ClassType.LAB)
        ))));

        IFT1015B.addSemester(new Semester("A24", start, end, new ArrayList<Period>(Arrays.asList(
            new Period(LocalTime.of(11,30), LocalTime.of(12,30), DayOfWeek.THURSDAY, ClassType.TH),
            new Period(LocalTime.of(10,30), LocalTime.of(12,30), DayOfWeek.TUESDAY, ClassType.TH),
            new Period(LocalTime.of(12,30), LocalTime.of(13,30), DayOfWeek.FRIDAY, ClassType.TP),
            new Period(LocalTime.of(13,30), LocalTime.of(15,30), DayOfWeek.FRIDAY, ClassType.LAB)
        ))));

        
        IFT1575A.addSemester(new Semester("A24", start, end, new ArrayList<Period>(Arrays.asList(
            new Period(LocalTime.of(16,30), LocalTime.of(18,30), DayOfWeek.WEDNESDAY, ClassType.TH),
            new Period(LocalTime.of(15,30), LocalTime.of(16,30), DayOfWeek.MONDAY, ClassType.TH),
            new Period(LocalTime.of(8,30), LocalTime.of(10,30), DayOfWeek.MONDAY, ClassType.TP)
        ))));

        
        IFT1575B.addSemester(new Semester("A24", start, end, new ArrayList<Period>(Arrays.asList(
            new Period(LocalTime.of(16,30), LocalTime.of(18,30), DayOfWeek.FRIDAY, ClassType.TH),
            new Period(LocalTime.of(14,30), LocalTime.of(15,30), DayOfWeek.MONDAY, ClassType.TH),
            new Period(LocalTime.of(8,30), LocalTime.of(10,30), DayOfWeek.MONDAY, ClassType.TP)
        ))));

        IFT2015.addSemester(new Semester("A24", start, end, new ArrayList<Period>(Arrays.asList(
            new Period(LocalTime.of(8,30), LocalTime.of(9,30), DayOfWeek.FRIDAY, ClassType.TH),
            new Period(LocalTime.of(10,30), LocalTime.of(12,30), DayOfWeek.MONDAY, ClassType.TH),
            new Period(LocalTime.of(9,30), LocalTime.of(11,30), DayOfWeek.MONDAY, ClassType.TP)
        ))));

        IFT2125.addSemester(new Semester("A24", start, end, new ArrayList<Period>(Arrays.asList(
            new Period(LocalTime.of(8,30), LocalTime.of(9,30), DayOfWeek.THURSDAY, ClassType.TH),
            new Period(LocalTime.of(10,30), LocalTime.of(12,30), DayOfWeek.TUESDAY, ClassType.TH),
            new Period(LocalTime.of(9,30), LocalTime.of(11,30), DayOfWeek.THURSDAY, ClassType.TP)
        ))));

        MAT1000.addSemester(new Semester("A24", start, end, new ArrayList<Period>(Arrays.asList(
            new Period(LocalTime.of(15,30), LocalTime.of(17,30), DayOfWeek.TUESDAY, ClassType.TH),
            new Period(LocalTime.of(15,30), LocalTime.of(17,30), DayOfWeek.THURSDAY, ClassType.TH),
            new Period(LocalTime.of(12,30), LocalTime.of(14,30), DayOfWeek.FRIDAY, ClassType.TP)
        ))));

        MAT1600.addSemester(new Semester("A24", start, end, new ArrayList<Period>(Arrays.asList(
            new Period(LocalTime.of(12,30), LocalTime.of(14,30), DayOfWeek.MONDAY, ClassType.TH),
            new Period(LocalTime.of(9,30), LocalTime.of(11,30), DayOfWeek.FRIDAY, ClassType.TP)
        ))));

        STT1700.addSemester(new Semester("A24", start, end, new ArrayList<Period>(Arrays.asList(
            new Period(LocalTime.of(10,30), LocalTime.of(11,30), DayOfWeek.THURSDAY, ClassType.TH),
            new Period(LocalTime.of(10,30), LocalTime.of(12,30), DayOfWeek.MONDAY, ClassType.TH),
            new Period(LocalTime.of(13,30), LocalTime.of(15,30), DayOfWeek.WEDNESDAY, ClassType.TP)
        ))));

        IFT3700.addSemester(new Semester("A24", start, end, new ArrayList<Period>(Arrays.asList(
            new Period(LocalTime.of(16,00), LocalTime.of(18,00), DayOfWeek.WEDNESDAY, ClassType.TH),
            new Period(LocalTime.of(15,30), LocalTime.of(16,30), DayOfWeek.MONDAY, ClassType.TH),
            new Period(LocalTime.of(16,30), LocalTime.of(18,30), DayOfWeek.MONDAY, ClassType.TP)
        ))));

        MAT2412.addSemester(new Semester("A24", start, end, new ArrayList<Period>(Arrays.asList(
            new Period(LocalTime.of(15,30), LocalTime.of(16,30), DayOfWeek.WEDNESDAY, ClassType.TH),
            new Period(LocalTime.of(12,30), LocalTime.of(14,30), DayOfWeek.MONDAY, ClassType.TH),
            new Period(LocalTime.of(15,30), LocalTime.of(17,30), DayOfWeek.THURSDAY, ClassType.TP)
        ))));

        STT2700.addSemester(new Semester("A24", start, end, new ArrayList<Period>(Arrays.asList(
            new Period(LocalTime.of(10,30), LocalTime.of(12,30), DayOfWeek.TUESDAY, ClassType.TH),
            new Period(LocalTime.of(11,30), LocalTime.of(12,30), DayOfWeek.FRIDAY, ClassType.TH),
            new Period(LocalTime.of(15,30), LocalTime.of(17,30), DayOfWeek.MONDAY, ClassType.TP)
        ))));

        MAT2717.addSemester(new Semester("A24", start, end, new ArrayList<Period>(Arrays.asList(
            new Period(LocalTime.of(14,30), LocalTime.of(15,30), DayOfWeek.WEDNESDAY, ClassType.TH),
            new Period(LocalTime.of(9,30), LocalTime.of(11,30), DayOfWeek.FRIDAY, ClassType.TH),
            new Period(LocalTime.of(8,30), LocalTime.of(10,30), DayOfWeek.THURSDAY, ClassType.TP)
        ))));

        IFT1005.addSemester(new Semester("A24", start, end, new ArrayList<Period>(Arrays.asList(
            new Period(LocalTime.of(14,30), LocalTime.of(15,30), DayOfWeek.WEDNESDAY, ClassType.TH),
            new Period(LocalTime.of(9,30), LocalTime.of(11,30), DayOfWeek.FRIDAY, ClassType.TH),
            new Period(LocalTime.of(8,30), LocalTime.of(10,30), DayOfWeek.THURSDAY, ClassType.TP)
        ))));

    }

    // ift1025, 1065, 1215
    // ift2105
    // mat1400
}
