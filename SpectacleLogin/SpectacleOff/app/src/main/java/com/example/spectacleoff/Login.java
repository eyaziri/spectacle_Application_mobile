package com.example.spectacleoff;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {

    private EditText nomEditText, prenomEditText, emailEditText, motDePasseEditText;
    private Button saveButton;

    // Variables pour récupérer les extras envoyés vers Login
    private int idSpec, idRubrique, prix, seatsReserved, prixTotal;
    private String movieGenre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Récupérer les données envoyées depuis l'activité précédente
        Intent intent = getIntent();
        idSpec = intent.getIntExtra("idSpec", -1);
        idRubrique = intent.getIntExtra("idRubrique", -1);
        prix = intent.getIntExtra("prix", 0);
        seatsReserved = intent.getIntExtra("seatsReserved", 0);
        prixTotal = intent.getIntExtra("prixTotal", 0);
        movieGenre = intent.getStringExtra("movieGenre");

        // Liaison avec les composants XML
        nomEditText = findViewById(R.id.nom);
        prenomEditText = findViewById(R.id.prenom);
        emailEditText = findViewById(R.id.email);
        motDePasseEditText = findViewById(R.id.motDePasse);
        saveButton = findViewById(R.id.save);

        // Événement sur le bouton "Confirmer"
        saveButton.setOnClickListener(v -> ajouterUtilisateur());
    }

    private void ajouterUtilisateur() {
        String nom = nomEditText.getText().toString().trim();
        String prenom = prenomEditText.getText().toString().trim();
        String email = emailEditText.getText().toString().trim();
        String motDePasse = motDePasseEditText.getText().toString().trim();

        if (nom.isEmpty() || prenom.isEmpty() || email.isEmpty() || motDePasse.isEmpty()) {
            Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
            return;
        }

        User nouvelUtilisateur = new User(nom, prenom, email, motDePasse);
        ApiService apiService = RetrofitClient.getApiService();
        Call<User> call = apiService.ajouterUtilisateur(nouvelUtilisateur);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(Login.this, "Utilisateur ajouté avec succès", Toast.LENGTH_LONG).show();

                    // On crée l'intent vers la page Payement
                    Intent intent = new Intent(Login.this, Payement.class);
                    intent.putExtra("nom", nom);
                    intent.putExtra("prenom", prenom);
                    intent.putExtra("email", email);
                    intent.putExtra("motdepasse", motDePasse);

                    // On transmet aussi les données du spectacle
                    intent.putExtra("idSpec", idSpec);
                    intent.putExtra("idRubrique", idRubrique);
                    intent.putExtra("prix", prix);
                    intent.putExtra("seatsReserved", seatsReserved);
                    intent.putExtra("prixTotal", prixTotal);
                    intent.putExtra("movieGenre", movieGenre);

                    startActivity(intent);
                    finish(); // Facultatif selon ta navigation
                } else {
                    Toast.makeText(Login.this, "Erreur : " + response.message(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(Login.this, "Erreur réseau : " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
