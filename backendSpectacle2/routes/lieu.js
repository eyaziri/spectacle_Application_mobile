const express = require('express');
const router = express.Router();
const db = require('../db');// Assure-toi que ce fichier contient la connexion à MySQL

// GET all lieux
router.get('/', (req, res) => {
  db.query('SELECT * FROM lieu', (err, results) => {
    if (err) return res.status(500).json({ error: err });
    res.json(results);
  });
});

// GET lieu by id
router.get('/:idLieu', (req, res) => {
  const id = req.params.idLieu;
  db.query('SELECT * FROM lieu WHERE idLieu = ?', [id], (err, results) => {
    if (err) return res.status(500).json({ error: err });
    if (results.length === 0) return res.status(404).json({ message: 'Lieu non trouvé' });
    res.json(results[0]);
  });
});

// POST add lieu
router.post('/add', (req, res) => {
  const { NomLieu, Adresse, capacite } = req.body;
  if (!Adresse || !capacite) {
    return res.status(400).json({ error: 'Champs requis manquants' });
  }
  db.query(
    'INSERT INTO lieu (NomLieu, Adresse, capacite) VALUES (?, ?, ?)',
    [NomLieu, Adresse, capacite],
    (err, result) => {
      if (err) return res.status(500).json({ error: err });
      res.status(201).json({ message: 'Lieu ajouté', id: result.insertId });
    }
  );
});

// PUT update lieu
router.put('/:idLieu', (req, res) => {
  const id = req.params.idLieu;
  const { NomLieu, Adresse, capacite } = req.body;
  db.query(
    'UPDATE lieu SET NomLieu = ?, Adresse = ?, capacite = ? WHERE idLieu = ?',
    [NomLieu, Adresse, capacite, id],
    (err, result) => {
      if (err) return res.status(500).json({ error: err });
      res.json({ message: 'Lieu mis à jour' });
    }
  );
});

// DELETE lieu
router.delete('/:idLieu', (req, res) => {
  const id = req.params.idLieu;
  db.query('DELETE FROM lieu WHERE idLieu = ?', [id], (err, result) => {
    if (err) return res.status(500).json({ error: err });
    res.json({ message: 'Lieu supprimé' });
  });
});

module.exports = router;
