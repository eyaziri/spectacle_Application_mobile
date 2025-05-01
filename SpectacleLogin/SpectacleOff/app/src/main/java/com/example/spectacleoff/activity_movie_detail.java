package com.example.spectacleoff;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class activity_movie_detail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        ImageView image = findViewById(R.id.movieImage);
        TextView title = findViewById(R.id.movieTitle4);
        TextView genre = findViewById(R.id.movieGenre);
        TextView description = findViewById(R.id.movieDescription);
        TextView duree = findViewById(R.id.duree);
        TextView prix = findViewById(R.id.prix);
        Button btnBooking = findViewById(R.id.btnBooking);

        // Récupérer les données passées via l'Intent
        Intent intent = getIntent();
        String titre = intent.getStringExtra("titre");
        String genreText = intent.getStringExtra("genre");
        String desc = intent.getStringExtra("description");
        String urlImage = intent.getStringExtra("urlImage");
        String dureeS =intent.getStringExtra("dureeS");
        int price = intent.getIntExtra("prix", 0);
        int idSpec = intent.getIntExtra("idSpec", -1);  // Récupérer l'ID du spectacle

        // Affichage des informations dans l'UI
        title.setText(titre);
        genre.setText(genreText);
        description.setText(desc);
        duree.setText(dureeS);
        prix.setText(String.valueOf(price) + " TND");

        // Charger l'image depuis les ressources
        int imageResId = getResources().getIdentifier(urlImage, "drawable", getPackageName());
        if (imageResId != 0) {
            image.setImageResource(imageResId);
        } else {
            image.setImageResource(R.drawable.img_1); // fallback
        }

        btnBooking.setOnClickListener(v -> {
            if (idSpec == -1) {
                Toast.makeText(activity_movie_detail.this, "ID Spectacle eya", Toast.LENGTH_SHORT).show();
            } else {
                Intent bookingIntent = new Intent(activity_movie_detail.this, Reservation_activity.class);
                bookingIntent.putExtra("movieTitle", titre);
                bookingIntent.putExtra("movieGenre", genreText);
                bookingIntent.putExtra("idSpec", idSpec);
                bookingIntent.putExtra("dureeS",dureeS);
                bookingIntent.putExtra("prix", getIntent().getIntExtra("prix", 0));
                startActivity(bookingIntent);
            }

        });
    }
}
