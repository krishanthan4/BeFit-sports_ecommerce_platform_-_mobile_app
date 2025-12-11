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
import com.sprinsec.mobile_v2.data.model.spinners.update_profile_district_model;
import com.sprinsec.mobile_v2.data.model.spinners.update_profile_district_model;

import java.util.List;

public class update_profile_district_adapter extends ArrayAdapter<update_profile_district_model> {
    List<update_profile_district_model> districtList;
    int layout;

    public update_profile_district_adapter(@NonNull Context context, int resource, @NonNull List<update_profile_district_model> objects) {
        super(context, resource, objects);
        districtList = objects;
        layout = resource;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(layout, parent, false);
        TextView districtName = view.findViewById(R.id.spinner_district_inner_item_district_name_text);
        TextView districtId = view.findViewById(R.id.spinner_district_inner_item_district_id);

        update_profile_district_model district = districtList.get(position);
        districtName.setText(district.getDistrict_name());
        districtId.setText(district.getDistrict_id());
        return view;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(layout, parent, false);
        TextView districtName = view.findViewById(R.id.spinner_district_inner_item_district_name_text);
        TextView districtId = view.findViewById(R.id.spinner_district_inner_item_district_id);

        update_profile_district_model district = districtList.get(position);
        districtName.setText(district.getDistrict_name());
        districtId.setText(district.getDistrict_id());
        return view;
    }
}
