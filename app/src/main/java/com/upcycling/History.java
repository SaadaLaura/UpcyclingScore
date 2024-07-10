package com.upcycling;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {ProductHistory.class}, version = 1)
public abstract class History extends RoomDatabase {
    public abstract HistoryRequests historyRequests();
}
