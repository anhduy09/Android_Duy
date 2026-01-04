package com.duy.habittracker.data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface TopicDao {
    @Query("SELECT * FROM Topic")
    List<Topic> getAll();

    @Insert
    void insert(Topic topic);

    @Query("SELECT * FROM Topic WHERE id = :id")
    Topic getById(int id);

    @Query("UPDATE Topic SET name = :name WHERE id = :id")
    void updateName(int id, String name);
}
