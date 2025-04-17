import java.util.ArrayList;
import java.util.Scanner;   //for user input

public class UserManager {

    // Gut: Zentralisierte Scanner-Instanz für die gesamte Klasse
    public static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        ArrayList<User> userList = new ArrayList<>();
        // Gut: Sicherstellung, dass der erste Benutzer immer der Admin ist
        userList.add(new Admin("admin", ""));
        User curUser = userList.get(0);

        Start(curUser);
        while (true) {
            curUser = Menu(userList, curUser, sc); // Gut: Flexible Navigation im Hauptmenü
        }
    }

    // Gut: Start-Methode für Initialisierung des Admins
    // Verbesserung: Eingabevalidierung für das Passwort hinzufügen
    public static void Start(User userAdmin) {
        System.out.println("\nHello, " + userAdmin.getUserName() + "!");
        System.out.println("Please set a password for admin account");
        System.out.print("password: ");
        userAdmin.setPassword(sc.next());
    }

    // Gut: Unterscheidung zwischen Admin- und Benutzer-Menü
    // Verbesserung: Teile des Codes könnten zusammengeführt werden, um Redundanz zu vermeiden
    public static User Menu(ArrayList<User> userList, User curUser, Scanner sc) {
        if (curUser instanceof Admin) {
            Admin admin = (Admin) curUser; // Downcasting
            System.out.println("\nHello, " + curUser.getUserName() + "!");

            while (true) {
                System.out.println("\n===== Menu =====");
                System.out.println("1: show all users");
                System.out.println("2: change user");
                System.out.println("3: add user");
                System.out.println("4: remove user");
                System.out.println("5: change current user's password");
                System.out.println("6: exit program");
                System.out.print("current user: ");
                System.out.println(curUser.getUserName());
                System.out.println("================");
                System.out.print("Choose an option: ");

                if (sc.hasNextInt()) { // Gut: Überprüfung auf gültige Eingabe
                    int choice = sc.nextInt();
                    switch (choice) {
                        case 1:
                            return admin.ShowAllUser(userList, curUser, sc); // Gut: Admin-spezifische Funktionen
                        case 2:
                            return admin.ChangeUser(userList, curUser, sc);
                        case 3:
                            return admin.AddUser(userList, curUser, sc);
                        case 4:
                            return admin.RemoveUser(userList, curUser, sc);
                        case 5:
                            return admin.ChangePassword(curUser, sc);
                        case 6:
                            return admin.ExitProgramm();
                        default:
                            System.out.println("\nInvalid option"); // Gut: Rückmeldung für ungültige Eingabe
                            break;
                    }
                } else {
                    System.out.println("\nInvalid option");
                    sc.next(); // Verbesserung: Zusätzliche Rückmeldung für ungültige Eingaben
                }
            }
        }

        // Menü für Standard-Benutzer
        while (true) {
            System.out.println("\n===== Menu =====");
            System.out.println("1: show all users");
            System.out.println("2: change user");
            System.out.println("3: add user");
            System.out.println("4: change current user's password");
            System.out.println("5: exit program");
            System.out.print("current user: ");
            System.out.println(curUser.getUserName());
            System.out.println("================");
            System.out.print("Choose an option: ");

            if (sc.hasNextInt()) { // Gut: Validierung der Benutzereingabe
                int choice = sc.nextInt();
                switch (choice) {
                    case 1:
                        return curUser.ShowAllUser(userList, curUser, sc);
                    case 2:
                        return curUser.ChangeUser(userList, curUser, sc);
                    case 3:
                        return curUser.AddUser(userList, curUser, sc);
                    case 4:
                        return curUser.ChangePassword(curUser, sc);
                    case 5:
                        return curUser.ExitProgramm();
                    default:
                        System.out.println("\nInvalid option"); // Gut: Rückmeldung für ungültige Auswahl
                        break;
                }
            } else {
                System.out.println("\nInvalid option");
                sc.next(); // Verbesserung: Zusätzliche Bearbeitung für fehlerhafte Eingaben
            }
        }
    }

    // Gut: Allgemeine Wartefunktion für Benutzerinteraktion
    // Verbesserung: Sollte nur eine Zeile von sc.nextLine() benötigen, um Eingaben abzufangen
    public static void WaitAnyInput() {
        System.out.println("\nPress Enter key to continue...");
        sc.nextLine();
        sc.nextLine();
    }
}

/*
Der Code ist solide strukturiert, sicher und benutzerfreundlich, mit klarer Aufgabenverteilung und bewährten Sicherheitsstandards wie Salt und Hashing.
Kleinere Verbesserungen wie die Reduktion von Redundanzen im Menü, robustere Eingabevalidierung und optimiertes Ressourcenmanagement
könnten ihn noch effizienter und erweiterbarer machen. Super gemacht, sehr Gute Arbeit!
 */
