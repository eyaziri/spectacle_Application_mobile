// routes/personne.js
const express = require('express');
const router = express.Router();
const db = require('../db');

// POST - Ajouter une personne
router.post('/', (req, res) => {
  const { nom, prenom, email, nombreDePlace, idRubrique } = req.body;

  if (!nom || !prenom || !email || !nombreDePlace || !idRubrique) {
    return res.status(400).json({ erreur: 'Tous les champs sont obligatoires' });
  }

  const sql = 'INSERT INTO personne (nom, prenom, email, nombreDePlace, idRubrique) VALUES (?, ?, ?, ?, ?)';
  const values = [nom, prenom, email, nombreDePlace, idRubrique];

  db.query(sql, values, (err, result) => {
    if (err) return res.status(500).json({ erreur: err.message });
    res.status(201).json({ message: 'Personne ajoutée', id: result.insertId });
  });
});

// GET - Récupérer toutes les personnes
router.get('/', (req, res) => {
  db.query('SELECT * FROM personne', (err, results) => {
    if (err) return res.status(500).json({ erreur: err.message });
    res.json(results);
  });
});

// 🔁 GET - Récupérer la dernière personne (avant /:id pour éviter conflit)
router.get('/derniere', (req, res) => {
  const sql = 'SELECT * FROM personne ORDER BY id DESC LIMIT 1';

  db.query(sql, (err, result) => {
    if (err) return res.status(500).json({ erreur: err.message });
    if (result.length === 0) {
      return res.status(404).json({ erreur: 'Aucune personne trouvée' });
    }
    res.json(result[0]);
  });
});

// GET - Récupérer une personne par ID
router.get('/:id', (req, res) => {
  const id = req.params.id;

  if (!id) {
    return res.status(400).json({ erreur: 'ID manquant' });
  }

  const sql = 'SELECT * FROM personne WHERE id = ?';

  db.query(sql, [id], (err, result) => {
    if (err) return res.status(500).json({ erreur: err.message });
    if (result.length === 0) {
      return res.status(404).json({ erreur: 'Personne non trouvée' });
    }
    res.json(result[0]);
  });
});

module.exports = router;
