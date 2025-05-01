package com.example.spectacleoff;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Reservation_activity extends AppCompatActivity {

    private GridLayout seatsGrid;
    private final Set<Integer> selectedSeats = new HashSet<>();
    private final Set<Integer> reservedSeats = new HashSet<>();

    private int selectedRubriqueId = -1;
    private String selectedDay = "";
    private String selectedQuantity = "";
    private int idSpec = -1;
    private String movieGenre = "";
    private int prixSpectacle = 0;

    private int availableSeats = 0;
    private int totalSeats = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reservation_activity);

        Intent intent = getIntent();
        idSpec = intent.getIntExtra("idSpec", -1);
        movieGenre = intent.getStringExtra("movieGenre");
        prixSpectacle = intent.getIntExtra("prix", 0);

        if (idSpec == -1) {
            Toast.makeText(this, "Erreur: ID spectacle manquant", Toast.LENGTH_SHORT).show();
            finish();
        }

        seatsGrid = findViewById(R.id.seatsGrid);

        setupCheckoutButton();
        loadRubriqueDates();
    }

    private void setupSeatsGrid() {
        seatsGrid.removeAllViews();

        for (int i = 0; i < totalSeats; i++) {
            Button seat = new Button(this);
            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.width = 100;
            params.height = 100;
            params.setMargins(10, 10, 10, 10);
            seat.setLayoutParams(params);

            int seatIndex = i;
            seat.setBackground(ContextCompat.getDrawable(this, R.drawable.seat_available));
            seat.setOnClickListener(v -> toggleSeatSelection(seat, seatIndex));

            seatsGrid.addView(seat);
        }
    }

    private void toggleSeatSelection(Button seat, int index) {
        if (reservedSeats.contains(index)) return;

        if (selectedSeats.contains(index)) {
            selectedSeats.remove(index);
            seat.setBackground(ContextCompat.getDrawable(this, R.drawable.seat_available));
        } else {
            if (selectedSeats.size() >= availableSeats) {
                Toast.makeText(this, "Nombre maximal de places atteint", Toast.LENGTH_SHORT).show();
                return;
            }
            selectedSeats.add(index);
            seat.setBackground(ContextCompat.getDrawable(this, R.drawable.seat_selected));
        }

        updateSelectedQuantity();
    }

    private void updateSelectedQuantity() {
        selectedQuantity = String.valueOf(selectedSeats.size());

        TextView qtyText = findViewById(R.id.selectedQuantityText);
        TextView priceText = findViewById(R.id.selectedQuantityText2);

        if (qtyText != null) qtyText.setText(" " + selectedQuantity);
        if (priceText != null)
            priceText.setText(String.format(Locale.getDefault(), "%d TND", prixSpectacle * selectedSeats.size()));
    }

    private void setupCheckoutButton() {
        Button checkoutButton = findViewById(R.id.checkoutButton);
        checkoutButton.setOnClickListener(v -> {
            if (selectedDay.isEmpty() || selectedQuantity.isEmpty() || selectedSeats.isEmpty()) {
                Toast.makeText(this, "Veuillez compléter toutes les sélections", Toast.LENGTH_SHORT).show();
            } else {
                processCheckout();
            }
        });
    }

    private void loadRubriqueDates() {
        RetrofitClient.getApiService().getRubriquesBySpectacle(idSpec).enqueue(new Callback<List<Rubrique>>() {
            @Override
            public void onResponse(Call<List<Rubrique>> call, Response<List<Rubrique>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    displayRubriqueDates(response.body());
                } else {
                    Toast.makeText(Reservation_activity.this, "Erreur chargement rubriques", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Rubrique>> call, Throwable t) {
                Toast.makeText(Reservation_activity.this, "Erreur réseau", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void displayRubriqueDates(List<Rubrique> rubriques) {
        LinearLayout dateContainer = findViewById(R.id.dateContainer);
        dateContainer.removeAllViews();

        for (int i = 0; i < Math.min(3, rubriques.size()); i++) {
            Rubrique rubrique = rubriques.get(i);
            String formattedDate = formatDateTime(rubrique.getDateRubrique(), rubrique.getHeureRubrique());

            Button dateButton = new Button(this);
            dateButton.setText(formattedDate);
            dateButton.setBackground(ContextCompat.getDrawable(this, R.drawable.button_rounded_border));
            dateButton.setTextColor(Color.BLACK);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(0, 16, 0, 0);
            dateButton.setLayoutParams(params);
            dateButton.setPadding(32, 24, 32, 24);

            int rubriqueId = rubrique.getIdRubrique();
            dateButton.setOnClickListener(v -> {
                selectedDay = formattedDate;
                selectedRubriqueId = rubriqueId;
                resetDayButtonsColor();
                dateButton.setBackground(ContextCompat.getDrawable(this, R.drawable.button_rounded_selected));
                dateButton.setTextColor(Color.WHITE);
                loadRubriqueInfo(rubrique);
            });

            dateContainer.addView(dateButton);
        }
    }

    private void resetDayButtonsColor() {
        LinearLayout dateContainer = findViewById(R.id.dateContainer);
        for (int i = 0; i < dateContainer.getChildCount(); i++) {
            Button btn = (Button) dateContainer.getChildAt(i);
            btn.setBackground(ContextCompat.getDrawable(this, R.drawable.button_rounded_border));
            btn.setTextColor(Color.BLACK);
        }
    }

    private String formatDateTime(String datePart, String timePart) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            SimpleDateFormat timeFormat = new SimpleDateFormat(timePart.length() == 5 ? "HH:mm" : "HH:mm:ss", Locale.getDefault());

            Date date = dateFormat.parse(datePart);
            Date time = timeFormat.parse(timePart);

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            Calendar timeCal = Calendar.getInstance();
            timeCal.setTime(time);
            calendar.set(Calendar.HOUR_OF_DAY, timeCal.get(Calendar.HOUR_OF_DAY));
            calendar.set(Calendar.MINUTE, timeCal.get(Calendar.MINUTE));

            return new SimpleDateFormat("dd/MM/yyyy - HH:mm", Locale.getDefault()).format(calendar.getTime());
        } catch (ParseException e) {
            return datePart + " " + timePart;
        }
    }

    private void loadRubriqueInfo(Rubrique rubrique) {
        availableSeats = rubrique.getNombreDeSpectateur();
        totalSeats = rubrique.getNombreDeSpectateur();
        reservedSeats.clear();

        if (rubrique.getPlacesReservees() != null && !rubrique.getPlacesReservees().isEmpty()) {
            for (String s : rubrique.getPlacesReservees().split(",")) {
                try {
                    reservedSeats.add(Integer.parseInt(s.trim()));
                } catch (NumberFormatException ignored) {}
            }
        }

        setupSeatsGrid();
        updateGridAvailability();

        int idLieu = rubrique.getIdLieu();
        if (idLieu == -1) {
            Toast.makeText(this, "ID Lieu invalide", Toast.LENGTH_SHORT).show();
            return;
        }

        RetrofitClient.getApiService().getLieuById(idLieu).enqueue(new Callback<Lieu>() {
            @Override
            public void onResponse(Call<Lieu> call, Response<Lieu> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Lieu lieu = response.body();
                    TextView lieuText = findViewById(R.id.Lieu);

                    if (lieuText != null) {
                        if (lieu.getNomLieu() != null && !lieu.getNomLieu().isEmpty()) {
                            lieuText.setText(lieu.getNomLieu());
                            lieuText.setClickable(true);
                            lieuText.setTextColor(Color.GREEN);
                            lieuText.setPaintFlags(lieuText.getPaintFlags() | android.graphics.Paint.UNDERLINE_TEXT_FLAG);
                            lieuText.setOnClickListener(v -> {
                                Uri mapIntentUri = Uri.parse("https://www.google.com/maps/search/?api=1&query=" + Uri.encode(lieu.getNomLieu()));
                                startActivity(new Intent(Intent.ACTION_VIEW, mapIntentUri));
                            });
                        } else {
                            lieuText.setText("Lieu non disponible");
                            lieuText.setOnClickListener(null);
                            lieuText.setPaintFlags(0);
                            lieuText.setTextColor(Color.BLACK);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<Lieu> call, Throwable t) {
                Toast.makeText(Reservation_activity.this, "Erreur lors du chargement du lieu", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateGridAvailability() {
        selectedSeats.clear();
        for (int i = 0; i < seatsGrid.getChildCount(); i++) {
            Button seat = (Button) seatsGrid.getChildAt(i);
            if (reservedSeats.contains(i)) {
                seat.setEnabled(false);
                seat.setBackground(ContextCompat.getDrawable(this, R.drawable.seat_reserved));
            } else {
                seat.setEnabled(true);
                seat.setBackground(ContextCompat.getDrawable(this, R.drawable.seat_available));
            }
        }
        updateSelectedQuantity();
    }

    private void processCheckout() {
        if (selectedRubriqueId == -1 || selectedDay.isEmpty()) {
            Toast.makeText(this, "Veuillez sélectionner une date de représentation", Toast.LENGTH_SHORT).show();
            return;
        }

        int selectedCount = selectedSeats.size();
        if (selectedCount == 0) {
            Toast.makeText(this, "Veuillez sélectionner des sièges", Toast.LENGTH_SHORT).show();
            return;
        }

        Set<Integer> newReservedSet = new HashSet<>(reservedSeats);
        newReservedSet.addAll(selectedSeats);

        StringBuilder updatedPlaces = new StringBuilder();
        for (int seat : newReservedSet) {
            updatedPlaces.append(seat).append(",");
        }
        if (updatedPlaces.length() > 0) {
            updatedPlaces.setLength(updatedPlaces.length() - 1);
        }

        String updatedPlacesStr = updatedPlaces.toString();
        Log.d("UpdatedPlaces", updatedPlacesStr);  // Vérifier la liste des places réservées


        Rubrique updatedRubrique = new Rubrique();
        updatedRubrique.setPlacesReservees(updatedPlaces.toString());
        updatedRubrique.setNombreDeSpectateur(availableSeats - selectedCount);

        RetrofitClient.getApiService().updateRubriquePlaces(selectedRubriqueId, updatedRubrique).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(Reservation_activity.this, "Passer à l'étape de paiement", Toast.LENGTH_SHORT).show();
                    loadRubriqueDates();
                    Intent intent = new Intent(Reservation_activity.this, Payement.class);
                    intent.putExtra("idSpec", idSpec);
                    intent.putExtra("idRubrique", selectedRubriqueId);
                    intent.putExtra("prix", prixSpectacle);
                    intent.putExtra("seatsReserved", selectedSeats.size());
                    intent.putExtra("genre", movieGenre);
                    intent.putExtra("prixTotal", prixSpectacle * selectedSeats.size());
                    selectedSeats.clear();
                    startActivity(intent);
                } else {
                    Toast.makeText(Reservation_activity.this, "Erreur lors de la mise à jour: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }


            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(Reservation_activity.this, "Erreur réseau", Toast.LENGTH_SHORT).show();
            }
        });
    }

}