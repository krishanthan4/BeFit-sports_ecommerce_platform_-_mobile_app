package com.sprinsec.mobile_v2.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.sprinsec.mobile_v2.R;
import com.sprinsec.mobile_v2.data.model.CountrySelectorModel;

import java.util.List;

public class SelectCountryAdapter extends ArrayAdapter<CountrySelectorModel> {

    List<CountrySelectorModel> countryList;
    int layout;

    public SelectCountryAdapter(@NonNull Context context, int resource, @NonNull List<CountrySelectorModel> objects) {
        super(context, resource, objects);
        countryList = objects;
        layout = resource;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(layout, parent, false);
        TextView countryName = view.findViewById(R.id.spinner_country_inner_item_country_name_text);
        ImageView flag = view.findViewById(R.id.spinner_country_inner_item_flag_image);

        CountrySelectorModel country = countryList.get(position);
        countryName.setText(country.getCountryName());
        flag.setImageResource(country.getCountryFlag());
        return view;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(layout, parent, false);
        TextView countryName = view.findViewById(R.id.spinner_country_inner_item_country_name_text);
        ImageView flag = view.findViewById(R.id.spinner_country_inner_item_flag_image);

        CountrySelectorModel country = countryList.get(position);
        countryName.setText(country.getCountryName());
        flag.setImageResource(country.getCountryFlag());
        return view;
    }
}