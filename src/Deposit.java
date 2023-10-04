import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Deposit {

    private static final String FILE_PATH = "account_details.txt";
    private static Scanner sc = new Scanner(System.in);

    public static void deposit() {
        boolean continueLoop = true;

        while (continueLoop) {
            String accountNumber = getLoggedInAccountNumber(); // Read the logged-in account number from the text file
            if (accountNumber.isEmpty()) {
                System.out.println("Error retrieving logged-in account number.");
                return;
            }

            double amount = readDepositAmount();

            // Perform deposit operation
            updateAccountBalance(accountNumber, amount);

            System.out.println("Deposit Successful!");
            System.out.println("Account Balance: " + getAccountBalance(accountNumber));
            continueLoop = false;
            ADM_Main.ADM_MainInterface(accountNumber);
        }

        System.out.println("\nExiting the deposit......");
    }

    private static String getLoggedInAccountNumber() {
        try {
            File file = new File(FILE_PATH);
            if (!file.exists()) {
                return "";
            }

            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;

            while ((line = reader.readLine()) != null) {
                String[] details = line.split(",");
                // Assuming the account number is stored in the first column (index 0)
                if (details.length >= 1) {
                    reader.close();
                    return details[0];
                }
            }

            reader.close();
            return "";
        } catch (IOException e) {
            System.out.println("Error retrieving logged-in account number: " + e.getMessage());
            return "";
        }
    }

    private static double readDepositAmount() {
        double amount = 0.0;
        boolean isValidAmount = false;

        while (!isValidAmount) {
            try {
                System.out.println("Enter deposit amount: ");
                amount = sc.nextDouble();

                if (amount > 0) {
                    isValidAmount = true;
                } else {
                    System.out.println("Invalid amount. Please enter a positive value.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid amount.");
                sc.nextLine(); // Clear the input buffer
            }
        }

        return amount;
    }

    private static double getAccountBalance(String accountNumber) {
        try {
            File file = new File(FILE_PATH);
            if (!file.exists()) {
                return 0.0;
            }

            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;

            while ((line = reader.readLine()) != null) {
                String[] details = line.split(",");
                if (details.length >= 12 && details[0].equals(accountNumber)) {
                    double balance = Double.parseDouble(details[11]);
                    reader.close();
                    return balance;
                }
            }

            reader.close();
            return 0.0;
        } catch (IOException e) {
            System.out.println("Error retrieving account balance: " + e.getMessage());
            return 0.0;
        }
    }

    private static void updateAccountBalance(String accountNumber, double amount) {
        try {
            File file = new File(FILE_PATH);
            File tempFile = new File("temp_account_details.txt");

            BufferedReader reader = new BufferedReader(new FileReader(file));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

            String line;

            while ((line = reader.readLine()) != null) {
                String[] details = line.split(",");

                if (details.length >= 3 && details[0].equals(accountNumber)) {
                    double currentBalance = Double.parseDouble(details[11]);
                    double newBalance = currentBalance + amount;
                    details[11] = String.valueOf(newBalance);
                    line = String.join(",", details);

                    // Append the deposit transaction to the account's transaction file
                    appendTransaction(accountNumber, "Deposit", amount);
                }
                writer.write(line);
                writer.newLine();
            }

            reader.close();
            writer.close();

            // Replace the original file with the updated file
            file.delete();
            tempFile.renameTo(file);

            System.out.println("Account balance updated successfully!");

        } catch (IOException e) {
            System.out.println("Error updating account balance: " + e.getMessage());

        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Invalid format in account_details.txt file.");
            System.out.println("Please ensure each line has three comma-separated values.");
        }
    }

    private static void appendTransaction(String accountNumber, String type, double amount) {
        try {
            String transactionFileName = accountNumber + "_transactions.txt";
            FileWriter writer = new FileWriter(transactionFileName, true);

            // Get the current timestamp
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String timestamp = dateFormat.format(new Date());

            // Append the transaction with timestamp and date
            writer.write(timestamp + "," + type + "," + amount + "\n");
            writer.close();
        } catch (IOException e) {
            System.out.println("Error appending transaction: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        deposit();
    }
}
