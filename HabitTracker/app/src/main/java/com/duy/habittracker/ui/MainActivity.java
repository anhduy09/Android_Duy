package com.duy.habittracker.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.duy.habittracker.R;
import com.duy.habittracker.data.AppDatabase;
import com.duy.habittracker.data.Topic;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private AppDatabase db;
    private RecyclerView recyclerView;
    private TopicAdapter adapter;
    private List<Topic> topics = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = AppDatabase.get(this);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new TopicAdapter(topics, topic -> {
            Intent intent = new Intent(this, TopicActivity.class);
            intent.putExtra("topicId", topic.id);
            startActivity(intent);
        });

        recyclerView.setAdapter(adapter);

        findViewById(R.id.fabAdd).setOnClickListener(v -> {
            Intent i = new Intent(this, TopicEditActivity.class);
            startActivity(i);
        });

        loadTopics();
    }

    private void loadTopics() {
        topics.clear();
        topics.addAll(db.topicDao().getAll());
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadTopics(); // re-query DB and update adapter
    }

    private void addTopic() {
        // TEMP: hardcoded, next step we replace with dialog
        Topic t = new Topic();
        t.name = "Cafe";
        db.topicDao().insert(t);
        loadTopics();
    }
}
