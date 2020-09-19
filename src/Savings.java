public class Savings extends Account {
    public static String accountType = "Savings";

    Savings(double initialDeposit) {
        super.setBalance(initialDeposit);

        if (initialDeposit < 15000){
            this.setInterest(0.75);
        }
        else{
            this.setInterest(0.81);
        }
    }

    @Override
    public String toString(){
        return "Account Type: " + accountType + "Account\n" +
                // System.out.println("Account #: " + accountType";
                "Balance: " + this.getBalance() + "\n" +
                "Interest Rate: " + this.getInterest() + "%/n";
    }
}
