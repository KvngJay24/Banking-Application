import java.sql.SQLOutput;
import java.util.Scanner;

public class BankMenu {

    Scanner fromTerminal = new Scanner(System.in);

    public static void main(String[] args) {
        BankMenu menu = new BankMenu();
        menu.intiateMenu();
    }
        boolean quit;


    public void intiateMenu(){
        Intro();
        Menu();
        while(!quit){
            int options = userInput();
            softwareAction(options);
        }
    }

    public void Intro(){
        System.out.println("____________________________________");
        System.out.println("|       Welcome to JAVA Bank!      |");
        System.out.println("------------------------------------");
    }

    public void Menu(){
        System.out.println("Please: ");
        System.out.println("1) Sign In");
        System.out.println("2) Sign Up");
        System.out.println("3) Exit");
    }

    public int userInput(){
        int options = 0;

        do {
            try{
                options = Integer.parseInt(fromTerminal.nextLine());
            }
            catch (Exception e){
                System.out.println("Invalid option. Try Again.");
            }

            if (options < 1 || options > 3){
                System.out.println("Option is outside of range");
            }
        }
        while(options < 1 || options > 3);
        return options;
    }


    public void softwareAction(int options){
        switch(options){
            case 1: // List Balances
            case 2:
                createAccount();
                break;
            case 3:
                System.out.println("Thank You! Please come again!");
                System.exit(0);
                break;
            default:
                System.out.println("Invalid Entry! Try Again!");
        }
    }

    public void createAccount(){
        String fullName, ssn, accountType;
        double initialDeposit;
        boolean valid = false;
        while(!valid){
            System.out.println("Enter in a valid account type (i.e. checking, savings): ");
            accountType = fromTerminal.nextLine();
            if (accountType.equalsIgnoreCase("checking") || accountType.equalsIgnoreCase("savings")){
                valid = true;
            }
            else{
                System.out.println("Input denied! Please enter in checking or savings.");
            }
        }
        System.out.println("Please enter your first & last name: ");
        fullName=fromTerminal.nextLine();
        System.out.println("Please enter your SSN: ");
        ssn=fromTerminal.nextLine();

        valid = false;
    }
}
