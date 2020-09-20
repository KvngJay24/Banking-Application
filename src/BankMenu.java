import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
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
        System.out.println("________________________________________________");
        System.out.println("|           Welcome to Secure Bank!            |");
        System.out.println("|       Where you know you money is safe!      |");
        System.out.println("------------------------------------------------");
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
            catch (NumberFormatException e){
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
            case 1: // User SIgn in
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
        String firstName, lastName, ssn, dateOfBirth,accountType = "";
        double initialDeposit = 0;
        boolean valid = false;

        /** Checks to make sure that user inputs valid account type**/
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

        System.out.println("Please enter your first name: ");
        firstName=fromTerminal.nextLine();
        System.out.println("Please enter your last name: ");
        lastName=fromTerminal.nextLine();
        System.out.println("Please enter you date of birth: ");
        DateOfBirth();

        System.out.println("Please enter your SSN: ");
        ssn=fromTerminal.nextLine();

        /** Checks to make sure that user inputs minimum amount for both Checking and Savings**/
        valid = false;
        while(!valid) {
            System.out.println("Please enter a minimum deposit: ");
            try{
                initialDeposit = Double.parseDouble(fromTerminal.nextLine());
            }
            catch(NumberFormatException e){
                System.out.println("Deposit must be a number.");
            }
            if(accountType.equalsIgnoreCase("checking" )){
                if (initialDeposit < 100){
                    System.out.println("Enter valid amount (Minimum = $100): ");
                }
                else{
                    valid = true;
                }
            }else if(accountType.equalsIgnoreCase("Savings")){
                if (initialDeposit < 50){
                    System.out.println("Enter valid amount (Minimum = $50): ");
                }
                else {
                    valid=true;
                }
            }
        }

        Account account;
        if (accountType.equalsIgnoreCase("checking")){
            account = new Checking(initialDeposit);
        }
        else{
            account = new Savings(initialDeposit);
        }

        BankCustomer customer = new BankCustomer(firstName, lastName, ssn, account);
        SecureBank.addCustomer();

    }

    public void DateOfBirth(){
        boolean valid = false;
        SimpleDateFormat dateOfBirth = new SimpleDateFormat("dd/MM/yyyy");
        Date date;
        dateOfBirth.setLenient(false);

        do {
            try{
                date = dateOfBirth.parse(fromTerminal.nextLine());
            }
            catch(Exception e){
                System.out.println("Please input valid date format. (i.e. dd/MM/yyyy)");
            }
        }while (!valid);

    }
}
