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

    public double MinBalance(){
        double fee_charged = MinBalanceFee();
        if (balance > 5){
            System.out.println("Your account is at: $" + balance + "\n");
            System.out.println("You have been charged: $" + fee_charged + ".\n");
        }
        return fee_charged;
    }

    private double MinBalanceFee(){
        double fee;
        fee = 5;

        balance=balance-fee;

        return fee;
    }
}
