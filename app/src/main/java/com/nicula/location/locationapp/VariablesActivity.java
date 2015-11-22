package com.nicula.location.locationapp;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.nicula.location.locationapp.adapter.CustomListAdapter;
import com.nicula.location.locationapp.model.Variable;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class VariablesActivity extends AppCompatActivity {

    // ListView reference
    private ListView mVariableListView;

    //Custom adapter for displaying variables
    private CustomListAdapter mAdapter;

    // List of variable objects
    private List<Variable> mVariables;

    private HttpRequestTask mHttpRequestTask = null;

    private String mCountryCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_variables);

        // Retrieve country code
        mCountryCode = getIntent().getExtras().getString("countryCode");
        mVariables = new ArrayList<>();

        mVariableListView = (ListView) findViewById(R.id.variable_list);

        // Retrieve variables from web service
        if(savedInstanceState == null) {
            mHttpRequestTask = new HttpRequestTask();
            mHttpRequestTask.execute();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {

        outState.putString("countryCode", mCountryCode);
        outState.putParcelableArrayList("variables", (ArrayList<Variable>) mVariables);

        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        mCountryCode = savedInstanceState.getString("countryCode");
        mVariables = savedInstanceState.getParcelableArrayList("variables");
        mAdapter = new CustomListAdapter(VariablesActivity.this, mVariables, mCountryCode);
        mVariableListView.setAdapter(mAdapter);
    }

    private class HttpRequestTask extends AsyncTask<Void, Void, List<Variable>> {

        private ProgressDialog dialog = new ProgressDialog(VariablesActivity.this);

        /** progress dialog to show user that the backup is processing. */
        /** application context. */
        @Override
        protected void onPreExecute() {
            this.dialog.setMessage("Please wait");
            this.dialog.show();
        }

        @Override
        protected List<Variable> doInBackground(Void... params) {
            List<Variable> variables = null;
            try {
                // Make RESTful web service call using RestTemplate object
                final String url = getResources().getString(R.string.variables_service) + "variables";
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                Variable[] variableArray = restTemplate.getForObject(url, Variable[].class);
                variables = new ArrayList<>(Arrays.asList(variableArray));
            } catch (Exception e) {
                Log.e("VariablesActivity", e.getMessage(), e);
            }
            return variables;
        }

        @Override
        protected void onPostExecute(List<Variable> variables) {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }

            // Validate REST service response
            if(variables != null) {
                mVariables = variables;
                mAdapter = new CustomListAdapter(VariablesActivity.this, mVariables, mCountryCode);
                mVariableListView.setAdapter(mAdapter);
            } else {
                Toast.makeText(getApplicationContext(), "Service Unreachable", Toast.LENGTH_LONG).show();
                finish();
            }

        }
    }
}
