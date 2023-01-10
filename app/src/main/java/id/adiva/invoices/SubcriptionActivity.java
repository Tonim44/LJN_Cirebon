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
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.testotp.R;

public class SubcriptionActivity extends AppCompatActivity {

    public static String EXTRA_PLAYER = "extra_player";
    TextView textViewSubcriptionNumber;
    TextView textViewSubcriptionDate;
    TextView textViewTotalSebelum;
    TextView textViewTotal;
    TextView textViewStatus;
    TextView textViewNextInvoiceDate;
    TextView textViewPajak;
    TextView textViewDeskripsi;
    TextView Nama;
    String  CustomerCode;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_subcription);

        ListView listView = findViewById(R.id.detail_subcription);
        listView.setAdapter(new CustomAdapter());
        ImageView back = findViewById(R.id.subcription_back);
        Nama =  (TextView)findViewById(R.id.UserSubcription);

        final SharedPrefManager prefManager = new SharedPrefManager(this);
        Customers customers = prefManager.getUserLogin();
        CustomerCode = customers.getCustomer_code();
        Nama.setText(HomeActivity.Nama.getText());

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

            View v = getLayoutInflater().inflate(R.layout.item_subcription,null);

            textViewSubcriptionNumber = (TextView) v.findViewById(R.id.kodelangganan_subcription);
            textViewSubcriptionDate = (TextView) v.findViewById(R.id.berlangganansejak);
            textViewTotalSebelum = (TextView) v.findViewById(R.id.subtotalsebelum);
            textViewTotal = (TextView) v.findViewById(R.id.total_detail_subcription);
          //  textViewStatus = (TextView) v.findViewById(R.id.status_detail_subcription);
            textViewNextInvoiceDate = (TextView) v.findViewById(R.id.tanggalserviceberikutya);
            textViewPajak = (TextView) v.findViewById(R.id.pajak);
            textViewDeskripsi = (TextView) v.findViewById(R.id.deskripsi_detail_subcription);

            Subcription subcription =  getIntent().getParcelableExtra(EXTRA_PLAYER);

            String subcription_code = subcription.getSubcription_code();
            String subcription_date = subcription.getSubcription_date();
            String subtotal_formatted = subcription.getSubtotal_formatted();
            String total_formatted = subcription.getTotall_formatted();
            //String status = subcription.getStatus();
            String next_invoice_date = subcription.getNext_invoice_date();
            String tax_formatted = subcription.getTax_formatted();
            String description = subcription.getDescription();

            String status = subcription.getStatus();
            if (status.equals("Aktif")) {
                textViewStatus = (TextView) v.findViewById(R.id.status_detail_aktif);
                textViewStatus.setText(status);
            }else{
                textViewStatus = (TextView) v.findViewById(R.id.status_detail_nonaktif);
                textViewStatus.setText(status);
            }

            textViewSubcriptionNumber.setText(subcription_code);
            textViewSubcriptionDate.setText(subcription_date);
            textViewTotalSebelum.setText(subtotal_formatted);
            textViewTotal.setText(total_formatted);
           // textViewStatus.setText(status);
            textViewNextInvoiceDate.setText(next_invoice_date);
            textViewPajak.setText(tax_formatted);
            textViewDeskripsi.setText(description);

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
        Intent intent = new Intent(SubcriptionActivity.this, SubcriptionListActivity.class);
        startActivity(intent);
    }

}