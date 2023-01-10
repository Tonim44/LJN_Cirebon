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

public class SubcriptionListActivity extends AppCompatActivity {

        ListView listView;
        private List<Subcription> subcriptionsList;
        String Token;

        @SuppressLint("WrongViewCast")
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            setContentView(R.layout.activity_subcription_list);

            listView =  findViewById(R.id.list_subcription);
            ImageView back = findViewById(R.id.subcription_list_back);
            subcriptionsList = new ArrayList<>();

            final SharedPrefManager prefManager = new SharedPrefManager(this);
            Customers customers = prefManager.getUserLogin();
            Token = customers.getToken();
            loadPlayer();

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                    Subcription subcription = subcriptionsList.get(position);
                    Intent i = new Intent(getApplicationContext(), SubcriptionActivity.class);
                    i.putExtra(SubcriptionActivity.EXTRA_PLAYER, subcription);
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
            String url = "https://ljn.lintasfiber.net/api/mobile-app/subcription-list";
            final ProgressDialog loading = ProgressDialog.show(this, "Langganan List", "Please wait..", false, false);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {

                        class SubcriptionListView extends ArrayAdapter<Subcription> {

                            private List<Subcription> subcriptionList;
                            private Context context;
                            TextView SubcriptionNumber, Deskripsi, Status, Total;

                            public SubcriptionListView (List<Subcription> subcriptionList, Context context) {
                                super(context, R.layout.item_listsubcription, subcriptionList);
                                this.subcriptionList = subcriptionList;
                                this.context = context;
                            }

                            @Override
                            public View getView(final int position, View convertView, ViewGroup parent) {
                                LayoutInflater inflater = LayoutInflater.from(context);

                                View listViewItem = inflater.inflate(R.layout.item_listsubcription, null, true);

                                SubcriptionNumber = listViewItem.findViewById(R.id.subcription_number);
                                Deskripsi = listViewItem.findViewById(R.id.deskripsi_subcription);
                                //Status = listViewItem.findViewById(R.id.status_subcription);
                                Total = listViewItem.findViewById(R.id.total_subcription);

                                Subcription subcription = subcriptionList.get(position);

                                SubcriptionNumber.setText(subcription.getSubcription_code());
                                Deskripsi.setText(subcription.getDescription());
                                //Status.setText(subcription.getStatus());
                                Total.setText(subcription.getTotall_formatted());

                                String status = subcription.getStatus();
                                if (status.equals("Aktif")) {
                                    Status = listViewItem.findViewById(R.id.status_aktif);
                                    Status.setText(subcription.getStatus());
                                } else {
                                    Status = listViewItem.findViewById(R.id.status_nonaktif);
                                    Status.setText(subcription.getStatus());
                                }

                                return listViewItem;

                            }
                        }

                        @Override
                        public void onResponse(String response) {
                            loading.dismiss();

                            try {
                                JSONObject responseJson = new JSONObject(response);
                                JSONObject result = responseJson.getJSONObject("result");
                                JSONArray playerArray = result.getJSONArray("subcriptions");

                                for (int i = 0; i < playerArray.length(); i++) {
                                    JSONObject playerObject = playerArray.getJSONObject(i);
                                   Subcription subcription = new Subcription(playerObject.getString("subcription_code"),
                                            playerObject.getString("subcription_date"),
                                            playerObject.getString("subtotal_formatted"),
                                            playerObject.getString("total_formatted"),
                                            playerObject.getString("status"),
                                            playerObject.getString("next_invoice_date"),
                                            playerObject.getString("tax_formatted"),
                                            playerObject.getString("description"));
                                    subcriptionsList.add(subcription);
                                }

                                SubcriptionListView adapter = new SubcriptionListView(subcriptionsList, getApplicationContext());
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
        Intent intent = new Intent(SubcriptionListActivity.this, HomeActivity.class);
        startActivity(intent);
    }

}


