import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class App {
    static DbManager dbManager = new DbManager();
    static ArrayList<Course> courses = dbManager.getAllCourses();
    static Scanner scanner = new Scanner(System.in);
    static String userInput;

    public static void main(String[] args) {

        System.out.println(getStartingMessage());
        
        while (!(userInput = scanner.nextLine()).equalsIgnoreCase("stop")) {
            switch (userInput) {
                case "1":
                    askGestionCours();
                    break;
            
                case "2":
                    askGestionÉtudiants();
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

    
    public static void askGestionÉtudiants() {
        System.out.println("222222222222");
    }

    public static String getGestionCoursMessage() {
        return "Entrez: (1) pour voir tous les cours enregistrés"
                 + "\n\t(2) pour regarder les caractéristiques d'un cours"
                 + "\n\t(3) pour ajouter un nouveau cours"
                 + "\n\t(4) pour modifier un cours existant"
                 + "\n\t(5) pour supprimer un cours existant"
                 + "\n\t(back) pour revenir au menu principale";
    }

    public static String getStartingMessage() {
        return "Entrez: (1) pour accéder à la gestion des cours"
                 + "\n\t(2) pour accéder à la gestion des horaires d'étudiants"
                 + "\n\t(stop) pour arreter le programme";
    }

}
