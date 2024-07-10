import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class App {
    static DbManager dbManager = new DbManager();
    static ArrayList<Course> courses = dbManager.getAllCourses();
    static Scanner scanner = new Scanner(System.in);
    static String userInput;
    static ArrayList<Student> students = new ArrayList<>();

    public static void main(String[] args) {

        System.out.println(getStartingMessage());

        while (!(userInput = scanner.nextLine()).equalsIgnoreCase("stop")) {
            switch (userInput) {
                case "1":
                    askGestionCours();
                    break;

                case "2":
                    askGestionEtudiants();
                    break;
            }
            System.out.println(getStartingMessage());
        }

        dbManager.addAllCourses(courses);

        // voir tous les étudiants
        // ajouter un étudiant
        // modifier un étudiant
        // supprimer un étudiant
        // voir l'horaire d'un étudiant
        // ajouter / supprimer un cours à un étudiant
        // générer un horaire pour un étudiant (tous / le meilleur / ...)
        //   -> à partir de tous les cours ou d'une sélection

        //System.out.println(students);
    }

    public static void askGestionCours() {
        System.out.println(getGestionCoursMessage());
        while (!(userInput = scanner.nextLine()).equalsIgnoreCase("back")) {
            switch (userInput) {
                case "1":
                    System.out.println(Arrays.asList(courses.toString()));
                    break;

                case "2":
                    askCourseDetails();
                    break;
                case "3":
                    askAddCourse();
                    break;
            }
            System.out.println(getGestionCoursMessage());
        }
    }

    public static void askAddCourse() {
        System.out.println("Quel est le sigle du cours (ex: IFT, MAT):");
        String subject = scanner.nextLine();
        System.out.println("Quelle est la valeur du cours (ex: 1015, 1025):");
        int value = scanner.nextInt();
        System.out.println("Quel est le nom du cours (ex: Calcul 1, Programmation 2):");
        String name = scanner.nextLine();
        System.out.println("Quelle est la description du cours (ex: 'Concepts avancés : classes, objets, héritage, interfaces, réutilisation...'):");
        String description = scanner.nextLine();
        System.out.println("Combien de crédits comport le cours cours:");
        int credit = scanner.nextInt();
        ArrayList<Semester> semesters = new ArrayList<>();
        ArrayList<Course> prerequisites = new ArrayList<>();
        System.out.println();
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

    public static String getGestionCoursMessage() {
        return "Entrez: (1) pour voir tous les cours enregistrés"
                + "\n\t(2) pour regarder les caractéristiques d'un cours"
                + "\n\t(3) pour ajouter un nouveau cours"
                + "\n\t(4) pour modifier un cours existant"
                + "\n\t(5) pour supprimer un cours existant"
                + "\n\t(back) pour revenir au menu principal";
    }

    public static String getStartingMessage() {
        return "Entrez: (1) pour accéder à la gestion des cours"
                + "\n\t(2) pour accéder à la gestion des horaires d'étudiants"
                + "\n\t(stop) pour arreter le programme";
    }

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

    public static String getModifyStudentMessage(Student student) {
        return "Vous voulez modifier l'étudiant: " + student
                + "\nEntrez: (1) pour modifer son prénom"
                + "\n\t(2) pour modifier son nom de famille"
                + "\n\t(3) pour modifier son matricule"
                + "\n\t(4) pour modifier ses cours réussis"
                + "\n\t(back) pour revenir au gestionnaire d'étudiant";
    }

    public static String getScheduleType() {
        return "Entrez: (1) pour obtenir toutes les horaires possibles"
                + "\n\t(2) pour obtenir toutes les horaires sans conflit"
                + "\n\t(3) pour obtenir le meilleur horaire";
    }

    public static void askGestionEtudiants() {
        System.out.println(getGestionEtudiantMessage());
        while (!(userInput = scanner.nextLine()).equalsIgnoreCase("back")) {
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

                case "8":
                    askGenerateSchedule();
                    break;

            }
            System.out.println(getGestionEtudiantMessage());
        }
    }

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

    public static Student validateStudent() {
        boolean vu = false;
        Student etudiantActif = null;
        boolean safe = false;

        while (!vu) {
            if (safe)
                break;

            System.out.println("Quel est le matricule de l'étudiant à modifier?");
            String matricule = scanner.nextLine();

            for (Student student : students) {
                if (student.getMatricule().equals(matricule)) {
                    etudiantActif = student;
                    vu = true;
                }
            }
            if (!vu) {
                System.out.println("Matricule non-trouvé. \nEntrez: (oui) Pour Réessayer \n\t (N'importe quoi) Pour sortir");
                String retry = scanner.nextLine();
                if (!retry.equalsIgnoreCase("oui")) {
                    safe = true;
                }
            }
        }
        return etudiantActif;
    }

    public static void askModifyStudent() {
        Student etudiantActif = validateStudent();

        if (etudiantActif != null) {
            System.out.println(getModifyStudentMessage(etudiantActif));
            while (!(userInput = scanner.nextLine()).equalsIgnoreCase("back")) {
                switch (userInput) {
                    case "1":
                        System.out.println("Quel est son nouveau prénom?");
                        String firstname = scanner.nextLine();
                        etudiantActif.setFirstname(firstname);
                        break;

                    case "2":
                        System.out.println("Quel est son nouveau nom de famille?");
                        String lastname = scanner.nextLine();
                        etudiantActif.setLastname(lastname);
                        break;

                    case "3":
                        System.out.println("Quel est son nouveau matricule?");
                        String matricule = scanner.nextLine();
                        etudiantActif.setMatricule(matricule);
                        break;

                    case "4":
                        //À faire
                        break;

                }
                System.out.println("Quoi d'autre?");
                System.out.println(getModifyStudentMessage(etudiantActif));
            }
        }
    }

    public static void askDeleteStudent() {
        boolean vu = false;
        int index = -1;
        System.out.println("Quel est le matricule de l'étudiant à supprimer?");
        String matricule = scanner.nextLine();

        for (Student student : students) {
            index++;
            if (student.getMatricule().equals(matricule)) {
                vu = true;
                break;
            }
        }
        if (!vu) {
            System.out.println("Matricule non-trouvée. Aucun étudiant n'a été supprimé!");
        } else {
            students.remove(index);
        }
    }

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

    public static void askAddSchedule() {
        Student etudiantActif = validateStudent();
        Course coursActif = null;

        try {
            Schedule schedule = etudiantActif.getSchedule();


            System.out.println("Entrez le code du cours à ajouter (ex: IFT1015):");
            String SCours = scanner.nextLine();
            // À FAIRE: Gérer mieux les erreurs
            for (Course course : courses) {
                if (course.getAbbreviatedName().equalsIgnoreCase(SCours))
                    coursActif = course;

            }
            System.out.println("Entrez le nom de la session (ex: A24)");
            String sessionName = scanner.nextLine();

            schedule.addCourse(coursActif, sessionName);

            if (etudiantActif.getSchedule().hasConflict()) {
                System.out.println("Il y a un conflit d'horaire! Voulez vous toujours ajouter le cours?\n " +
                        "Entrez: (oui) Pour l'ajouter quand même"
                        + "\n\t (N'importe quoi) pour ne pas l'ajouter");
                String answer = scanner.nextLine();

                if (!answer.equalsIgnoreCase("oui"))
                    schedule.removeClass(coursActif, sessionName);
            }
        } catch (Exception e) {
            System.out.println("Les informations sont érronées! Aucun cours n'a été ajouté");
        }
    }

    public static void askDeleteSchedule() {
        Student etudiantActif = validateStudent();
        Course coursActif = null;

        try {
            Schedule schedule = etudiantActif.getSchedule();


            System.out.println("Entrez le code du cours à supprimer (ex: IFT1015):");
            String SCours = scanner.nextLine();
            // À FAIRE: Gérer mieux les erreurs
            for (Course course : courses) {
                if (course.getAbbreviatedName().equalsIgnoreCase(SCours))
                    coursActif = course;

            }
            System.out.println("Entrez le nom de la session (ex: A24)");
            String sessionName = scanner.nextLine();

            schedule.removeClass(coursActif, sessionName);
        } catch (Exception e) {
            System.out.println("Les informations sont érronées! Aucun cours n'a été supprimé");
        }
    }

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
            System.out.println(getGestionCoursMessage());
        }
    }

    public static ArrayList<Course> choiceCoursCandidats() {
        ArrayList<Course> coursCandidats = new ArrayList<>();
        System.out.println("Quels sont les cours candidats?\n Entrer: (All) Pour tous les cours disponibles " +
                "\n\t (Le nom) Pour entrer les nom 1 par 1" +
                "\n\t (stop) pour finir");
        String SCours;
        List<Schedule> result;

        while (!(SCours = scanner.nextLine()).equalsIgnoreCase("stop")) {

            if (SCours.equalsIgnoreCase("All"))
                return courses;

            for (Course course : courses) {
                if (course.getAbbreviatedName().equalsIgnoreCase(SCours)) {
                    coursCandidats.add(course);
                    break;
                }
                System.out.println("Quels sont les cours candidats? Entrer le nom; (stop) pour finir");
            }
        }
        return coursCandidats;
    }

    public static void askAllSchedule(Student student) {
        ArrayList<Course> coursCandidats = choiceCoursCandidats();
        List<Schedule> result;

        System.out.println("Quel est le nom de la session (ex: A24)");
        String sessionName = scanner.nextLine();

        result = Schedule.generateAllPossibleSchedules(coursCandidats, sessionName);

        for (Schedule schedule : result) {
            schedule.printScheduleGrid();
        }

        System.out.println("Laquelle voulez-vous? (Entrer le numéro)");
        int choice = scanner.nextInt();

        student.setSchedule(result.get(choice + 1));
    }

    public static void askSuitableSchedule(Student student) {
        ArrayList<Course> coursCandidats = choiceCoursCandidats();
        List<Schedule> result;

        System.out.println("Quel est le nombre de crédits minimal pour la session?");
        int creditMin = scanner.nextInt();

        System.out.println("Quel est le nombre de crédits maximal pour la session?");
        int creditMax = scanner.nextInt();

        System.out.println("Quel est le nom de la session (ex: A24)");
        String sessionName = scanner.nextLine();

        result = Schedule.genarateSuitableSchedules(coursCandidats, creditMin, creditMax, sessionName);

        for (Schedule schedule : result) {
            schedule.printScheduleGrid();
        }

        System.out.println("Laquelle voulez-vous? (Entrer le numéro)");
        int choice = scanner.nextInt();

        student.setSchedule(result.get(choice + 1));
    }

    public static void askBestSchedule(Student student) {
        ArrayList<Course> coursCandidats = choiceCoursCandidats();
        Schedule result;

        System.out.println("Quel est le nombre de crédits minimal pour la session?");
        int creditMin = scanner.nextInt();

        System.out.println("Quel est le nombre de crédits maximal pour la session?");
        int creditMax = scanner.nextInt();

        System.out.println("Quel est le nom de la session (ex: A24)");
        String sessionName = scanner.nextLine();

        result = Schedule.genarateBestSchedule(coursCandidats, creditMin, creditMax, sessionName);

        result.printScheduleGrid();

        student.setSchedule(result);
    }
}