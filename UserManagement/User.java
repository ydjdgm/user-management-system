import java.util.ArrayList;
import java.util.Scanner;

public class User {
    private String userName;
    private String passwordHash;
    private String salt;

    // Gut: Konstruktor initialisiert Benutzername und setzt sofort ein sicheres Passwort mit Salt und Hash
    public User(String name, String password) {
        this.userName = name;
        this.salt = PasswordUtils.getSalt(); // Gut: Zufälliger Salt zur Erhöhung der Sicherheit
        this.passwordHash = PasswordUtils.hashPassword(password, this.salt); // Gut: Hashing sorgt für sichere Passwortspeicherung
    }

    // Getter und Setter: Standard-Methoden, gut umgesetzt
    public String getUserName() {
        return this.userName;
    }

    public String getPasswordHash() {
        return this.passwordHash;
    }

    public String getSalt() {
        return this.salt;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.salt = PasswordUtils.getSalt(); // Gut: Generiert neuen Salt bei Passwortänderung
        this.passwordHash = PasswordUtils.hashPassword(password, this.salt);
    }

    // Gut: Passwortüberprüfung ist durch die Verwendung des gespeicherten Salts und des Hashs sicher
    public boolean verifyPassword(String password) {
        return PasswordUtils.verifyPassword(password, this.passwordHash, this.salt);
    }

    // Verbesserung: Methode könnte statisch sein, da sie keine Instanzvariablen verwendet
    public User ShowAllUser(ArrayList<User> userList, User curUser, Scanner sc) {
        int i = 0;
        System.out.println("\n===== User List =====");
        System.out.println("index | user name");
        System.out.println("---------------------");
        for (User user : userList) {
            System.out.println(i + "     |" + user.getUserName()); // Gut: Klare Ausgabe der Benutzerliste
            i++;
        }
        System.out.println("=====================");
        UserManager.WaitAnyInput();
        return curUser;
    }

    // Gut: Methode erlaubt Benutzerwechsel nach korrekter Verifizierung
    public User ChangeUser(ArrayList<User> userList, User curUser, Scanner sc) {
        System.out.print("\nuser name: ");
        String tempUserName = sc.next();

        if (tempUserName.equals(curUser.getUserName())) {
            System.out.println("\nYou are already " + curUser.getUserName());
            UserManager.WaitAnyInput();
            return curUser;
        }

        User Check = userList.stream()
                .filter(user -> user.getUserName().equals(tempUserName))
                .findFirst()
                .orElse(null);

        if (Check != null) {
            System.out.print("password: ");
            String tempPassword = sc.next();
            if (Check.verifyPassword(tempPassword)) { // Gut: Passwortüberprüfung sorgt für Sicherheit
                System.out.println("\nYou are now " + Check.getUserName());
                UserManager.WaitAnyInput();
                return Check;
            }
            System.out.println("\nPassword is wrong");
            UserManager.WaitAnyInput();
            return curUser;
        }
        System.out.println("\nUsername is not found");
        UserManager.WaitAnyInput();
        return curUser;
    }

    // Verbesserung: Benutzername-Validierung könnte effizienter gestaltet werden
    public User AddUser(ArrayList<User> userList, User curUser, Scanner sc) {
        String tempUserName = "";
        while (true) {
            System.out.print("\nuser name: ");
            String _tempUserName = sc.next();

            User check = userList.stream()
                    .filter(user -> user.getUserName().equals(_tempUserName))
                    .findFirst()
                    .orElse(null);
            if (check == null) {
                tempUserName = _tempUserName; // Gut: Prüft, ob Benutzername bereits existiert
                break;
            }
            System.out.println("\nUsername is already used");
            UserManager.WaitAnyInput();
            return curUser;
        }
        System.out.print("password: ");
        userList.add(new User(tempUserName, sc.next())); // Gut: Neuer Benutzer wird sicher erstellt
        System.out.println("\nNew user has been added");
        UserManager.WaitAnyInput();
        return curUser;
    }

    public User ChangePassword(User curUser, Scanner sc) {
        System.out.print("\ncurrent password: ");
        String currentPassword = sc.next();
        if (curUser.verifyPassword(currentPassword)) { // Gut: Passwortänderung setzt vorherige Verifizierung voraus
            System.out.print("new password: ");
            String newPassword = sc.next();
            if (newPassword.equals(currentPassword)) { // Gut: Verhindert Wiederverwendung des gleichen Passworts
                System.out.println("\nNew password is same as current password");
                UserManager.WaitAnyInput();
                return curUser;
            }
            System.out.print("new password again: ");
            String checkNewPassword = sc.next();
            if (!newPassword.equals(checkNewPassword)) { // Gut: Bestätigung des neuen Passworts
                System.out.println("\nPassword is not same");
                UserManager.WaitAnyInput();
                return curUser;
            }
            curUser.setPassword(newPassword);
            System.out.println("\nYour password has been changed");
        } else {
            System.out.println("\nPassword is wrong");
        }
        UserManager.WaitAnyInput();
        return curUser;
    }

    // Gut: Exit-Logik beendet das Programm klar
    public User ExitProgram() {
        System.out.println("\nProgram has been closed");
        System.out.println("Good bye!");
        System.exit(0); // Verbesserung: Programm könnte Ressourcen vor dem Exit freigeben
        return null;
    }
}
