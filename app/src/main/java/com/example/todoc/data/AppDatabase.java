package com.example.todoc.data;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.todoc.data.dao.ProjectDao;
import com.example.todoc.data.dao.TaskDao;
import com.example.todoc.data.entities.ProjectEntity;
import com.example.todoc.data.entities.TaskEntity;
import com.example.todoc.data.entities.TimeStampConverter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@Database(entities = {TaskEntity.class, ProjectEntity.class}, version = 1, exportSchema = false)
@TypeConverters({TimeStampConverter.class})
public abstract class AppDatabase extends RoomDatabase {

    public abstract TaskDao getTaskDao();

    public abstract ProjectDao getProjectDao();

    public static volatile AppDatabase INSTANCE;

    private final static int threads = Runtime.getRuntime().availableProcessors();
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(threads);


    public static AppDatabase getDatabase(@NonNull final Context context) {  // SINGLETON (getDatabase() returns it)
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    AppDatabase.class,
                                    "app_database")
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static final RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            databaseWriteExecutor.execute(() -> {
                ProjectDao dao = INSTANCE.getProjectDao();
                dao.insertProject(new ProjectEntity(0, "Projet Tartampion", 0xFFEADAD1));
                dao.insertProject(new ProjectEntity(0, "Projet Lucidia", 0xFFB4CDBA));
                dao.insertProject(new ProjectEntity(0, "Projet Circus", 0xFFA3CED2));
            });
        }
    };
}
