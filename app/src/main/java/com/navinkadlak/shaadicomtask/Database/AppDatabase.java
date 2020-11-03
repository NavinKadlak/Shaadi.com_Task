package com.navinkadlak.shaadicomtask.Database;

import com.navinkadlak.shaadicomtask.Database.DAO.ProfileDao;
import com.navinkadlak.shaadicomtask.Model.Profile;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Profile.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract ProfileDao profileDao();
}
