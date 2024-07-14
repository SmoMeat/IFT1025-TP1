/**
 * La classe Test sert de point d'entrée pour exécuter les tests des différentes classes du projet.
 * Cette classe contient une méthode main qui exécute les méthodes de test des classes du programme.
 * 
 * 
 * >>> del *.class; javac Test.java -d .\bin\; java -ea -classpath "bin;lib/*" Test
 */

public class Test {
    /**
     * Le point d'entrée principal de l'application. Cette méthode exécute les tests des différentes classes.
     *
     * @param args les arguments de la ligne de commande (non utilisés)
     */
    public static void main(String[] args) {
        Course.runTests();
        Exam.runTests();
        Period.runTests();
        Schedule.runTests();
        SchedulePeriod.runTests();
        Semester.runTests();
        Student.runTests();
    }
}
