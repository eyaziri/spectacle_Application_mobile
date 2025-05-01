const express = require('express');
const router = express.Router();
const db = require('../db'); // Connexion MySQL

// ✅ 1. Ajouter un utilisateur (POST)
router.post('/add', (req, res) => {
  const { nom, prenom, email, motdepasse } = req.body;

  if (!nom || !prenom || !email || !motdepasse) {
    return res.status(400).json({ error: 'Tous les champs sont obligatoires.' });
  }

  const sql = 'INSERT INTO user (nom, prenom, email, motdepasse) VALUES (?, ?, ?, ?)';
  db.query(sql, [nom, prenom, email, motdepasse], (err, result) => {
    if (err) {
      console.error('Erreur MySQL :', err);
      return res.status(500).json({ error: 'Erreur lors de la création de l\'utilisateur.' });
    }
    res.status(201).json({ message: 'Utilisateur ajouté avec succès.', idUser: result.insertId });
  });
});

// ✅ 2. Connexion utilisateur (placer AVANT /:id ou /email/:email)
router.get('/connexion', (req, res) => {
  const { email, motdepasse } = req.query;

  if (!email || !motdepasse) {
    return res.status(400).json({ error: 'Email et mot de passe requis.' });
  }

  const sqlEmail = 'SELECT * FROM user WHERE email = ?';
  db.query(sqlEmail, [email], (err, results) => {
    if (err) {
      console.error('Erreur MySQL :', err);
      return res.status(500).json({ error: 'Erreur serveur.' });
    }

    if (results.length === 0) {
      return res.status(404).json({ error: 'Email incorrect' });
    }

    const utilisateur = results[0];
    if (utilisateur.motdepasse !== motdepasse) {
      return res.status(401).json({ error: 'Mot de passe incorrect' });
    }

    delete utilisateur.motdepasse;
    res.status(200).json(utilisateur);
  });
});


// ✅ 3. Obtenir un utilisateur par email
router.get('/email/:email', (req, res) => {
  const email = req.params.email;

  const sql = 'SELECT * FROM user WHERE email = ?';
  db.query(sql, [email], (err, results) => {
    if (err) {
      console.error('Erreur MySQL :', err);
      return res.status(500).json({ error: 'Erreur lors de la récupération.' });
    }

    if (results.length === 0) {
      return res.status(404).json({ error: 'Utilisateur non trouvé.' });
    }

    const utilisateur = results[0];
    delete utilisateur.motdepasse;
    res.json(utilisateur);
  });
});

// ✅ 4. Obtenir un utilisateur par ID
router.get('/:id', (req, res) => {
  const userId = req.params.id;

  const sql = 'SELECT * FROM user WHERE idUser = ?';
  db.query(sql, [userId], (err, results) => {
    if (err) {
      console.error('Erreur MySQL :', err);
      return res.status(500).json({ error: 'Erreur lors de la récupération.' });
    }

    if (results.length === 0) {
      return res.status(404).json({ error: 'Utilisateur non trouvé.' });
    }

    const utilisateur = results[0];
    delete utilisateur.motdepasse;
    res.json(utilisateur);
  });
});

// ✅ 5. Obtenir tous les utilisateurs
router.get('/', (req, res) => {
  const sql = 'SELECT * FROM user';
  db.query(sql, (err, results) => {
    if (err) {
      console.error('Erreur MySQL :', err);
      return res.status(500).json({ error: 'Erreur lors de la récupération des utilisateurs.' });
    }

    const utilisateurs = results.map(u => {
      delete u.motdepasse;
      return u;
    });

    res.json(utilisateurs);
  });
});

module.exports = router;
