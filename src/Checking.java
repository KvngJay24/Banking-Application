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
         "Account #: " + this.getAccountNum() + accountType +
        "Balance: " + this.getBalance() + "\n";
    }

    public void MinBalance(){
        if (balance <= 99){
            System.out.println("Your account is at: $" + balance + "\n");
            System.out.println("You have been charged: $" + MinBalanceFee() + ".\n");
        }
    }

    private double MinBalanceFee(){
        double fee;
        fee = 25;

        fee-=balance;

        return fee;
    }
}
