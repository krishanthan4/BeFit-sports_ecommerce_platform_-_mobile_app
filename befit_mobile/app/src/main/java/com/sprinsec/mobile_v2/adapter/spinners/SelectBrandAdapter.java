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
import com.sprinsec.mobile_v2.data.model.SelectBrandModel;

import java.util.List;

public class SelectBrandAdapter extends ArrayAdapter<SelectBrandModel> {

    List<SelectBrandModel> brandList;
    int layout;

    public SelectBrandAdapter(@NonNull Context context, int resource, @NonNull List<SelectBrandModel> objects) {
        super(context, resource, objects);
        brandList = objects;
        layout = resource;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(layout, parent, false);
        TextView brandName = view.findViewById(R.id.spinner_brand_inner_item_brand_name_text);
        TextView brandId = view.findViewById(R.id.spinner_brand_inner_item_brand_id);

        SelectBrandModel brand = brandList.get(position);
        brandName.setText(brand.getBrand_name());
        brandId.setText(brand.getBrand_id());
        return view;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(layout, parent, false);
        TextView brandName = view.findViewById(R.id.spinner_brand_inner_item_brand_name_text);
        TextView brandId = view.findViewById(R.id.spinner_brand_inner_item_brand_id);

        SelectBrandModel brand = brandList.get(position);
        brandName.setText(brand.getBrand_name());
        brandId.setText(brand.getBrand_id());
        return view;
    }
}
