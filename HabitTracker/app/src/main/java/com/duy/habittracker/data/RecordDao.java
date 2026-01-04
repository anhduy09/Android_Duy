package com.duy.habittracker.data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface RecordDao {

    @Insert
    void insert(TrackRecord record);

    @Query(
            "SELECT COUNT(*) FROM TrackRecord " +
                    "WHERE topicId = :topicId " +
                    "AND timestamp BETWEEN :start AND :end"
    )
    int countInRange(int topicId, long start, long end);

    @Query("SELECT COUNT(*) FROM TrackRecord WHERE topicId = :topicId")
    int getTotalCount(int topicId);

    @Query(
            "SELECT COUNT(*) FROM TrackRecord " +
                    "WHERE topicId = :topicId " +
                    "AND date(timestamp / 1000, 'unixepoch') = date('now')"
    )
    int getTodayCount(int topicId);

    @Query(
            "SELECT * FROM TrackRecord " +
                    "WHERE topicId = :topicId " +
                    "ORDER BY timestamp DESC " +
                    "LIMIT 1"
    )
    TrackRecord getLast(int topicId);
}