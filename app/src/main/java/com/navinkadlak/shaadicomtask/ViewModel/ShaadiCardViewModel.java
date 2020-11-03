package com.navinkadlak.shaadicomtask.ViewModel;

import android.content.Context;

import com.navinkadlak.shaadicomtask.Model.Profile;
import com.navinkadlak.shaadicomtask.Repository.ShaadiCardRepo;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ShaadiCardViewModel extends ViewModel {

    private ShaadiCardRepo shaadiCardRepo;
    private MutableLiveData<List<Profile>> mutableLiveData;

    public ShaadiCardViewModel() {
        shaadiCardRepo = new ShaadiCardRepo();
    }

    public LiveData<List<Profile>> getData(Context context) {
        if (mutableLiveData == null) {
            mutableLiveData = shaadiCardRepo.getProfileData(context);
        }
        return mutableLiveData;
    }


}
