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
import com.sprinsec.mobile_v2.data.model.UserHomeCategoryModel;

import java.util.List;

public class SelectCategoryAdapter extends ArrayAdapter<UserHomeCategoryModel> {

    List<UserHomeCategoryModel> categoryList;
    int layout;

    public SelectCategoryAdapter(@NonNull Context context, int resource, @NonNull List<UserHomeCategoryModel> objects) {
        super(context, resource, objects);
        categoryList = objects;
        layout = resource;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(layout, parent, false);
        TextView categoryName = view.findViewById(R.id.spinner_category_inner_item_category_name_text);
        TextView categoryId = view.findViewById(R.id.spinner_category_inner_item_category_id);

        UserHomeCategoryModel category = categoryList.get(position);
        categoryName.setText(category.getCategoryName());
        categoryId.setText(category.getCategoryId());
        return view;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(layout, parent, false);
        TextView categoryName = view.findViewById(R.id.spinner_category_inner_item_category_name_text);
        TextView categoryId = view.findViewById(R.id.spinner_category_inner_item_category_id);

        UserHomeCategoryModel category = categoryList.get(position);
        categoryName.setText(category.getCategoryName());
        categoryId.setText(category.getCategoryId());
        return view;
    }
}
