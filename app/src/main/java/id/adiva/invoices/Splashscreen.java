package id.adiva.invoices;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;

import com.example.testotp.R;

public class Splashscreen extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_splashscreen);

        new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    final SharedPrefManager prefManager = new SharedPrefManager(getApplicationContext());
                    Boolean isLoggedIn = prefManager.isUserLoggedIn();

                    if (isLoggedIn) {
                        Intent intent = new Intent(Splashscreen.this, HomeActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                    Intent intent = new Intent(Splashscreen.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
                }
            }, SPLASH_TIME_OUT);
        }

}
