package id.adiva.invoices;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
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

public class AddSupportTicketActivity2 extends AppCompatActivity {
    public static String EXTRA_PLAYER = "extra_player";
    String[] items =  {"Tinggi","Menengah","Rendah"};
    public TextView GetDepartements, GetJudul, GetPesan, Priority;
    private Button Btnpost;
    public static String Token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addsupportticket);

        RelativeLayout departement = findViewById(R.id.pilih);
        GetJudul = (TextView) findViewById(R.id.insert_judul);
        GetPesan = (TextView) findViewById(R.id.insert_pesan);
        GetDepartements = (TextView) findViewById(R.id.get_departement);
        Priority = (TextView) findViewById(R.id.pilih_priority);
        Btnpost = findViewById(R.id.button_kirim);

        final SharedPrefManager prefManager = new SharedPrefManager(this);
        Departements departements1 = prefManager.getSupportTicket();

        String judul = departements1.getJudul();
        String pesan = departements1.getPesan();
        String prioitas = departements1.getPrioitas();

        GetJudul.setText(judul);
        GetPesan.setText(pesan);

        Departements departements2 = getIntent().getParcelableExtra(EXTRA_PLAYER);
        String getdepartement = departements2.getDepartment_name();
        GetDepartements.setText(getdepartement);
        ImageView back = findViewById(R.id.addsupportticket_back);

        Customers customers = prefManager.getUserLogin();
        Token = customers.getToken();

        final Spinner prioitas_spinner = findViewById(R.id.spinner_prioitas);
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, items);
        prioitas_spinner.setAdapter(adapter);
        prioitas_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView adapterView, View view, int i, long l) {
                String item = adapterView.getItemAtPosition(i).toString();
                if (item.equals("Tinggi")){
                    Priority.setText("high");
                } if (item.equals("Menengah")){
                    Priority.setText("medium");
                } if(item.equals("Rendah")) {
                    Priority.setText("low");
                }
            }

            @Override
            public void onNothingSelected(AdapterView adapterView) {
            Priority.setText(prioitas);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        departement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final SharedPrefManager prefManager = new SharedPrefManager(getApplicationContext());
                Departements departements = new Departements(GetJudul.getText().toString(), Priority.getText().toString(), GetPesan.getText().toString());
                prefManager.setSupportTicket(departements, true);
                Intent intent = new Intent(AddSupportTicketActivity2.this, DepartementActivity.class);
                startActivity(intent);
                finish();}
        });

        Btnpost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(GetJudul.getText().toString()) || TextUtils.isEmpty(GetPesan.getText().toString()) || TextUtils.isEmpty(GetDepartements.getText().toString()) || TextUtils.isEmpty(Priority.getText().toString())) {
                    Toast.makeText(AddSupportTicketActivity2.this, "Masukan Semua Data", Toast.LENGTH_LONG).show();
                } else {
                    post();
                }

            }
        });
    }
        public void post() {
            RequestQueue queue = Volley.newRequestQueue(this);
            String url = "https://ljn.lintasfiber.net/api/mobile-app/support-ticket-create";
            final ProgressDialog loading = ProgressDialog.show(this, "Kirim", "Tunggu Sebentar...", false, false);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            loading.dismiss();

                            try {
                                JSONObject responseJson = new JSONObject(response);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            Toast.makeText(AddSupportTicketActivity2.this, "Berhasil", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(AddSupportTicketActivity2.this, SupportTicketListActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(AddSupportTicketActivity2.this, "Gagal", Toast.LENGTH_LONG).show();
                    loading.dismiss();
                }

            }) {
                @Override
                protected Response<String> parseNetworkResponse(NetworkResponse response) {
                    int mStatusCode = response.statusCode;
                    Log.i("DATA_API", Integer.toString(mStatusCode));
                    return super.parseNetworkResponse(response);
                }

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("Accept", "application/json");
                    return params;
                }

                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("token", Token);
                    params.put("department_name", GetDepartements.getText().toString());
                    Log.i("DATA_API", GetDepartements.getText().toString());
                    params.put("title", GetJudul.getText().toString());
                    Log.i("DATA_API", GetJudul.getText().toString());
                    params.put("content", GetPesan.getText().toString());
                    Log.i("DATA_API", GetPesan.getText().toString());
                    params.put("priority", Priority.getText().toString());
                    Log.i("DATA_API", Priority.getText().toString());
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
        Intent intent = new Intent(AddSupportTicketActivity2.this, SupportTicketListActivity.class);
        startActivity(intent);
    }

}