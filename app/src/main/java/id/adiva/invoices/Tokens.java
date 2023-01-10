package id.adiva.invoices;

public class Tokens {

    public static String token;
    public static int amount_of_unpaid_invoices;


    public Tokens(String token) {
        this.token = token;
        }

    public static String getToken() {
        return token;
    }

    public static void setToken(String token) {
        Tokens.token = token;
    }
}
