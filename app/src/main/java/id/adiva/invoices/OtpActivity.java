package id.adiva.invoices;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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

public class OtpActivity extends AppCompatActivity {

    public static EditText Otp;
    public static TextView CustomerCode;
    Button btnOtp;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_otp);

        Otp = findViewById(R.id.otp);
        btnOtp = findViewById(R.id.button_veritifikasi);
        CustomerCode = LoginActivity.Kodelangganan;

        btnOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(Otp.getText().toString())) {
                    Toast.makeText(OtpActivity.this,"Masukan Code Verifikasi", Toast.LENGTH_LONG).show();
                }else{
                    otp();
                }
            }
        });
    }

    public void otp(){
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://ljn.lintasfiber.net/api/mobile-app/otp";
        final ProgressDialog loading = ProgressDialog.show(this, "Otp", "Tunggu Sebentar...", false, false);
        final SharedPrefManager prefManager = new SharedPrefManager(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        loading.dismiss();

                        try {
                            JSONObject responseJson = new JSONObject(response);
                            JSONObject result = responseJson.getJSONObject("result");
                            JSONObject customer = result.getJSONObject("customer");

                            Customers customers = new Customers(customer.getString("name"),
                                    customer.getString("customer_code"),
                                    customer.getString("deposit"),
                                    customer.getString("total_unpaid"),
                                    result.getString("token"),
                                    customer.getInt("amount_of_unpaid_invoices"));

                            Log.i("DATA_API", customer.toString());
                            prefManager.setUserLogin(customers, true);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        Toast.makeText(OtpActivity.this,"Berhasil", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(OtpActivity.this, HomeActivity.class);
                        startActivity(intent);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"Login Gagal", Toast.LENGTH_LONG).show();
                loading.dismiss();
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
                params.put("api_key", "API_KEY");
                params.put("customer_code", CustomerCode.getText().toString());
                params.put("otp", Otp.getText().toString());
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

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(OtpActivity.this, LoginActivity.class);
        startActivity(intent);
    }
}
