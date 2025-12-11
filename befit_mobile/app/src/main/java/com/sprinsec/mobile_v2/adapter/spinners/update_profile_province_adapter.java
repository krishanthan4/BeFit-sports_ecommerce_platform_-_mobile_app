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
import com.sprinsec.mobile_v2.data.model.spinners.update_profile_province_model;

import java.util.List;

public class update_profile_province_adapter extends ArrayAdapter<update_profile_province_model> {
    List<update_profile_province_model> provinceList;
    int layout;

    public update_profile_province_adapter(@NonNull Context context, int resource, @NonNull List<update_profile_province_model> objects) {
        super(context, resource, objects);
        provinceList = objects;
        layout = resource;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(layout, parent, false);
        TextView provinceName = view.findViewById(R.id.spinner_province_inner_item_province_name_text);
        TextView provinceId = view.findViewById(R.id.spinner_province_inner_item_province_id);

        update_profile_province_model province = provinceList.get(position);
        provinceName.setText(province.getProvince_name());
        provinceId.setText(province.getProvince_id());
        return view;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(layout, parent, false);
        TextView provinceName = view.findViewById(R.id.spinner_province_inner_item_province_name_text);
        TextView provinceId = view.findViewById(R.id.spinner_province_inner_item_province_id);

        update_profile_province_model province = provinceList.get(position);
        provinceName.setText(province.getProvince_name());
        provinceId.setText(province.getProvince_id());
        return view;
    }
}
