package com.example.bmi;

import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class WeightHistoryActivity extends AppCompatActivity {
    private LineChart lineChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weight_history);

        lineChart = findViewById(R.id.line_chart);

        List<Entry> entries = getWeightDataEntries();
        LineDataSet dataSet = new LineDataSet(entries, "Weight History");
        LineData lineData = new LineData(dataSet);
        lineChart.setData(lineData);
        lineChart.invalidate(); // refresh
    }

    private List<Entry> getWeightDataEntries() {
        SharedPreferences preferences = getSharedPreferences("user_details", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = preferences.getString("weight_history", "");
        Type type = new TypeToken<List<String>>() {}.getType();
        List<String> weightHistory = gson.fromJson(json, type);

        List<Entry> entries = new ArrayList<>();
        if (weightHistory != null) {
            for (int i = 0; i < weightHistory.size(); i++) {
                entries.add(new Entry(i, Float.parseFloat(weightHistory.get(i))));
            }
        }

        return entries;
    }
}
