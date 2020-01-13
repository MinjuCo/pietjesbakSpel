package com.example.pietjesbakspel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.NumberViewHolder>{

    private ArrayList<Integer> mLiveLines = new ArrayList<>();

    private int viewHolderCount;

    public Adapter(ArrayList<Integer> mLiveLines, int viewHolderCount) {
        this.mLiveLines = mLiveLines;
        this.viewHolderCount = viewHolderCount;
    }

    @NonNull
    @Override
    public NumberViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.live_list_player, parent, false);
        NumberViewHolder viewHolder = new NumberViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter.NumberViewHolder holder, int position) {

        holder.bind(mLiveLines.get(position));
    }

    @Override
    public int getItemCount() {
        return mLiveLines.size();
    }

    class NumberViewHolder extends RecyclerView.ViewHolder{
        ImageView blackLine;
        public NumberViewHolder(View itemView) {

            super(itemView);
            blackLine = itemView.findViewById(R.id.iv_live_item);
        }

        public void bind(int position){
            blackLine.setBackgroundResource(position);

        }


    }


}
