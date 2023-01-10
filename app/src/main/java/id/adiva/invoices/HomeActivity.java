package id.adiva.invoices;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.testotp.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class HomeActivity extends AppCompatActivity {

    public static TextView Nama;
    TextView Code, Deposit, Tunggakan, Notif;
    Button Logout;
    AlertDialog.Builder builder;
    String CustomerCode, Token;
    Integer code;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_home);

        CardView button1 = findViewById(R.id.icon1);
        CardView button2 = findViewById(R.id.icon2);
        CardView button3 = findViewById(R.id.icon3);

        builder = new AlertDialog.Builder(this);

        final SharedPrefManager prefManager = new SharedPrefManager(this);

        if (!prefManager.isUserLoggedIn()) {
            backToLogin();
        }else {

            Customers token = prefManager.getUserLogin();
            Token = token.getToken();
            Log.i("DATA_API", Token);

            getData();

                Nama = (TextView) findViewById(R.id.User);
                Code = (TextView) findViewById(R.id.kodepelanggan);
                Deposit = (TextView) findViewById(R.id.nominaldeposit);
                Tunggakan = (TextView) findViewById(R.id.nominaltunggakan);
                Notif = (TextView) findViewById(R.id.notifinvoice);
                Logout = findViewById(R.id.logout);

                Customers customer = prefManager.getUserLogin();

                Nama.setText(customer.getName());
                Code.setText(CustomerCode);
                Deposit.setText(customer.getDeposit());
                Tunggakan.setText(customer.getTotal_unpaid());
                Notif.setText(String.valueOf(customer.getAmount_of_unpaid_invoices()));

        }

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, InvoiceListActivity.class);
                startActivity(intent);
                finish();
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, SubcriptionListActivity.class);
                startActivity(intent);
                finish();
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, SupportTicketListActivity.class);
                startActivity(intent);
                finish();
                //Toast toast = Toast.makeText(HomeActivity.this, R.string.toast_message, Toast.LENGTH_SHORT);
                //toast.show();
            }
        });

        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                builder.setTitle("Logout");
                        builder.setMessage("Apakah Anda Yakin Untuk Keluar Akun ?")
                        .setCancelable(false)
                        .setPositiveButton("Iya",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        moveTaskToBack(true);
                                        prefManager.userLogout();
                                        backToLogin();
                                    }
                                })
                        .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

    }

    private void getData() {
        final SharedPrefManager prefManager = new SharedPrefManager(this);
        RequestQueue queue = Volley.newRequestQueue(this);
        final ProgressDialog loading = ProgressDialog.show(this, "Home", "Please wait..", false, false);
        String url = "https://ljn.lintasfiber.net/api/mobile-app/profile";
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            loading.dismiss();
                            try {
                                JSONObject responseJson = new JSONObject(response);
                                code = responseJson.getInt("code");
                                Log.i("DATA_API", code.toString());

                                //code = 401;

                                if(code == 401) {
                                    moveTaskToBack(true);
                                    prefManager.userLogout();
                                    backToLogin();
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    loading.dismiss();
                    Toast.makeText(getApplicationContext(),"Login Gagal", Toast.LENGTH_LONG).show();
                }

            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("Accept", "application/json");
                    return params;
                }

                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("token",Token);
                    return params;
                }
            };

            stringRequest.setRetryPolicy(new RetryPolicy() {
                @Override
                public int getCurrentTimeout() {
                    return 50000;
                }

                @Override
                public int getCurrentRetryCount() {
                    return 50000;
                }

                @Override
                public void retry(VolleyError error) throws VolleyError {

                }
            });

            queue.add(stringRequest);
        }

    private void backToLogin() {
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Keluar");
        alertDialogBuilder
                .setMessage("Apakah Anda Yakin Untuk Menutup Aplikasi ?")
                .setCancelable(false)
                .setPositiveButton("Iya",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                moveTaskToBack(true);
                                android.os.Process.killProcess(android.os.Process.myPid());
                                System.exit(1);
                            }
                        })
                .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }


}
