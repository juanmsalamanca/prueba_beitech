package com.lastfmapp.ui.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lastfmapp.R;
import com.lastfmapp.persistence.DTO.TrackDTO;
import com.squareup.picasso.Picasso;

import java.util.List;

public class Track2Adapter extends RecyclerView.Adapter<Track2Adapter.Track2ViewHolder> {

    private List<TrackDTO> tracks;
    private Context context;
    private AdapterEventInterface eventInterface;

    public Track2Adapter(List<TrackDTO> tracks, Context context, AdapterEventInterface eventInterface) {
        this.tracks = tracks;
        this.context = context;
        this.eventInterface = eventInterface;
    }

    @NonNull
    @Override
    public Track2ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recyclerview_item_track_2, viewGroup, false);
        return new Track2Adapter.Track2ViewHolder(view, eventInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull Track2ViewHolder holder, int position) {

        holder.name.setText(tracks.get(position).getName());
        holder.artistName.setText(tracks.get(position).getArtistName());

        ;
        if (tracks.get(position).getImage() != null &&
                !tracks.get(position).getImage().isEmpty()) {

            Picasso
                    .with(context)
                    .load(tracks.get(position).getImage())
                    .placeholder(R.drawable.image_not_found)
                    .error(R.drawable.image_not_found)
                    .into(holder.image);
        } else {
            Picasso
                    .with(context)
                    .load(R.drawable.image_not_found)
                    .placeholder(R.drawable.image_not_found)
                    .error(R.drawable.image_not_found)
                    .into(holder.image);
        }


    }

    @Override
    public int getItemCount() {
        return tracks.size();
    }

    public static class Track2ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView name;
        public TextView artistName;
        public ImageView image;
        private AdapterEventInterface eventInterface;

        public Track2ViewHolder(View itemView, AdapterEventInterface eventInterface) {
            super(itemView);

            this.name = itemView.findViewById(R.id.tv_track2_name);
            this.artistName = itemView.findViewById(R.id.tv_track2_artist_name);
            this.image = itemView.findViewById(R.id.img_track2_image);
            this.eventInterface = eventInterface;

            name.setSelected(true);
            artistName.setSelected(true);
        }

        @Override
        public void onClick(View view) {
            eventInterface.onClickItem(name.getText().toString());
        }
    }
}
