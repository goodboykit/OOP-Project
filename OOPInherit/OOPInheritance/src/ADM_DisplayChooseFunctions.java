
import java.util.InputMismatchException;

class ADM_DisplayChooseFunctions extends DisplayChooseFunctions {
    
    @Override
    public int displayFunctions() {
        System.out.println("[1] Deposit");
        System.out.println("[2] Withdraw");
        System.out.println("[3] Balance Inquiry");
        System.out.println("[4] Exit");

        int inputInt = 0;
        boolean done = false;

        while (!done) {
            try {
                while (true) {
                    System.out.println("Enter Procedure Number: ");
                    inputInt = sc.nextInt();
                    if (inputInt > 0 && inputInt <= 4) {
                        done = true;
                        break;
                    }
                    System.out.println("\nInput Error: Inputted operation number doesn't exist.");
                }
            } catch (InputMismatchException ime) {
                String temp = sc.nextLine();
                System.out.println("\nInput Error: Input must be an integer.");
            }
        }
        return inputInt;
    }
}