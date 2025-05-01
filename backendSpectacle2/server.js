const express = require('express');
const app = express();
const PORT = 3000;

// Middleware JSON
app.use(express.json());

// Importation des routes
const emailRoutes = require('./routes/email');
const personneRoutes = require('./routes/personne');
const spectacleRoutes = require('./routes/spectacle');
const billetRoutes = require('./routes/billet');
const rubriqueRoutes = require('./routes/rubrique');
const userRoutes = require('./routes/user'); // ✅ Import route user
const lieuRoutes = require('./routes/lieu');

// Utilisation des routes
app.use('/email', emailRoutes); 
app.use('/personnes', personneRoutes);
app.use('/spectacles', spectacleRoutes);
app.use('/billets', billetRoutes);
app.use('/rubriques', rubriqueRoutes);
app.use('/users', userRoutes); // ✅ Route user disponible à /users
app.use('/lieux', lieuRoutes);

// Gestion des routes non trouvées
app.use((req, res, next) => {
  res.status(404).json({ erreur: 'Route non trouvée' });
});

// Gestion des erreurs internes
app.use((err, req, res, next) => {
  console.error(err.stack);
  res.status(500).json({ erreur: 'Erreur interne du serveur' });
});

// Démarrage du serveur
app.listen(PORT, () => {
  console.log(`🚀 Serveur lancé sur http://localhost:${PORT}`);
});
