package id.adiva.invoices;

import android.content.Context;
import android.content.SharedPreferences;

    public class SharedPrefManager {
        final SharedPreferences preferences;
        private static final String TOKEN_KEY = "TOKEN_KEY";
        private static final String SHARED_PREF_NAME = "SHARED_PREF_NAME";
        private static final String NAME_KEY = "NAME_KEY";
        private static final String CUSTOMER_KEY = "CUSTOMER_KEY";
        private static final String DEPOSIT_KEY = "DEPOSIT_KEY";
        private static final String TUNGGAKAN_KEY = "TUNGGAKAN_KEY";
        private static final String OTP_KEY = "OTP_KEY";
        private static final String IS_LOGGED_IN_KEY = "ISLOGGEDIN";
        private static final String TITLE_KEY = "TITLE_KEY";
        private static final String PRIOITAS_KEY = "PRIOITAS_KEY";
        private static final String MESSAGE_KEY = "MESSAGE_KEY";
        private static Integer NOTIFIKASI_KEY = null;
        private static final String DEPARTEMENT_KEY = "DEPARTEMENT_KEY";

        public SharedPrefManager(Context context) {
            preferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        }

        public Customers getUserLogin() {
            return new Customers(
                    preferences.getString(NAME_KEY, null),
                    preferences.getString(CUSTOMER_KEY, null),
                    preferences.getString(DEPOSIT_KEY, null),
                    preferences.getString(TUNGGAKAN_KEY, null),
                    preferences.getString(TOKEN_KEY, null),
                    preferences.getInt(String.valueOf(NOTIFIKASI_KEY), 0));
        }

        public void setUserLogin(Customers customers, Boolean isLoggedIn) {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString(NAME_KEY, customers.getName());
            editor.putString(CUSTOMER_KEY, customers.getCustomer_code());
            editor.putString(DEPOSIT_KEY, customers.getDeposit());
            editor.putString(TUNGGAKAN_KEY, customers.getTotal_unpaid());
            editor.putString(TOKEN_KEY, customers.getToken());
            editor.putInt(String.valueOf(NOTIFIKASI_KEY), customers.getAmount_of_unpaid_invoices());
            editor.putString(OTP_KEY, OtpActivity.Otp.getText().toString());
            editor.putBoolean(IS_LOGGED_IN_KEY, isLoggedIn);
            editor.apply();
        }

        public Departements getSupportTicket() {
            return new Departements(
                    preferences.getString(TITLE_KEY, null),
                    preferences.getString(PRIOITAS_KEY, null),
                    preferences.getString(MESSAGE_KEY, null)
                    );
        }

        public void setSupportTicket(Departements departements, Boolean isLoggedIn) {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString(TITLE_KEY, departements.getJudul());
            editor.putString(PRIOITAS_KEY, departements.getPrioitas());
            editor.putString(MESSAGE_KEY, departements.getPesan());
            editor.apply();
        }
        public Boolean isUserLoggedIn() {
            return preferences.getBoolean(IS_LOGGED_IN_KEY, false);
        }

        public void userLogout() {
            SharedPreferences.Editor editor = preferences.edit();
            editor.clear();
            editor.apply();
        }

    }

