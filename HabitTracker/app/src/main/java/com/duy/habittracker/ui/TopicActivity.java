package com.duy.habittracker.ui;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.duy.habittracker.R;
import com.duy.habittracker.data.AppDatabase;
import com.duy.habittracker.data.Topic;
import com.duy.habittracker.data.TrackRecord;

import java.text.DateFormat;
import java.util.Date;

public class TopicActivity extends AppCompatActivity {

    private AppDatabase db;
    private int topicId;
    private Topic topic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic);

        db = AppDatabase.get(this);
        topicId = getIntent().getIntExtra("topicId", -1);

        TextView txtTopicName = findViewById(R.id.txtTopicName);
        TextView txtLastTime = findViewById(R.id.txtLastTime);

        topic = db.topicDao().getById(topicId);
        txtTopicName.setText(topic.name);

        updateLastTime(txtLastTime);

        findViewById(R.id.btnRecord).setOnClickListener(v -> {
            TrackRecord r = new TrackRecord();
            r.topicId = topicId;
            r.timestamp = System.currentTimeMillis();
            db.recordDao().insert(r);

            updateLastTime(txtLastTime);
        });
    }

    private void updateLastTime(TextView txt) {
        TrackRecord last = db.recordDao().getLast(topicId);
        if (last != null) {
            String time = DateFormat.getDateTimeInstance()
                    .format(new Date(last.timestamp));

            txt.setText(getString(R.string.last_record, time));
        } else {
            txt.setText(R.string.last_record_none);
        }
    }

}
