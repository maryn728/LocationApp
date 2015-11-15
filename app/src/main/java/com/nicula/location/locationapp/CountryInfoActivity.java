package com.nicula.location.locationapp;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

public class CountryInfoActivity extends AppCompatActivity {

    private EditText mIpView;
    private HttpRequestTask mHttpRequestTask = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country_info);
        FragmentManager fm = getFragmentManager();
        fm.beginTransaction()
                .hide(fm.findFragmentById(R.id.search_results_fragment))
                .commit();
        mIpView = (EditText) findViewById(R.id.ip_edit);
    }

    public void onSearchEvent(View view) {
        String ip = mIpView.getText().toString();
        if(!ip.isEmpty()) {
            mHttpRequestTask = new HttpRequestTask(ip);
            mHttpRequestTask.execute();
        } else {
            Toast.makeText(getApplicationContext(), "Please provide valid IP address", Toast.LENGTH_LONG).show();
        }
    }

    public void onOpenMapEvent(View view) {
        Intent intent = new Intent(this, MapsActivity.class);
        double latitude = Double.parseDouble(((TextView) findViewById(R.id.latitude_value)).getText().toString());
        double longitude = Double.parseDouble(((TextView) findViewById(R.id.longitude_value)).getText().toString());
        intent.putExtra("latitude", latitude);
        intent.putExtra("longitude", longitude);
        startActivity(intent);
    }

    private class HttpRequestTask extends AsyncTask<Void, Void, IpLocation> {

        private String mIp;
        private ProgressDialog dialog = new ProgressDialog(CountryInfoActivity.this);

        public HttpRequestTask(String ip) {
            mIp = ip;
        }

        /** progress dialog to show user that the backup is processing. */
        /** application context. */
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
                final String url = "https://freegeoip.net/json/" + mIp;
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                location = restTemplate.getForObject(url, IpLocation.class);
            } catch (Exception e) {
                Log.e("CountryInfoActivity", e.getMessage(), e);
            }
            return location;
        }

        @Override
        protected void onPostExecute(IpLocation location) {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }

            ((SearchResultsFragment)getFragmentManager().findFragmentById(R.id.search_results_fragment)).update(location);
            FragmentManager fm = getFragmentManager();
            fm.beginTransaction()
                    .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
                    .show(fm.findFragmentById(R.id.search_results_fragment))
                    .commit();
        }
    }
}
