import java.util.InputMismatchException;
import java.util.Scanner;

// Admin Display Functions, will display and choose all the function that Admin interface can offer
public class ADM_DisplayChooseFunctions {

    static Scanner sc = new Scanner(System.in);
    public static int adminDisplayFunctions() {

        System.out.println("[1] Deposit");
        System.out.println("[2] Withdraw");
        System.out.println("[3] Balance Iquiry");
        System.out.println("[4] Exit");

        int inputInt = 0;
        boolean done = false;

        while (!done) {
            try {
                while (true) {
                    System.out.println("Enter Procedural Number: ");
                    inputInt = sc.nextInt();
                    if(inputInt > 0 && inputInt <= 4) {
                        done = true;
                        break;
                    }
                    System.out.println("\nInput Error: Inputted Number operation doesn't exist.");
                }
            } catch (InputMismatchException ime) {
                
                String temp = sc.nextLine();
                System.out.println("\nInput Error: Input must be an integer.");
            }
        }
        return inputInt;
    }


}
