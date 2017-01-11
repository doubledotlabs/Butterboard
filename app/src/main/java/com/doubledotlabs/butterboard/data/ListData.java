package com.doubledotlabs.butterboard.data;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.doubledotlabs.butterboard.Butterboard;

public abstract class ListData<T extends ListData.ViewHolder> {

    public abstract T getViewHolder(LayoutInflater inflater, ViewGroup parent);

    public abstract void bindView(T holder);

    public static abstract class ViewHolder extends RecyclerView.ViewHolder {

        Butterboard butterboard;
        AppCompatActivity activity;

        ViewHolder(View itemView) {
            super(itemView);
            butterboard = (Butterboard) itemView.getContext().getApplicationContext();
            activity = (AppCompatActivity) itemView.getContext();
        }
    }

}
