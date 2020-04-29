package com.example.findmynanny.database;


import androidx.room.Database;
import androidx.room.RoomDatabase;
import com.example.findmynanny.models.User;

@Database(entities = {User.class}, version = 1, exportSchema = false)

public abstract class MyDataBase extends RoomDatabase {
    public abstract UserDao getUserDao();

}
