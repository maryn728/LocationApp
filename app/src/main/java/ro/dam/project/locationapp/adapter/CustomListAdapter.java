package ro.dam.project.locationapp.adapter;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import ro.dam.project.locationapp.ChartActivity;
import ro.dam.project.locationapp.R;
import ro.dam.project.locationapp.model.Variable;

import java.util.List;

public class CustomListAdapter extends BaseAdapter {

    private Activity mActivity;
    private LayoutInflater mInflater;
    private List<Variable> mVariables;
    private String mCountryCode;

    public CustomListAdapter(Activity activity, List<Variable> variables, String countryCode) {
        this.mActivity = activity;
        this.mVariables = variables;
        this.mCountryCode = countryCode;
    }

    @Override
    public int getCount() {
        return mVariables.size();
    }

    @Override
    public Object getItem(int position) {
        return mVariables.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (mInflater == null)
            mInflater = (LayoutInflater) mActivity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = mInflater.inflate(R.layout.list_row, null);

        TextView name = (TextView) convertView.findViewById(R.id.name);
        TextView units = (TextView) convertView.findViewById(R.id.units);
        TextView subtheme = (TextView) convertView.findViewById(R.id.subtheme);
        TextView interval = (TextView) convertView.findViewById(R.id.time_interval);

        // Get the variable for the given position
        final Variable variable = mVariables.get(position);

        // Name
        name.setText(variable.getShortName() == null ? variable.getName() : variable.getShortName());

        // Units
        units.setText(variable.getUnits());

        // Subtheme
        subtheme.setText(variable.getGeoSubTheme());

        // Time Interval
        interval.setText(variable.getTimePeriod());

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ChartActivity.class);
                intent.putExtra("countryCode", mCountryCode);
                intent.putExtra("id", variable.getId());
                intent.putExtra("name", variable.getShortName() == null ? variable.getName() : variable.getShortName());
                v.getContext().startActivity(intent);
            }
        });

        return convertView;
    }
}
