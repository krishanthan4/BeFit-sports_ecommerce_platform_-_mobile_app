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
import com.sprinsec.mobile_v2.data.model.SelectModelModel;
import com.sprinsec.mobile_v2.data.model.SelectModelModel;

import java.util.List;

public class SelectModelAdapter extends ArrayAdapter<SelectModelModel> {

    List<SelectModelModel> modelList;
    int layout;

    public SelectModelAdapter(@NonNull Context context, int resource, @NonNull List<SelectModelModel> objects) {
        super(context, resource, objects);
        modelList = objects;
        layout = resource;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(layout, parent, false);
        TextView modelName = view.findViewById(R.id.spinner_model_inner_item_model_name_text);
        TextView modelId = view.findViewById(R.id.spinner_model_inner_item_model_id);

        SelectModelModel model = modelList.get(position);
        modelName.setText(model.getModel_name());
        modelId.setText(model.getModel_id());
        return view;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(layout, parent, false);
        TextView modelName = view.findViewById(R.id.spinner_model_inner_item_model_name_text);
        TextView modelId = view.findViewById(R.id.spinner_model_inner_item_model_id);

        SelectModelModel model = modelList.get(position);
        modelName.setText(model.getModel_name());
        modelId.setText(model.getModel_id());
        return view;
    }
}
