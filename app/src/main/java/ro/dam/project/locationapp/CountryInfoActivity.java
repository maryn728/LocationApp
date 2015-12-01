package ro.dam.project.locationapp;

import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import ro.dam.project.locationapp.dao.CountryInfoDAO;
import ro.dam.project.locationapp.dao.IpLocationDAO;
import ro.dam.project.locationapp.dao.UserDAO;
import ro.dam.project.locationapp.model.CountryInfo;
import ro.dam.project.locationapp.model.IpLocation;
import ro.dam.project.locationapp.model.LocationSearch;
import ro.dam.project.locationapp.model.User;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

public class CountryInfoActivity extends AppCompatActivity {

    private static final String TAG = "CountryInfoActivity";

    private EditText mIpView;
    private HttpRequestTask mHttpRequestTask = null;
    private IpLocation mLocation;

    long idUser;
    private IpLocationDAO ipLocDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country_info);

        //user id from previous activity
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            idUser = extras.getLong("userId", -1);
            Log.i("User id", idUser + "");
        }

        FragmentManager fm = getFragmentManager();
        fm.beginTransaction()
                .hide(fm.findFragmentById(R.id.search_results_fragment))
                .commit();
        mIpView = (EditText) findViewById(R.id.ip_edit);
    }

    public void onSearchEvent(View view) {
        String ip = mIpView.getText().toString();
        if (!ip.isEmpty()) {
            mHttpRequestTask = new HttpRequestTask(ip);
            mHttpRequestTask.execute();
        } else {
            Toast.makeText(getApplicationContext(), "Please provide valid IP address", Toast.LENGTH_LONG).show();
        }
    }

    public void onOpenMapEvent(View view) {
        Intent intent = new Intent(this, MapsActivity.class);
        intent.putExtra("latitude", mLocation.getLatitude());
        intent.putExtra("longitude", mLocation.getLongitude());
        startActivity(intent);
    }

    public void onClickVariablesButtonEvent(View view) {
        Intent intent = new Intent(this, VariablesActivity.class);
        intent.putExtra("countryCode", mLocation.getCountryCode());
        startActivity(intent);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {

        outState.putSerializable("location", mLocation);
        outState.putString("ip", mIpView.getText().toString());

        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        mLocation = (IpLocation) savedInstanceState.getSerializable("location");
        if (mLocation != null) {
            mIpView.setText(savedInstanceState.getString("ip"));
            ((SearchResultsFragment) getFragmentManager().findFragmentById(R.id.search_results_fragment))
                    .update((IpLocation) savedInstanceState.getSerializable("location"));
            FragmentManager fm = getFragmentManager();
            fm.beginTransaction()
                    .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
                    .show(fm.findFragmentById(R.id.search_results_fragment))
                    .commit();
        }
    }

    // when I press get country info if i get all data I want to send them to the next activity
    public void onGetCountryInfoEvent(View view) {
        CountryInfoDAO dao = new CountryInfoDAO(getApplicationContext());
        CountryInfo countryI;
        if (mLocation != null) {
            boolean countryExists = dao.verifyCountryInfo(mLocation.getCountryName());
            if (countryExists) {
                countryI = dao.selectCountryInfoById(mLocation.getCountryName());
                if (!countryI.getCapital().isEmpty()) {
                    Intent i = new Intent(CountryInfoActivity.this, CountryDetailsActivity.class);
                    i.putExtra("countryName", countryI.getCountryName());
                    startActivity(i);
                } else {
                    Log.i("Nu e capitala", countryI.getCapital());
                    AlertDialog.Builder builder = new AlertDialog.Builder(CountryInfoActivity.this);
                    builder.setMessage("You'll not be able to get additional info about: '" + mLocation.getCountryName() + "' becaue it's not in DB!");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
                    builder.create().show();
                }
            }
        }
    }

    private class HttpRequestTask extends AsyncTask<Void, Void, IpLocation> {

        private String mIp;
        private ProgressDialog dialog = new ProgressDialog(CountryInfoActivity.this);

        public HttpRequestTask(String ip) {
            mIp = ip;
        }

        /** progress dialog to show user that the backup is processing. */
        /**
         * application context.
         */
        @Override
        protected void onPreExecute() {
            this.dialog.setMessage("Please wait");
            this.dialog.show();
        }

        @Override
        protected IpLocation doInBackground(Void... params) {
            IpLocation location = null;
            try {
                // Make RESTful web service call using RestTemplate object
                final String url = getResources().getString(R.string.location_service) + mIp;
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                location = restTemplate.getForObject(url, IpLocation.class);
            } catch (Exception e) {
                Log.e(TAG, e.getMessage(), e);
            }
            return location;
        }

        @Override
        protected void onPostExecute(IpLocation location) {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }

            // Validate REST service response
            if (location != null) {
                mLocation = location;
                ((SearchResultsFragment) getFragmentManager().findFragmentById(R.id.search_results_fragment)).update(location);
                FragmentManager fm = getFragmentManager();
                fm.beginTransaction()
                        .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
                        .show(fm.findFragmentById(R.id.search_results_fragment))
                        .commit();

                UserDAO dao = new UserDAO(getApplicationContext());
                User user = dao.selectUserById(idUser);

                //Ip_Location object which will be insterted in IP_LOCATION table
                //Ip_Location ipLocation= new Ip_Location(project.getIp(),countryI,user);

                ipLocDAO = new IpLocationDAO(getApplicationContext());

                //if the ip was searched by the same user it wouldn't be inserted in db
                boolean ipInsertedByTheSameUser = ipLocDAO.verifyIpInsertedBySameUser(location.getIp(), user.getIdUser());

                if (!ipInsertedByTheSameUser) {
                    LocationSearch ipLoc1 = ipLocDAO.insertIpLocationData(mLocation.getIp(), location.getCountryName(), user.getIdUser());
                }
            } else {
                Toast.makeText(getApplicationContext(), mIp + " is not a valid IP address or domain", Toast.LENGTH_LONG).show();
            }

        }
    }
}
