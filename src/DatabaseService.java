import java.lang.reflect.Type;
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseService {
    String url ="jdbc:mysql://localhost:3306/securebankdb";
    String user = "jmitch";
    String password = "N3wyorkknicks";

    private Connection connect(){
        Connection connection;
        try{
            connection = DriverManager.getConnection(url,user,password);
        }
        catch(SQLException e){
            connection = null;
        }
        return connection;
    }

    //CRUD Operations

    //Create (Add Account)
    int addAccount(String fName, String lName,String DOB, String SSN, AccType accType, Double balance){
    int userID = -1;
    int accID = -1;
    Connection connection = connect();
    try {
        connection.setAutoCommit(false);
        //Add Customer
        String addCustomerSQL = "insert into customers (FName, LName, DOB, SSN) values (?,?,?,?)";
        PreparedStatement addCustomer = connection.prepareStatement(addCustomerSQL, Statement.RETURN_GENERATED_KEYS); //SQL query that uses question marks as place holder that doesn't risk SQL injection when taking input from user
        addCustomer.setString(1,fName);
        addCustomer.setString(2,lName);
        addCustomer.setString(3,DOB);
        addCustomer.setString(4,SSN);
        addCustomer.executeUpdate();
        ResultSet addCustomerResults = addCustomer.getGeneratedKeys();
        if (addCustomerResults.next()){
            userID = addCustomerResults.getInt(1);
        }
        //Add account
        String addAccountSQL = "insert into accounts(Type, Balance) values (?,?)";
        PreparedStatement addAccount = connection.prepareStatement(addAccountSQL, Statement.RETURN_GENERATED_KEYS);
        addAccount.setString(1, accType.name());
        addAccount.setDouble(2, balance);
        addAccount.executeUpdate();
        ResultSet addAccResults = addAccount.getGeneratedKeys();
        if (addAccResults.next()){
            accID = addAccResults.getInt(1);
        }
        //Link customer to account
        if (userID > 0 && accID > 0){
            String linkAccSQL = "insert into mappings(CustomerId, AccID) values (?,?)";
            PreparedStatement linkAcc = connection.prepareStatement(linkAccSQL);
            linkAcc.setInt(1,userID);
            linkAcc.setInt(2,accID);
            linkAcc.executeUpdate();
            connection.commit();
        }
        else{
           connection.rollback();
        }
        connection.close();
    }
    catch(SQLException e){
        System.err.println("Error: " + e.getMessage());
    }
return accID;
    }
    //Read
    BankCustomer GetAccount(int accId){
        BankCustomer customer = null;
        Connection connection = connect();
        try {
            String findCustomerSQL = "select FName, LName, DOB, SSN, Type, Balance "
                    + "from customers a join mappings b on a.ID = b=CustomerId "
                    + "join accounts c on c.ID = b.AccId "
                    + "where c.ID = ?";
            PreparedStatement findCustomer=connection.prepareStatement(findCustomerSQL);
            findCustomer.setInt(1, accId);
            ResultSet findCustomerResults = findCustomer.executeQuery();
            if (findCustomerResults.next()){
                String fName = findCustomerResults.getString("FName");
                String lName = findCustomerResults.getString("LName");
                String DOB = findCustomerResults.getString("DOB");
                String SSN = findCustomerResults.getString("SSN");
                AccType accType = AccType.valueOf(findCustomerResults.getString("Type"));
                double balance = findCustomerResults.getDouble("Balance");
                Account account;
                if (accType == AccType.Checking){
                    account = new Checking(accId,balance);
                }
                else if (accType == AccType.Savings){
                    account = new Savings(accId,balance);
                }
                else {
                   throw new Exception("Invalid account type");
                }
                customer = new BankCustomer(fName,lName,SSN,account);
            }
            else{
                System.err.println("No customer found for ID " + accId);
            }
        }
        catch(Exception e){
            System.err.println(e.getMessage());
        }

        return customer;
    }
    //Update
    boolean UpdateAccount(int accountId, double balance){
        boolean success = false;
        Connection connection = connect();
        try {
            String updateSQL = "UPDATE accounts set Balance = ? WHERE ID = ?";
            PreparedStatement updateBalance = connection.prepareStatement(updateSQL);
            updateBalance.setDouble(1, balance);
            updateBalance.setDouble(2, accountId);
            updateBalance.executeUpdate();
            success = true;
        } catch (SQLException e) {
            Logger.getLogger(DatabaseService.class.getName()).log(Level.SEVERE, null, e);
        }
        return success;
    }
    //Delete
    boolean DelAccount(int accountId){
        boolean success = false;
        Connection connection = connect();
        try {
            String deleteSQL = "DELETE customers, accounts "
                    + "FROM customers a join mappings b on a.ID = b=CustomerId "
                    + "join accounts c on c.ID = b.AccId "
                    + "WHERE c.ID = ?";
            PreparedStatement delAccount = connection.prepareStatement(deleteSQL);
            delAccount.setDouble(1, accountId);
            delAccount.executeUpdate();
            success = true;
        } catch (SQLException e) {
            Logger.getLogger(DatabaseService.class.getName()).log(Level.SEVERE, null, e);
        }
        return success;
    }

    //Get All Accounts
    ArrayList<BankCustomer> GetAllAcc(){
        ArrayList<BankCustomer> customers = new ArrayList<>();
        Connection connection = connect();
        try {


            String findAllCustomersSQL = "select AccId, FName, LName, DOB, SSN, Type, Balance "
                    + "from customers a join mappings b on a.ID = b=CustomerId "
                    + "join accounts c on c.ID = b.AccId "
                    + "where c.ID = ?";
            PreparedStatement findAllCustomers = connection.prepareStatement(findAllCustomersSQL);
            ResultSet findCustomerResults = findAllCustomers.executeQuery();
            while (findCustomerResults.next()) {
                String fName = findCustomerResults.getString("FName");
                String lName = findCustomerResults.getString("LName");
                String DOB = findCustomerResults.getString("DOB");
                String SSN = findCustomerResults.getString("SSN");
                AccType accType = AccType.valueOf(findCustomerResults.getString("Type"));
                double balance = findCustomerResults.getDouble("Balance");
                int accId = findCustomerResults.getInt("AccId");
                Account account;
                if (accType == AccType.Checking) {
                    account = new Checking(accId, balance);
                } else if (accType == AccType.Savings) {
                    account = new Savings(accId, balance);
                } else {
                    throw new Exception("Invalid account type");
                }
                customers.add(new BankCustomer(fName, lName, SSN, account));
            }
        }
        catch (Exception e){
            System.err.println(e.getMessage());
        }
        return customers;

    }
}
