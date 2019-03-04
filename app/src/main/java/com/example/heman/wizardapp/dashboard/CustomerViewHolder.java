package com.example.heman.wizardapp.dashboard;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.heman.wizardapp.R;

public class CustomerViewHolder extends RecyclerView.ViewHolder {
    public TextView textViewName,textViewID;
    public CustomerViewHolder(@NonNull View itemView) {
        super(itemView);
        textViewName = itemView.findViewById(R.id.textViewName);
        textViewID= itemView.findViewById(R.id.textViewID);
    }
}
