package com.example.heman.wizardapp.androidutils;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

public interface AdapterInterface<T> {
    void onItemClick(T object, View view, int position);

    RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int position, View view);

    void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position, T object);
}
