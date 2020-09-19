import java.util.ArrayList;

public class SecureBank {
    ArrayList<BankCustomer> customers = new ArrayList<BankCustomer>();

    public static void addCustomer() {
    }

    public void addCustomer(BankCustomer customer) {
        customers.add(customer);
    }
}
