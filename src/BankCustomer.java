public class BankCustomer {
    private final String firstName;
    private final String lastName;
    private final String ssn;
    private final Account account;

    BankCustomer(String firstName, String lastName, String ssn, Account account) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.ssn = ssn;
        this.account = account;
    }

    @Override
    public String toString(){
        return "***** Customer Information *****\n" +
                " Name: " + lastName + ", " + firstName + "\n" +
                "SSN: " + ssn + account;
    }
}
