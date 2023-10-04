
import java.util.Scanner;

// Main interface for ADMIN account
public class ADM_Main {

    static Scanner sc = new Scanner(System.in);

    public static void ADM_MainInterface(String AccountName) {

        System.out.println("\nWelcome to your Account! " + AccountName + "...");

        ADM_MainInterfaceBody();
    }

    public static void ADM_MainInterfaceBody() {
        System.out.println();
        int inputIntFunc = ADM_DisplayChooseFunctions.adminDisplayFunctions();

        if (inputIntFunc == 1) {
            Deposit.deposit();
            System.out.println();
            ADM_Main.ADM_MainInterfaceBody();

        } else if (inputIntFunc == 2) {
            Withdraw.withdraw();
            System.out.println();
            ADM_Main.ADM_MainInterfaceBody();

        } else if (inputIntFunc == 3) {
            BalanceInquiry.main(new String[0]);
            System.out.println();
            ADM_Main.ADM_MainInterfaceBody();

        } else if (inputIntFunc == 4){
            System.out.println();
        }

    }
}
