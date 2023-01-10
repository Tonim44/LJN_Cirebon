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

public class InvoiceActivity extends AppCompatActivity {

        public static String EXTRA_PLAYER = "extra_player";
        TextView textViewInvoiceNumber;
        TextView textViewTanggalInvoice;
        TextView textViewJatuhtempo;
        TextView textViewStatus;
        TextView textViewTanggalLunas;
        TextView textViewTerbayar;
        TextView textViewDeskripsi;
        TextView textViewTotal;
        TextView Nama;
        String CustomerCode;

        @SuppressLint("SetTextI18n")
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            setContentView(R.layout.activity_invoice);

            ListView listView = findViewById(R.id.detail_invoice);
            listView.setAdapter(new CustomAdapter());
            ImageView back = findViewById(R.id.invoice_back);
            Nama =  (TextView)findViewById(R.id.UserInvoice);

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

            View v = getLayoutInflater().inflate(R.layout.item_invoice,null);

            textViewInvoiceNumber = (TextView) v.findViewById(R.id.nomerinvoice);
            textViewTanggalInvoice = (TextView) v.findViewById(R.id.tanggalinvoice);
            textViewJatuhtempo = (TextView) v.findViewById(R.id.jatuhtempoinvoice);
            textViewTanggalLunas = (TextView) v.findViewById(R.id.tanggallunasinvoice);
            textViewTotal = (TextView) v.findViewById(R.id.totalinvoicedetail);
            textViewTerbayar = (TextView) v.findViewById(R.id.terbayarinvoice);
            textViewDeskripsi = (TextView) v.findViewById(R.id.deskripsiinvoice);

            Invoices invoices =  getIntent().getParcelableExtra(EXTRA_PLAYER);

            String invoice_number = invoices.getInvoice_number();
            String invoice_date = invoices.getInvoice_date();
            String due_date = invoices.getDue_date();
            String total_formatted = invoices.getTotal_formatted();
            String paid_date = invoices.getPaid_date();
            String subtotal_formatted = invoices.getSubtotal_formatted();
            String description = invoices.getDescription();

            String status = invoices.getStatus();
            if (status.equals("Lunas")) {
                textViewStatus = (TextView) v.findViewById(R.id.statusinvoicedetail1);
                textViewStatus.setText(status);
            }else{
                textViewStatus = (TextView) v.findViewById(R.id.statusinvoicedetail2);
                textViewStatus.setText(status);
            }

            textViewInvoiceNumber.setText(invoice_number);
            textViewTanggalInvoice.setText(invoice_date);
            textViewJatuhtempo.setText(due_date);
            textViewTotal.setText(total_formatted);
            textViewTanggalLunas.setText(paid_date);
            textViewTerbayar.setText(subtotal_formatted);
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
        Intent intent = new Intent(InvoiceActivity.this, InvoiceListActivity.class);
        startActivity(intent);
    }

    }