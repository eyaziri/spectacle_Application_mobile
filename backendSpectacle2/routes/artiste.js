// routes/artiste.js
const express = require('express');
const router = express.Router();
const db = require('../db');

// GET - Récupérer tous les artistes
router.get('/', (req, res) => {
  db.query('SELECT * FROM artiste', (err, results) => {
    if (err) return res.status(500).json({ erreur: err.message });
    res.json(results);
  });
});

// POST - Ajouter un artiste
router.post('/', (req, res) => {
  const { NomArt, PrenomArt, specialite } = req.body;

  if (!NomArt || !PrenomArt || !specialite) {
    return res.status(400).json({ erreur: 'Tous les champs sont obligatoires' });
  }

  const sql = 'INSERT INTO artiste (NomArt, PrenomArt, specialite) VALUES (?, ?, ?)';
  const values = [NomArt, PrenomArt, specialite];

  db.query(sql, values, (err, result) => {
    if (err) return res.status(500).json({ erreur: err.message });
    res.status(201).json({ message: 'Artiste ajouté', id: result.insertId });
  });
});

module.exports = router;
