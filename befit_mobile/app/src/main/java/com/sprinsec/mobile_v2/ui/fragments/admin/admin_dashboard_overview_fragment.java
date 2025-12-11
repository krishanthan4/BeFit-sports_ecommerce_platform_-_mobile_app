package com.sprinsec.mobile_v2.ui.fragments.admin;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.sprinsec.mobile_v2.R;
import com.sprinsec.mobile_v2.adapter.admin.admin_dashboard_get_feedback_recycler_view_adapter;
import com.sprinsec.mobile_v2.data.api.admin.GetAdminDashboardOverviewService;
import com.sprinsec.mobile_v2.data.model.AdminDashboardFeedbackModel;
import com.sprinsec.mobile_v2.interfaces.GeneralCallBack;
import com.sprinsec.mobile_v2.util.AdminDashboardReportGenerator;
import com.sprinsec.mobile_v2.util.Config;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class admin_dashboard_overview_fragment extends Fragment {

    TextView total_users, total_categories, total_products, total_sellers, best_seller_name, monthly_income_text, total_income_text, best_selling_product;
    LineChart lineChart, lineChart2;
    ImageView  best_selling_product_image, best_seller_image;
    PieChart pieChart;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment__admin__dashboard_overview, container, false);
        initializeViews(view);
        fetchDashboardData();

        return view;
    }

    private void initializeViews(View view) {
        total_users = view.findViewById(R.id.admin_dashboard_overview_total_users_count);
        total_sellers = view.findViewById(R.id.admin_dashboard_overview_total_sellers_count);
        total_products = view.findViewById(R.id.admin_dashboard_overview_total_products_count);
        total_categories = view.findViewById(R.id.admin_dashboard_overview_total_categories_count);
        best_selling_product = view.findViewById(R.id.admin_dashboard_overview_best_selling_product_name);
        best_selling_product_image = view.findViewById(R.id.fragment_admin_dashboard_overview_best_selling_product_image);
        best_seller_name = view.findViewById(R.id.admin_dashboard_overview_best_selling_seller_name);
        best_seller_image = view.findViewById(R.id.admin_dashboard_overview_best_selling_seller_image);
        monthly_income_text = view.findViewById(R.id.admin_dashboard_overview_monthly_income);
        total_income_text = view.findViewById(R.id.admin_dashboard_overview_total_sold_products);
        pieChart = view.findViewById(R.id.admin_dashboard_overview_best_selling_category_pie_chart);
        lineChart = view.findViewById(R.id.admin_dashboard_overview_income_line_chart);
        lineChart2 = view.findViewById(R.id.admin_dashboard_overview_user_registration_line_chart);
    }

    private void fetchDashboardData() {
        GetAdminDashboardOverviewService.getData(new GeneralCallBack() {
            @Override
            public void onSuccess(JsonObject jsonObject) {
                if (getActivity() == null) return;

                getActivity().runOnUiThread(() -> {
                    try {
                        if (jsonObject.has("dashboard_overview_data")) {
                            JsonObject overviewData = jsonObject.getAsJsonObject("dashboard_overview_data");
                            updateDashboardMetrics(overviewData);
                            updateCharts(overviewData);
                            getActivity().findViewById(R.id.admin_dashboard_overview_download_report).setOnClickListener(v -> {
                                try {
                                    AdminDashboardReportGenerator reportGenerator = new AdminDashboardReportGenerator(getActivity(), overviewData);
                                    reportGenerator.generateAndOpenReport();
                                } catch (Exception e) {
                                    Log.e("befit_logs", "Error generating report: ", e);
                                    Toast.makeText(getContext(), "Error opening report. Ensure you have a PDF viewer.", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    } catch (Exception e) {
                        Log.e("befit_logs", "Dashboard overview data processing error: ", e);
                    }
                });
            }

            @Override
            public void onFailure(String errorMessage) {
                if (getActivity() == null) return;
                getActivity().runOnUiThread(() -> {
                    Log.e("befit_logs", "Error: " + errorMessage);
                    Toast.makeText(getContext(), "Something went wrong. Please try again later", Toast.LENGTH_SHORT).show();
                });
            }
        });
    }

    private void updateDashboardMetrics(JsonObject overviewData) {
        total_users.setText(String.valueOf(overviewData.get("users_count").getAsInt()));
        total_sellers.setText(String.valueOf(overviewData.get("sellers_count").getAsInt()));
        total_products.setText(String.valueOf(overviewData.get("products_count").getAsInt()));
        total_categories.setText(String.valueOf(overviewData.get("categories_count").getAsInt()));
        best_selling_product.setText(overviewData.get("best_selling_product_name").getAsString());
        best_seller_name.setText(overviewData.get("best_sold_seller_name").getAsString());
        Glide.with(this).load(Config.BACKEND_URL+overviewData.get("best_selling_product_img").getAsString().substring(2)).error(R.drawable.default_product_image2).into(best_selling_product_image);
        Glide.with(this).load(Config.BACKEND_URL+overviewData.get("best_sold_seller_profile_img").getAsString().substring(2)).error(R.drawable.profile_icon3).into(best_seller_image);
        monthly_income_text.setText(String.valueOf(overviewData.get("monthlyincome").getAsInt()));
        total_income_text.setText(String.valueOf(overviewData.get("totalSoldProducts").getAsInt()));
    }

    private void updateCharts(JsonObject overviewData) {
        loadTotalIncomeChart(overviewData.getAsJsonObject("total_income_chart_data"));
        loadMostSoldCategoryChart(overviewData.getAsJsonArray("most_sold_categories_chart_data"));
        loadUserRegistrationsChart(overviewData.getAsJsonArray("user_registration_chart_data"));
    }

    private void loadTotalIncomeChart(JsonObject incomeData) {
        List<Entry> lineEntryList = new ArrayList<>();
        int index = 0;

        for (Map.Entry<String, JsonElement> entry : incomeData.entrySet()) {
            lineEntryList.add(new Entry(index++, entry.getValue().getAsFloat()));
        }

        LineDataSet lineDataSet = new LineDataSet(lineEntryList, "Monthly Income");
        lineDataSet.setDrawFilled(true);
        lineDataSet.setFillDrawable(getResources().getDrawable(R.drawable.admin_dashboard_user_chart_gradient_background, null));

        LineData lineData = new LineData(lineDataSet);
        lineData.setValueTextSize(10);
        lineData.setValueTextColor(getActivity().getColor(R.color.black));
        lineChart.getAxisRight().setEnabled(false);
        lineChart.getDescription().setEnabled(false);
        lineChart.setData(lineData);
        lineChart.animateY(2000, Easing.EaseInCirc);
        lineChart.invalidate();
    }

    private void loadMostSoldCategoryChart(JsonArray categoryData) {
        List<PieEntry> pieEntryList = new ArrayList<>();

        for (JsonElement element : categoryData) {
            JsonObject category = element.getAsJsonObject();
            pieEntryList.add(new PieEntry(
                    category.get("total_sales").getAsFloat(),
                    category.get("category_name").getAsString()
            ));
        }

        PieDataSet pieDataSet = new PieDataSet(pieEntryList, "Most Sold Categories");
pieDataSet.setColors(new int[]{0xFF1B2631, 0xFF212F3C, 0xFF283747, 0xFF2E4053, 0xFF34495E, 0xFF5D6D7E, 0xFF85929E});
pieDataSet.setValueTextSize(10);

        PieData pieData = new PieData(pieDataSet);
        pieData.setValueTextColor(getActivity().getColor(R.color.gray_200));

        pieChart.setDescription(null);
        pieChart.setData(pieData);
        pieChart.animateY(2000, Easing.EaseInCirc);
        pieChart.invalidate();
    }

    private void loadUserRegistrationsChart(JsonArray registrationData) {
        List<Entry> lineEntryList = new ArrayList<>();

        for (JsonElement element : registrationData) {
            JsonObject registration = element.getAsJsonObject();
            lineEntryList.add(new Entry(
                    registration.get("month").getAsFloat(),
                    registration.get("user_count").getAsFloat()
            ));
        }

        LineDataSet lineDataSet = new LineDataSet(lineEntryList, "User Registrations");
        lineDataSet.setDrawFilled(true);
        lineDataSet.setFillDrawable(getResources().getDrawable(R.drawable.admin_dashoard_total_income_gradient_chart_background, null));

        LineData lineData = new LineData(lineDataSet);
        lineData.setValueTextSize(10);
        lineData.setValueTextColor(getActivity().getColor(R.color.black));
        lineChart2.getAxisRight().setEnabled(false);
        lineChart2.getDescription().setEnabled(false);
        lineChart2.setData(lineData);
        lineChart2.animateY(2000, Easing.EaseInCirc);
        lineChart2.invalidate();
    }
}