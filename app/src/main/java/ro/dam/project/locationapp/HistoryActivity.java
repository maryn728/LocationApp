package ro.dam.project.locationapp;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ro.dam.project.locationapp.dao.IpLocationDAO;
import ro.dam.project.locationapp.model.LocationSearch;

public class HistoryActivity extends ListActivity {
    private IpLocationDAO ipLocationDao;
    private List<LocationSearch> ipLocationList;
    private List<String> ipLocStrList = new ArrayList<>();
    private long idUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        //user id from previous activity
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            idUser = extras.getLong("userId", -1);
        }

        ipLocationDao = new IpLocationDAO(getApplicationContext());
        ipLocationList = ipLocationDao.selectAllIpAddressesByUserId(idUser);

        if (ipLocationList.size() > 0) {
            for (int i = 0; i < ipLocationList.size(); i++) {
                ipLocStrList.add(i + 1 + ") IP address: " + ipLocationList.get(i).getIp() + ", is from " + ipLocationList.get(i).getCountryInfo().getCountryName());
            }
        }

        if (ipLocStrList.size() > 0) {
            setListAdapter(new ArrayAdapter(this, android.R.layout.simple_list_item_1, ipLocStrList));
        } else {
            Toast.makeText(HistoryActivity.this, "You haven't searched any IP address yet!", Toast.LENGTH_SHORT).show();
        }
    }
}
