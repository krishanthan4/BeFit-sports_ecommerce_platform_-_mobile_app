package com.sprinsec.mobile_v2.adapter.spinners;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.sprinsec.mobile_v2.R;
import com.sprinsec.mobile_v2.data.model.spinners.update_profile_country_model;

import java.util.List;

public class update_profile_country_adapter extends ArrayAdapter<update_profile_country_model> {
    List<update_profile_country_model> countryList;
    int layout;

    public update_profile_country_adapter(@NonNull Context context, int resource, @NonNull List<update_profile_country_model> objects) {
        super(context, resource, objects);
        countryList = objects;
        layout = resource;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(layout, parent, false);
        TextView countryName = view.findViewById(R.id.spinner_user_profile_country_inner_item_user_profile_country_name_text);
        TextView countryId = view.findViewById(R.id.spinner_user_profile_country_inner_item_user_profile_country_id);

        update_profile_country_model country = countryList.get(position);
        countryId.setText(country.getCountry_id());
        countryName.setText(country.getCountry_name());

        return view;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(layout, parent, false);
        TextView countryName = view.findViewById(R.id.spinner_user_profile_country_inner_item_user_profile_country_name_text);
        TextView countryId = view.findViewById(R.id.spinner_user_profile_country_inner_item_user_profile_country_id);

        update_profile_country_model country = countryList.get(position);
        countryId.setText(country.getCountry_id());
        countryName.setText(country.getCountry_name());
        return view;
    }
}
