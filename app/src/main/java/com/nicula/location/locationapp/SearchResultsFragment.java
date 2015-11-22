package com.nicula.location.locationapp;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nicula.location.locationapp.model.IpLocation;


public class SearchResultsFragment extends Fragment {

    private TextView mIpView;
    private TextView mCountryView;
    private TextView mCityView;
    private TextView mLatitudeView;
    private TextView mLongitudeView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search_results, container, false);
        mIpView = (TextView) view.findViewById(R.id.ip_value);
        mCountryView = (TextView) view.findViewById(R.id.country_value);
        mCityView = (TextView) view.findViewById(R.id.city_value);
        mLatitudeView = (TextView) view.findViewById(R.id.latitude_value);
        mLongitudeView = (TextView) view.findViewById(R.id.longitude_value);
        return view;
    }

    public void update(IpLocation location) {
        // Set IP
        mIpView.setText(location.getIp());

        // Set Country
        mCountryView.setText(location.getCountryName());

        // Set city
        mCityView.setText(location.getCity());

        // Set latitude
        mLatitudeView.setText(String.valueOf(location.getLatitude()));

        // Set longitude
        mLongitudeView.setText(String.valueOf(location.getLongitude()));
    }
}
