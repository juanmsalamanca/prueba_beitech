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
import com.lastfmapp.persistence.DTO.AlbumDTO;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;

public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.AlbumViewHolder> {

    private List<AlbumDTO> albums;
    private Context context;
    private AdapterEventInterface eventInterface;

    public AlbumAdapter(List<AlbumDTO> albums, Context context, AdapterEventInterface eventInterface) {
        this.albums = albums;
        this.context = context;
        this.eventInterface = eventInterface;
    }

    @NonNull
    @Override
    public AlbumViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recyclerview_item_album, viewGroup, false);
        return new AlbumViewHolder(view, eventInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull AlbumViewHolder holder, int position) {
        DecimalFormat formatter = new DecimalFormat("###,###.##");

        holder.name.setText(albums.get(position).getName());
        if (albums.get(position).getPlayCount() != null) {
            holder.playCount.setText(formatter.format(Integer.parseInt(albums.get(position).getPlayCount())) + " Reproducciones");
        }

        if (albums.get(position).getArtistName() != null) {
            holder.artistName.setText(albums.get(position).getArtistName());
        }

        if (!albums.get(position).getImage().isEmpty()) {
            Picasso
                    .with(context)
                    .load(albums.get(position).getImage())
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
        return albums.size();
    }

    public static class AlbumViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView name ;
        public TextView artistName;
        public TextView playCount;
        public ImageView image;
        private AdapterEventInterface eventInterface;

        public AlbumViewHolder(View itemView, AdapterEventInterface eventInterface) {
            super(itemView);
            itemView.setOnClickListener(this);
            this.name = itemView.findViewById(R.id.tv_album_name);
            this.playCount = itemView.findViewById(R.id.tv_album_playcount);
            this.image = itemView.findViewById(R.id.img_album_image);
            this.artistName = itemView.findViewById(R.id.tv_album_artistname);
            this.eventInterface = eventInterface;

            name.setSelected(true);


        }
        @Override
        public void onClick(View view) {
            eventInterface.onClickItem(name.getText().toString());
        }
    }

}
