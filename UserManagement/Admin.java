import java.util.ArrayList;
import java.util.Scanner;

public class Admin extends User {
    public Admin(String name, String password) {
        super(name, password);
    }

    // Gut: Klare Trennung der Verantwortlichkeiten in eigenen Methoden
    // Verbesserung: Statt eine Exception bei falschen Eingaben auszulösen, wäre eine bessere Validierung vor der Aktion sinnvoll
    public User RemoveUser(ArrayList<User> userList, User curUser, Scanner sc){
        int i = 0;
        System.out.println("\n===== User List =====");
        System.out.println("index | user name");
        System.out.println("---------------------");
        for(User user : userList) {
            System.out.println(i + "     |" + user.getUserName());
            i++;
        }
        System.out.println("=====================");
        System.out.println("\nWhich user do you want to delete?");
        System.out.print("index number: ");
        int deletedIndex = sc.nextInt();
        // Gut: Admin-Schutz verhindert Löschung des Administrators
        if(deletedIndex == 0){
            System.out.println("\nAdmin account cannot be deleted");
        } else if(deletedIndex > userList.size() - 1){
            // Verbesserung: Ausgabe spezifischer Fehler und Validierung der Benutzer-Eingabe
            System.out.println("\nIndex is out of range");
        } else {
            userList.remove(deletedIndex);
            System.out.println("\nSelected account has been deleted");
        }
        UserManager.WaitAnyInput();
        return curUser;
    }
}
