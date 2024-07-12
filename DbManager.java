import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * La classe DbManager est responsable de la gestion de la connexion à la base de données,
 * de la création des tables nécessaires, et des opérations CRUD pour les cours, étudiants,
 * périodes et autres entités liées.
 */
public class DbManager {
    private Connection connection;
    private Statement statement;
    private HashMap<Integer, Course> courses = new HashMap<>();
    private HashMap<Integer, Period> periods = new HashMap<>();
    private HashMap<Integer, Student> students = new HashMap<>();

    /**
     * Construit un nouvel objet DbManager. Initialise la connexion à la base de données,
     * crée les tables nécessaires si elles n'existent pas, et récupère tous les cours
     * et étudiants de la base de données.
     */
    public DbManager() {
        try {
            this.connection = DriverManager.getConnection("jdbc:sqlite:ift1025_tp1.db");
            this.statement = connection.createStatement();

            statement.setQueryTimeout(30);

            statement.executeUpdate("CREATE TABLE IF NOT EXISTS courses (course_id INTEGER PRIMARY KEY, subject TEXT NOT NULL, value INTEGER NOT NULL, course_name TEXT, description TEXT, credit INTEGER NOT NULL)");
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS semesters (semester_id INTEGER PRIMARY KEY, semester_name TEXT NOT NULL, start_date DATE NOT NULL, end_date DATE NOT NULL, course_id INTEGER, FOREIGN KEY (course_id) REFERENCES courses(course_id))");
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS periods (period_id INTEGER PRIMARY KEY, start_time TIME NOT NULL, end_time TIME NOT NULL, day_of_week TEXT NOT NULL, type TEXT NOT NULL, section TEXT, semester_id INTEGER, FOREIGN KEY (semester_id) REFERENCES semesters(semester_id))");
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS exams (exam_id INTEGER PRIMARY KEY, start_time TIME NOT NULL, end_time TIME NOT NULL, day_of_week TEXT NOT NULL, type TEXT NOT NULL, section TEXT, date DATE NOT NULL, semester_id INTEGER, FOREIGN KEY (semester_id) REFERENCES semesters(semester_id))");
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS students (student_id INTEGER PRIMARY KEY, firstname TEXT NOT NULL, lastname TEXT NOT NULL, matricule INTEGER NOT NULL)");
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS schedules (schedule_id INTEGER PRIMARY KEY, semester_name TEXT NOT NULL, student_id INTEGER NOT NULL, FOREIGN KEY (student_id) REFERENCES students(student_id) )");
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS schedule_periods (schedule_period_id INTEGER PRIMARY KEY, course_id INTEGER NOT NULL, period_id INTEGER NOT NULL, schedule_id INTEGER NOT NULL, FOREIGN KEY (course_id) REFERENCES courses(course_id), FOREIGN KEY (period_id) REFERENCES periods(period_id), FOREIGN KEY (schedule_id) REFERENCES schedules(schedule_id) )");
        
            this.courses = retreiveAllCourses();
            this.students = retreiveAllStudents();
        
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
    }

    /**
     * Sauvegarde les cours et les étudiants dans la base de données.
     *
     * @param courses  la liste des cours à sauvegarder
     * @param students la liste des étudiants à sauvegarder
     */
    public void saveToDB(ArrayList<Course> courses, ArrayList<Student> students) {
        try {
            statement.executeUpdate("DROP TABLE IF EXISTS courses");
            statement.executeUpdate("DROP TABLE IF EXISTS semesters");
            statement.executeUpdate("DROP TABLE IF EXISTS periods");
            statement.executeUpdate("DROP TABLE IF EXISTS exams");
            statement.executeUpdate("DROP TABLE IF EXISTS students");
            statement.executeUpdate("DROP TABLE IF EXISTS schedules");
            statement.executeUpdate("DROP TABLE IF EXISTS schedule_periods");

            statement.executeUpdate("CREATE TABLE IF NOT EXISTS courses (course_id INTEGER PRIMARY KEY, subject TEXT NOT NULL, value INTEGER NOT NULL, course_name TEXT, description TEXT, credit INTEGER NOT NULL)");
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS semesters (semester_id INTEGER PRIMARY KEY, semester_name TEXT NOT NULL, start_date DATE NOT NULL, end_date DATE NOT NULL, course_id INTEGER, FOREIGN KEY (course_id) REFERENCES courses(course_id))");
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS periods (period_id INTEGER PRIMARY KEY, start_time TIME NOT NULL, end_time TIME NOT NULL, day_of_week TEXT NOT NULL, type TEXT NOT NULL, section TEXT, semester_id INTEGER, FOREIGN KEY (semester_id) REFERENCES semesters(semester_id))");
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS exams (exam_id INTEGER PRIMARY KEY, start_time TIME NOT NULL, end_time TIME NOT NULL, day_of_week TEXT NOT NULL, type TEXT NOT NULL, section TEXT, date DATE NOT NULL, semester_id INTEGER, FOREIGN KEY (semester_id) REFERENCES semesters(semester_id))");
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS students (student_id INTEGER PRIMARY KEY, firstname TEXT NOT NULL, lastname TEXT NOT NULL, matricule INTEGER NOT NULL)");
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS schedules (schedule_id INTEGER PRIMARY KEY, semester_name TEXT NOT NULL, student_id INTEGER NOT NULL, FOREIGN KEY (student_id) REFERENCES students(student_id) )");
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS schedule_periods (schedule_period_id INTEGER PRIMARY KEY, course_id INTEGER NOT NULL, period_id INTEGER NOT NULL, schedule_id INTEGER NOT NULL, FOREIGN KEY (course_id) REFERENCES courses(course_id), FOREIGN KEY (period_id) REFERENCES periods(period_id), FOREIGN KEY (schedule_id) REFERENCES schedules(schedule_id) )");

            courses.forEach(course -> saveCourse(course));
            students.forEach(student -> saveStudent(student));
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
    }

    /**
     * Récupère la liste des cours.
     *
     * @return la liste de tous les cours dans la db
     */
    public ArrayList<Course> getCourses() {
        return new ArrayList<>(retreiveAllCourses().values());
    }

    /**
     * Récupère la liste des étudiants.
     *
     * @return la liste de tous les étudiants dans la db
     */
    public ArrayList<Student> getStudents() {
        return new ArrayList<>(retreiveAllStudents().values());
    }

    /**
     * Ferme la connexion à la base de données.
     */
    public void closeConnection() {
        try {
            connection.close();
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
    }

    /**
     * Sauvegarde un cours dans la base de données.
     *
     * @param course le cours à sauvegarder
     */
    private void saveCourse(Course course) {
        try {
            String sqlQuery = String.format("INSERT INTO courses (subject, value, course_name, description, credit) VALUES ('%s', %d, '%s', '%s', %d)",
                                            course.getSubject(), course.getValue(), course.getAbbreviatedName(), course.getDescription(), course.getCredit());
            statement.executeUpdate(sqlQuery);
            saveSemesters(course.getSemesters(), statement.executeQuery("SELECT last_insert_rowid()").getInt(1));
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
    }

    /**
     * Sauvegarde un étudiant dans la base de données.
     *
     * @param student l'étudiant à sauvegarder
     */
    private void saveStudent(Student student) {
        try {
            String sqlQuery = String.format("INSERT INTO students (firstname, lastname, matricule) VALUES ('%s', '%s', '%s')",
                                            student.getFirstname(), student.getLastname(), student.getMatricule());
            statement.executeUpdate(sqlQuery);
            saveSchedule(student.getSchedule(), statement.executeQuery("SELECT last_insert_rowid()").getInt(1));
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
    }

    /**
     * Sauvegarde un horaire dans la base de données.
     *
     * @param schedule  l'horaire à sauvegarder
     * @param studentId l'ID de l'étudiant auquel l'horaire appartient
     */
    private void saveSchedule(Schedule schedule, int studentId) {
        try {
            String sqlQuery = String.format("INSERT INTO schedules (semester_name, student_id) VALUES ('%s', %d)",
                                            schedule.getSemesterName(), studentId);
            statement.executeUpdate(sqlQuery);

            int scheduleId = statement.executeQuery("SELECT last_insert_rowid()").getInt(1);
            schedule.getSchedulePeriods().forEach(schedulePeriod -> saveSchedulePeriod(schedulePeriod, scheduleId));
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
    }

    /**
     * Sauvegarde une période d'horaire dans la base de données.
     *
     * @param schedulePeriod la période d'horaire à sauvegarder
     * @param scheduleId     l'ID de l'horaire associé
     */
    private void saveSchedulePeriod(SchedulePeriod schedulePeriod, int scheduleId) {
        try {
            String sqlQuery = String.format("INSERT INTO schedule_periods (course_id, period_id, schedule_id) VALUES (%d, %d, %d)",
                                            getCourseId(schedulePeriod.getCourse()), getPeriodId((Period) schedulePeriod), scheduleId);
            statement.executeUpdate(sqlQuery);
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
    }

    /**
     * Récupère l'ID d'un cours stocké dans la base de données.
     *
     * @param course le cours dont l'ID est à récupérer
     * @return l'ID du cours
     */
    private int getCourseId(Course course) {
        try {
            String description = course.getDescription().isEmpty() ? "null" : course.getDescription();
            String sqlQuery = String.format("SELECT course_id FROM courses WHERE subject='%s' AND value=%d AND course_name='%s' AND description='%s' AND credit=%d;",
                                            course.getSubject(), course.getValue(), course.getName(), description, course.getCredit());
            ResultSet rs = statement.executeQuery(sqlQuery);

            return rs.getInt("course_id");
        } catch (Exception e) {
            e.printStackTrace(System.err);
            return 0;
        }
    }

    /**
     * Récupère l'ID d'une période stockée dans la base de données.
     *
     * @param course la période dont l'ID est à récupérer
     * @return l'ID de la période
     */
    private int getPeriodId(Period period) {
        try {
            String sqlQuery = String.format("SELECT period_id FROM periods WHERE start_time='%s' AND end_time='%s' AND day_of_week='%s' AND type='%s' AND section='%s';",
                                            period.getStart(), period.getEnd(), period.getDayOfWeek(), period.getType(), period.getSection());
            ResultSet rs = statement.executeQuery(sqlQuery);

            return rs.getInt("period_id");
        } catch (Exception e) {
            e.printStackTrace(System.err);
            return 0;
        }
    }

    /**
     * Sauvegarde les semestres dans la base de données.
     *
     * @param semesters la liste des semestres à sauvegarder
     * @param courseId  l'ID du cours auquel le semestre appartient
     */
    private void saveSemesters(List<Semester> semesters, int courseId) {
        semesters.forEach(semester -> saveSemester(semester, courseId));

    }

    /**
     * Sauvegarde un semestre dans la base de données.
     *
     * @param semester le semestre à sauvegarder
     * @param courseId l'ID du cours auquel le semestre appartient
     */
    private void saveSemester(Semester semester, int courseId) {
        try {
            String sqlQuery = String.format("INSERT INTO semesters (semester_name, start_date, end_date, course_id) VALUES ('%s', '%s', '%s', %d)",
                                            semester.getName(), semester.getStart(), semester.getEnd(), courseId);
            statement.executeUpdate(sqlQuery);
            int semesterId = statement.executeQuery("SELECT last_insert_rowid()").getInt(1);
            savePeriods(semester.getPeriods(), semesterId);
            saveExams(semester.getExams(), semesterId);
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
    }

    /**
     * Sauvegarde les périodes dans la base de données.
     *
     * @param periods    la liste des périodes à sauvegarder
     * @param semesterId l'ID du semestre auquel la période appartient
     */
    private void savePeriod(Period period, int semesterId) {
        try {
            String sqlQuery = String.format("INSERT INTO periods (start_time, end_time, day_of_week, type, section, semester_id) VALUES ('%s', '%s', '%s', '%s', '%s', %d)",
                                            period.getStart(), period.getEnd(), period.getDayOfWeek(), period.getType(), period.getSection(), semesterId);
            statement.executeUpdate(sqlQuery);
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
    }

    /**
     * Sauvegarde une période dans la base de données.
     *
     * @param period     la période à sauvegarder
     * @param semesterId l'ID du semestre auquel la période appartient
     */
    private void savePeriods(List<Period> periods, int semesterId) {
        periods.forEach(period -> savePeriod(period, semesterId));
    }

    /**
     * Sauvegarde un examen dans la base de données.
     *
     * @param exam       l'examen à sauvegarder
     * @param semesterId l'ID du semestre auquel l'examen appartient
     */
    private void saveExam(Exam exam, int semesterId) {
        try {
            String sqlQuery = String.format("INSERT INTO exams (start_time, end_time, day_of_week, type, section, date, semester_id) VALUES ('%s', '%s', '%s', '%s', '%s', '%s', %d)",
                                            exam.getStart(), exam.getEnd(), exam.getDayOfWeek(), exam.getType(), exam.getSection(), exam.getDate(), semesterId);
            statement.executeUpdate(sqlQuery);
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
    }

    /**
     * Sauvegarde les examens dans la base de données.
     *
     * @param exams      la liste des examens à sauvegarder
     * @param semesterId l'ID du semestre auquel l'examen appartient
     */
    private void saveExams(List<Exam> exams, int semesterId) {
        exams.forEach(exam -> saveExam(exam, semesterId));
    }

    /**
     * Récupère tous les cours de la base de données.
     *
     * @return une liste de couple des cours et de leur id dans la bd
     */
    private HashMap<Integer, Course> retreiveAllCourses() {
        HashMap<Integer, Course> courses = new HashMap<>();

        try {
            String sqlQuery = String.format("SELECT courses.course_id, courses.subject, courses.value, courses.course_name, courses.description, courses.credit, semesters.semester_id, semesters.semester_name, semesters.start_date, semesters.end_date, periods.period_id, periods.start_time AS period_start, periods.end_time AS period_end, periods.day_of_week AS period_day, periods.type AS period_type, periods.section AS period_section, exams.exam_id, exams.start_time AS exam_start, exams.end_time AS exam_end, exams.day_of_week AS exams_day, exams.type AS exam_type, exams.section AS exam_section, exams.date AS exam_date FROM courses LEFT JOIN semesters ON courses.course_id = semesters.course_id LEFT JOIN periods ON semesters.semester_id = periods.semester_id LEFT JOIN exams ON semesters.semester_id = exams.semester_id");

            ResultSet rs = statement.executeQuery(sqlQuery);

            Course currentCourse = new Course();
            String currentSemesterName = "";

            ArrayList<Integer> courseIds = new ArrayList<>();
            ArrayList<Integer> semesterIds = new ArrayList<>();
            ArrayList<Integer> periodIds = new ArrayList<>();
            ArrayList<Integer> examIds = new ArrayList<>();

            while (rs.next()) {

                int courseId = rs.getInt("course_id");
                int semesterId = rs.getInt("semester_id");
                int periodId = rs.getInt("period_id");
                int examId = rs.getInt("exam_id");

                //System.out.println("cours=" + courseId + " semester=" + semesterId + " period=" + periodId + " exam=" + examId);

                if (!courseIds.contains(courseId)) {
                    currentCourse = new Course(rs.getString("subject"), rs.getInt("value"), rs.getString("course_name"), rs.getString("description"), rs.getInt("credit"));
                    courses.put(courseId, currentCourse);
                    courseIds.add(courseId);
                }

                if (!semesterIds.contains(semesterId) && semesterId != 0) {
                    currentSemesterName = rs.getString("semester_name");
                    currentCourse.addSemester(new Semester(currentSemesterName, LocalDate.parse(rs.getString("start_date")), LocalDate.parse(rs.getString("end_date"))));
                    semesterIds.add(semesterId);
                }


                if (!periodIds.contains(periodId) && periodId != 0) {
                    Period period = new Period(LocalTime.parse(rs.getString("period_start")), LocalTime.parse(rs.getString("period_end")), DayOfWeek.valueOf(rs.getString("period_day")), ClassType.valueOf(rs.getString("period_type")), rs.getString("period_section"));
                    currentCourse.getSemesterByName(currentSemesterName).addPeriod(period);
                    periods.put(periodId, period);
                    periodIds.add(periodId);
                }

                if (!examIds.contains(examId) && examId != 0) {
                    currentCourse.getSemesterByName(currentSemesterName).addExam(new Exam(LocalDate.parse(rs.getString("exam_date")), LocalTime.parse(rs.getString("exam_start")), LocalTime.parse(rs.getString("exam_end")), ClassType.valueOf(rs.getString("exam_type")), rs.getString("exam_section")));
                    examIds.add(examId);
                }
            }


        } catch (Exception e) {
            e.printStackTrace(System.err);
        }

        return courses;
    }

    /**
     * Récupère tous les étudiants de la base de données.
     *
     * @return une liste de couple des étudiants et de leur id dans la bd
     */
    private HashMap<Integer, Student> retreiveAllStudents() {
        HashMap<Integer, Student> students = new HashMap<>();

        try {
            String sqlQuery = String.format("SELECT * FROM students LEFT JOIN schedules ON schedules.student_id=students.student_id LEFT JOIN schedule_periods ON schedule_periods.schedule_id=schedules.schedule_id LEFT JOIN periods ON periods.period_id=schedule_periods.period_id LEFT JOIN courses ON courses.course_id=schedule_periods.course_id");

            ResultSet rs = statement.executeQuery(sqlQuery);

            Student currentStudent = new Student();

            ArrayList<Integer> studentIds = new ArrayList<>();
            ArrayList<Integer> scheduleIds = new ArrayList<>();
            ArrayList<Integer> schedulePeriodIds = new ArrayList<>();

            while (rs.next()) {

                int studentId = rs.getInt("student_id");
                int scheduleId = rs.getInt("schedule_id");
                int schedulePeriodId = rs.getInt("schedule_period_id");
                int courseId = rs.getInt("course_id");
                int periodId = rs.getInt("period_id");

                // System.out.println("student_id=" + studentId + " schedule_period_id=" + schedulePeriodId + " course_id=" + courseId + " period_id=" + periodId);

                if (!studentIds.contains(studentId) && studentId != 0) {
                    currentStudent = new Student(rs.getString("firstname"), rs.getString("lastname"), rs.getString("matricule"));
                    students.put(studentId, currentStudent);
                    studentIds.add(studentId);
                }

                if (!scheduleIds.contains(scheduleId) && scheduleId != 0) {
                    currentStudent.getSchedule().setSemesterName(rs.getString("semester_name"));
                    scheduleIds.add(scheduleId);
                }

                if (!schedulePeriodIds.contains(schedulePeriodId) && schedulePeriodId != 0) {
                    SchedulePeriod schedulePeriod = new SchedulePeriod(
                        getCourseById(courseId),
                        getPeriodById(periodId)
                    );

                    currentStudent.getSchedule().addSchedulePeriod(schedulePeriod);
                }
            }
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }

        return students;
    }

    /**
     * Récupère un cours dans la liste de cours interne grace à l'ID.
     * 
     * @param i l'ID du cours dans la base de données
     * @return l'objet cours
     */
    private Course getCourseById(int i) {
        return courses.get(i);
    }

    /**
     * Récupère une période dans la liste de cours interne grace à l'ID.
     * 
     * @param i l'ID de la période dans la base de données
     * @return l'objet période
     */
    private Period getPeriodById(int i) {
        return periods.get(i);
    }

}
