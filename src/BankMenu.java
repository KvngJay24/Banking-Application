import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


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
            case 1: // User Sign in
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
        String firstName, lastName,ssn,accountType = "";
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

        System.out.println("Please enter email address: ");
        Email();

        System.out.println("Please enter password: ");
        System.out.println(" ");
        System.out.println("- It contains at least 8 characters and at most 36 characters.");
        System.out.println("- It contains at least one digit.");
        System.out.println("- It contains at least one upper case alphabet.");
        System.out.println("- It contains at least one lower case alphabet.");
        System.out.println("- It contains at least one special character which includes !@#$%&*()-+=^.");
        System.out.println("- It doesn’t contain any white space.");
        Password();

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
        double balance = 0;
        if (accountType.equalsIgnoreCase("checking")){
            account = new Checking(initialDeposit, balance);
        }
        else{
            account = new Savings(initialDeposit, balance);
        }

        BankCustomer customer = new BankCustomer(firstName, lastName, ssn, account);


    }

    public void DateOfBirth(){
        boolean valid = false;
        SimpleDateFormat dateOfBirth = new SimpleDateFormat("dd/MM/yyyy");
        Date date;
        dateOfBirth.setLenient(false);
        do {
            try{
                date = dateOfBirth.parse(fromTerminal.nextLine());
                valid = true;
            }
            catch(Exception e){
                System.out.println("Please input valid date format. (i.e. dd/MM/yyyy)");
            }
        }while (!valid);

    }

    public void Email(){
        boolean valid = false;

        do {
            if (emailValidation()==true) {
                System.out.println("Valid email address!");
                valid = true;
            } else {
                System.out.println("Invalid entry. Please try again!");
            }
        }while(!valid);



    }

    public boolean emailValidation(){

            String emailRegex = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$";
            Pattern ePattern = Pattern.compile(emailRegex, Pattern.CASE_INSENSITIVE);
            Matcher matcher = ePattern.matcher(fromTerminal.nextLine());
            return matcher.find();
    }


    public void Password(){
        boolean valid = false;
        do {
            String password = fromTerminal.nextLine();
            String input = ("^[A-Za-z0-9@#$%^+=.]{8,20}$");
            Pattern ePass = Pattern.compile(input);
            Matcher m = ePass.matcher(password);
            boolean phraseValid = m.find();

            if (phraseValid){
                System.out.println("Valid password!");
                valid = true;
            }
            else{
                System.out.println("Invalid password!");
            }
        }while(!valid);

        String passphrase = fromTerminal.nextLine();

        try{
            SecretKey mykey = PrivKey("AES");
            Cipher cipher;
            cipher = Cipher.getInstance("AES");

            byte[] encryptedData = PasswordEncryption(passphrase,mykey,cipher);
//            String encryptedInput = new String(encryptedData);
//            System.out.println(encryptedInput); *Proves that the encryption works.*
        }
        catch(Exception ex){

        }


    }

    public SecretKey PrivKey(String encrypType){
        try{
            KeyGenerator keygen = KeyGenerator.getInstance(encrypType);
            SecretKey key = keygen.generateKey();
            return key;
        }
        catch(Exception e){
            return null;
        }
    }

    private static final String encode_format = "UTF-8";

    public static byte[] PasswordEncryption(String dataEncrypt, SecretKey mykey, Cipher cipher){
    try{
        byte[] passphrase = dataEncrypt.getBytes(encode_format);
        cipher.init(Cipher.ENCRYPT_MODE,mykey);
        byte[] infoEncrypt = cipher.doFinal(passphrase);
        return infoEncrypt;

    }
    catch(Exception e){
        return null;
    }
    }


    }