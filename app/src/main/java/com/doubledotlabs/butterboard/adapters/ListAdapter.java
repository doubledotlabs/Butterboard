package com.doubledotlabs.butterboard.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.doubledotlabs.butterboard.data.ListData;

import java.util.ArrayList;
import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListData.ViewHolder> {

    private List<ListData> list;

    public ListAdapter(List<ListData> list) {
        this.list = new ArrayList<>(list);
    }

    public void setList(List<? extends ListData> list) {
        this.list = new ArrayList<>(list);
        notifyDataSetChanged();
    }

    public List<ListData> getList() {
        return new ArrayList<>(list);
    }

    @Override
    public ListData.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return list.get(viewType).getViewHolder(LayoutInflater.from(parent.getContext()), parent);
    }

    @Override
    public void onBindViewHolder(ListData.ViewHolder holder, int position) {
        list.get(position).bindView(holder);
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
