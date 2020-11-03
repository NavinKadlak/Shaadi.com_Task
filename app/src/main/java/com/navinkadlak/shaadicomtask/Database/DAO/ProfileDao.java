package com.navinkadlak.shaadicomtask.Database.DAO;

import com.navinkadlak.shaadicomtask.Model.Profile;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface ProfileDao {

    @Query("SELECT * FROM Profile")
    List<Profile> getAllProfileData();

    @Insert
    List<Long> insertAll(List<Profile> profiles);
/*
    @Delete
    void delete(Profile Profile);*/

    @Update
    int update(Profile Profile);

    @Query("SELECT count(*) FROM Profile")
    int getCount();

}
