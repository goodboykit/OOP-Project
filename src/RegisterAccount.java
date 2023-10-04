import java.util.Date;
import java.util.Scanner;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.text.SimpleDateFormat;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class RegisterAccount {

    private static final String ACCOUNT_DETAILS_FILE = "account_details.txt";
    private static int initialAccountNumber = 2023100000;
    private static double initialDeposit = 500.0;

    public static void main(String[] args) {
        loadAccountNumber();

        createAccount();
        Runtime.getRuntime().addShutdownHook(new Thread(RegisterAccount::saveAccountNumber));
    }

    private static void loadAccountNumber() {
        
        try (BufferedReader reader = new BufferedReader(new FileReader(ACCOUNT_DETAILS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] details = line.split(",");
                int accountNumber = Integer.parseInt(details[0]);
                if (accountNumber >= initialAccountNumber) {
                    initialAccountNumber = accountNumber + 1;
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading account number: " + e.getMessage());
        }
    }

    private static void saveAccountNumber() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ACCOUNT_DETAILS_FILE, true))) {
            writer.write(String.valueOf(initialAccountNumber));
        } catch (IOException e) {
            System.out.println("Error saving account number: " + e.getMessage());
        }
    }

    public static void createAccount() {

        Scanner input = new Scanner(System.in);

        String firstName;
        String middleName;
        String lastName;
        String gender;
        String address;
        String fatherName;
        String motherName;
        String contactNo;
        String password;

        System.out.println("Register Account");
        System.out.println("----------------");

        System.out.print("First Name: ");
        firstName = input.nextLine().trim();

        System.out.print("Middle Name: ");
        middleName = input.nextLine().trim();

        System.out.print("Last Name: ");
        lastName = input.nextLine().trim();

        String birthdateStr = "";
        Date birthdate = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");

        boolean isValidBirthdate = false;
        while (!isValidBirthdate) {
            System.out.print("Enter Birthdate (MM/dd/yyyy): ");
            birthdateStr = input.nextLine().trim();

            try {
                birthdate = dateFormat.parse(birthdateStr);
                isValidBirthdate = true;
            } catch (Exception e) {
                System.out.println("Invalid birthdate format. Please enter in MM/dd/yyyy format.");
            }
        }

        System.out.print("Gender: ");
        gender = input.nextLine().trim();

        System.out.print("Address: ");
        address = input.nextLine().trim();

        System.out.print("Father Name: ");
        fatherName = input.nextLine().trim();

        System.out.print("Mother Name: ");
        motherName = input.nextLine().trim();

        System.out.print("Contact No: ");
        contactNo = input.nextLine().trim();
        System.out.print("Enter your PIN: ");
        password = input.nextLine().trim();

        boolean isValidPIN = false;

        while (!isValidPIN) {
            if (password.length() != 6) {
                System.out.println("Invalid PIN length. Must be exactly 6 characters long.");
                System.out.print("Enter your PIN: ");
                password = input.nextLine().trim();

            } else if (!isNumericPIN(password)) {
                System.out.println("Invalid PIN. Must contain only numeric digits and should be exactly 6 characters long.");
                System.out.print("Enter your PIN: ");
                password = input.nextLine().trim();
            } else {
                isValidPIN = true;
            }
        }

        // Increment the account number
        int newAccountNumber = initialAccountNumber;

        // Save account details
        saveAccountDetails(String.valueOf(newAccountNumber), password, firstName, middleName, lastName, birthdateStr,
                gender, address, fatherName, motherName, contactNo, initialDeposit);

        // Increment the initialAccountNumber for the next account
        initialAccountNumber++;

        System.out.println("Congratulations!! Your Account Number is " + newAccountNumber);
        System.out.println("Record has been saved");
        LoginOrRegister.main(new String[0]);
    }

    public static void saveAccountDetails(String accountNumber, String password, String firstName, String middleName,
            String lastName, String birthdate, String gender, String address, String fatherName, String motherName,
            String contactNo, double initialDeposit) {
        try {
            List<String> accountDetails = Files.readAllLines(Paths.get(ACCOUNT_DETAILS_FILE));
            if (accountDetails.size() > 0) {
                String lastAccountDetails = accountDetails.get(accountDetails.size() - 1);
                String[] lastAccountFields = lastAccountDetails.split(",");
                int lastAccountNumber = Integer.parseInt(lastAccountFields[0]);
                initialAccountNumber = lastAccountNumber + 1;
            }

            String newAccountDetails = accountNumber + "," + password + "," + firstName + "," + middleName + ","
                    + lastName + "," + birthdate + "," + gender + "," + address + "," + fatherName + "," + motherName
                    + "," + contactNo + "," + initialDeposit;
            accountDetails.add(newAccountDetails);

            Files.write(Paths.get(ACCOUNT_DETAILS_FILE), accountDetails);
        } catch (IOException e) {
            System.out.println("Error saving account details: " + e.getMessage());
        }
    }

    private static boolean isNumericPIN(String pin) {
        return pin.matches("\\d+") && pin.length() == 6;
    }

    public static void returnToLoginOrRegister() {
        LoginOrRegister.main(new String[0]);
    }
}
