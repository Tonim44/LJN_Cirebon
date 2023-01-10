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
import android.widget.Button;
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

public class SupportTicketListActivity extends AppCompatActivity {

    ListView listView;
    private List<SupportTicket> supportTicketList;
    String Token;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_supportticket_list);

        listView =  findViewById(R.id.list_supportticket);
        ImageView back = findViewById(R.id.supportticket_back);
        Button add = findViewById(R.id.add);
        supportTicketList = new ArrayList<>();

        final SharedPrefManager prefManager = new SharedPrefManager(this);
        Customers customers = prefManager.getUserLogin();
        Token = customers.getToken();
        loadPlayer();

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SupportTicketListActivity.this, AddSupportTicketActivity1.class);
                startActivity(intent);
                finish();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                SupportTicket supportTicket = supportTicketList.get(position);
                Intent i = new Intent(getApplicationContext(), SupportTicketActivity.class);
                i.putExtra(SupportTicketActivity.EXTRA_PLAYER, supportTicket);
                startActivity(i);
            }
        });

    }

    public void loadPlayer(){
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://ljn.lintasfiber.net/api/mobile-app/support-ticket-list";
        final ProgressDialog loading = ProgressDialog.show(this, "Support Ticket", "Tunggu Sebentar...", false, false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()  {
                    class SupportTicketView extends ArrayAdapter<SupportTicket> {

                        private List<SupportTicket> supportTicketList;
                        private Context context;
                        TextView Tanggal, Status, Tittle;

                        public SupportTicketView(List<SupportTicket> supportTicketList, Context context) {
                            super(context, R.layout.item_listsupportticket, supportTicketList);
                            this.supportTicketList = supportTicketList;
                            this.context = context;
                        }

                        @Override
                        public View getView(final int position, View convertView, ViewGroup parent) {
                            LayoutInflater inflater = LayoutInflater.from(context);

                            View listViewItem = inflater.inflate(R.layout.item_listsupportticket, null, true);

                            Tanggal = listViewItem.findViewById(R.id.tanggal_supportticket);
                            Tittle = listViewItem.findViewById(R.id.tittle);

                            SupportTicket supportTicket = supportTicketList.get(position);
                            String status = supportTicket.getStatus();
                            if (status.equals("Open")) {
                                Status = listViewItem.findViewById(R.id.status1);
                                Status.setText(supportTicket.getStatus());
                            } else {
                                Status = listViewItem.findViewById(R.id.status2);
                                Status.setText(supportTicket.getStatus());
                            }

                            Tanggal.setText(supportTicket.getDate());
                            Tittle.setText(supportTicket.getTitle());
                            Status.setText(supportTicket.getStatus());

                            return listViewItem;

                        }
                    }

                    @Override
                    public void onResponse(String response) {
                        loading.dismiss();

                        try {
                            JSONObject responseJson = new JSONObject(response);
                            JSONObject result = responseJson.getJSONObject("result");
                            JSONArray playerArray = result.getJSONArray("supportTickets");

                            for (int i = 0; i < playerArray.length(); i++) {
                                JSONObject playerObject = playerArray.getJSONObject(i);

                                SupportTicket supportTicket= new SupportTicket(playerObject.getString("customer_name"),
                                        playerObject.getString("department_name"),
                                        playerObject.getString("title"),
                                        playerObject.getString("content"),
                                        playerObject.getString("status"),
                                        playerObject.getString("priority"),
                                        playerObject.getString("date"));
                                supportTicketList.add(supportTicket);
                            }

                            SupportTicketView adapter = new SupportTicketView(supportTicketList, getApplicationContext());
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
        Intent intent = new Intent(SupportTicketListActivity.this, HomeActivity.class);
        startActivity(intent);
    }
}