package com.navinkadlak.shaadicomtask.NetworkUtils;

import com.navinkadlak.shaadicomtask.Model.ProfileDetails;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiCall {


    @GET("api/")
    Call<ProfileDetails> getProfileData(@Query("results") String result);

}
