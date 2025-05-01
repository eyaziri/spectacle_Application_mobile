package com.example.spectacleoff;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Personne implements Parcelable {
    @SerializedName("id")
    private int id;
    @SerializedName("nom")
    private String nom;
    @SerializedName("prenom")
    private String prenom;
    @SerializedName("email")
    private String email;
    @SerializedName("nombreDePlace")
    private int nombreDePlace;
    @SerializedName("idRubrique")
    private int idRubrique;

    // Constructeur complet
    public Personne(int id, String nom, String prenom, String email, int nombreDePlace, int idRubrique) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.nombreDePlace = nombreDePlace;
        this.idRubrique = idRubrique;
    }

    // Constructeur sans ID (si nécessaire)
    public Personne(String nom, String prenom, String email, int nombreDePlace, int idRubrique) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.nombreDePlace = nombreDePlace;
        this.idRubrique = idRubrique;
    }

    public Personne() {

    }

    // Getters / Setters (inchangés)
    public int getId() { return id; }
    public String getNom() { return nom; }
    public String getPrenom() { return prenom; }
    public String getEmail() { return email; }
    public int getNombreDePlace() { return nombreDePlace; }
    public int getIdRubrique() { return idRubrique; }

    public void setId(int id) { this.id = id; }
    public void setNom(String nom) { this.nom = nom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }
    public void setEmail(String email) { this.email = email; }
    public void setNombreDePlace(int nombreDePlace) { this.nombreDePlace = nombreDePlace; }
    public void setIdRubrique(int idRubrique) { this.idRubrique = idRubrique; }

    // Parcelable
    protected Personne(Parcel in) {
        id = in.readInt();
        nom = in.readString();
        prenom = in.readString();
        email = in.readString();
        nombreDePlace = in.readInt();
        idRubrique = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(nom);
        dest.writeString(prenom);
        dest.writeString(email);
        dest.writeInt(nombreDePlace);
        dest.writeInt(idRubrique);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Personne> CREATOR = new Creator<Personne>() {
        @Override
        public Personne createFromParcel(Parcel in) {
            return new Personne(in);
        }

        @Override
        public Personne[] newArray(int size) {
            return new Personne[size];
        }
    };
}
