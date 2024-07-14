import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {
    static DbManager dbManager = new DbManager();
    static ArrayList<Course> courses = dbManager.getCourses();
    static ArrayList<Student> students = dbManager.getStudents();
    static Scanner scanner = new Scanner(System.in);
    static String userInput;

    public static void main(String[] args) {
        System.err.println("\t*-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-*");
        System.out.println("\t\tBienvenue sur le planificateur académique");
        System.out.println("<!> " + courses.size() + " cours et " + students.size() + " étudiants ont été récupérés dans la base de données <!>");
        System.err.println("\t*-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-*\n");

        String mainMenu = "Entrez: (1) pour accéder à la gestion des cours"
                        + "\n\t(2) pour accéder à la gestion des horaires d'étudiants"
                        + "\n\t(stop) pour fermer et sauvegarder le programme";

        System.out.println(mainMenu);

        while (!(userInput = scanner.nextLine()).equalsIgnoreCase("stop")) {
            switch (userInput) {
                case "1":
                    askCoursManager();
                    break;

                case "2":
                    askGestionEtudiants();
                    break;
            }
            System.out.println(mainMenu);
        }

        dbManager.saveToDB(courses, students);
        dbManager.closeConnection();
    }

    public static void askCoursManager() {
        System.out.println(getGestionCoursMessage());
        while (!(userInput = scanner.nextLine()).equalsIgnoreCase("back")) {
            switch (userInput) {
                case "1":
                    System.out.println(getCoursesAsString());
                    break;
            
                case "2":
                    askCourseDetails();
                    break;
                case "3":
                    askAddCourse();
                    break;
                case "4":
                    askModifyCourse();
                    break;
                case "5":
                    askDeleteCourse();
                    break;
            }
            System.out.println(getGestionCoursMessage());
        }
    }

    public static void askModifyCourse() {
        System.out.println("Quel cours voulez-vous modifier (ex: IFT1015) ?");
        int index = askFindCourse();

        if (index == -1)
            return;

        Course course = courses.get(index);

        System.out.println("Modification du cours " + course.getAbbreviatedName() + "...");

        System.out.println("Voulez-vous modifier le sigle (" + course.getSubject() + ") [y/n] ?");
        if (askYesOrNo()) {
            System.out.println("Entrez le nouveau sigle:");
            course.setSubject( askCourseSubject() );
        }

        System.out.println("Voulez-vous modifier la valeur (" + course.getValue() + ") [y/n] ?");
        if (askYesOrNo()) {
            System.out.println("Entrez la nouvelle valeur:");
            course.setValue( askCourseValue() );
        }

        System.out.println("Voulez-vous modifier la nom (" + course.getName() + ") [y/n] ?");
        if (askYesOrNo()) {
            System.out.println("Entrez le nouveau nom:");
            course.setName( askNoWhiteSpace() );
        }

        System.out.println("Voulez-vous modifier la description (" + course.getDescription() + ") [y/n] ?");
        if (askYesOrNo()) {
            System.out.println("Entrez la nouvelle description:");
            course.setDescription( scanner.nextLine().trim() );
        }

        System.out.println("Voulez-vous modifier la nombre de crédits (" + course.getCredit() + ") [y/n] ?");
        if (askYesOrNo()) {
            System.out.println("Entrez le nouveau nombre de crédits:");
            course.setCredit( askCourseCredit() );
        }

        System.out.println("Voulez-vous modifier un semestre (" + course.getSemestersAsString() + ") [y/n] ?");
        if (askYesOrNo()) {
            askModifySemester(course);
        }
    }

    public static void askModifySemester(Course course) {
        System.out.println("Quel semestre voulez-vous modifier parmis: " + course.getSemestersAsString() + " ?");
    
        Semester semester;

        while ((semester = course.getSemesterByName(userInput = scanner.nextLine())) == null) {
            if (userInput.equals("stop"))
                return;
            System.out.println("Le semestre " + userInput + " n'a pas été trouvé, réessayez (ou 'stop')!");
        }

        System.out.println("Modification du semestre " + semester.getName() + " du cours " + course.getAbbreviatedName() + "...");

        System.out.println("Voulez-vous modifier le nom (" + semester.getName() + ") [y/n] ?");
        if (askYesOrNo()) {
            System.out.println("Entrez le nouveau nom:");
            semester.setName( askSemesterName() );
        }

        System.out.println("Voulez-vous modifier la date de début (" + semester.getStart() + ") [y/n] ?");
        if (askYesOrNo()) {
            System.out.println("Entrez la nouvelle date:");
            semester.setStart( askDate() );
        }

        System.out.println("Voulez-vous modifier la date de fin (" + semester.getEnd() + ") [y/n] ?");
        if (askYesOrNo()) {
            System.out.println("Entrez la nouvelle date:");
            semester.setEnd( askDate() );
        }

        System.out.println("Voulez-vous modifier les périodes (" + semester.getPeriodsAsString() + ") [y/n] ?");
        if (askYesOrNo()) {
            askModifyPeriod(semester);
        }

        System.out.println("Voulez-vous modifier les examens (" + semester.getExamsAsString() + ") [y/n] ?");
        if (askYesOrNo()) {
            askModifyExam(semester);
        }

        System.out.print("Voulez-vous supprimer un semestre [y/n] ? : ");
        while (askYesOrNo()) {
            course.removeSemester(semester);
            System.out.print("Voulez-vous supprimer un autre semestre [y/n] ? : ");
        }

        course.addSemesters(askSemesters());
    }

    public static void askModifyExam(Semester semester) {
        ArrayList<Exam> examsToRemove = new ArrayList<>();
        for (Exam exam : semester.getExams()) {
            System.out.println("Voulez-vous supprimer l'examen '" + exam + "' du semestre [y/n] ?");
            if (askYesOrNo()) {
                examsToRemove.add(exam);
            }
        }
        semester.removeExams(examsToRemove);
        System.out.println("Voulez-vous ajouter un examen [y/n] ?: ");
        semester.addExams(askExams());
    }

    public static void askModifyPeriod(Semester semester) {
        ArrayList<Period> periodsToRemove = new ArrayList<>();
        for (Period period : semester.getPeriods()) {
            System.out.println("Voulez-vous supprimer la période '" + period + "' du semestre [y/n] ?");
            if (askYesOrNo()) {
                periodsToRemove.add(period);
            }
        }
        semester.removePeriods(periodsToRemove);
        System.out.println("Voulez-vous ajouter une période [y/n] ?: ");
        semester.addPeriods(askPeriods());
    }

    public static int askFindSemester(Course course) {
        String userInput = scanner.nextLine();
        int index = getIndexOfSemester(course, userInput);

        while (index == -1) {
            System.out.println("Le semestre " + userInput + " n'a pas été trouvé, réessayez (ou 'stop')!");
            userInput = scanner.nextLine();

            if (userInput.equals("stop"))
                return -1;

            index = getIndexOfSemester(course, userInput);
        }
        return index;
    }

    public static int getIndexOfSemester(Course course, String semesterName) {
        for (int i=0; i<course.getSemesters().size(); i++) {
            if (course.getSemesters().get(i).getName().equals(semesterName)) {
                return i;
            }
        }
        return -1;
    }

    public static boolean askYesOrNo() {
        try {
            String userInput = scanner.nextLine().trim().toLowerCase();
            if (userInput.matches("[y|yes|oui]"))
                return true;
            if (userInput.matches("[n|no|non]"))
                return false;
            throw new IOException();
        } catch (Exception e) {
            System.out.println("Veuillez écrire 'y' ou 'n', réessayez!");
            return askYesOrNo();
        }
    }

    public static String getCoursesAsString() {
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < courses.size(); i++) {
            stringBuilder.append(courses.get(i).getAbbreviatedName());
            if (i < courses.size() - 1) {
                stringBuilder.append(", ");
            }
        }

        return stringBuilder.toString();
    }

    public static int askFindCourse() {
        String userInput = scanner.nextLine();
        int index = getIndexOfCourse(userInput);

        while (index == -1) {
            System.out.println("Le cours " + userInput + " n'a pas été trouvé, réessayez (ou 'stop')!");
            userInput = scanner.nextLine();

            if (userInput.equals("stop"))
                return -1;

            index = getIndexOfCourse(userInput);
        }
        return index;
    }

    public static void askDeleteCourse() {
        System.out.println("Quelle cours voulez-vous supprimer (ex: IFT1015) ?");
        int index = askFindCourse();

        if (index == -1)
            return;

        courses.remove(index);
        System.out.println("<!> Le cours " + userInput + " a bien été supprimé <!>");
    }

    public static int getIndexOfCourse(String courseName) {
        for (int i=0; i<courses.size(); i++) {
            if (courses.get(i).getAbbreviatedName().equals(courseName)) {
                return i;
            }
        }
        return -1;
    }

    public static String askCourseSubject() {
        try {
            String userInput = scanner.nextLine().trim().toUpperCase();
            if (userInput.matches("[A-Z]{3}"))
                return userInput;
            throw new IOException();
        } catch (Exception e) {
            System.out.println("Le sigle du cours doit etre composé de 3 lettres, réessayez!");
            return askCourseSubject();
        }
    }

    public static int askCourseValue() {
        try {
            String userInput = scanner.nextLine().trim().toUpperCase();
            if (userInput.matches("[0-9]{4}"))
                return Integer.parseInt(userInput);
            throw new IOException();
        } catch (Exception e) {
            System.out.println("Le valeur du cours est un entier entre 1000 et 9999, réessayez!");
            return askCourseValue();
        }
    }

    public static String askNoWhiteSpace() {
        try {
            String userInput = scanner.nextLine().trim().toUpperCase();
            if (!userInput.isBlank())
                return userInput;
            throw new IOException();
        } catch (Exception e) {
            System.out.println("L'entrée ne peut etre vide, réessayez!");
            return askNoWhiteSpace();
        }
    }

    public static int askCourseCredit() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (Exception e) {
            System.out.println("Le nombre de crédit doit etre un nombre, réessayez!");
            return askCourseCredit();
        }
    }

    public static void askAddCourse() {
        System.out.println("Quel est le sigle du cours (ex: IFT, MAT):");
        String subject = askCourseSubject();

        System.out.println("Quelle est la valeur du cours (ex: 1015, 1025):");
        int value = askCourseValue();

        System.out.println("Quel est le nom du cours (ex: Calcul 1, Programmation 2):");
        String name = askNoWhiteSpace();

        System.out.println("Quelle est la description du cours (ex: 'Concepts avancés : classes, objets, héritage, interfaces, réutilisation...'):");
        String description = scanner.nextLine();

        System.out.println("Combien de crédits comporte le cours cours:");
        int credit = askCourseCredit();

        System.out.println("Vous pouvez maintenant ajouteur un horaire pour un semestre.");
        ArrayList<Semester> semesters = askSemesters();

        Course course = new Course(subject, value, name, description, credit);
        course.addSemesters(semesters);

        courses.add(course);
        System.out.println("<!> Ajout de '" + course + "' <!>");
    }

    public static ArrayList<Semester> askSemesters() {
        ArrayList<Semester> semesters = new ArrayList<>();
        
        System.out.print("Voulez-vous ajouter un semestre [y/n] ? : ");

        while (askYesOrNo()) {
            Semester semester = askSemester();
            semesters.add(semester);
            System.out.println("<!> Ajout de '" + semester + "' <!>");
            System.out.print("Voulez-vous ajouter un autre semestre [y/n] ? : ");
        }

        return semesters;
    }

    public static Semester askSemester() {
        System.out.println("Quel semestre voulez-vous ajouter (ex: A24, H25):");
        String name = askSemesterName();

        System.out.println("Quelle est la date de début (format: YYYY-MM-DD):");
        LocalDate startDate = askDate();

        System.out.println("Quelle est la date de fin (format: YYYY-MM-DD):");
        LocalDate endDate = askDate();

        System.out.println("Voulez-vous ajouter des périodes au semestre [y/n]:");
        ArrayList<Period> periods = askPeriods();

        System.out.println("Voulez-vous ajouter des examens au semestre [y/n]:");
        ArrayList<Exam> exams = askExams();
        
        return new Semester(name, startDate, endDate, periods, exams);
    }

    public static String askSemesterName() {
        try {
            String userInput = scanner.nextLine();
            if (userInput.matches("[A|H|E][0-9]{2}"))
                return userInput;
            throw new IOException();
        } catch (Exception e) {
            System.out.println("Le semestre doit commencer par A, H ou E et finir par un nombre entre 01 et 99, réessayez!");
            return askSemesterName();
        }
    }

    public static ArrayList<Exam> askExams() {
        ArrayList<Exam> exams = new ArrayList<>();
        while (askYesOrNo()) {
            Exam exam = askExam();
            exams.add(exam);
            System.out.println("<!> Ajout de '" + exam + "' <!>");
            System.out.println("Voulez-vous ajouter d'autres examens [y/n] ?:");
        }
        return exams;
    }

    public static Exam askExam() {
        System.out.println("Quelle la date de l'examen (format: YYYY-MM-DD):");
        LocalDate date = askDate();

        System.out.println("Quelle le temps de début de l'examen (format: hh:mm):");
        LocalTime startTime = askTime();

        System.out.println("Quelle le temps de fin de l'examen (format: hh:mm):");
        LocalTime endTime = askTime();

        System.out.println("Quelle le type de l'examen (INTRA, FINAL, QUIZ):");
        ClassType classType = askClassType();

        System.out.println("Quelle est la section de l'examen (ex: A, B, A101):");
        String section = scanner.nextLine();

        return new Exam(date, startTime, endTime, classType, section);
    }

    public static ArrayList<Period> askPeriods() {
        ArrayList<Period> periods = new ArrayList<>();
        while (askYesOrNo()) {
            Period period = askPeriod();
            periods.add(period);
            System.out.println("<!> Ajout de '" + period + "' <!>");
            System.out.println("Voulez-vous ajouter d'autres périodes [y/n]?:");
        }
        return periods;
    }

    public static Period askPeriod() {
        System.out.println("Quelle le temps de début de la période (format: hh:mm):");
        LocalTime startTime = askTime();

        System.out.println("Quelle le temps de fin de la période (format: hh:mm):");
        LocalTime endTime = askTime();

        System.out.println("Quelle le jour de la période (MONDAY, TUESDAY, WEDNESDAY, ...):");
        DayOfWeek dayOfWeek = askDayOfWeek();

        System.out.println("Quelle le type de période (TP, TH, LAB):");
        ClassType classType = askClassType();

        System.out.println("Quelle est la section de la période (ex: A, B, A101):");
        String section = scanner.nextLine();

        return new Period(startTime, endTime, dayOfWeek, classType, section);
    }

    public static ClassType askClassType() {
        try {
            return ClassType.valueOf(scanner.nextLine());
        } catch (Exception e) {
            System.out.println("Le type de période est invalide, réessayez!");
            return askClassType();
        }
    }

    public static DayOfWeek askDayOfWeek() {
        try {
            return DayOfWeek.valueOf(scanner.nextLine());
        } catch (Exception e) {
            System.out.println("Le jour de la semaine est invalide, réessayez!");
            return askDayOfWeek();
        }
    }

    public static LocalTime askTime() {
        try {
            return LocalTime.parse(scanner.nextLine());
        } catch (Exception e) {
            System.out.println("Le temps entré est invalide, réessayez!");
            return askTime();
        }
    }

    public static LocalDate askDate() {
        try {
            return LocalDate.parse(scanner.nextLine());
        } catch (Exception e) {
            System.out.println("La date entrée est invalide, réessayez!");
            return askDate();
        }
    }

    public static void askCourseDetails() {
        System.out.println("Entrez le code du cours à regarder (ex: IFT1015):");
        userInput = scanner.nextLine();

        boolean courseFind = false;
        for (Course course : courses) {
            if (course.getAbbreviatedName().equals(userInput)) {
                System.err.println(course);
                courseFind = true;
            }
        }
        if (!courseFind)
            System.out.println("Aucun cours " + userInput + " n'a été trouvé, essayez à nouveau ");
    }

    /**
     * C'est le menu de la gestion des cours
     * 
     * @return String - Le format du menu
     */
    public static String getGestionCoursMessage() {
        return "Entrez: (1) pour voir tous les cours enregistrés"
                + "\n\t(2) pour regarder les caractéristiques d'un cours"
                + "\n\t(3) pour ajouter un nouveau cours"
                + "\n\t(4) pour modifier un cours existant"
                + "\n\t(5) pour supprimer un cours existant"
                + "\n\t(back) pour revenir au menu principal";
    }

    /**
     * C'est le menu de la gestion des étudiants
     * 
     * @return String - Le format du menu
     */
    public static String getGestionEtudiantMessage() {
        return "Entrez: (1) pour afficher tous les étudiants"
                + "\n\t(2) pour ajouter un étudiant"
                + "\n\t(3) pour modifier un étudiant"
                + "\n\t(4) pour supprimer un étudiant"
                + "\n\t(5) pour voir l'horaire d'un étudiant"
                + "\n\t(6) pour ajouter un cours à un étudiant"
                + "\n\t(7) pour supprimer un cours à un étudiant"
                + "\n\t(8) pour générer un horaire pour un étudiant (tous / le meilleur / ...)"
                + "\n\t(back) pour revenir au menu principal";
    }

    /**
     * C'est le menu de la gestion des modification sur un étudiant
     * 
     * @return String - Le format du menu
     */
    public static String getModifyStudentMessage(Student student) {
        return "Vous voulez modifier l'étudiant: " + student
                + "\nEntrez: (1) pour modifer son prénom"
                + "\n\t(2) pour modifier son nom de famille"
                + "\n\t(3) pour modifier son matricule"
                + "\n\t(back) pour revenir au gestionnaire d'étudiant";
    }

    /**
     * C'est le menu de la gestion de la génération des horaires
     * 
     * @return String - Le format du menu
     */
    public static String getScheduleType() {
        return "Entrez: (1) pour obtenir toutes les horaires possibles"
                + "\n\t(2) pour obtenir toutes les horaires sans conflit"
                + "\n\t(3) pour obtenir le meilleur horaire"
                + "\n\t(back) pour revenir au gestionnaire d'étudiant";
    }

    /**
     * C'est la fonction qui gère les entrées de l'utilisateur face au menu de la gestion des étudiants
     */
    public static void askGestionEtudiants() {
        // On affiche le menu
        System.out.println(getGestionEtudiantMessage());
        // Tant que l'utilisateur n'entre pas "back"
        while (!(userInput = scanner.nextLine()).equalsIgnoreCase("back")) {
            // On vérifie l'entrée
            switch (userInput) {
                case "1":
                    for (Student student : students)
                        System.out.println(student);
                    break;

                case "2":
                    askAddStudent();
                    break;

                case "3":
                    askModifyStudent();
                    break;

                case "4":
                    askDeleteStudent();
                    break;

                case "5":
                    askSeeSchedule();
                    break;

                case "6":
                    askAddSchedule();
                    break;

                case "7":
                    askDeleteSchedule();
                    break;

                case "8":
                    askGenerateSchedule();
                    break;

            }
            // On réaffiche le menu de gestion des étudiants
            System.out.println(getGestionEtudiantMessage());
        }
    }

    /**
     * Cette méthode gère l'ajout d'un étudiant
     */
    public static void askAddStudent() {
        System.out.println("Quel est le prénom de l'étudiant");
        String firstname = scanner.nextLine();

        System.out.println("Quelle est le nom de famille de l'étudiant");
        String lastname = scanner.nextLine();

        System.out.println("Quel est le matricule de l'étudiant");
        String matricule = scanner.nextLine();

        Student nouvelEtudiant = new Student(firstname, lastname, matricule);
        students.add(nouvelEtudiant);
    }
    
    /**
     * Cette fonction est un algorithme qui demande le matricule d'un étudiant et vérifie s'il correspond
     * au matricule d'une des étudiants de l'attribut "students". Si c'est le cas, on retourne l'étudiant.
     * Sinon, on offre la possibilité à l'utilisateur de soit réessayer en tappant "oui" ou bien abandonné
     * en tappant n'importe quoi d'autre. Si aucun étudiant n'est finalement choisi, on retourne null.
     * 
     * @return Student - Retourne l'étudiant choisi ou null si aucun n'est finalement choisi
     */
    public static Student validateStudent() {
        // Initialisation des variables
        boolean vu = false;
        Student etudiantActif = null;
        boolean safe = false;

        while (!vu) {
            if (safe)
                break;
            
            System.out.println("Quel est le matricule de l'étudiant?");
            String matricule = scanner.nextLine();
            
            // On cherche l'étudiant avec le matricule
            for (Student student : students) {
                if (student.getMatricule().equals(matricule)) {
                    etudiantActif = student;
                    vu = true;
                }
            }
            // Si on ne le trouve pas, on demande de réessayer ou de quitter
            if (!vu) {
                System.out.println("Matricule non-trouvé. \nEntrez:\t(oui) Pour Réessayer \n\t(N'importe quoi) Pour sortir");
                String retry = scanner.nextLine();
                if (!retry.equalsIgnoreCase("oui")) {
                    safe = true;
                }
            }
        }
        return etudiantActif;
    }

    /**
     * Cette méthode gère la modification d'un étudiant grâce à un menu qui propose à l'utilisateur ce qu'il
     * veut choisir de modifier (Prénom, nom ou matricule). Il a aussi l'option de revenir en arrière.
     */
    public static void askModifyStudent() {
         // On demande à l'utilisateur de choisir l'étudiant à modifier
        Student etudiantActif = validateStudent();
        
        // Si l'étudiant existe...
        if (etudiantActif != null) {
            // On affiche le menu de modification d'un étudiant
            System.out.println(getModifyStudentMessage(etudiantActif));
            // Tant qu'on n'entre pas back
            while (!(userInput = scanner.nextLine()).equalsIgnoreCase("back")) {
                switch (userInput) {
                    // On modifie le prénom
                    case "1":
                        System.out.println("Quel est son nouveau prénom?");
                        String firstname = scanner.nextLine();
                        etudiantActif.setFirstname(firstname);
                        break;

                    // On modifie le nom de famille
                    case "2":
                        System.out.println("Quel est son nouveau nom de famille?");
                        String lastname = scanner.nextLine();
                        etudiantActif.setLastname(lastname);
                        break;

                    // On modifie le matricule
                    case "3":
                        System.out.println("Quel est son nouveau matricule?");
                        String matricule = scanner.nextLine();
                        etudiantActif.setMatricule(matricule);
                        break;
                }
                // On demande la prochaine modification
                System.out.println("Quoi d'autre?");
                System.out.println(getModifyStudentMessage(etudiantActif));
            }
        }
    }

    /**
     * Cette méthode gère la suppression d'un étudiant.
     */
    public static void askDeleteStudent() {
        boolean vu = false;
        int index = -1;
        System.out.println("Quel est le matricule de l'étudiant à supprimer?");
        String matricule = scanner.nextLine();

        // On  parcours les étudiants et on trouve l'index
        for (Student student : students) {
            index++;
            if (student.getMatricule().equals(matricule)) {
                vu = true;
                break;
            }
        }
        // On retire l'étudiant ou présente une erreur
        if (!vu) {
            System.out.println("Matricule non-trouvée. Aucun étudiant n'a été supprimé!");
        } else {
            students.remove(index);
            System.out.println("L'étudiant avec le matricule " + matricule + " a été supprimé!");
        }
    }

    /**
     * Cette méthode gère l'affichage de l'horaire d'un étudiant
     */
    public static void askSeeSchedule() {
        boolean vu = false;
        System.out.println("Quel est le matricule de l'étudiant dont vous voulez voir l'horaire?");
        String matricule = scanner.nextLine();

        for (Student student : students) {
            if (student.getMatricule().equals(matricule)) {
                student.getSchedule().printScheduleGrid();
                vu = true;
            }
        }
        if (!vu)
            System.out.println("Matricule non-trouvée");

    }

    /**
     * Cette méthode gère l'ajout de cours à un horaire d'étudiant en vérifiant si l'ajout crée
     * un conflit d'horaire ou s'il y a déjà le maximum de 10 cours par horaire
     */
    public static void askAddSchedule() {
        Student etudiantActif = validateStudent();
        Course coursActif = null;
        
        // Vérification que l'horaire à moins de 10 cours pour faire le traitement
        if (etudiantActif.getSchedule().getCourses().size() < 10) {
            try {
                // Si l'étudiant est "null", ça plante
                Schedule schedule = etudiantActif.getSchedule();

                System.out.println("Entrez le code du cours à ajouter (ex: IFT1015):");
                String SCours = scanner.nextLine();
                
                for (Course course : courses) {
                    if (course.getAbbreviatedName().equalsIgnoreCase(SCours))
                        coursActif = course;

                }
                System.out.println("Entrez le nom de la session (ex: A24)");
                String sessionName = scanner.nextLine();

                // Si le cours est "null" ou que la session n'est pas bonne, ça plante
                schedule.addCourse(coursActif, sessionName);

                // S'il y a un conflit d'horaire, on demande à l'usagé ce qu'il veut faire
                if (etudiantActif.getSchedule().hasConflict()) {
                    System.out.println("Il y a un conflit d'horaire! Voulez vous toujours ajouter le cours?\n " +
                            "Entrez: (oui) Pour l'ajouter quand même"
                            + "\n\t (N'importe quoi) pour ne pas l'ajouter");
                    String answer = scanner.nextLine();

                    if (!answer.equalsIgnoreCase("oui")) {
                        schedule.removeCourse(coursActif, sessionName);
                        System.out.println("Le cours n'a pas été ajouté");
                    } else {
                        System.out.println("Le cours a été ajouté avec succès");
                    }
                } else {
                    System.out.println("Le cours a été ajouté avec succès");
                }
            } catch (Exception e) {
            System.out.println("Les informations sont érronées! Aucun cours n'a été ajouté");
            }
        } else {
            System.out.println("L'horaire est déjà composé de 10 cours, soit le maximum!");
        }
    }

    /**
     * Cette méthode gère la suppression de cours à un horaire d'étudiant
     */
    public static void askDeleteSchedule() {
        Student etudiantActif = validateStudent();
        Course coursActif = null;

        try {
            // Si l'étudiant est "null", ça plante
            Schedule schedule = etudiantActif.getSchedule();

            System.out.println("Entrez le code du cours à supprimer (ex: IFT1015):");
            String SCours = scanner.nextLine();
            
            for (Course course : courses) {
                if (course.getAbbreviatedName().equalsIgnoreCase(SCours))
                    coursActif = course;

            }

            System.out.println("Entrez le nom de la session (ex: A24)");
            String sessionName = scanner.nextLine();

            // Si le cours est "null" ou la session erronée, ça plante
            if (schedule.getCourses().contains(coursActif)) {
                
                schedule.removeCourse(coursActif, sessionName);
                System.out.println("Le cours a été supprimé avec succès");
            } else {
                System.out.println("Le cours n'est pas à l'horaire!");
            }

        } catch (Exception e) {
            System.out.println("Les informations sont érronées! Aucun cours n'a été supprimé");
        }
    }

    /**
     * Cette méthode gère la génération automatique d'horaire d'étudiant. On peut choisir de générer
     * tous les horaires possibles pour en choisir 1 parmi la liste, tous les horaires sans conflits
     * possible pour en choisir un parmi la liste ou bien le meilleur horaire selon des critères 
     * préétablis qui sera ajouter automatique à l'étudiant.
     */
    public static void askGenerateSchedule() {
        Student etudiantActif = validateStudent();

        System.out.println(getScheduleType());
        while (!(userInput = scanner.nextLine()).equalsIgnoreCase("back")) {
            switch (userInput) {
                case "1":
                    askAllSchedule(etudiantActif);
                    break;

                case "2":
                    askSuitableSchedule(etudiantActif);
                    break;
                case "3":
                    askBestSchedule(etudiantActif);
                    break;
            }
            System.out.println(getScheduleType());
        }
    }

    /**
     * Cette fonction demande à l'utilisateur d'entrer les cours candidats pour la génération de l'horaire.
     * Si on entre (all), on ajoute tous les cours disponibles. Sinon, on peut les entrer 1 à 1 ou bien quitter
     * 
     * @return ArrayList<Course> - Retourne une liste des cours candidats
     */
    public static ArrayList<Course> choiceCoursCandidats() {
        ArrayList<Course> coursCandidats = new ArrayList<>();
        // On présente le menu des choix possibles
        System.out.println("Quels sont les cours candidats?\n Entrer: (All) Pour tous les cours disponibles " +
                "\n\t (Le nom) Pour entrer les nom 1 par 1" +
                "\n\t (End) pour finir");
        String SCours;

        while (!(SCours = scanner.nextLine()).equalsIgnoreCase("End")) {
            boolean vu = false;

            if (SCours.equalsIgnoreCase("All"))
                return courses;

            // On va chercher le cours
            for (Course course : courses) {
                if (course.getAbbreviatedName().equalsIgnoreCase(SCours)) {
                    coursCandidats.add(course);
                    vu = true;
                    break;
                }
            }

            if (!vu) {
                System.out.println("Le cours n'existe pas!");
            }
            // On redemande les prochains cours à ajouter
            System.out.println("Quels sont les autres cours candidats? Entrer le nom" +
             "\n\t (End) pour finir" + "\n\t (ALL) pour tous les cours");
        }
        return coursCandidats;
    }

    /**
     * Cette fonction gère le demande de tous les horaires possibles en fonction de la 
     * session de demandée et des cours candidats
     * 
     * @param student Student - Il s'agit de l'étudiant pour lequel on génère les horaires
     */
    public static void askAllSchedule(Student student) {
        // On demande les cours candidats
        ArrayList<Course> coursCandidats = choiceCoursCandidats();
        List<Schedule> result;

        System.out.println("Quel est le nom de la session (ex: A24)");
        String sessionName = scanner.nextLine();

        result = Schedule.generateAllPossibleSchedules(coursCandidats, sessionName);

        // On numérote les horaires générées
        for (int i=0; i<result.size(); i++) {
            System.out.println("Horaire " + (i+1) + ": ");
            result.get(i).printScheduleGrid();
        }

        boolean choiceB = false;
        while (!choiceB) {
            try {
                System.out.println("Laquelle voulez-vous? (Entrer le numéro)");
                int choice = askInt();

                // On ajuste l'horaire selon le choix
                student.setSchedule(result.get(choice - 1));
                choiceB = true;
            } catch(IndexOutOfBoundsException e) {
                System.out.println("Le numéro est invalide!");
            }
        }
    }

    /**
     * Cette fonction gère le demande de tous les horaires possibles sans conflit en fonction de la 
     * session de demandée et des cours candidats. 
     * 
     * @param student Student - Il s'agit de l'étudiant pour lequel on génère les horaires
     */
    public static void askSuitableSchedule(Student student) {
        /* La fonction est la même que la précédente à l'exception de l'utilisation de la
         * fonction "genarateSuitableSchedules" au lieu de "genarateAllPossibleSchedules"
         * et du fait qu'on demande un nombre de crédit maximal et minimal pour les horaires
         */
        ArrayList<Course> coursCandidats = choiceCoursCandidats();
        List<Schedule> result;

        System.out.println("Quel est le nombre de crédits minimal pour la session?");
        int creditMin = askInt();

        System.out.println("Quel est le nombre de crédits maximal pour la session?");
        int creditMax = askInt();

        System.out.println("Quel est le nom de la session (ex: A24)");
        String sessionName = scanner.nextLine();

        result = Schedule.genarateSuitableSchedules(coursCandidats, creditMin, creditMax, sessionName);

        for (int i=0; i<result.size(); i++) {
            System.out.println("Horaire " + (i+1) + ": ");
            result.get(i).printScheduleGrid();
        }

        boolean choiceB = false;
        while (!choiceB) {
            try {
                System.out.println("Laquelle voulez-vous? (Entrer le numéro)");
                int choice = askInt();

                // On ajuste l'horaire selon le choix
                student.setSchedule(result.get(choice - 1));
                choiceB = true;
            } catch(IndexOutOfBoundsException e) {
                System.out.println("Le numéro est invalide!");
            }
        }
    }

    /**
     * Cette fonction gère le demande de la meilleure horaire possible en fonction de la 
     * session de demandée et des cours candidats. 
     * 
     * @param student Student - Il s'agit de l'étudiant pour lequel on génère les horaires
     */
    public static void askBestSchedule(Student student) {
        ArrayList<Course> coursCandidats = choiceCoursCandidats();
        Schedule result;

        System.out.println("Quel est le nombre de crédits minimal pour la session?");
        int creditMin = askInt();

        System.out.println("Quel est le nombre de crédits maximal pour la session?");
        int creditMax = askInt();

        System.out.println("Quel est le nom de la session (ex: A24)");
        String sessionName = scanner.nextLine();

        result = Schedule.genarateBestSchedule(coursCandidats, creditMin, creditMax, sessionName);

        result.printScheduleGrid();

        student.setSchedule(result);
    }
    
    /**
     * Cette fonction demande à l'utilisateur d'entrer un nombre entié et permet
     * de retirer certain bug que l'on a avec la fonction "scanner.nextInt"
     * 
     * @return int - L'entier choisi
     */
    public static int askInt() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (Exception e) {
            System.out.println("Entrez un nombre valide, réessayez!");
            return askInt();
        }
    }
}