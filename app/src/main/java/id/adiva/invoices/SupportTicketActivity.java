
package id.adiva.invoices;

        import android.annotation.SuppressLint;
        import android.content.Intent;
        import android.content.pm.ActivityInfo;
        import android.os.Bundle;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.BaseAdapter;
        import android.widget.ImageView;
        import android.widget.ListView;
        import android.widget.TextView;
        import androidx.appcompat.app.AppCompatActivity;

        import com.example.testotp.R;

public class SupportTicketActivity extends AppCompatActivity {

    public static String EXTRA_PLAYER = "extra_player";
    TextView Date, Name, Departement, Tittle, Content, Status, Prioity;
    String CustomerCode;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_supportticket);

        ListView listView = findViewById(R.id.detail_supportticket);
        listView.setAdapter(new CustomAdapter());
        ImageView back = findViewById(R.id.supporticket_detail_back);
        Name =  (TextView)findViewById(R.id.UserSupportTicket);

        final SharedPrefManager prefManager = new SharedPrefManager(this);
        Customers customers = prefManager.getUserLogin();
        CustomerCode = customers.getCustomer_code();

        SupportTicket supportTicket =  getIntent().getParcelableExtra(EXTRA_PLAYER);
        String name = supportTicket.getCustomer_name();
        Name.setText(name);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    class CustomAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return 1;
        }

        @Override
        public Object getItem(int i) {
            return 0;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {

            View v = getLayoutInflater().inflate(R.layout.item_supportticket,null);
            Date = (TextView) v.findViewById(R.id.tanggal_supportticket_detail);
            Departement = (TextView) v.findViewById(R.id.nama_departement_detail);
            Tittle = (TextView) v.findViewById(R.id.tittle_detail);
            Content = (TextView) v.findViewById(R.id.content_detail);
            Prioity = (TextView) v.findViewById(R.id.prioitas_detail);

            SupportTicket supportTicket =  getIntent().getParcelableExtra(EXTRA_PLAYER);

            String date = supportTicket.getDate();
            String department_name = supportTicket.getDepartment_name();
            String priority = supportTicket.getPriority();
            String title = supportTicket.getTitle();
            String content = supportTicket.getContentd();

            String status = supportTicket.getStatus();
            if (status.equals("Open")) {
              Status = (TextView) v.findViewById(R.id.status_detail1);
              Status.setText(status);
            }else{
                Status = (TextView) v.findViewById(R.id.status_detail2);
                Status.setText(status);
            }

            Date.setText(date);
            Departement.setText(department_name);
            Prioity.setText(priority);
            Tittle.setText(title);
            Content.setText(content);

            return v;
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(SupportTicketActivity.this, SupportTicketListActivity.class);
        startActivity(intent);
    }

}