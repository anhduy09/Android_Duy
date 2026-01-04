package com.duy.habittracker.data;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Topic {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @NonNull
    public String name;
}