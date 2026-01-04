package com.duy.habittracker.ui;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.duy.habittracker.R;
import com.duy.habittracker.data.AppDatabase;
import com.duy.habittracker.data.Topic;
import com.duy.habittracker.data.TrackRecord;

import java.util.List;

public class TopicAdapter
        extends RecyclerView.Adapter<TopicAdapter.ViewHolder> {

    public interface OnItemClickListener {
        void onClick(Topic topic);
    }

    private List<Topic> topics;
    private OnItemClickListener listener;

    public TopicAdapter(List<Topic> topics, OnItemClickListener listener) {
        this.topics = topics;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_topic, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(
            @NonNull ViewHolder holder, int position) {

        Topic topic = topics.get(position);
        holder.bind(topic, listener);
        Context context = holder.itemView.getContext();
        AppDatabase db = AppDatabase.get(context);

        holder.itemView.setOnClickListener(v -> {
            //1
            TrackRecord r = new TrackRecord();
            r.topicId = topic.id;
            r.timestamp = System.currentTimeMillis();
            db.recordDao().insert(r);

            //2
            FragmentActivity activity =
                    (FragmentActivity) v.getContext();

            TopicSummaryDialogFragment
                    .newInstance(topic.id)
                    .show(activity.getSupportFragmentManager(),
                            "topic_summary");
        });

        //Long press → edit topic
        holder.itemView.setOnLongClickListener(v -> {
            Intent i = new Intent(context, TopicEditActivity.class);
            i.putExtra("topic_id", topic.id);
            context.startActivity(i);
            return true;
        });

        //Tap stats button → open graph screen
        holder.btnStats.setOnClickListener(v -> {
            Intent i = new Intent(context, StatsActivity.class);
            i.putExtra("topic_id", topic.id);
            context.startActivity(i);
        });
    }

    @Override
    public int getItemCount() {
        return topics.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        public View btnStats;
        TextView txtName;

        ViewHolder(View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.tvTopicName);
            btnStats = itemView.findViewById(R.id.btnStats);
        }

        void bind(Topic topic, OnItemClickListener listener) {
            txtName.setText(topic.name);
            itemView.setOnClickListener(v -> listener.onClick(topic));
        }
    }
}
