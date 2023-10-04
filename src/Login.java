

import java.util.Scanner;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedReader;

public class Login {

    public static void main(String[] args) {

        Scanner scan = new Scanner(System.in);

        System.out.print("Enter Account No: ");
        String accNo = scan.nextLine();

        System.out.print("Enter PIN: ");
        String password = scan.nextLine();

        if (isValidCredentials(accNo, password)) {
            String[] userDetails = getUserDetails(accNo);
            String firstName = userDetails[3];
            String lastName = userDetails[4];
            System.out.println("Login successful!" + "\n");
            ADM_Main.ADM_MainInterface(accNo);
        } else {
            System.out.println("Account doesn't exist.");
            System.out.println("Please Register Account." + "\n");
            LoginOrRegister.main(new String[0]);
}
    }

    private static String[] getUserDetails(String accountNumber) {
        try {
            File file = new File("account_details.txt");
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


    

    
    public static boolean isValidCredentials(String accountNumber, String password) {
        try {
            File file = new File("account_details.txt");
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

    
    
}
