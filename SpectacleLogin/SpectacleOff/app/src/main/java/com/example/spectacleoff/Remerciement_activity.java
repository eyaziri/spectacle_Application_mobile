package com.example.spectacleoff;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
public class Remerciement_activity extends AppCompatActivity {

    private TextView titre, date, lieu, numeroPlaces;
    private Button mailButton;
    private Personne personne;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remerciement);

        initViews();

        // Récupération des données passées par l'intent
        Intent intent = getIntent();
        int idSpec = intent.getIntExtra("idSpec", -1);
        int idRubrique = intent.getIntExtra("idRubrique", -1);
        int seatsReserved = intent.getIntExtra("seatsReserved", 0);
        personne = intent.getParcelableExtra("p1");

        // Récupérer la dernière personne si non fournie dans l'intent
        if (personne == null) {
            getDernierePersonne();
        }

        // Vérification du mail reçu
        if (personne != null && personne.getEmail() != null && !personne.getEmail().isEmpty()) {
            Log.d("Email", "Reçu : " + personne.getEmail());
        } else {
            Log.d("Email", "Aucun email reçu ou email vide");
        }

        // Affichage du nombre de places réservé
        numeroPlaces.setText(String.valueOf(seatsReserved));

        // Appel des fonctions pour récupérer les informations
        getTitreSpectacle(idSpec);
        getRubriqueInfo(idRubrique);

        // Gestion de l'envoi de l'email
        mailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (personne != null && personne.getEmail() != null && !personne.getEmail().isEmpty()) {
                    sendEmail(personne);
                } else {
                    // Email par défaut si personne ou email vide
                    Personne defaultPerson = new Personne();
                    defaultPerson.setEmail("eyaziri2@gmail.com"); // Utilise un email par défaut si nécessaire
                    sendEmail(defaultPerson);
                }
            }
        });
    }

    private void initViews() {
        titre = findViewById(R.id.titre);
        date = findViewById(R.id.date);
        lieu = findViewById(R.id.lieu);
        numeroPlaces = findViewById(R.id.num); // Affichage du mail de la personne
        mailButton = findViewById(R.id.mail);
    }

    // Récupérer la dernière personne
    private void getDernierePersonne() {
        ApiService api = RetrofitClient.getApiService();
        Call<Personne> call = api.getDernierePersonne();

        call.enqueue(new Callback<Personne>() {
            @Override
            public void onResponse(Call<Personne> call, Response<Personne> response) {
                if (response.isSuccessful() && response.body() != null) {
                    personne = response.body();
                    Log.d("Personne", "Dernière personne récupérée : " + personne.getEmail());

                    // Tu peux activer le bouton ou même appeler sendEmail ici si nécessaire
                    mailButton.setOnClickListener(v -> sendEmail(personne));
                } else {
                    Log.e("API Error", "Code: " + response.code() + " - Message: " + response.message());
                    Toast.makeText(Remerciement_activity.this, "Erreur lors de la récupération de la dernière personne", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Personne> call, Throwable t) {
                Toast.makeText(Remerciement_activity.this, "Erreur réseau : " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }



    // Récupérer les informations du spectacle
    private void getTitreSpectacle(int idSpec) {
        ApiService api = RetrofitClient.getApiService();
        Call<Spectacle> call = api.getSpectacleById(idSpec);

        call.enqueue(new Callback<Spectacle>() {
            @Override
            public void onResponse(Call<Spectacle> call, Response<Spectacle> response) {
                if (response.isSuccessful() && response.body() != null) {
                    titre.setText(response.body().getTitre());
                } else {
                    Toast.makeText(Remerciement_activity.this, "Spectacle introuvable", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Spectacle> call, Throwable t) {
                Toast.makeText(Remerciement_activity.this, "Erreur réseau : " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    // Récupérer les informations de la rubrique
    // Récupérer les informations de la rubrique
    private void getRubriqueInfo(int idRubrique) {
        ApiService api = RetrofitClient.getApiService();
        Call<Rubrique> call = api.getRubriqueById(idRubrique);

        call.enqueue(new Callback<Rubrique>() {
            @Override
            public void onResponse(Call<Rubrique> call, Response<Rubrique> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Rubrique rubrique = response.body();
                    String dateISO = rubrique.getDateRubrique();
                    date.setText(formaterDate(dateISO));

                    // Récupérer l'idLieu de la rubrique
                    int idLieu = rubrique.getIdLieu(); // Assurez-vous que cette méthode existe dans votre classe Rubrique
                    getLieuInfo(idLieu); // Récupérer les informations du lieu en utilisant l'idLieu

                } else {
                    Toast.makeText(Remerciement_activity.this, "Rubrique introuvable", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Rubrique> call, Throwable t) {
                Toast.makeText(Remerciement_activity.this, "Erreur réseau : " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    // Récupérer les informations du lieu
    private void getLieuInfo(int idLieu) {
        ApiService api = RetrofitClient.getApiService();
        Call<Lieu> call = api.getLieuById(idLieu); // L'appel pour obtenir les informations du lieu

        call.enqueue(new Callback<Lieu>() {
            @Override
            public void onResponse(Call<Lieu> call, Response<Lieu> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Récupérer et afficher les informations du lieu
                    String lieuName = response.body().getNomLieu(); // Assurez-vous que la méthode getNomLieu existe
                    lieu.setText(lieuName);
                } else {
                    Toast.makeText(Remerciement_activity.this, "Lieu introuvable", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Lieu> call, Throwable t) {
                Toast.makeText(Remerciement_activity.this, "Erreur réseau : " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }


    // Envoi de l'email
    private void sendEmail(Personne personne) {
        // Validation de l'email
        if (personne == null || !isValidEmail(personne.getEmail())) {
            Toast.makeText(getApplicationContext(), "Email invalide", Toast.LENGTH_SHORT).show();
            return;
        }

        ApiService apiService = RetrofitClient.getApiService();

        EmailRequest email = new EmailRequest(
                titre.getText().toString(),
                date.getText().toString(),
                lieu.getText().toString(),
                String.valueOf(numeroPlaces.getText()), // Utilisation du texte propre
                personne.getEmail()
        );

        // Log des données envoyées
        Log.d("EmailRequest", "Titre: " + titre.getText().toString());
        Log.d("EmailRequest", "Date: " + date.getText().toString());
        Log.d("EmailRequest", "Lieu: " + lieu.getText().toString());
        Log.d("EmailRequest", "Numéro des places: " + numeroPlaces.getText().toString());
        Log.d("EmailRequest", "Email: " + personne.getEmail());

        Call<Void> call = apiService.envoyerEmailVerification(email);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "E-mail envoyé avec succès", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Échec de l'envoi de l'e-mail", Toast.LENGTH_SHORT).show();
                    Log.e("Email", "Erreur : " + response.code() + " - " + response.message());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Erreur : " + t.getMessage(), Toast.LENGTH_LONG).show();
                Log.e("Email", "Erreur réseau : " + t.getMessage());
            }
        });
    }

    private boolean isValidEmail(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    // Formatter la date au format souhaité
    private String formaterDate(String dateISO) {
        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault());
            inputFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

            Date parsedDate = inputFormat.parse(dateISO);
            SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            return outputFormat.format(parsedDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return "Date invalide";
        }
    }
}
