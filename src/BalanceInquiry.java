import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class BalanceInquiry {
    private static final String FILE_PATH = "account_details.txt";
    private static Scanner sc = new Scanner(System.in);

    public static void balanceInquiry() {
        System.out.println("Enter account number: ");
        String accountNumber = sc.nextLine();

        System.out.println("Enter password: ");
        String password = sc.nextLine();

        if (isAccountExist(accountNumber, password)) {
            displayAccountInformation(accountNumber);
            displayTransactionHistory(accountNumber);
            ADM_Main.ADM_MainInterface(accountNumber);
        } else {
            System.out.println("Invalid account number or password. Please try again.");
        }

        System.out.println("\nExiting the balance inquiry......");
        ADM_Main.ADM_MainInterface(accountNumber);
    }

    private static boolean isAccountExist(String accountNumber, String password) {
        try {
            File file = new File(FILE_PATH);
            if (!file.exists()) {
                return false;
            }

            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;

            while ((line = reader.readLine()) != null) {
                String[] details = line.split(",");
                if (details.length >= 2 && details[0].equals(accountNumber) && details[1].equals(password)) {
                    reader.close();
                    return true;
                }
            }

            reader.close();
            return false;
        } catch (IOException e) {
            System.out.println("Error checking account existence: " + e.getMessage());
            return false;
        }
    }

    private static void displayAccountInformation(String accountNumber) {
        try {
            File file = new File(FILE_PATH);
            if (!file.exists()) {
                System.out.println("No account information found.");
                return;
            }

            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;

            System.out.println("\nBalance Inquiry");
            System.out.println("Account Number       | Full Name                      | Balance   ");
            System.out.println("-------------------------------------------------------------------");

            while ((line = reader.readLine()) != null) {
                String[] details = line.split(",");
                if (details.length >= 11 && details[0].equals(accountNumber)) {
                    String accNum = details[0];
                    String name = details[2] + " " + details[3]; // Combine the first and last name
                    double balance = Double.parseDouble(details[11]);
                    System.out.printf("%-20s | %-30s | %-10s\n", accNum, name, balance);
                    break;
                }
            }

            reader.close();
        } catch (IOException e) {
            System.out.println("Error displaying account information: " + e.getMessage());
        }
    }

    private static void displayTransactionHistory(String accountNumber) {
        try {
            String transactionFileName = accountNumber + "_transactions.txt";
            File file = new File(transactionFileName);
    
            if (!file.exists()) {
                System.out.println("No transactions found for the account.");
                return;
            }
    
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
    
            System.out.println("\nTransactions made for Account: " + accountNumber);
            System.out.println("-------------------------------------------------");
            System.out.printf("%-20s | %-10s | %-15s\n", "Date and Time", "Type", "Amount");
            System.out.println("-------------------------------------------------");
    
            while ((line = reader.readLine()) != null) {
                String[] details = line.split(",");
                if (details.length >= 3) {
                    String timestamp = details[0];
                    String type = details[1];
                    String amount = details[2];
                    System.out.printf("%-20s | %-10s | %-15s\n", timestamp, type, amount);
                }
            }
    
            reader.close();
        } catch (IOException e) {
            System.out.println("Error displaying transaction history: " + e.getMessage());
        }
    }
    

    public static void main(String[] args) {
        balanceInquiry();
    }
}
