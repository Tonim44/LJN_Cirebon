package id.adiva.invoices;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.testotp.R;

public class AddSupportTicketActivity1 extends AppCompatActivity{
    String[] items = {"Tinggi","Menengah","Rendah"};
    public EditText judul;
    public EditText pesan;
    public TextView txtprioitas1;
    private Button Btnpost;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addsupportticket);

        RelativeLayout departement = findViewById(R.id.pilih);
        judul = findViewById(R.id.insert_judul);
        final Spinner prioitas_spinner = findViewById(R.id.spinner_prioitas);
        pesan = findViewById(R.id.insert_pesan);
        txtprioitas1 = (TextView) findViewById(R.id.pilih_priority);
        CardView prioitas = findViewById(R.id.prioitas);
        ImageView back= findViewById(R.id.addsupportticket_back);
        Btnpost = findViewById(R.id.button_kirim);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, items);
        prioitas_spinner.setAdapter(adapter);
        prioitas_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView adapterView, View view, int i, long l) {
                String item = adapterView.getItemAtPosition(i).toString();
                if (item.equals("Tinggi")){
                    txtprioitas1.setText("high");
                } if (item.equals("Menengah")){
                    txtprioitas1.setText("medium");
                } if(item.equals("Rendah")) {
                    txtprioitas1.setText("low");
                }
            }

            @Override
            public void onNothingSelected(AdapterView adapterView) {
                txtprioitas1.setText("Priority");
            }
        });

        departement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final SharedPrefManager prefManager = new SharedPrefManager(getApplicationContext());
                Departements departements = new Departements(judul.getText().toString(), prioitas.toString(), pesan.getText().toString());
                prefManager.setSupportTicket(departements, true);
                Intent intent = new Intent(AddSupportTicketActivity1.this, DepartementActivity.class);
                startActivity(intent);
                finish();
            }
        });

        Btnpost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Toast.makeText(AddSupportTicketActivity1.this, "Masukan Semua Data", Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(AddSupportTicketActivity1.this, SupportTicketListActivity.class);
        startActivity(intent);
    }
}