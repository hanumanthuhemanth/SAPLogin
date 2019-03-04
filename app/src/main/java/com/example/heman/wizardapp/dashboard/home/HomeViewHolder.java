package com.example.heman.wizardapp.dashboard.home;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.heman.wizardapp.R;

public class HomeViewHolder extends RecyclerView.ViewHolder {
    public TextView textViewName;
    public ImageView imageViewIcon;
    public HomeViewHolder(@NonNull View itemView) {
        super(itemView);
        textViewName = itemView.findViewById(R.id.textViewName);
        imageViewIcon= itemView.findViewById(R.id.imageViewIcon);
    }
}
