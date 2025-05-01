package com.example.spectacleoff;

import com.google.gson.annotations.SerializedName;

public class Lieu {

    @SerializedName("idLieu")
    private int idLieu;

    @SerializedName("NomLieu")
    private String nomLieu;

    @SerializedName("Adresse")
    private String adresse;

    @SerializedName("capacite")
    private int capacite;

    // Constructeur vide requis pour Retrofit / Gson
    public Lieu() {}

    // Constructeur complet
    public Lieu(int idLieu, String nomLieu, String adresse, int capacite) {
        this.idLieu = idLieu;
        this.nomLieu = nomLieu;
        this.adresse = adresse;
        this.capacite = capacite;
    }

    // Getters et Setters
    public int getIdLieu() {
        return idLieu;
    }

    public void setIdLieu(int idLieu) {
        this.idLieu = idLieu;
    }

    public String getNomLieu() {
        return nomLieu;
    }

    public void setNomLieu(String nomLieu) {
        this.nomLieu = nomLieu;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public int getCapacite() {
        return capacite;
    }

    public void setCapacite(int capacite) {
        this.capacite = capacite;
    }
}
