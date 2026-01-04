package com.duy.habittracker.ui;

import android.os.Bundle;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.duy.habittracker.R;
import com.duy.habittracker.data.AppDatabase;
import com.duy.habittracker.data.Topic;

public class TopicEditActivity extends AppCompatActivity {

    EditText etTopicName;
    AppDatabase db;
    int topicId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic_edit);

        etTopicName = findViewById(R.id.etTopicName);
        db = AppDatabase.get(this);

        topicId = getIntent().getIntExtra("topic_id", -1);

        if (topicId != -1) {
            Topic topic = db.topicDao().getById(topicId);
            etTopicName.setText(topic.name);
        }

        findViewById(R.id.btnSave).setOnClickListener(v -> save());
    }

    private void save() {
        String name = etTopicName.getText().toString().trim();
        if (name.isEmpty()) {
            etTopicName.setError("Please enter topic name");
            return;
        }

        if (topicId == -1) {
            Topic topic = new Topic();
            topic.name = name;
            db.topicDao().insert(topic);
        } else {
            db.topicDao().updateName(topicId, name);
        }
        finish();
    }
}
