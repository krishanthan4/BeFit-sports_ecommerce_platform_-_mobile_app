package com.sprinsec.mobile_v2.util;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.pdf.PdfDocument;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

import androidx.core.content.FileProvider;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.sprinsec.mobile_v2.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class AdminDashboardReportGenerator {
    private static final int PAGE_WIDTH = 595;
    private static final int PAGE_HEIGHT = 842;
    private static final int MARGIN = 50;
    private static final int CHART_WIDTH = 400;
    private static final int CHART_HEIGHT = 200;
    // Colors for the report
    private static final int PRIMARY_COLOR = Color.rgb(33, 150, 243);   // Blue
    private static final int SECONDARY_COLOR = Color.rgb(76, 175, 80);  // Green
    private static final int ACCENT_COLOR = Color.rgb(255, 152, 0);     // Orange
    private static final int TEXT_COLOR = Color.rgb(33, 33, 33);        // Dark Gray
    private Activity activity;
    private JsonObject dashboardData;

    public AdminDashboardReportGenerator(Activity activity, JsonObject dashboardData) {
        this.activity = activity;
        this.dashboardData = dashboardData;
    }

    public void generateAndOpenReport() {
        PdfDocument document = new PdfDocument();
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(PAGE_WIDTH, PAGE_HEIGHT, 1).create();
        PdfDocument.Page page = document.startPage(pageInfo);
        Canvas canvas = page.getCanvas();

        // Initialize paints with simplified styling
        Paint titlePaint = new Paint();
        titlePaint.setTextSize(28);
        titlePaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        titlePaint.setColor(Color.BLACK);

        Paint headingPaint = new Paint();
        headingPaint.setTextSize(20);
        headingPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        headingPaint.setColor(Color.BLACK);

        Paint textPaint = new Paint();
        textPaint.setTextSize(14);
        textPaint.setColor(Color.BLACK);

        Paint boxPaint = new Paint();
        boxPaint.setStyle(Paint.Style.FILL);

        int yOffset = MARGIN;

        // Draw header background (white)
        boxPaint.setColor(Color.WHITE);
        canvas.drawRect(0, 0, PAGE_WIDTH, MARGIN * 3, boxPaint);

        // Add centered logo
        try {
            Bitmap logoBitmap = ((BitmapDrawable) activity.getResources()
                    .getDrawable(R.drawable.app_logo)).getBitmap();
            canvas.drawBitmap(logoBitmap, null,
                    new RectF((PAGE_WIDTH - 100) / 2, MARGIN / 2,
                            (PAGE_WIDTH + 100) / 2, MARGIN / 2 + 100), null);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Add report header
        String currentDate = new SimpleDateFormat("dd MMMM yyyy", Locale.getDefault()).format(new Date());
        yOffset = MARGIN * 3 + 20;
        canvas.drawText("Administrative Dashboard Report", MARGIN, yOffset, titlePaint);
        yOffset += 30;
        canvas.drawText("Generated on: " + currentDate, MARGIN, yOffset, textPaint);
        yOffset += 40;

        // Add company details
        canvas.drawText("Company Name: BeFit", MARGIN, yOffset, textPaint);
        yOffset += 20;
        canvas.drawText("Address: " + Config.COMPANY_ADDRESS, MARGIN, yOffset, textPaint);
        yOffset += 20;
        canvas.drawText("Contact: " + Config.COMPANY_PHONE, MARGIN, yOffset, textPaint);
        yOffset += 40;

        // Draw metrics boxes
        drawMetricsBoxes(canvas, yOffset, boxPaint, textPaint);
        yOffset += 150;

        // Draw income chart with proper spacing
        canvas.drawText("Monthly Income Trend", MARGIN, yOffset + 20, headingPaint);
        yOffset += 45;
        drawIncomeChart(canvas, yOffset, dashboardData.getAsJsonObject("total_income_chart_data"));
        yOffset += CHART_HEIGHT + 60;

        document.finishPage(page);

        // Create second page for additional details
        pageInfo = new PdfDocument.PageInfo.Builder(PAGE_WIDTH, PAGE_HEIGHT, 2).create();
        page = document.startPage(pageInfo);
        canvas = page.getCanvas();
        yOffset = MARGIN;

        // Draw category sales pie chart with proper spacing and full view
        canvas.drawText("Category Sales Distribution", MARGIN, yOffset, headingPaint);
        yOffset += 30;
        drawCategorySalesChart(canvas, yOffset, dashboardData.getAsJsonArray("most_sold_categories_chart_data"));
        yOffset += CHART_HEIGHT + 60;

        // Draw user registration chart with proper spacing
        canvas.drawText("User Registration Growth", MARGIN, yOffset, headingPaint);
        yOffset += 30;
        drawUserRegistrationChart(canvas, yOffset, dashboardData.getAsJsonArray("user_registration_chart_data"));

        document.finishPage(page);
        savePdfAndOpen(document);
    }

    private void drawCategorySalesChart(Canvas canvas, int yOffset, JsonArray categoryData) {
        Paint piePaint = new Paint();
        piePaint.setStyle(Paint.Style.FILL);
        Paint labelPaint = new Paint();
        labelPaint.setColor(Color.BLACK);
        labelPaint.setTextSize(12);

        float total = 0;
        for (JsonElement element : categoryData) {
            total += element.getAsJsonObject().get("total_sales").getAsFloat();
        }

        // Center the pie chart
        int chartSize = CHART_HEIGHT;
        int startX = MARGIN + 50;  // Add some margin for labels
        RectF pieRect = new RectF(startX, yOffset, startX + chartSize, yOffset + chartSize);

        float startAngle = 0;
        int[] pieColors = {
                Color.rgb(233, 30, 99),   // Pink
                Color.rgb(156, 39, 176),  // Purple
                Color.rgb(33, 150, 243),  // Blue
                Color.rgb(76, 175, 80),   // Green
                Color.rgb(255, 152, 0)    // Orange
        };

        // Draw legend
        int legendX = startX + chartSize + 50;
        int legendY = yOffset + 20;

        int colorIndex = 0;
        for (JsonElement element : categoryData) {
            JsonObject category = element.getAsJsonObject();
            float value = category.get("total_sales").getAsFloat();
            float sweepAngle = (value / total) * 360;
            String categoryName = category.get("category_name").getAsString();

            // Draw pie slice
            piePaint.setColor(pieColors[colorIndex % pieColors.length]);
            canvas.drawArc(pieRect, startAngle, sweepAngle, true, piePaint);

            // Draw legend item
            RectF legendRect = new RectF(legendX, legendY - 10, legendX + 20, legendY + 10);
            canvas.drawRect(legendRect, piePaint);
            canvas.drawText(categoryName + " (" + String.format("%.1f", (value / total) * 100) + "%)",
                    legendX + 30, legendY + 5, labelPaint);

            startAngle += sweepAngle;
            colorIndex++;
            legendY += 25;
        }
    }

    private void drawUserRegistrationChart(Canvas canvas, int yOffset, JsonArray registrationData) {
        Paint chartPaint = new Paint();
        chartPaint.setStyle(Paint.Style.STROKE);
        chartPaint.setColor(Color.BLACK);
        chartPaint.setStrokeWidth(3);

        Paint gridPaint = new Paint();
        gridPaint.setColor(Color.LTGRAY);
        gridPaint.setStrokeWidth(1);

        Paint labelPaint = new Paint();
        labelPaint.setColor(Color.BLACK);
        labelPaint.setTextSize(12);

        List<Float> values = new ArrayList<>();
        float maxValue = 0;

        for (JsonElement element : registrationData) {
            JsonObject registration = element.getAsJsonObject();
            float value = registration.get("user_count").getAsFloat();
            values.add(value);
            maxValue = Math.max(maxValue, value);
        }

        // Round up maxValue to nearest 100
        maxValue = ((int) ((maxValue + 99) / 100)) * 100;

        float xStep = CHART_WIDTH / (values.size() - 1);
        float yScale = CHART_HEIGHT / maxValue;

        // Draw grid lines and labels
        for (int i = 0; i <= 5; i++) {
            float y = yOffset + CHART_HEIGHT - (i * CHART_HEIGHT / 5);
            canvas.drawLine(MARGIN, y, MARGIN + CHART_WIDTH, y, gridPaint);
            canvas.drawText(String.valueOf((int) (maxValue * i / 5)),
                    MARGIN - 40, y + 5, labelPaint);
        }

        // Draw x-axis labels
        for (int i = 0; i < values.size(); i++) {
            float x = MARGIN + (i * xStep);
            canvas.drawText("M" + (i + 1), x - 10, yOffset + CHART_HEIGHT + 20, labelPaint);
        }

        // Draw the line chart
        Path path = new Path();
        path.moveTo(MARGIN, yOffset + CHART_HEIGHT - (values.get(0) * yScale));

        for (int i = 1; i < values.size(); i++) {
            float x = MARGIN + (i * xStep);
            float y = yOffset + CHART_HEIGHT - (values.get(i) * yScale);
            path.lineTo(x, y);
        }

        canvas.drawPath(path, chartPaint);
    }

    private void drawMetricsBoxes(Canvas canvas, int yOffset, Paint boxPaint, Paint textPaint) {
        int boxWidth = (PAGE_WIDTH - (MARGIN * 3)) / 2;
        int boxHeight = 60;
        int spacing = 20;

        // Define metrics data
        String[][] metrics = {
                {"Total Users", String.valueOf(dashboardData.get("users_count").getAsInt())},
                {"Total Sellers", String.valueOf(dashboardData.get("sellers_count").getAsInt())},
                {"Total Products", String.valueOf(dashboardData.get("products_count").getAsInt())},
                {"Monthly Income", "$" + dashboardData.get("monthlyincome").getAsInt()}
        };

        int[] boxColors = {
                Color.rgb(233, 30, 99),   // Pink
                Color.rgb(156, 39, 176),  // Purple
                Color.rgb(33, 150, 243),  // Blue
                Color.rgb(76, 175, 80)    // Green
        };

        for (int i = 0; i < metrics.length; i++) {
            int row = i / 2;
            int col = i % 2;
            int left = MARGIN + (col * (boxWidth + spacing));
            int top = yOffset + (row * (boxHeight + spacing));

            // Draw box
            boxPaint.setColor(boxColors[i]);
            RectF boxRect = new RectF(left, top, left + boxWidth, top + boxHeight);
            canvas.drawRoundRect(boxRect, 15, 15, boxPaint);

            // Draw text
            textPaint.setColor(Color.WHITE);
            textPaint.setTextSize(16);
            canvas.drawText(metrics[i][0], left + 20, top + 25, textPaint);
            textPaint.setTextSize(20);
            textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
            canvas.drawText(metrics[i][1], left + 20, top + 50, textPaint);
            textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
            textPaint.setColor(TEXT_COLOR);
            textPaint.setTextSize(14);
        }
    }

private void drawIncomeChart(Canvas canvas, int yOffset, JsonObject incomeData) {
    Paint chartPaint = new Paint();
    chartPaint.setStyle(Paint.Style.STROKE);
    chartPaint.setColor(PRIMARY_COLOR);
    chartPaint.setStrokeWidth(3);

    Paint numberPaint = new Paint();
    numberPaint.setColor(Color.BLACK);
    numberPaint.setTextSize(12);

    List<Float> values = new ArrayList<>();
    float maxValue = 0;

    // Convert data to points
    for (Map.Entry<String, JsonElement> entry : incomeData.entrySet()) {
        float value = entry.getValue().getAsFloat();
        values.add(value);
        maxValue = Math.max(maxValue, value);
    }

    // Draw chart
    float xStep = CHART_WIDTH / (values.size() - 1);
    float yScale = CHART_HEIGHT / maxValue;

    Path path = new Path();
    path.moveTo(MARGIN, yOffset + CHART_HEIGHT - (values.get(0) * yScale));

    for (int i = 1; i < values.size(); i++) {
        float x = MARGIN + (i * xStep);
        float y = yOffset + CHART_HEIGHT - (values.get(i) * yScale);
        path.lineTo(x, y);
        canvas.drawText(String.valueOf(values.get(i)), x, y - 10, numberPaint);
    }

    canvas.drawPath(path, chartPaint);
}
    private void savePdfAndOpen(PdfDocument document) {
        try {
            File reportsDir = new File(activity.getFilesDir(), "reports");
            if (!reportsDir.exists()) {
                reportsDir.mkdirs();
            }

            String fileName = "BeFit_Report_" +
                    new SimpleDateFormat("dd_MM_yyyy_HH_mm", Locale.getDefault()).format(new Date()) +
                    ".pdf";
            File reportFile = new File(reportsDir, fileName);
            FileOutputStream fos = new FileOutputStream(reportFile);
            document.writeTo(fos);
            document.close();
            fos.close();

            Uri fileUri = FileProvider.getUriForFile(
                    activity,
                    activity.getPackageName() + ".fileprovider",
                    reportFile
            );

            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(fileUri, "application/pdf");
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            activity.startActivity(intent);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}