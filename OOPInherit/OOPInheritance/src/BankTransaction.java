public class BankTransaction extends Account {

    private String accountNumber;

    public BankTransaction(String accountNumber) {
        super(accountNumber);
        this.accountNumber = accountNumber;
    }

    public void ADM_MainInterface() {
        System.out.println("\nWelcome to your Account! " + accountNumber + "...");
        ADM_MainInterfaceBody();
    }

    private void ADM_MainInterfaceBody() {
        System.out.println();
        ADM_DisplayChooseFunctions displayFunctions = new ADM_DisplayChooseFunctions();
        int inputIntFunc = displayFunctions.displayFunctions();

        if (inputIntFunc == 1) {
            double amount = readAmount("deposit");
            updateAccountBalance(accountNumber, amount);

            System.out.println("Deposit Successful!");
            System.out.println("Account Balance: " + getAccountBalance(accountNumber));
            System.out.println();
            ADM_MainInterfaceBody();

        } else if (inputIntFunc == 2) {
            double amount = readAmount("withdrawal");
            double balance = getAccountBalance(accountNumber);

            if (amount > balance) {
            System.out.println("Insufficient funds. Withdrawal amount exceeds account balance.");
             } else {
            updateAccountBalance(accountNumber, -amount);
            System.out.println("Withdrawal Successful!");
            System.out.println("Account Balance: " + getAccountBalance(accountNumber));
        }
        
            System.out.println();
            ADM_MainInterfaceBody();

        } else if (inputIntFunc == 3) {

            balanceInquiry();
            System.out.println();
            ADM_MainInterfaceBody();
            
        } else if (inputIntFunc == 4) {

            System.out.println("Exiting now the System.....\n");
            Main.main(null);
        }
    }
}
