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
import com.sprinsec.mobile_v2.data.model.spinners.update_profile_city_model;

import java.util.List;

public class update_proflie_city_adapter extends ArrayAdapter<update_profile_city_model> {
    List<update_profile_city_model> cityList;
    int layout;

    public update_proflie_city_adapter(@NonNull Context context, int resource, @NonNull List<update_profile_city_model> objects) {
        super(context, resource, objects);
        cityList = objects;
        layout = resource;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(layout, parent, false);
        TextView cityName = view.findViewById(R.id.spinner_city_inner_item_city_name_text);
        TextView cityId = view.findViewById(R.id.spinner_city_inner_item_city_id);

        update_profile_city_model city = cityList.get(position);
        cityName.setText(city.getCity_name());
        cityId.setText(city.getCity_id());
        return view;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(layout, parent, false);
        TextView cityName = view.findViewById(R.id.spinner_city_inner_item_city_name_text);
        TextView cityId = view.findViewById(R.id.spinner_city_inner_item_city_id);

        update_profile_city_model city = cityList.get(position);
        cityName.setText(city.getCity_name());
        cityId.setText(city.getCity_id());
        return view;
    }
}
