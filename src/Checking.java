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

    public double overDraft() {
        double fee_charged = 0.0;
        if (35.0 < balance) {
            fee_charged = overDraftFee();
            System.out.println("You have been charge a $"+ fee_charged +" fee.");
            System.out.println("You have insufficient funds.");
        }
        return fee_charged;
    }

    private double overDraftFee(){
        double fee = 35;

        balance= balance-fee;

        return fee;
    }
}
