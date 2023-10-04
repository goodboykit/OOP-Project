import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;

abstract class Account {
    
    private static final String FILE_PATH = "NewAccount.txt";
    protected String accountNumber;
    protected static Scanner sc = new Scanner(System.in);

    public Account(String accountNumber) {
        this.accountNumber = accountNumber;
        this.sc = new Scanner(System.in);
    }

    protected String getAccountNumber() {
            return accountNumber;
        }

    protected void balanceInquiry() {
        
     if (isAccountExist(accountNumber)) {
                displayAccountInformation(accountNumber);
                displayTransactionHistory(accountNumber);
            } else {
                System.out.println("Invalid account number. Please try again.");
            }
    
            System.out.println("\nExiting the balance inquiry...");
            BankTransaction admMain = new BankTransaction(accountNumber);
            admMain.ADM_MainInterface();
        }

        private static boolean isAccountExist(String accountNumber) {
            try {
                File file = new File(FILE_PATH);
                if (!file.exists()) {
                    return false;
                }
    
                BufferedReader reader = new BufferedReader(new FileReader(file));
                String line;
    
                while ((line = reader.readLine()) != null) {
                    String[] details = line.split(",");
                    if (details.length >= 1 && details[0].equals(accountNumber)) {
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
                        String name = details[2] + " " + details[4]; // Combine the first and last name
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

    protected String getLoggedInAccountNumber() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH));
            String line;

            while ((line = reader.readLine()) != null) {
                String[] details = line.split(",");
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

    protected static double readAmount(String transactionType) {
        double amount = 0.0;
        boolean isValidAmount = false;

        while (!isValidAmount) {
            try {
                System.out.println("Enter " + transactionType + " amount: ");
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

    protected double getAccountBalance(String accountNumber) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH));
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

    protected void updateAccountBalance(String accountNumber, double amount) {
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
    
                    // Append the deposit or withdrawal transaction to the account's transaction file
                    if (amount > 0) {
                        appendTransaction(accountNumber, "Deposit", amount);
                    } else {
                        appendTransaction(accountNumber, "Withdrawal", -amount);
                    }
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
            System.out.println("Invalid format in NewAccount.txt file.");
            System.out.println("Please ensure each line has three comma-separated values.");
        }
    }
    

    protected void appendTransaction(String accountNumber, String type, double amount) {
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
}
