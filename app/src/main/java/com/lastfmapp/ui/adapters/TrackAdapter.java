package com.lastfmapp.ui.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lastfmapp.R;
import com.lastfmapp.persistence.DTO.TrackDTO;

import java.util.List;

public class TrackAdapter extends RecyclerView.Adapter<TrackAdapter.TrackViewHolder> {

    private List<TrackDTO> tracks;
    private Context context;
    private AdapterEventInterface eventInterface;

    public TrackAdapter(List<TrackDTO> tracks, Context context, AdapterEventInterface eventInterface) {
        this.tracks = tracks;
        this.context = context;
        this.eventInterface = eventInterface;
    }

    @NonNull
    @Override
    public TrackViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recyclerview_item_track, viewGroup, false);
        return new TrackViewHolder(view, eventInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull TrackViewHolder holder, int position) {
        holder.rank.setText(tracks.get(position).getRank());
        holder.name.setText(tracks.get(position).getName());

        int time = Integer.parseInt(tracks.get(position).getDuration());
        int minutes = time / (60 );
        int seconds = (time ) % 60;
        String str = String.format("%d:%02d", minutes, seconds);

        holder.duration.setText(str);
    }

    @Override
    public int getItemCount() {
        return tracks.size();
    }

    public static class TrackViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView rank;
        public TextView name;
        public TextView duration;
        private AdapterEventInterface eventInterface;

        public TrackViewHolder(View itemView, AdapterEventInterface eventInterface) {
            super(itemView);
            itemView.setOnClickListener(this);
            this.rank = itemView.findViewById(R.id.tv_track_rank);
            this.name = itemView.findViewById(R.id.tv_track_name);
            this.duration = itemView.findViewById(R.id.tv_track_duration);
            this.eventInterface = eventInterface;

            name.setSelected(true);

        }

        @Override
        public void onClick(View view) {

        }
    }

}
