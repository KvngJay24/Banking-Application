import java.util.Random;
public class Account {
    double balance = 0;
    double interest = 0.75;
    double accountNum;
    static int numOfAccounts = 1000000;

    Account(){
        accountNum = numOfAccounts++;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public double getInterest() {
        return interest * 100;
    }

    public void setInterest(double interest) {
        this.interest = interest;
    }

    public double getAccountNum() {
        return accountNum;
    }

  public void deposit(double amount){
      balance+=amount;
      System.out.println("You have deposited: $" + amount);
      System.out.println("Your balance is now: $" + balance);
  }

  public void withdraw(double amount){
      if (amount <= balance){
          balance -= amount;
      }
      else {
          return;
      }
    }


}


