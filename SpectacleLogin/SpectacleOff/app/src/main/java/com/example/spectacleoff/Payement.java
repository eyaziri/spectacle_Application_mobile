package com.example.spectacleoff;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.spectacleoff.User;

import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Payement extends AppCompatActivity {

    private int idSpec, idRubrique, prixSpectacle, seatsReserved;
    private int prixTotalInitial;
    private String movieGenre;
    private boolean estVisiteur = false;
    private int identite;
    private String mail;

    private EditText nom, prenom, email, emailLogin, password;
    private TextView avezVousDeCompte, textView17;
    private CheckBox checkboxCarte, checkboxD17, checkboxCash;
    private Button seConnecter, passerCommeVisiteur, payer;

    private ApiService apiService;
    private Personne personne;
    private Personne p1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payement);

        // Récupération des données de l'intent
        Intent intent = getIntent();
        idSpec = intent.getIntExtra("idSpec", -1);
        idRubrique = intent.getIntExtra("idRubrique", -1);
        prixSpectacle = intent.getIntExtra("prix", 0);
        seatsReserved = intent.getIntExtra("seatsReserved", 0);
        prixTotalInitial = intent.getIntExtra("prixTotal", 0);
        movieGenre = intent.getStringExtra("movieGenre");

        // Initialisation de Retrofit
        apiService = RetrofitClient.getApiService();
        // Liaison des vues
        nom = findViewById(R.id.nom);
        prenom = findViewById(R.id.prenom);
        email = findViewById(R.id.email);
        emailLogin = findViewById(R.id.emailLogin);
        password = findViewById(R.id.password);
        avezVousDeCompte = findViewById(R.id.avezVousDeCompte);
        textView17 = findViewById(R.id.textView17);
        checkboxCarte = findViewById(R.id.checkbox_carte);
        checkboxD17 = findViewById(R.id.checkbox_d17);
        checkboxCash = findViewById(R.id.checkbox_cash);
        seConnecter = findViewById(R.id.SeConnecter);
        passerCommeVisiteur = findViewById(R.id.PassercommeVisiteur);
        payer = findViewById(R.id.payer);

        // Mise à jour du prix sur le bouton payer
        updatePayerButton(prixTotalInitial);

        avezVousDeCompte.setPaintFlags(avezVousDeCompte.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        // Listeners
        avezVousDeCompte.setOnClickListener(v -> {
            Intent loginIntent = new Intent(Payement.this, Login.class);
            loginIntent.putExtra("idSpec", idSpec);
            loginIntent.putExtra("idRubrique", idRubrique);
            loginIntent.putExtra("prix", prixSpectacle);
            loginIntent.putExtra("seatsReserved", seatsReserved);
            loginIntent.putExtra("prixTotal", prixTotalInitial);  // 👈 le plus important
            loginIntent.putExtra("movieGenre", movieGenre);
            startActivity(loginIntent);
            finish(); // 👈 très important pour éviter le retour avec un ancien état
        });


        seConnecter.setOnClickListener(v -> {
            switchMode(false); // faux = pas visiteur
            payer.setText(prixTotalInitial + " DT | Payer"); // remettre le prix
        });

        // Bouton Passer comme visiteur
        passerCommeVisiteur.setOnClickListener(v -> {
            switchMode(true); // vrai = visiteur
            payer.setText(prixTotalInitial + " DT | Payer"); // remettre le prix
        });
        setupCheckBoxes();

        payer.setOnClickListener(v -> processPayment());
    }

    private void setupCheckBoxes() {
        CheckBox[] checkBoxes = {checkboxCarte, checkboxD17, checkboxCash};
        for (CheckBox cb : checkBoxes) {
            cb.setOnClickListener(v -> {
                for (CheckBox other : checkBoxes) {
                    if (other != cb) {
                        other.setChecked(false);
                        other.setButtonTintList(null);
                        other.setTextColor(Color.WHITE);
                    }
                }
                if (cb.isChecked()) {
                    cb.setButtonTintList(ColorStateList.valueOf(Color.parseColor("#22C55E")));
                    cb.setTextColor(Color.parseColor("#22C55E"));
                } else {
                    cb.setButtonTintList(null);
                    cb.setTextColor(Color.WHITE);
                }
            });
        }
    }

    private void updatePayerButton(int prix) {
        payer.setText(String.format("%d DT | Payer", prix));
    }

    private void processPayment() {
        if (!checkboxCarte.isChecked() && !checkboxD17.isChecked() && !checkboxCash.isChecked()) {
            Toast.makeText(this, "Veuillez sélectionner un mode de paiement", Toast.LENGTH_SHORT).show();
            return;
        }

        if (estVisiteur) {
            processVisitorPayment();
        } else {
            processUserPayment();
        }
    }

    private void processVisitorPayment() {
        String nomText = nom.getText().toString().trim();
        String prenomText = prenom.getText().toString().trim();
        String emailText = email.getText().toString().trim();

        if (nomText.isEmpty() || prenomText.isEmpty() || emailText.isEmpty()) {
            Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
            return;
        }

        Personne nouvellePersonne = new Personne(nomText, prenomText, emailText, seatsReserved, idSpec);

        apiService.enregistrerReservation(nouvellePersonne).enqueue(new Callback<Personne>() {
            @Override
            public void onResponse(Call<Personne> call, Response<Personne> response) {
                if (response.isSuccessful() && response.body() != null) {
                    personne = response.body();
                    identite = personne.getId();
                    mail = personne.getEmail();
                    p1 = personne;
                    payerBillet();
                } else {
                    afficherErreur(response);
                }
            }

            @Override
            public void onFailure(Call<Personne> call, Throwable t) {
                Toast.makeText(Payement.this, "Erreur réseau: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void processUserPayment() {
        String emailText = emailLogin.getText().toString().trim();
        String passwordText = password.getText().toString().trim();

        if (emailText.isEmpty() || passwordText.isEmpty()) {
            Toast.makeText(this, "Veuillez remplir l'email et le mot de passe", Toast.LENGTH_SHORT).show();
            return;
        }

        apiService.connexionUtilisateur(emailText, passwordText).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful() && response.body() != null) {
                    User utilisateur = response.body();
                    identite = utilisateur.getIdUser();
                    payerBillet();
                } else {
                    Toast.makeText(Payement.this, "Email ou mot de passe incorrect", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(Payement.this, "Erreur réseau lors de la connexion", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void payerBillet() {
        if (identite == 0) {
            Toast.makeText(this, "ID invalide pour la réservation", Toast.LENGTH_SHORT).show();
            return;
        }

        Billet billet = estVisiteur
                ? new Billet("Spectacle", prixTotalInitial, idSpec, true, identite, 2)
                : new Billet("Spectacle", prixTotalInitial, idSpec, true, 133, identite);

        apiService.ajouterBillet(billet).enqueue(new Callback<Billet>() {
            @Override
            public void onResponse(Call<Billet> call, Response<Billet> response) {
                if (response.isSuccessful()) {
                    Intent intent = new Intent(Payement.this, Remerciement_activity.class);
                    intent.putExtra("idRubrique", idRubrique);
                    intent.putExtra("idSpec", idSpec);
                    intent.putExtra("seatsReserved", seatsReserved);
                    intent.putExtra("prixTotal", prixTotalInitial);
                    intent.putExtra("movieGenre", movieGenre);
                    intent.putExtra("personne", p1);
                    startActivity(intent);
                    finish();
                } else {
                    afficherErreur(response);
                }
            }

            @Override
            public void onFailure(Call<Billet> call, Throwable t) {
                Toast.makeText(Payement.this, "Erreur lors de l'achat du billet", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void afficherErreur(Response<?> response) {
        try {
            String errorBody = response.errorBody() != null ? response.errorBody().string() : "Erreur inconnue";
            Toast.makeText(this, "Erreur: " + errorBody, Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void switchMode(boolean isVisitor) {
        estVisiteur = isVisitor;
        nom.setVisibility(isVisitor ? View.VISIBLE : View.GONE);
        prenom.setVisibility(isVisitor ? View.VISIBLE : View.GONE);
        email.setVisibility(isVisitor ? View.VISIBLE : View.GONE);

        int loginVisibility = isVisitor ? View.GONE : View.VISIBLE;
        emailLogin.setVisibility(loginVisibility);
        password.setVisibility(loginVisibility);
        avezVousDeCompte.setVisibility(loginVisibility);

        textView17.setVisibility(View.GONE);

        afficherPaiement(true);
        seConnecter.setVisibility(View.GONE);
        passerCommeVisiteur.setVisibility(View.GONE);
        payer.setText(prixTotalInitial + " DT | Payer");
    }

    private void afficherPaiement(boolean visible) {
        int visibility = visible ? View.VISIBLE : View.GONE;
        checkboxCarte.setVisibility(visibility);
        checkboxD17.setVisibility(visibility);
        checkboxCash.setVisibility(visibility);
        payer.setVisibility(visibility);
    }
}