public class Checking extends Account {
    public static String accountType="Checking";

    Checking(double initialDeposit) {
        super.setBalance(initialDeposit);

        if (initialDeposit < 15000){
            this.setInterest(1);
        }
        else{
            this.setInterest(3);
        }
    }

    @Override
    public String toString(){
        return "Account Type: " + accountType + "Account\n" +
        // System.out.println("Account #: " + accountType";
        "Balance: " + this.getBalance() + "\n";
    }
}
