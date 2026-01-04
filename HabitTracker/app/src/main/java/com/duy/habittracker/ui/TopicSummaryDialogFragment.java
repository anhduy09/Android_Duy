package com.duy.habittracker.ui;

import android.app.Dialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.DialogFragment;

import com.duy.habittracker.R;
import com.duy.habittracker.data.AppDatabase;
import com.duy.habittracker.data.TrackRecord;

import java.text.DateFormat;
import java.util.Date;

public class TopicSummaryDialogFragment extends DialogFragment {

    private static final String ARG_TOPIC_ID = "topic_id";

    public static TopicSummaryDialogFragment newInstance(int topicId) {
        Bundle args = new Bundle();
        args.putInt(ARG_TOPIC_ID, topicId);

        TopicSummaryDialogFragment fragment =
                new TopicSummaryDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = new Dialog(requireContext(), R.style.CenterDialog);

        dialog.setOnShowListener(d -> {
            Window window = dialog.getWindow();
            if (window != null) {
                WindowManager.LayoutParams params = window.getAttributes();
                params.gravity = Gravity.CENTER;
                window.setAttributes(params);
            }
        });

        return dialog;
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {

        View view = inflater.inflate(
                R.layout.activity_topic_summary_dialog_fragment,
                container,
                false);

        int topicId = getArguments().getInt(ARG_TOPIC_ID);

        TextView tvTotal = view.findViewById(R.id.tvTotal);
        TextView tvToday = view.findViewById(R.id.tvToday);
        TextView tvLast = view.findViewById(R.id.tvLast); // optional
        Button btnClose = view.findViewById(R.id.btnClose);

        AppDatabase db = AppDatabase.get(requireContext());

        int total = db.recordDao().getTotalCount(topicId);
        int today = db.recordDao().getTodayCount(topicId);
        TrackRecord last = db.recordDao().getLast(topicId);

        tvTotal.setText("Total: " + total);
        tvToday.setText("Today: " + today);

        if (last != null) {
            String time = DateFormat.getDateTimeInstance()
                    .format(new Date(last.timestamp));
            tvLast.setText("Last: " + time);
        }

        btnClose.setOnClickListener(v -> dismiss());

        return view;
    }
}
