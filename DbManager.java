import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DbManager {

    private Connection connection;
    private Statement statement;

    public DbManager() {
        try {
            this.connection = DriverManager.getConnection("jdbc:sqlite:ift1025_tp1.db");
            this.statement = connection.createStatement();

            statement.setQueryTimeout(30);

            // statement.executeUpdate("DROP TABLE IF EXISTS courses"); // à changer pour IF NOT EXISTS
            // statement.executeUpdate("DROP TABLE IF EXISTS semesters");
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS courses (course_id INTEGER PRIMARY KEY, subject TEXT NOT NULL, value INTEGER NOT NULL, course_name TEXT, description TEXT, credit INTEGER NOT NULL)");
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS semesters (semester_id INTEGER PRIMARY KEY, semester_name TEXT NOT NULL, start_date DATE NOT NULL, end_date DATE NOT NULL, course_id INTEGER, FOREIGN KEY (course_id) REFERENCES courses(course_id))");
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS periods (period_id INTEGER PRIMARY KEY, start_time TIME NOT NULL, end_time TIME NOT NULL, day_of_week TEXT NOT NULL, type TEXT NOT NULL, section TEXT, semester_id INTEGER, FOREIGN KEY (semester_id) REFERENCES semesters(semester_id))");
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS exams (exam_id INTEGER PRIMARY KEY, start_time TIME NOT NULL, end_time TIME NOT NULL, day_of_week TEXT NOT NULL, type TEXT NOT NULL, section TEXT, date DATE NOT NULL, semester_id INTEGER, FOREIGN KEY (semester_id) REFERENCES semesters(semester_id))");
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
    }
    public static void main(String[] args) {
        DbManager app = new DbManager();
        Course IFT1575 = new Course("IFT", 1575, 3);
        IFT1575.addSemester(new Semester("A24", LocalDate.of(2024,1,1), LocalDate.of(2024,6,1), new ArrayList<Period>(Arrays.asList(
            new Period(LocalTime.of(16,30), LocalTime.of(18,30), DayOfWeek.WEDNESDAY, ClassType.TH, "A"),
            new Period(LocalTime.of(15,30), LocalTime.of(16,30), DayOfWeek.MONDAY, ClassType.TH, "A"),
            new Period(LocalTime.of(8,30), LocalTime.of(10,30), DayOfWeek.MONDAY, ClassType.TP, "A101")
        )), new ArrayList<Exam>(Arrays.asList(new Exam(LocalDate.of(2024,5,23), LocalTime.of(8,30), LocalTime.of(11,30), ClassType.FINAL, "A"))) ));
        // app.addCourse(IFT1575);

        ArrayList<Course> x = app.getAllCourses();

        for (Course course : x) {
            System.out.println(course);
        }
    }


    public void addAllCourses(List<Course> courses) {
        try {
            statement.executeUpdate("DROP TABLE IF EXISTS courses");
            statement.executeUpdate("DROP TABLE IF EXISTS semesters");
            statement.executeUpdate("DROP TABLE IF EXISTS periods");
            statement.executeUpdate("DROP TABLE IF EXISTS exams");
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS courses (course_id INTEGER PRIMARY KEY, subject TEXT NOT NULL, value INTEGER NOT NULL, course_name TEXT, description TEXT, credit INTEGER NOT NULL)");
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS semesters (semester_id INTEGER PRIMARY KEY, semester_name TEXT NOT NULL, start_date DATE NOT NULL, end_date DATE NOT NULL, course_id INTEGER, FOREIGN KEY (course_id) REFERENCES courses(course_id))");
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS periods (period_id INTEGER PRIMARY KEY, start_time TIME NOT NULL, end_time TIME NOT NULL, day_of_week TEXT NOT NULL, type TEXT NOT NULL, section TEXT, semester_id INTEGER, FOREIGN KEY (semester_id) REFERENCES semesters(semester_id))");
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS exams (exam_id INTEGER PRIMARY KEY, start_time TIME NOT NULL, end_time TIME NOT NULL, day_of_week TEXT NOT NULL, type TEXT NOT NULL, section TEXT, date DATE NOT NULL, semester_id INTEGER, FOREIGN KEY (semester_id) REFERENCES semesters(semester_id))");
        
            courses.forEach(course -> addCourse(course));
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
    }


    public void addCourse(Course course) {
        try {
            String sqlQuery = String.format("INSERT INTO courses (subject, value, course_name, description, credit) VALUES ('%s', %d, '%s', '%s', %d)",
                                            course.getSubject(), course.getValue(), course.getAbbreviatedName(), course.getDescription(), course.getCredit());
            statement.executeUpdate(sqlQuery);
            addSemesters(course.getSemesters(), statement.executeQuery("SELECT last_insert_rowid()").getInt(1));
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
    }

    public void addSemesters(List<Semester> semesters, int courseId) {
        semesters.forEach(semester -> addSemester(semester, courseId));
        
    }
    
    public void addSemester(Semester semester, int courseId) {
        try {
            String sqlQuery = String.format("INSERT INTO semesters (semester_name, start_date, end_date, course_id) VALUES ('%s', '%s', '%s', %d)",
                                            semester.getName(), semester.getStart(), semester.getEnd(), courseId);
            statement.executeUpdate(sqlQuery);
            int semesterId = statement.executeQuery("SELECT last_insert_rowid()").getInt(1);
            addPeriods(semester.getPeriods(), semesterId);
            addExams(semester.getExams(), semesterId);
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
    }

    public void addPeriod(Period period, int semesterId) {
        try {
            String sqlQuery = String.format("INSERT INTO periods (start_time, end_time, day_of_week, type, section, semester_id) VALUES ('%s', '%s', '%s', '%s', '%s', %d)",
                                            period.getStart(), period.getEnd(), period.getDayOfWeek(), period.getType(), period.getSection(), semesterId);
            statement.executeUpdate(sqlQuery);
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
    }

    public void addPeriods(List<Period> periods, int semesterId) {
        periods.forEach(period -> addPeriod(period, semesterId));
    }

    public void addExam(Exam exam, int semesterId) {
        try {
            String sqlQuery = String.format("INSERT INTO exams (start_time, end_time, day_of_week, type, section, date, semester_id) VALUES ('%s', '%s', '%s', '%s', '%s', '%s', %d)",
                                            exam.getStart(), exam.getEnd(), exam.getDayOfWeek(), exam.getType(), exam.getSection(), exam.getDate(), semesterId);
            statement.executeUpdate(sqlQuery);
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
    }

    public void addExams(List<Exam> exams, int semesterId) {
        exams.forEach(exam -> addExam(exam, semesterId));
    }


    public ArrayList<Course> getAllCourses() {
        ArrayList<Course> courses = new ArrayList<>();

        try {
            String sqlQuery = String.format("SELECT * FROM courses LEFT JOIN semesters ON courses.course_id = semesters.course_id LEFT JOIN periods ON semesters.semester_id = periods.semester_id LEFT JOIN exams ON semesters.semester_id = exams.semester_id");
            ResultSet rs = statement.executeQuery(sqlQuery);

            Course currentCourse = new Course();
            int previousCourseId = -1; int previousSemesterId = -1; int previousPeriodId = -1; int previousExamId = -1;
            String currentSemesterName = ""; 

            while (rs.next()) {
                int courseId = rs.getInt("course_id");
                int semesterId = rs.getInt("semester_id");
                int periodId = rs.getInt("period_id");
                int examId = rs.getInt("exam_id");

                // System.out.println("cours=" + courseId + " semester=" + semesterId + " period=" + periodId + " exam=" + examId);

                if (previousCourseId != courseId) {
                    currentCourse = new Course(rs.getString("subject"), rs.getInt("value"), rs.getInt("credit"));
                    courses.add(currentCourse);
                }

                if (previousSemesterId != semesterId) {
                    currentSemesterName = rs.getString("semester_name");
                    currentCourse.addSemester(new Semester(currentSemesterName, LocalDate.parse(rs.getString("start_date")), LocalDate.parse(rs.getString("end_date"))));
                }

                if (previousPeriodId != periodId) {
                    currentCourse.getSemesterByName(currentSemesterName).addPeriod(new Period(LocalTime.parse(rs.getString("start_time")), LocalTime.parse(rs.getString("end_time")), DayOfWeek.valueOf(rs.getString("day_of_week")), ClassType.valueOf(rs.getString("type")), rs.getString("section")));
                }

                if (previousExamId != examId) {
                    currentCourse.getSemesterByName(currentSemesterName).addExam(new Exam(LocalDate.parse(rs.getString("date")), LocalTime.parse(rs.getString("start_time")), LocalTime.parse(rs.getString("end_time")), ClassType.valueOf(rs.getString("type")), rs.getString("section")));
                }


                previousCourseId = courseId;
                previousSemesterId = semesterId;
                previousPeriodId = periodId;
                previousExamId = examId;
            }

            
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }

        return courses;
    }


    public void getCourseByCode(String subject, int value) {
        try {
            String sqlQuery = String.format("SELECT * FROM courses JOIN semesters ON courses.course_id = semesters.course_id JOIN periods ON semesters.semester_id = periods.semester_id JOIN exams ON semesters.semester_id = exams.semester_id WHERE courses.subject='%s' AND courses.value='%s'", subject, value);
            ResultSet rs = statement.executeQuery(sqlQuery);
            

            System.out.println(rs.getString("subject") + " " + rs.getString("value") + " " + rs.getString("course_name") + " " + rs.getString("description") + " " + rs.getInt("credit"));
            System.out.println(rs.getString("semester_id") + " " + rs.getString("semester_name") + " " + rs.getString("start_date"));

            Course course = new Course(rs.getString("subject"), rs.getInt("value"), rs.getInt("credit"));

            int previousSemesterId = -1; int previousPeriodId = -1; int previousExamId = -1;
            String currentSemesterName = ""; 

            while (rs.next()) {
                int semesterId = rs.getInt("semester_id");
                int periodId = rs.getInt("period_id");
                int examId = rs.getInt("exam_id");

                if (previousSemesterId != semesterId) {
                    currentSemesterName = rs.getString("semester_name");
                    course.addSemester(new Semester(currentSemesterName, LocalDate.parse(rs.getString("start_date")), LocalDate.parse(rs.getString("end_date"))));
                }

                if (previousPeriodId != periodId) {
                    course.getSemesterByName(currentSemesterName).addPeriod(new Period(LocalTime.parse(rs.getString("start_time")), LocalTime.parse(rs.getString("end_time")), DayOfWeek.valueOf(rs.getString("day_of_week")), ClassType.valueOf(rs.getString("type")), rs.getString("section")));
                }

                if (previousExamId != examId) {
                    course.getSemesterByName(currentSemesterName).addExam(new Exam(LocalDate.parse(rs.getString("date")), LocalTime.parse(rs.getString("start_time")), LocalTime.parse(rs.getString("end_time")), ClassType.valueOf(rs.getString("type")), rs.getString("section")));
                }



                previousSemesterId = semesterId;
                previousPeriodId = periodId;
                previousExamId = examId;
            }

            System.out.println(course.getSemesterByName("A24"));
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
    }
}
