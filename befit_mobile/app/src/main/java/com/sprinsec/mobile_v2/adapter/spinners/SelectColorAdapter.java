package com.sprinsec.mobile_v2.adapter.spinners;

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
import com.sprinsec.mobile_v2.data.model.SelectColorModel;

import java.util.List;

public class SelectColorAdapter extends ArrayAdapter<SelectColorModel> {

    List<SelectColorModel> colorList;
    int layout;

    public SelectColorAdapter(@NonNull Context context, int resource, @NonNull List<SelectColorModel> objects) {
        super(context, resource, objects);
        colorList = objects;
        layout = resource;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(layout, parent, false);
        TextView colorName = view.findViewById(R.id.spinner_color_inner_item_color_name_text);
        TextView colorId = view.findViewById(R.id.spinner_color_inner_item_color_id);

        SelectColorModel color = colorList.get(position);
        colorName.setText(color.getColorName());
        colorId.setText(color.getColorId());
        return view;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(layout, parent, false);
        TextView colorName = view.findViewById(R.id.spinner_color_inner_item_color_name_text);
        TextView colorId = view.findViewById(R.id.spinner_color_inner_item_color_id);

        SelectColorModel color = colorList.get(position);
        colorName.setText(color.getColorName());
        colorId.setText(color.getColorId());
        return view;
    }
}
