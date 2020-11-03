package com.navinkadlak.shaadicomtask.Repository;

import android.content.Context;
import android.os.AsyncTask;

import com.navinkadlak.shaadicomtask.Database.DatabaseClient;
import com.navinkadlak.shaadicomtask.Model.Profile;
import com.navinkadlak.shaadicomtask.Model.ProfileDetails;
import com.navinkadlak.shaadicomtask.NetworkUtils.ApiCall;
import com.navinkadlak.shaadicomtask.NetworkUtils.RetrofitClient;

import java.util.List;

import androidx.lifecycle.MutableLiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShaadiCardRepo {

    private static String PROFILE_FETCH_RESULT = "10";


    public MutableLiveData<List<Profile>> getProfileData(final Context context) {

        final MutableLiveData<List<Profile>> mutableLiveData = new MutableLiveData<>();

        new AsyncTask<Void, List<Profile>, List<Profile>>() {
            @Override
            protected List<Profile> doInBackground(Void... voids) {
                List<Profile> profiles = null;
                if (isDataPresent(context)) {
                    profiles = getProfilesFromDB(context, mutableLiveData);

                } else {
                    getProfileDataFromAPI(context, mutableLiveData);


                }

                return profiles;
            }

            @Override
            protected void onPostExecute(List<Profile> profiles) {
                super.onPostExecute(profiles);

                mutableLiveData.setValue(profiles);

            }
        }.execute();


        return mutableLiveData;
    }


    private void getProfileDataFromAPI(final Context context, final MutableLiveData<List<Profile>> mutableLiveData) {

        ApiCall apiService = RetrofitClient.getServices().create(ApiCall.class);
        Call<ProfileDetails> call = apiService.getProfileData(PROFILE_FETCH_RESULT);

        call.enqueue(new Callback<ProfileDetails>() {
            @Override
            public void onResponse(Call<ProfileDetails> call, Response<ProfileDetails> response) {

                if (response.body() != null) {
                    List<Profile> profiles = response.body().getProfiles();
                    saveDataToDB(profiles, context, mutableLiveData);
                }
            }

            @Override
            public void onFailure(Call<ProfileDetails> call, Throwable t) {

            }
        });

    }

    private void saveDataToDB(final List<Profile> profileList, final Context context, final MutableLiveData<List<Profile>> mutableLiveData) {


        new AsyncTask<Void, List<Profile>, List<Profile>>() {
            @Override
            protected List<Profile> doInBackground(Void... voids) {
                List<Profile> profiles = null;
                try {
                    DatabaseClient.getInstance(context).getAppDatabase()
                            .profileDao()
                            .insertAll(profileList);

                    profiles = getProfilesFromDB(context, mutableLiveData);


                } catch (Exception e) {
                    e.printStackTrace();

                }
                return profiles;
            }

            @Override
            protected void onPostExecute(List<Profile> profiles) {
                super.onPostExecute(profiles);

                mutableLiveData.setValue(profiles);
            }
        }.execute();


    }


    private boolean isDataPresent(final Context context) {


        long i = DatabaseClient.getInstance(context).getAppDatabase()
                .profileDao()
                .getCount();
        if (i > 0) {
            return true;
        } else
            return false;

    }

    private List<Profile> getProfilesFromDB(Context context, MutableLiveData<List<Profile>> mutableLiveData) {
        return DatabaseClient.getInstance(context).getAppDatabase()
                .profileDao()
                .getAllProfileData();

    }

    public void updateData(final Context context, final Profile profile) {

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                DatabaseClient.getInstance(context).getAppDatabase()
                        .profileDao()
                        .update(profile);
            }
        });

    }
}
