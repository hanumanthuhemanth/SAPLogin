package com.example.heman.wizardapp.androidutils;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("all")
public class SimpleRecyclerViewAdapter<T> extends RecyclerView.Adapter{
    private ArrayList<T> commonArrayList = new ArrayList<>();
    private int resource;
    private AdapterInterface<T> adapterInterface = null;
    private View listView;
    private View noDataFound;
    private Context mContext;

    public SimpleRecyclerViewAdapter(Context mContext, int resource, AdapterInterface<T> adapterInterface, View listView, View noDataFound) {
        this.mContext = mContext;
        this.resource = resource;
        this.adapterInterface = adapterInterface;
        this.listView = listView;
        this.noDataFound = noDataFound;
    }

    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (this.adapterInterface != null) {
            View viewItem = LayoutInflater.from(parent.getContext()).inflate(this.resource, parent, false);
            return this.adapterInterface.onCreateViewHolder(parent, viewType, viewItem);
        } else {
            return null;
        }
    }

    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final T t = this.commonArrayList.get(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (SimpleRecyclerViewAdapter.this.adapterInterface != null) {
                    SimpleRecyclerViewAdapter.this.adapterInterface.onItemClick(t, v, position);
                }

            }
        });
        if (this.adapterInterface != null) {
            this.adapterInterface.onBindViewHolder(holder, position, t);
        }

    }

    public int getItemCount() {
        return this.commonArrayList.size();
    }

    public void refreshAdapter(List commonArrayList) {
        this.commonArrayList.clear();
        this.commonArrayList.addAll(commonArrayList);
        this.notifyDataSetChanged();
        if (this.commonArrayList.isEmpty()) {
            if (this.noDataFound != null) {
                this.noDataFound.setVisibility(0);
            }

            if (this.listView != null) {
                this.listView.setVisibility(8);
            }
        } else {
            if (this.noDataFound != null) {
                this.noDataFound.setVisibility(8);
            }

            if (this.listView != null) {
                this.listView.setVisibility(0);
            }
        }

    }
}
