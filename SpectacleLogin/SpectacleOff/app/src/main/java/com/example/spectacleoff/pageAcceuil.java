package com.example.spectacleoff;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class pageAcceuil extends AppCompatActivity {

    private RecyclerView recyclerView;
    private SpectacleAdapter adapter;
    private List<Spectacle> spectacles = new ArrayList<>();

    private EditText searchEditText;
    private ImageButton searchButton;
    private Spinner searchTypeSpinner;

    private String currentSearchType = "Recherche par titre";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_acceuil);

        recyclerView = findViewById(R.id.recyclerViewFilms);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        searchEditText = findViewById(R.id.searchEditText);
        searchButton = findViewById(R.id.recherche);
        searchTypeSpinner = findViewById(R.id.searchTypeSpinner);
        searchTypeSpinner.setVisibility(View.GONE);

        setupSpinner();
        setupSearchButton();
        setupSearchListener();
        setupEnterKeyHandler();  // <--- ajout de la méthode ici
        setupBottomNavigation();

        loadSpectaclesFromAPI();
    }

    private void setupSpinner() {
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(
                this,
                R.array.search_options,
                R.layout.spinner_item_white
        );
        spinnerAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item_white);
        searchTypeSpinner.setAdapter(spinnerAdapter);

        searchTypeSpinner.setBackgroundColor(getResources().getColor(android.R.color.transparent));
        searchTypeSpinner.setPopupBackgroundDrawable(getDrawable(R.drawable.spinner_dropdown_background));

        searchTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                currentSearchType = parent.getItemAtPosition(position).toString();
                searchEditText.setHint(currentSearchType.equals("Recherche par lieu")
                        ? "Lieu du spectacle..." : "Titre du spectacle...");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    private void setupSearchButton() {
        searchButton.setOnClickListener(v -> {
            if (searchEditText.getVisibility() == View.GONE) {
                searchEditText.setVisibility(View.VISIBLE);
                searchTypeSpinner.setVisibility(View.VISIBLE);
                searchEditText.requestFocus();
            } else {
                searchEditText.setVisibility(View.GONE);
                searchTypeSpinner.setVisibility(View.GONE);
            }
        });
    }

    private void setupSearchListener() {
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void afterTextChanged(Editable s) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String query = s.toString();
                if (currentSearchType.equals("Recherche par lieu")) {
                    filterSpectaclesByLieu(query);
                } else {
                    filterSpectaclesByTitle(query);
                }
            }
        });
    }

    private void setupEnterKeyHandler() {
        searchEditText.setOnEditorActionListener((TextView v, int actionId, KeyEvent event) -> {
            // Empêche la suppression du texte ou d'autres actions automatiques
            return true;
        });
    }

    private void setupBottomNavigation() {
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            String selectedGenre = item.getTitle().toString();
            filterSpectaclesByGenre(selectedGenre);
            return true;
        });
    }

    private void loadSpectaclesFromAPI() {
        ApiService apiService = RetrofitClient.getApiService();
        apiService.getSpectacles().enqueue(new Callback<List<Spectacle>>() {
            @Override
            public void onResponse(Call<List<Spectacle>> call, Response<List<Spectacle>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    spectacles = response.body();
                    adapter = new SpectacleAdapter(pageAcceuil.this, spectacles);
                    recyclerView.setAdapter(adapter);
                } else {
                    showToast("Erreur de réponse");
                }
            }

            @Override
            public void onFailure(Call<List<Spectacle>> call, Throwable t) {
                Log.e("API_ERROR", t.getMessage());
                showToast("Erreur : " + t.getMessage());
            }
        });
    }

    private void filterSpectaclesByGenre(String genre) {
        if (spectacles != null) {
            List<Spectacle> filtered = spectacles.stream()
                    .filter(s -> s.getGenre().equalsIgnoreCase(genre))
                    .collect(Collectors.toList());

            updateAdapter(filtered);
        }
    }

    private void filterSpectaclesByTitle(String query) {
        if (spectacles != null) {
            List<Spectacle> filtered = spectacles.stream()
                    .filter(s -> s.getTitre().toLowerCase().contains(query.toLowerCase()))
                    .collect(Collectors.toList());

            updateAdapter(filtered);
        }
    }

    private void filterSpectaclesByLieu(String query) {
        if (spectacles == null || query.isEmpty()) return;

        List<Spectacle> filtered = new ArrayList<>();
        int[] counter = {0};

        for (Spectacle spectacle : spectacles) {
            ApiService apiService = RetrofitClient.getApiService();
            apiService.getRubriquesBySpectacle(spectacle.getIdSpec()).enqueue(new Callback<List<Rubrique>>() {
                @Override
                public void onResponse(Call<List<Rubrique>> call, Response<List<Rubrique>> response) {
                    counter[0]++;
                    if (response.isSuccessful() && response.body() != null) {
                        List<Rubrique> rubriques = response.body();

                        for (Rubrique rubrique : rubriques) {
                            getLieuById(rubrique.getIdLieu(), new Callback<Lieu>() {
                                @Override
                                public void onResponse(Call<Lieu> call, Response<Lieu> responseLieu) {
                                    if (responseLieu.isSuccessful() && responseLieu.body() != null) {
                                        Lieu lieu = responseLieu.body();
                                        if (lieu.getNomLieu().toLowerCase().contains(query.toLowerCase())) {
                                            if (!filtered.contains(spectacle)) {
                                                filtered.add(spectacle);
                                            }
                                        }
                                    }
                                    checkAndUpdateAdapter(filtered, counter[0]);
                                }

                                @Override
                                public void onFailure(Call<Lieu> call, Throwable t) {
                                    Log.e("API_ERROR", t.getMessage());
                                    showToast("Erreur : " + t.getMessage());
                                    checkAndUpdateAdapter(filtered, counter[0]);
                                }
                            });
                        }

                        if (rubriques.isEmpty()) {
                            checkAndUpdateAdapter(filtered, counter[0]);
                        }
                    } else {
                        checkAndUpdateAdapter(filtered, counter[0]);
                    }
                }

                @Override
                public void onFailure(Call<List<Rubrique>> call, Throwable t) {
                    counter[0]++;
                    checkAndUpdateAdapter(filtered, counter[0]);
                    Log.e("API_ERROR", t.getMessage());
                    showToast("Erreur : " + t.getMessage());
                }
            });
        }
    }

    private void getLieuById(int idLieu, Callback<Lieu> callback) {
        ApiService apiService = RetrofitClient.getApiService();
        apiService.getLieuById(idLieu).enqueue(callback);
    }

    private void checkAndUpdateAdapter(List<Spectacle> filtered, int counter) {
        if (counter == spectacles.size()) {
            updateAdapter(filtered);
        }
    }

    private void updateAdapter(List<Spectacle> newList) {
        adapter = new SpectacleAdapter(this, newList);
        recyclerView.setAdapter(adapter);
    }

    private void showToast(String message) {
        Toast.makeText(pageAcceuil.this, message, Toast.LENGTH_SHORT).show();
    }
}
