package com.navinkadlak.shaadicomtask.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import androidx.room.ColumnInfo;


public class Dob {


    @ColumnInfo(name = "dob_date")
    @SerializedName("date")
    @Expose
    private String date;

    @ColumnInfo(name = "dob_age")
    @SerializedName("age")
    @Expose
    private String age;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

}
