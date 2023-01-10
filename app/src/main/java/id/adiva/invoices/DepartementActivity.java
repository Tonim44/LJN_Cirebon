package id.adiva.invoices;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DepartementActivity extends AppCompatActivity {

    ListView listView;
    private List<Departements> departemenList;
    String Token;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_departement);

        listView =  findViewById(R.id.list_departement);
        ImageView back = findViewById(R.id.departement_list_back);
        departemenList = new ArrayList<>();

        final SharedPrefManager prefManager = new SharedPrefManager(this);
        Customers customers = prefManager.getUserLogin();
        Token = customers.getToken();
        loadPlayer();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        Departements departements = departemenList.get(position);
        Intent i = new Intent(getApplicationContext(), AddSupportTicketActivity2.class);
        i.putExtra(AddSupportTicketActivity2.EXTRA_PLAYER, departements);
        startActivity(i);
         }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    public void loadPlayer(){
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://ljn.lintasfiber.net/api/mobile-app/department-list";
        final ProgressDialog loading = ProgressDialog.show(this, "Departement", "Tunggu Sebentar...", false, false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()  {
                    class DepartementView extends ArrayAdapter<Departements> {

                        private List<Departements> departementList;
                        private Context context;
                        TextView Tittle, Keterangan;

                        public DepartementView (List<Departements>  departementList, Context context) {
                            super(context, R.layout.item_departement, departementList);
                            this.departementList = departementList;
                            this.context = context;
                        }
                        @Override
                        public View getView(final int position, View convertView, ViewGroup parent) {
                            LayoutInflater inflater = LayoutInflater.from(context);

                            View listViewItem = inflater.inflate(R.layout.item_departement, null, true);

                            Tittle = listViewItem.findViewById(R.id.departement_name);
                            Keterangan = listViewItem.findViewById(R.id.departement_keterangan);

                            Departements departements = departementList.get(position);

                            Tittle.setText(departements.getDepartment_name());
                            Keterangan.setText(departements.getDescription());

                            return listViewItem;

                        }
                    }
                    @Override
                    public void onResponse(String response) {
                        loading.dismiss();

                        try {
                            JSONObject responseJson = new JSONObject(response);
                            JSONObject result = responseJson.getJSONObject("result");
                            JSONArray playerArray = result.getJSONArray("departments");

                            for (int i = 0; i < playerArray.length(); i++) {
                                JSONObject playerObject = playerArray.getJSONObject(i);

                                Departements departements = new Departements(playerObject.getString("department_name"),
                                        playerObject.getString("description"));
                                departemenList.add(departements);
                            }

                            DepartementView adapter = new DepartementView(departemenList, getApplicationContext());
                            listView.setAdapter(adapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"Gagal Ditampilkan", Toast.LENGTH_LONG).show();//display the response on screen
            }
        })
        {
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
        Intent intent = new Intent(DepartementActivity.this, AddSupportTicketActivity1.class);
        startActivity(intent);
    }

}


