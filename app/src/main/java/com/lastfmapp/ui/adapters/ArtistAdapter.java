package com.lastfmapp.ui.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lastfmapp.R;
import com.lastfmapp.persistence.DTO.ArtistDTO;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;

public class ArtistAdapter extends RecyclerView.Adapter<ArtistAdapter.ArtistViewHolder> {

    private List<ArtistDTO> artists;
    private Context context;
    private AdapterEventInterface eventInterface;

    public ArtistAdapter(List<ArtistDTO> artists, Context context, AdapterEventInterface eventInterface) {
        this.artists = artists;
        this.context = context;
        this.eventInterface = eventInterface;

    }

    @NonNull
    @Override
    public ArtistViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recyclerview_item_artist, viewGroup, false);
        return new ArtistViewHolder(view, eventInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull ArtistViewHolder holder, int position) {

        DecimalFormat formatter = new DecimalFormat("###,###.##");

        holder.name.setText(artists.get(position).getName());
        if (artists.get(position).getPlayCount() != null) {
            holder.playCount.setText(formatter.format(Integer.parseInt(artists.get(position).getPlayCount())) + " Reproducciones");
        }


        if (artists.get(position).getImage() != null &&
                !artists.get(position).getImage().isEmpty()) {
            Picasso
                    .with(context)
                    .load(artists.get(position).getImage())
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
        return artists.size();
    }

    public static class ArtistViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView name;
        public TextView playCount;
        public ImageView image;
        private AdapterEventInterface eventInterface;

        public ArtistViewHolder(View itemView, AdapterEventInterface eventInterface) {
            super(itemView);
            itemView.setOnClickListener(this);
            this.name = itemView.findViewById(R.id.tv_artist_name);
            this.playCount = itemView.findViewById(R.id.tv_artist_playcount);
            this.image = itemView.findViewById(R.id.img_artist_image);
            this.eventInterface = eventInterface;

        }

        @Override
        public void onClick(View v) {
            eventInterface.onClickItem(name.getText().toString());
        }
    }
}
