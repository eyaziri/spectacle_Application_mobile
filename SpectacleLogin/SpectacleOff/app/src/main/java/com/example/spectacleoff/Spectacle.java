package com.example.spectacleoff;

import com.google.gson.annotations.SerializedName;

public class Spectacle {

    @SerializedName("idSpec")
    private int idSpec;

    @SerializedName("Titre")
    private String titre;

    @SerializedName("dureeS")
    private String dureeS;

    @SerializedName("genre")
    private String genre;

    @SerializedName("urlImage")
    private String urlImage;

    @SerializedName("description")
    private String description;

    @SerializedName("prix")
    private Integer prix; // Peut être null

    // Getters
    public int getIdSpec() {
        return idSpec;
    }

    public String getTitre() {
        return titre;
    }

    public String getDureeS() {
        return dureeS;
    }

    public String getGenre() {
        return genre;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public String getDescription() {
        return description;
    }

    public Integer getPrix() {
        return prix;
    }

    // Setters
    public void setIdSpec(int idSpec) {
        this.idSpec = idSpec;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public void setDureeS(String dureeS) {
        this.dureeS = dureeS;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrix(Integer prix) {
        this.prix = prix;
    }
}
