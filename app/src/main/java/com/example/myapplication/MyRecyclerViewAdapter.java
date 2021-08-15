package com.example.myapplication;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.MyViewHolder>
{
    private ArrayList<User> mList = new ArrayList<>();

    public MyRecyclerViewAdapter(ArrayList<User> mList)
    {
        this.mList = mList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_row, parent, false);

        MyViewHolder myViewHolder = new MyViewHolder(view);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position)
    {
        String name = mList.get(position).getName();
        holder.txtName.setText(name);

        String desc = mList.get(position).getDescription();
        holder.txtDisc.setText(desc);

        String photo =mList.get(position).getPhoto();
        Glide
                .with(holder.itemView.getContext())
                .load(photo)
                .centerCrop()
                .placeholder(R.drawable.ic_launcher_background)
                .into(holder.imgUser);
        holder.txtTag.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(mList.get(position).getColor())));

    }

    @Override
    public int getItemCount()
    {
        return mList.size();
    }

    @Override
    public int getItemViewType(int position)
    {
        return position;
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    class MyViewHolder extends RecyclerView.ViewHolder
    {
        private TextView txtName;
        private TextView txtDisc;
        private ImageView imgUser;
        private TextView txtTag;

        public MyViewHolder(View view)
        {
            super(view);

            txtName = view.findViewById(R.id.txtName);
            txtDisc = view.findViewById(R.id.txtDisc);
            imgUser = view.findViewById(R.id.imgUser);
            txtTag = view.findViewById(R.id.txtTag);

        }
    }
}
