const express = require('express');
const router = express.Router();
const db = require('../db');

// GET - Récupérer tous les billets
router.get('/', (req, res) => {
  db.query('SELECT * FROM billet', (err, results) => {
    if (err) return res.status(500).json({ erreur: err.message });
    res.json(results);
  });
});

// POST - Ajouter un billet
router.post('/', (req, res) => {
  const { categorie, prix, idSpec, Vendu, idPersonne, idUser } = req.body;

  // Vérifie les champs obligatoires
  if (!categorie || !prix || !idSpec || typeof Vendu === 'undefined') {
    return res.status(400).json({ erreur: 'Tous les champs obligatoires doivent être remplis' });
  }

  let sql, values;

  // Vérifie si idPersonne ou idUser est fourni et construit la requête en conséquence
  if (idPersonne) {
    sql = 'INSERT INTO billet (categorie, prix, idSpec, Vendu, idPersonne) VALUES (?, ?, ?, ?, ?)';
    values = [categorie, prix, idSpec, Vendu, idPersonne];
  } else if (idUser) {
    sql = 'INSERT INTO billet (categorie, prix, idSpec, Vendu, idUser) VALUES (?, ?, ?, ?, ?)';
    values = [categorie, prix, idSpec, Vendu, idUser];
  } else {
    return res.status(400).json({ erreur: 'L\'idPersonne ou l\'idUser doit être fourni' });
  }

  // Exécution de la requête
  db.query(sql, values, (err, result) => {
    if (err) return res.status(500).json({ erreur: err.message });
    res.status(201).json({ message: 'Billet ajouté', id: result.insertId });
  });
});

// POST - Réserver un billet
router.post('/reserver', (req, res) => {
  const { categorie, prix, idSpec, Vendu, idPersonne, idUser, placesReservees } = req.body;

  if (!categorie || !prix || !idSpec || typeof Vendu === 'undefined' || !placesReservees || !Array.isArray(placesReservees)) {
    return res.status(400).json({ erreur: 'Champs manquants ou invalides' });
  }

  const reservations = [];
  const valuesArray = [];

  // Construire les valeurs pour chaque place
  for (let place of placesReservees) {
    if (idPersonne) {
      valuesArray.push([categorie, prix, idSpec, Vendu, idPersonne, null, place]);
    } else if (idUser) {
      valuesArray.push([categorie, prix, idSpec, Vendu, null, idUser, place]);
    } else {
      return res.status(400).json({ erreur: "idPersonne ou idUser est requis" });
    }
  }

  const sql = 'INSERT INTO billet (categorie, prix, idSpec, Vendu, idPersonne, idUser, place) VALUES ?';

  db.query(sql, [valuesArray], (err, result) => {
    if (err) return res.status(500).json({ erreur: err.message });
    res.status(201).json({ message: 'Billets réservés avec succès', nombre: result.affectedRows });
  });
});

module.exports = router;
