package com.navinkadlak.shaadicomtask.Ui.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.navinkadlak.shaadicomtask.Model.Profile;
import com.navinkadlak.shaadicomtask.R;
import com.navinkadlak.shaadicomtask.Repository.ShaadiCardRepo;
import com.navinkadlak.shaadicomtask.Utility.CircleTransform;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ShaadiCardAdapter extends RecyclerView.Adapter<ShaadiCardAdapter.MyViewHolder> {

    private Context context;
    private List<Profile> data;
    private int layouts;


    public ShaadiCardAdapter(Context context, List<Profile> data, int layouts) {
        this.context = context;
        this.data = data;
        this.layouts = layouts;
    }

    @NonNull
    @Override
    public ShaadiCardAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(layouts, parent, false);
        MyViewHolder holder = new MyViewHolder(layout);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ShaadiCardAdapter.MyViewHolder holder, final int position) {
        if (layouts == R.layout.shaadi_match_card)
            Picasso.get().load(data.get(position).getPicture().getLarge())
                    .fit().networkPolicy(NetworkPolicy.NO_CACHE)
                    .memoryPolicy(MemoryPolicy.NO_CACHE)
                    .into(holder.profilePic);
        else
            Picasso.get().load(data.get(position).getPicture().getLarge()).transform(new CircleTransform()).into(holder.profilePic);

        holder.name.setText(data.get(position).getName().getTitle() + " " + data.get(position).getName().getFirst() + " " + data.get(position).getName().getLast());

        holder.age_height.setText(data.get(position).getRegistered().getAge());
        holder.location.setText(data.get(position).getLocation().getCity() + " " + data.get(position).getLocation().getState());

        int status = data.get(position).getStatus();
        if (status == 0) {
            holder.status.setVisibility(View.GONE);
            holder.button_layout.setVisibility(View.VISIBLE);

        } else {
            holder.status.setVisibility(View.VISIBLE);
            holder.button_layout.setVisibility(View.GONE);
            if (status == 1) {
                holder.status.setText(R.string.memberAccepted);
                holder.status.setTextColor(Color.parseColor("#4CAF50"));
            } else if (status == 2) {
                holder.status.setText(R.string.memberDecline);
                holder.status.setTextColor(Color.parseColor("#FF5722"));

            }
        }

        holder.accept_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.button_layout.setVisibility(View.GONE);
                holder.status.setVisibility(View.VISIBLE);
                holder.status.setText(R.string.memberAccepted);
                holder.status.setTextColor(Color.parseColor("#4CAF50"));

                data.get(position).setStatus(1);
                ShaadiCardRepo shaadiCardRepo = new ShaadiCardRepo();
                shaadiCardRepo.updateData(context, data.get(position));
            }
        });

        holder.decline_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.button_layout.setVisibility(View.GONE);
                holder.status.setVisibility(View.VISIBLE);
                holder.status.setText(R.string.memberDecline);
                holder.status.setTextColor(Color.parseColor("#FF5722"));

                data.get(position).setStatus(2);
                ShaadiCardRepo shaadiCardRepo = new ShaadiCardRepo();
                shaadiCardRepo.updateData(context, data.get(position));

            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView profilePic, accept_btn, decline_btn;
        TextView name, location, age_height, status;
        LinearLayout button_layout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            profilePic = (ImageView) itemView.findViewById(R.id.profileImage);
            accept_btn = (ImageView) itemView.findViewById(R.id.accept_btn);
            decline_btn = (ImageView) itemView.findViewById(R.id.decline_btn);

            name = (TextView) itemView.findViewById(R.id.name_tv);
            location = (TextView) itemView.findViewById(R.id.location_tv);
            age_height = (TextView) itemView.findViewById(R.id.age_height_tv);
            status = (TextView) itemView.findViewById(R.id.status_tv);

            button_layout = (LinearLayout) itemView.findViewById(R.id.button_layout);
        }
    }
}
