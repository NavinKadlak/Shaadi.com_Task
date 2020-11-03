package com.navinkadlak.shaadicomtask.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class ProfileDetails {

    @SerializedName("results")
    @Expose
    private List<Profile> profiles = null;
    @SerializedName("info")
    @Expose
    private Info info;

    public List<Profile> getProfiles() {
        return profiles;
    }

    public void setProfiles(List<Profile> profiles) {
        this.profiles = profiles;
    }

    public Info getInfo() {
        return info;
    }

    public void setInfo(Info info) {
        this.info = info;
    }

}
