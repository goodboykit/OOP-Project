import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Login extends Account {

    public Login(String accountNumber) {
        super(accountNumber);
    }

    public void execute() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter Account No: ");
        String accountNumber = scanner.nextLine();

        System.out.print("Enter PIN: ");
        String password = scanner.nextLine();

        if (isValidCredentials(accountNumber, password)) {
            System.out.println("Login successful!\n");

            String[] userDetails = getUserDetails(accountNumber);

            if (userDetails != null && userDetails.length >= 5) {
                String firstName = userDetails[2];
                String lastName = userDetails[4];

                System.out.println("Welcome, " + firstName + " " + lastName + "!");
                BankTransaction admMain = new BankTransaction(accountNumber);
                admMain.ADM_MainInterface();

            } else {
                System.out.println("Invalid user details. Please try again.\n");
            }
        } else {
            System.out.println("Invalid account number or password.\n");
            System.out.println("Please register an account.\n");
            Main.main(new String[0]);
        }
        scanner.close();
    }

    private boolean isValidCredentials(String accountNumber, String password) {

        try {
            File file = new File("NewAccount.txt");
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
    
            while ((line = reader.readLine()) != null) {
                String[] details = line.split(",");
    
                if (details.length >= 3 && details[1].equals(password) && details[0].equals(accountNumber)) {
                    reader.close();
                    return true;
                }
            }
            reader.close();

            // Return false if username and password combination is not found
            return false;
        } catch (Exception e) {
            System.out.println("Error reading account details: " + e.getMessage());
            return false;
        }
    }

    private String[] getUserDetails(String accountNumber) {
        
        try {
            File file = new File("NewAccount.txt");
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
    
            while ((line = reader.readLine()) != null) {
                String[] details = line.split(",");
                if (details.length >= 3 && details[0].equals(accountNumber)) {
                    reader.close();
                    return details;
                }
            }
            reader.close();
            // Return null if user details are not found
            return null;
            
        } catch (IOException e) {
            System.out.println("Error reading user details: " + e.getMessage());
            return null;
        }
    }
}
