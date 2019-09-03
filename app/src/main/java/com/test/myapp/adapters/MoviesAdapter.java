package com.test.myapp.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.test.myapp.R;
import com.test.myapp.model.Data;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder> {

    private Context context;
    private ArrayList<Data> dataArrayList;

    public MoviesAdapter(Context context, ArrayList<Data> dataArrayList) {
        this.context = context;
        this.dataArrayList = dataArrayList;
    }

    @NonNull
    @Override
    public MoviesAdapter.MoviesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_view_item, parent, false);
        return new MoviesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MoviesAdapter.MoviesViewHolder holder, int position) {
        holder.tvTitle.setText(dataArrayList.get(position).getTitle().toString());
        holder.tvYear.setText(dataArrayList.get(position).getYear().toString());
        Picasso.get().load(dataArrayList.get(position).getPoster()).into(holder.ivPoster);
    }

    @Override
    public int getItemCount() {
        return dataArrayList.size();
    }

    public void updateData(ArrayList<Data> data) {
        dataArrayList = data;
        notifyDataSetChanged();
    }

    public class MoviesViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tvTitle)
        TextView tvTitle;

        @BindView(R.id.tvYear)
        TextView tvYear;

        @BindView(R.id.ivPoster)
        ImageView ivPoster;

        MoviesViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}