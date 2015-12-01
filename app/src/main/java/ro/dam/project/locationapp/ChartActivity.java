package ro.dam.project.locationapp;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import ro.dam.project.locationapp.model.Data;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ChartActivity extends AppCompatActivity {

    private static final String TAG = "ChartActivity";

    // Data set for generating chart
    private List<Data> mData = null;

    // Country code
    private String mCountryCode;

    // Variable id
    private int mId;

    // Variable name
    private String mVariableName;

    // Line chart
    private LineChart mLineChart;

    private HttpRequestTask mHttpRequestTask = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);

        mCountryCode = getIntent().getExtras().getString("countryCode");
        mId = getIntent().getExtras().getInt("id");
        mVariableName = getIntent().getExtras().getString("name");

        setTitle(mVariableName);

        mLineChart = (LineChart) findViewById(R.id.line_chart);

        if(savedInstanceState == null) {
            mHttpRequestTask = new HttpRequestTask(mId, mCountryCode, mVariableName);
            mHttpRequestTask.execute();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {

        outState.putString("name", mVariableName);
        outState.putParcelableArrayList("data", (ArrayList<Data>) mData);

        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        mVariableName = savedInstanceState.getString("name");
        mData = savedInstanceState.getParcelableArrayList("data");
        if(mData != null) {
            createChart(mData);
        }
    }

    private void createChart(List<Data> data) {
        List<String> xVals = new ArrayList<>();
        List<Entry> entries = new ArrayList<>();
        int i = 0;
        for(Data d : data) {
            entries.add(new Entry(d.getValue(), i++));
            xVals.add(Integer.toString(d.getYear()));
        }

        LineDataSet dataSet = new LineDataSet(entries, "");
        dataSet.setDrawValues(false);
        List<LineDataSet> dataSets = new ArrayList<>();
        dataSets.add(dataSet);

        LineData lineData = new LineData(xVals, dataSets);
        mLineChart.setData(lineData);

        // Set X axis position at bottom
        XAxis xAxis = mLineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setAvoidFirstLastClipping(true);

        // Hide right Y axis
        YAxis rightYAxis = mLineChart.getAxisRight();
        rightYAxis.setEnabled(false);
        rightYAxis.setStartAtZero(false);

        // Set left Y axis from value
        mLineChart.getAxisLeft().setStartAtZero(false);

        mLineChart.setScaleMinima(10f, 1f);
        mLineChart.getLegend().setEnabled(false);
        mLineChart.setDescription(mVariableName);
        mLineChart.invalidate();
    }

    private class HttpRequestTask extends AsyncTask<Void, Void, List<Data>> {

        private ProgressDialog dialog = new ProgressDialog(ChartActivity.this);
        private int id;
        private String countryCode;
        private String name;

        public HttpRequestTask(int id, String countryCode, String name) {
            this.id = id;
            this.countryCode = countryCode;
            this.name = name;
        }

        public HttpRequestTask(int id, String countryCode) {
            this.id = id;
            this.countryCode = countryCode;
        }

        /** progress dialog to show user that the backup is processing. */
        /** application context. */
        @Override
        protected void onPreExecute() {
            this.dialog.setMessage("Please wait");
            this.dialog.show();
        }

        @Override
        protected List<Data> doInBackground(Void... params) {
            List<Data> data = null;
            try {
                // Make RESTful web service call using RestTemplate object
                final String url = getResources().getString(R.string.variables_service) + "countries/" + countryCode + "/variables/" + id;
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                Data[] dataArray = restTemplate.getForObject(url, Data[].class);
                data = new ArrayList<>(Arrays.asList(dataArray));
            } catch (Exception e) {
                Log.e(TAG, e.getMessage(), e);
            }
            return data;
        }

        @Override
        protected void onPostExecute(List<Data> data) {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }

            // Validate REST service response
            if(data != null) {
                mData = data;
                createChart(data);
            } else {
                Toast.makeText(getApplicationContext(), "No data available", Toast.LENGTH_LONG).show();
                finish();
            }

        }
    }
}
