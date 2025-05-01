package com.example.spectacleoff;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class SpectacleAdapter extends RecyclerView.Adapter<SpectacleAdapter.ViewHolder> {
    private Context context;
    private List<Spectacle> spectacleList;

    public SpectacleAdapter(Context context, List<Spectacle> spectacleList) {
        this.context = context;
        this.spectacleList = spectacleList;
    }

    @NonNull
    @Override
    public SpectacleAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.film_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Spectacle spectacle = spectacleList.get(position);
        holder.titleTextView.setText(spectacle.getTitre());

        // Charger l'image depuis les ressources locales (res/drawable)
        int imageResId = context.getResources().getIdentifier(
                spectacle.getUrlImage(), // Par exemple, "img_10"
                "drawable",
                context.getPackageName()
        );

        if (imageResId != 0) {
            holder.imageView.setImageResource(imageResId);
        } else {
            // Si l'image n'est pas trouvée, afficher une image par défaut
            holder.imageView.setImageResource(R.drawable.img_1); // Image par défaut
        }

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, activity_movie_detail.class);
            intent.putExtra("titre", spectacle.getTitre());
            intent.putExtra("genre", spectacle.getGenre());
            intent.putExtra("description", spectacle.getDescription());
            intent.putExtra("prix", spectacle.getPrix());
            intent.putExtra("idSpec", spectacle.getIdSpec());
            intent.putExtra("urlImage", spectacle.getUrlImage()); // Passer l'URL de l'image
            intent.putExtra("dureeS", spectacle.getDureeS());
            context.startActivity(intent);
        });
    }


    @Override
    public int getItemCount() {
        return spectacleList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView;
        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.title_spectacle);
            imageView = itemView.findViewById(R.id.image_spectacle);
        }
    }
}
