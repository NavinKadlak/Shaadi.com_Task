package com.navinkadlak.shaadicomtask.Ui;

import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.navinkadlak.shaadicomtask.Model.Profile;
import com.navinkadlak.shaadicomtask.NetworkUtils.NetworkChangeReactor;
import com.navinkadlak.shaadicomtask.R;
import com.navinkadlak.shaadicomtask.Ui.Adapter.ShaadiCardAdapter;
import com.navinkadlak.shaadicomtask.ViewModel.ShaadiCardViewModel;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ShaadiCardViewModel shaadiCardViewModel = new ShaadiCardViewModel();

    ShaadiCardAdapter mAdapter;
    ImageView noNetwork;

    AlphaAnimation inAnimation;
    AlphaAnimation outAnimation;

    FrameLayout progressBarHolder;
    FloatingActionButton floatingActionButton;

    int current_layout = R.layout.shaadi_match_card2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        noNetwork = (ImageView) findViewById(R.id.no_network_imageview);
        progressBarHolder = (FrameLayout) findViewById(R.id.progressBarHolder);
        floatingActionButton = findViewById(R.id.fab);
        startDisplayLoader();

        loadData(current_layout);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (current_layout == R.layout.shaadi_match_card2) {
                    current_layout = R.layout.shaadi_match_card;
                } else {
                    current_layout = R.layout.shaadi_match_card2;
                }

                loadData(current_layout);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    public void loadData(final int layout) {
        shaadiCardViewModel.getData(MainActivity.this).observe(MainActivity.this, new Observer<List<Profile>>() {
            @Override
            public void onChanged(List<Profile> profiles) {
                if (profiles != null && profiles.size() > 0) {
                    noNetwork.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                    mAdapter = new ShaadiCardAdapter(MainActivity.this, profiles, layout);
                    recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this, RecyclerView.VERTICAL, false));
                    recyclerView.setAdapter(mAdapter);
                    stopDisplayLoader();
                } else {
                    if (!new NetworkChangeReactor().isNetworkAvailable(MainActivity.this)) {
                        noNetwork.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.GONE);
                        stopDisplayLoader();
                    }
                }


            }
        });
    }

    private void startDisplayLoader() {
        inAnimation = new AlphaAnimation(0f, 1f);
        inAnimation.setDuration(200);
        progressBarHolder.setAnimation(inAnimation);
        progressBarHolder.setVisibility(View.VISIBLE);
    }


    private void stopDisplayLoader() {
        outAnimation = new AlphaAnimation(1f, 0f);
        outAnimation.setDuration(200);
        progressBarHolder.setAnimation(outAnimation);
        progressBarHolder.setVisibility(View.GONE);
    }

}
