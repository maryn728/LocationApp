package ro.dam.project.locationapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HomeActivity extends AppCompatActivity {

    private Button btnSearchIp;
    private Button btnHistoryIp;
    private long idUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //user id from previous activity
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            idUser = extras.getLong("userId", -1);
        }

        btnSearchIp = (Button) findViewById(R.id.buttonSearchIp);
        btnSearchIp.setTextColor(getResources().getColor(R.color.dpurple));
        btnSearchIp.setTextSize(18);

        btnHistoryIp = (Button) findViewById(R.id.buttonHistory);
        btnHistoryIp.setTextColor(getResources().getColor(R.color.dpurple));
        btnHistoryIp.setTextSize(18);

        btnSearchIp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomeActivity.this, CountryInfoActivity.class);
                i.putExtra("userId", idUser);
                startActivity(i);
            }
        });

        btnHistoryIp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomeActivity.this, HistoryActivity.class);
                i.putExtra("userId", idUser);
                startActivity(i);
            }
        });
    }
}
