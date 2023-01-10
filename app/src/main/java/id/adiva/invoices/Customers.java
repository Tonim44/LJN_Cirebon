package id.adiva.invoices;

public class Customers {

    public static String name, customer_code, deposit, total_unpaid, token;
    public static int amount_of_unpaid_invoices;

    public Customers(String name, String customer_code, String deposit, String total_unpaid, String token, int amount_of_unpaid_invoices) {
        this.name = name;
        this.customer_code = customer_code;
        this.deposit = deposit;
        this.total_unpaid = total_unpaid;
        this.token = token;
        this.amount_of_unpaid_invoices = amount_of_unpaid_invoices;
    }

    public static String getName() {
        return name;
    }

    public static void setName(String name) {
        Customers.name = name;
    }

    public static String getCustomer_code() {
        return customer_code;
    }

    public static void setCustomer_code(String customer_code) {
        Customers.customer_code = customer_code;
    }

    public static String getDeposit() {
        return deposit;
    }

    public static void setDeposit(String deposit) {
        Customers.deposit = deposit;
    }

    public static String getTotal_unpaid() {
        return total_unpaid;
    }

    public static void setTotal_unpaid(String total_unpaid) {
        Customers.total_unpaid = total_unpaid;
    }

    public static String getToken() {
        return token;
    }

    public static void setToken(String token) {
        Tokens.token = token;
    }

    public static int getAmount_of_unpaid_invoices() {
        return amount_of_unpaid_invoices;
    }

    public static void setAmount_of_unpaid_invoices(int amount_of_unpaid_invoices) {
        Customers.amount_of_unpaid_invoices = amount_of_unpaid_invoices;
    }
}
