package com.duy.habittracker.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class TrackRecord {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public int topicId;
    public long timestamp;
}
