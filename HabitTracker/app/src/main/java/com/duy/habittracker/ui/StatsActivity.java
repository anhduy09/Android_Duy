package com.duy.habittracker.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.duy.habittracker.R;
import com.duy.habittracker.data.AppDatabase;
import com.duy.habittracker.util.DateUtils;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class StatsActivity extends AppCompatActivity {

    private AppDatabase db;
    private int topicId;
    private BarChart barChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        db = AppDatabase.get(this);
        topicId = getIntent().getIntExtra("topicId", -1);

        barChart = findViewById(R.id.barChart);

        Spinner spinner = findViewById(R.id.spinnerRange);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                new String[]{"Day", "Week", "Month"}
        );
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> p, View v, int pos, long id) {
                        if (pos == 0) showDaily();
                        if (pos == 1) showWeekly();
                        if (pos == 2) showMonthly();
                    }
                    @Override public void onNothingSelected(AdapterView<?> p) {}
                }
        );
    }

    private void showDaily() {
        List<BarEntry> entries = new ArrayList<>();
        long start = DateUtils.startOfDay();
        int count = db.recordDao()
                .countInRange(topicId, start, System.currentTimeMillis());

        entries.add(new BarEntry(0, count));
        updateChart(entries, "Today");
    }

    private void showWeekly() {
        List<BarEntry> entries = new ArrayList<>();

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);

        for (int i = 0; i < 7; i++) {
            long start = cal.getTimeInMillis();
            cal.add(Calendar.DAY_OF_MONTH, 1);
            long end = cal.getTimeInMillis();

            int count = db.recordDao()
                    .countInRange(topicId, start, end);

            entries.add(new BarEntry(i, count));
        }

        updateChart(entries, "This Week");
    }

    private void showMonthly() {
        List<BarEntry> entries = new ArrayList<>();

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);

        int daysInMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH);

        for (int i = 0; i < daysInMonth; i++) {
            long start = cal.getTimeInMillis();
            cal.add(Calendar.DAY_OF_MONTH, 1);
            long end = cal.getTimeInMillis();

            int count = db.recordDao()
                    .countInRange(topicId, start, end);

            entries.add(new BarEntry(i + 1, count));
        }

        updateChart(entries, "This Month");
    }



    private void updateChart(List<BarEntry> entries, String label) {
        BarDataSet set = new BarDataSet(entries, label);
        BarData data = new BarData(set);
        barChart.setData(data);
        barChart.invalidate();
    }
}
