package com.example.spectacleoff;

import com.google.gson.annotations.SerializedName;

public class Rubrique {

    @SerializedName("idRubrique")
    private int idRubrique;

    @SerializedName("idSpec")
    private int idSpec;

    @SerializedName("dateRubrique")
    private String dateRubrique;

    @SerializedName("heureRubrique")
    private String heureRubrique;

    @SerializedName("idLieu")
    private int idLieu;

    @SerializedName("places_reservees")
    private String placesReservees;

    @SerializedName("nombreDeSpectateur")
    private int nombreDeSpectateur;

    // Getters et Setters

    public String getPlacesReservees() {
        return placesReservees;
    }

    public void setPlacesReservees(String placesReservees) {
        this.placesReservees = placesReservees;
    }

    public int getNombreDeSpectateur() {
        return nombreDeSpectateur;
    }

    public void setNombreDeSpectateur(int nombreDeSpectateur) {
        this.nombreDeSpectateur = nombreDeSpectateur;
    }

    public int getIdRubrique() {
        return idRubrique;
    }

    public void setIdRubrique(int idRubrique) {
        this.idRubrique = idRubrique;
    }

    public int getIdSpec() {
        return idSpec;
    }

    public void setIdSpec(int idSpec) {
        this.idSpec = idSpec;
    }

    public String getDateRubrique() {
        return dateRubrique;
    }

    public void setDateRubrique(String dateRubrique) {
        this.dateRubrique = dateRubrique;
    }

    public String getHeureRubrique() {
        return heureRubrique;
    }

    public void setHeureRubrique(String heureRubrique) {
        this.heureRubrique = heureRubrique;
    }

    public int getIdLieu() {
        return idLieu;
    }

    public void setIdLieu(int idLieu) {
        this.idLieu = idLieu;
    }

    // Constructeurs
    public Rubrique(int idSpec, String dateRubrique, String heureRubrique, int idLieu) {
        this.idSpec = idSpec;
        this.dateRubrique = dateRubrique;
        this.heureRubrique = heureRubrique;
        this.idLieu = idLieu;
    }

    public Rubrique() {
    }
}
