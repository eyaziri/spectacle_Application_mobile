package com.example.spectacleoff;

import com.google.gson.annotations.SerializedName;

public class EmailRequest {
    @SerializedName("titre")
    private String titre;
    @SerializedName("date")
    private String date;
    @SerializedName("lieu")
    private String lieu;
    @SerializedName("nbPlaces")
    private String nbPlaces;
    @SerializedName("emailReceiver")

    private String emailReceiver;


    // Constructeur
    public EmailRequest(String titre, String date, String lieu, String nbPlaces, String emailDestinataire) {
        this.titre = titre;
        this.date = date;
        this.lieu = lieu;
        this.nbPlaces = nbPlaces;
        this.emailReceiver = emailDestinataire;

    }
    // Getters et Setters
    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLieu() {
        return lieu;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    public String getNbPlaces() {
        return nbPlaces;
    }

    public void setNbPlaces(String nbPlaces) {
        this.nbPlaces = nbPlaces;
    }

    public String getEmailDestinataire() { return emailReceiver; }
    public void setEmailDestinataire(String emailDestinataire) { this.emailReceiver = emailDestinataire; }

}


