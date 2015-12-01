package ro.dam.project.locationapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.widget.ImageView;
import android.widget.TextView;

import ro.dam.project.locationapp.dao.CountryInfoDAO;
import ro.dam.project.locationapp.model.CountryInfo;

public class CountryDetailsActivity extends AppCompatActivity {
    String countryName = "";
    ImageView ivFlag;
    TextView tvCountryName;
    TextView tvDescription;
    CountryInfo countryInfo;
    CountryInfoDAO countryInfoDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country_details);

        //countryName from previous activity
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            countryName = extras.getString("countryName");
        }

        ivFlag = (ImageView) findViewById(R.id.ivFlag);
        tvCountryName = (TextView) findViewById(R.id.tvCountryName);
        tvCountryName.setTextSize(TypedValue.COMPLEX_UNIT_SP, 21);

        tvDescription = (TextView) findViewById(R.id.tvDescription);
        tvDescription.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);

        countryInfoDao = new CountryInfoDAO(getApplicationContext());
        countryInfo = countryInfoDao.selectCountryInfoById(countryName);

        int idRes = getResources().getIdentifier(countryInfo.getCountryFlag(), "drawable", getPackageName());
        ivFlag.setImageResource(idRes);

        tvCountryName.setText(countryInfo.getCountryName());
        tvDescription.setText(countryInfo.toString());

    }
}
