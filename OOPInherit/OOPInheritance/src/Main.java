import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the Banking System!");

        while (true) {
            System.out.println("\nPlease select an option:");
            System.out.println("1. Login");
            System.out.println("2. Register");
            System.out.println("3. Exit");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    Login login = new Login(null);
                    login.execute();
                    break;
                    
                case 2:
                    RegisterAccount register = new RegisterAccount(null);
                    register.execute();
                    break;

                case 3:
                    System.out.println("Thank you for using iBanking System. Goodbye!");
                    return;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
                            scanner.close();

        }

    }
}
