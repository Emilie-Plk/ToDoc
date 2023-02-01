package com.example.todoc.data.entities;

import androidx.room.TypeConverter;

import java.sql.Timestamp;


public class TimeStampConverter {
    @TypeConverter
    public static Timestamp fromLong(Long value) {
        return value == null ? null : new Timestamp(value);
    }

    @TypeConverter
    public static Long toLong(Timestamp timestamp) {
        return timestamp == null ? null : timestamp.getTime();
    }
}
