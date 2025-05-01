package com.example.spectacleoff;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

import java.util.List;

public interface ApiService {

    // ==== LIEUX ====
    @GET("lieux")
    Call<List<Lieu>> getLieux();

    @GET("lieux/{idLieu}")
    Call<Lieu> getLieuById(@Path("idLieu") int idLieu);

    @POST("lieux")
    @Headers("Content-Type: application/json")
    Call<Void> ajouterLieu(@Body Lieu lieu);

    @PUT("lieux/{id}")
    Call<Void> updateLieu(@Path("id") int id, @Body Lieu lieu);

    // ==== UTILISATEURS ====

    @GET("users/connexion")
    Call<User> connexionUtilisateur(@Query("email") String email, @Query("motdepasse") String motdepasse);

    @GET("users")
    Call<List<User>> getAllUsers();

    @GET("users/{idUser}")
    Call<User> getUserById(@Path("idUser") int idUser);




    @GET("users/email/{email}")
    Call<User> getUserByEmail(@Path("email") String email);

    @POST("users/add")
    @Headers("Content-Type: application/json")
    Call<User> ajouterUtilisateur(@Body User user);


    // ==== RÉSERVATIONS ====
    @GET("personnes/{id}")
    Call<Personne> getPersonneById(@Path("id") int id);

    @GET("personnes/derniere")
    Call<Personne> getDernierePersonne();

    @POST("personnes")
    @Headers("Content-Type: application/json")
    Call<Personne> enregistrerReservation(@Body Personne reservation);

    // ==== SPECTACLES ====
    @GET("spectacles")
    Call<List<Spectacle>> getSpectacles();

    @GET("spectacles/{idSpec}")
    Call<Spectacle> getSpectacleById(@Path("idSpec") int idSpec);

    @POST("spectacles")
    @Headers("Content-Type: application/json")
    Call<Void> ajouterSpectacle(@Body Spectacle spectacle);

    @GET("spectacles/date/{date}/genre/{genre}")
    Call<List<Spectacle>> getSpectaclesByGenreAndDate(@Path("genre") String genre, @Path("date") String date);

    @GET("spectacles/date/{date}")
    Call<List<Spectacle>> getSpectaclesByDate(@Path("date") String date);

    @GET("spectacles/genre/{genre}")
    Call<List<Spectacle>> getSpectaclesByGenre(@Path("genre") String genre);

    @GET("spectacles/titre/{title}")
    Call<List<Spectacle>> getSpectaclesByTitle(@Path("title") String title);

    @GET("spectacles/genre/{genre}/date/{date}/titre/{title}")
    Call<List<Spectacle>> getSpectaclesByGenreDateAndTitle(@Path("genre") String genre, @Path("date") String date, @Path("title") String title);

    @GET("spectacles/genre/{genre}/titre/{title}")
    Call<List<Spectacle>> getSpectaclesByGenreAndTitle(@Path("genre") String genre, @Path("title") String title);

    @GET("spectacles/date/{date}/titre/{title}")
    Call<List<Spectacle>> getSpectaclesByDateAndTitle(@Path("date") String date, @Path("title") String title);

    @GET("spectacles/genre/{genre}/date/{date}/title/{title}/lieu/{lieu}")
    Call<List<Spectacle>> getSpectaclesByAll(@Path("genre") String genre, @Path("date") String date, @Path("title") String title, @Path("lieu") String lieu);

    @POST("email/envoyerrem")
    Call<Void> envoyerEmailVerification(@Body EmailRequest emailRequest);

    // ==== BILLETS ====
    @GET("billets")
    Call<List<Billet>> getAllBillets();

    @GET("billets/{idBillet}")
    Call<Billet> getBilletById(@Path("idBillet") int idBillet);

    @POST("billets")      // ✅ À GARDER
    Call<Billet> ajouterBillet(@Body Billet billet);


    @PUT("billets/{idBillet}")
    @Headers("Content-Type: application/json")
    Call<Billet> updateBillet(@Path("idBillet") int idBillet, @Body Billet billet);
    // ==== RUBRIQUES ====
    @GET("rubriques")
    Call<List<Rubrique>> getRubriques();

    @GET("rubriques/{idRubrique}")
    Call<Rubrique> getRubriqueById(@Path("idRubrique") int idRubrique);

    @GET("rubriques/getLieuRubrique/{idRubrique}")
    Call<LieuResponse> getLieuRubrique(@Path("idRubrique") int idRubrique);

    @GET("rubriques/spectacle/{idSpec}")
    Call<List<Rubrique>> getRubriquesBySpectacle(@Path("idSpec") int idSpec);

    @POST("rubriques")
    @Headers("Content-Type: application/json")
    Call<Void> ajouterRubrique(@Body Rubrique rubrique);

    @PUT("rubriques/{idRubrique}/updatePlaces")
    Call<Void> updateRubriquePlaces(
            @Path("idRubrique") int idRubrique,
            @Body Rubrique rubrique
    );
    @POST("billets/reserver")
    @Headers("Content-Type: application/json")
    Call<Billet> reserverBillet(@Body Billet billet);




}