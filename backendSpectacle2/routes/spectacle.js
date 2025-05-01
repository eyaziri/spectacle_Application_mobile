// routes/spectacle.js
const express = require('express');
const router = express.Router();
const db = require('../db');

// GET - Tous les spectacles
router.get('/', (req, res) => {
  db.query('SELECT * FROM spectacle', (err, results) => {
    if (err) return res.status(500).json({ erreur: err.message });
    res.json(results);
  });
});

// GET - Spectacle par ID
router.get('/:idSpec', (req, res) => {
  const idSpec = req.params.idSpec;
  db.query('SELECT * FROM spectacle WHERE idSpec = ?', [idSpec], (err, results) => {
    if (err) return res.status(500).json({ erreur: err.message });
    if (results.length === 0) return res.status(404).json({ erreur: 'Spectacle non trouvé' });
    res.json(results[0]);
  });
});

// GET - Spectacles par genre
router.get('/genre/:genre', (req, res) => {
  const { genre } = req.params;
  const sql = "SELECT * FROM spectacle WHERE genre = ?";
  db.query(sql, [genre], (err, results) => {
    if (err) return res.status(500).json({ erreur: err.message });
    res.json(results);
  });
});

// GET - Recherche par titre
router.get('/titre/:titre', (req, res) => {
  const { titre } = req.params;
  const sql = "SELECT * FROM spectacle WHERE Titre LIKE ?";
  db.query(sql, [`%${titre}%`], (err, results) => {
    if (err) return res.status(500).json({ erreur: err.message });
    res.json(results);
  });
});

// GET - Recherche par genre et titre
router.get('/genre/:genre/titre/:titre', (req, res) => {
  const { genre, titre } = req.params;
  const sql = "SELECT * FROM spectacle WHERE genre = ? AND Titre LIKE ?";
  db.query(sql, [genre, `%${titre}%`], (err, results) => {
    if (err) return res.status(500).json({ erreur: err.message });
    res.json(results);
  });
});

// POST - Ajouter un spectacle (sans nbrSpectateur)
router.post('/', (req, res) => {
  const { Titre, dureeS, genre, urlImage, description, prix } = req.body;

  // Vérification des champs obligatoires
  if (!Titre || !genre) {
    return res.status(400).json({ erreur: 'Champs obligatoires manquants' });
  }

  const sql = `INSERT INTO spectacle 
    (Titre, dureeS, genre, urlImage, description, prix) 
    VALUES (?, ?, ?, ?, ?, ?)`;

  const values = [Titre, dureeS, genre, urlImage, description, prix];

  db.query(sql, values, (err, result) => {
    if (err) return res.status(500).json({ erreur: err.message });
    res.status(201).json({ message: 'Spectacle ajouté', id: result.insertId });
  });
});

module.exports = router;
