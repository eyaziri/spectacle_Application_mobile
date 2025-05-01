const express = require('express');
const router = express.Router();
const db = require('../db');


// POST - Ajouter une rubrique à un spectacle
router.post('/', (req, res) => {
  const { idSpec, dateRubrique, heureRubrique, idLieu, places_reservees } = req.body;

  // Vérifie les champs requis
  if (!idSpec || !dateRubrique || !heureRubrique || !idLieu) {
    return res.status(400).json({ erreur: 'Champs requis manquants' });
  }

  // Étape 1 : Récupérer la capacité du lieu
  const getCapaciteSql = 'SELECT capacite FROM lieu WHERE idLieu = ?';
  db.query(getCapaciteSql, [idLieu], (err, results) => {
    if (err) return res.status(500).json({ erreur: err.message });
    if (results.length === 0) return res.status(404).json({ erreur: 'Lieu non trouvé' });

    const nombreDeSpectateur = results[0].capacite;

    // Étape 2 : Insérer la rubrique avec nombreDeSpectateur auto
    const insertSql = `
      INSERT INTO rubrique 
      (idSpec, dateRubrique, heureRubrique, idLieu, places_reservees, nombreDeSpectateur)
      VALUES (?, ?, ?, ?, ?, ?)
    `;

    db.query(
      insertSql,
      [idSpec, dateRubrique, heureRubrique, idLieu, places_reservees || null, nombreDeSpectateur],
      (err, result) => {
        if (err) return res.status(500).json({ erreur: err.message });
        res.status(201).json({ message: 'Rubrique ajoutée', id: result.insertId });
      }
    );
  });
});
// GET - Toutes les rubriques
router.get('/', (req, res) => {
  db.query('SELECT * FROM rubrique', (err, results) => {
    if (err) return res.status(500).json({ erreur: err.message });
    res.json(results);
  });
});

// GET - Rubrique par idRubrique
router.get('/:idRubrique', (req, res) => {
  const idRubrique = req.params.idRubrique;
  db.query('SELECT * FROM rubrique WHERE idRubrique = ?', [idRubrique], (err, results) => {
      if (err) return res.status(500).json({ erreur: err.message });
      if (results.length === 0) return res.status(404).json({ erreur: 'Rubrique non trouvée' });
      res.json(results[0]);
  });
});


// GET - Rubriques par idSpec
router.get('/spectacle/:idSpec', (req, res) => {
  const idSpec = req.params.idSpec;
  db.query('SELECT * FROM rubrique WHERE idSpec = ?', [idSpec], (err, results) => {
    if (err) return res.status(500).json({ erreur: err.message });
    res.json(results);
  });
});

// GET - Rubriques par dateRubrique
router.get('/date/:dateRubrique', (req, res) => {
  const dateRubrique = req.params.dateRubrique;
  db.query('SELECT * FROM rubrique WHERE dateRubrique = ?', [dateRubrique], (err, results) => {
    if (err) return res.status(500).json({ erreur: err.message });
    res.json(results);
  });
});

// GET - Rubriques par idLieu
router.get('/lieu/:idLieu', (req, res) => {
  const idLieu = req.params.idLieu;
  db.query('SELECT * FROM rubrique WHERE idLieu = ?', [idLieu], (err, results) => {
    if (err) return res.status(500).json({ erreur: err.message });
    res.json(results);
  });
});

// PUT - Mettre à jour une rubrique
// Mettre à jour une rubrique
router.put('/:idRubrique', (req, res) => {
  const { idRubrique } = req.params;
  const { idSpec, dateRubrique, heureRubrique, idLieu, places_reservees } = req.body;

  // Vérifie les champs requis
  if (!idSpec || !dateRubrique || !heureRubrique || !idLieu) {
    return res.status(400).json({ erreur: 'Champs requis manquants' });
  }

  // Récupère la capacité du lieu
  const getCapaciteSql = 'SELECT capacite FROM lieu WHERE idLieu = ?';
  db.query(getCapaciteSql, [idLieu], (err, results) => {
    if (err) return res.status(500).json({ erreur: err.message });
    if (results.length === 0) return res.status(404).json({ erreur: 'Lieu non trouvé' });

    const nombreDeSpectateur = results[0].capacite;

    // Mettre à jour la rubrique
    const updateSql = `UPDATE rubrique 
                       SET idSpec = ?, dateRubrique = ?, heureRubrique = ?, idLieu = ?, places_reservees = ?, nombreDeSpectateur = ?
                       WHERE idRubrique = ?`;

    db.query(
      updateSql,
      [idSpec, dateRubrique, heureRubrique, idLieu, places_reservees || null, nombreDeSpectateur, idRubrique],
      (err, result) => {
        if (err) return res.status(500).json({ erreur: err.message });
        if (result.affectedRows === 0) return res.status(404).json({ erreur: 'Rubrique non trouvée' });
        res.status(200).json({ message: 'Rubrique mise à jour' });
      }
    );
  });
});


// PUT - Mettre à jour le nombre de places réservées pour une rubrique
router.put('/:idRubrique/updatePlaces', (req, res) => {
  const { idRubrique } = req.params;
  const { places_reservees } = req.body;

  // Vérifie que le nombre de places réservées est bien fourni
  if (places_reservees === undefined) {
    return res.status(400).json({ erreur: 'Le nombre de places réservées est requis' });
  }

  // Vérifie si la rubrique existe
  const checkRubriqueSql = 'SELECT * FROM rubrique WHERE idRubrique = ?';
  db.query(checkRubriqueSql, [idRubrique], (err, results) => {
    if (err) return res.status(500).json({ erreur: err.message });
    if (results.length === 0) return res.status(404).json({ erreur: 'Rubrique non trouvée' });

    // Si la rubrique existe, met à jour le nombre de places réservées
    const updateSql = `
      UPDATE rubrique
      SET places_reservees = ?
      WHERE idRubrique = ?
    `;
    db.query(updateSql, [places_reservees, idRubrique], (err, result) => {
      if (err) return res.status(500).json({ erreur: err.message });
      if (result.affectedRows === 0) return res.status(404).json({ erreur: 'Rubrique non trouvée pour mise à jour' });

      res.status(200).json({ message: 'Rubrique mise à jour avec succès' });
    });
  });
});


module.exports = router;

