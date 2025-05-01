package com.example.spectacleoff;

public class UpdateRequest {
    private String places_reservees;
    private int nombreSpectateur;

    // Getters and setters
    public String getPlaces_reservees() {
        return places_reservees;
    }

    public void setPlaces_reservees(String places_reservees) {
        this.places_reservees = places_reservees;
    }

    public int getNombreSpectateur() {
        return nombreSpectateur;
    }

    public void setNombreSpectateur(int nombreSpectateur) {
        this.nombreSpectateur = nombreSpectateur;
    }
}
