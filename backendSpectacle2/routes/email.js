/*const express = require('express');
const nodemailer = require('nodemailer');
const router = express.Router();

const transporter = nodemailer.createTransport({
  service: 'gmail',
  auth: {
    user: 'eyaziri2@gmail.com',
    pass: 'jkpy uhlu dmyq cwio' // Remplace par le mot de passe d'application
  }
});

router.post('/envoyer-rem', (req, res) => {
  const { titre, date, lieu, nbPlaces } = req.body;

  const htmlContent = `
    <div style="font-family: Arial, sans-serif; max-width: 600px; margin: auto; border: 2px solid #d63384; border-radius: 12px; padding: 20px; background: #f9f9f9;">
      <header style="text-align: center;">
        <h1 style="font-size: 28px; color: #d63384;">🎭 Billet de Spectacle 🎫</h1>
        <p style="font-size: 20px; color: #666;">Merci pour votre achat !</p>
      </header>
      
      <div style="margin-top: 20px;">
        <p style="font-size: 18px;"><strong>Titre:</strong> ${titre}</p>
        <p style="font-size: 18px;"><strong>Date:</strong> ${date}</p>
        <p style="font-size: 18px;"><strong>Lieu:</strong> ${lieu}</p>
        <p style="font-size: 18px;"><strong>Nombre de places:</strong> ${nbPlaces}</p>
      </div>

      <footer style="text-align: center; margin-top: 30px;">
        <p style="font-size: 18px; color: #444;">🎉 Profitez de votre soirée et amusez-vous bien ! 🎉</p>
      </footer>
    </div>
  `;

  const mailOptions = {
    from: 'eyaziri2@gmail.com',
    to: 'eyaziri2@gmail.com', // Mettez l'email du destinataire ici
    subject: '🎫 Merci pour votre achat de billet',
    html: htmlContent
  };

  transporter.sendMail(mailOptions, (error, info) => {
    if (error) {
      console.error('Erreur d’envoi :', error);
      res.status(500).json({ success: false, error: error.toString() });
    } else {
      console.log('Email envoyé :', info.response);
      res.status(200).json({ success: true, message: 'Email de remerciement envoyé ✔' });
    }
  });
});

module.exports = router;*/


const express = require('express');
const nodemailer = require('nodemailer');
const router = express.Router();

const transporter = nodemailer.createTransport({
  service: 'gmail',
  auth: {
    user: 'eyaziri2@gmail.com',
    pass: 'jkpy uhlu dmyq cwio' // Remplace par le mot de passe d'application
  }
});

router.post('/envoyerrem', (req, res) => {
  const { titre, date, lieu, nbPlaces, emailReceiver } = req.body; // Remplacer 'receiver' par 'emailReceiver'

  // Vérifiez si le champ emailReceiver existe dans la requête
  const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
if (!emailReceiver || !emailRegex.test(emailReceiver)) {
  return res.status(400).json({ success: false, error: 'Adresse email du destinataire invalide' });
}
  

  const htmlContent = `
    <div style="font-family: Arial, sans-serif; max-width: 600px; margin: auto; border: 2px solid #d63384; border-radius: 12px; padding: 20px; background: #f9f9f9;">
      <header style="text-align: center;">
        <h1 style="font-size: 28px; color: #d63384;">🎭 Billet de Spectacle 🎫</h1>
        <p style="font-size: 20px; color: #666;">Merci pour votre achat !</p>
      </header>
      
      <div style="margin-top: 20px;">
        <p style="font-size: 18px;"><strong>Titre:</strong> ${titre}</p>
        <p style="font-size: 18px;"><strong>Date:</strong> ${date}</p>
        <p style="font-size: 18px;"><strong>Lieu:</strong> ${lieu}</p>
        <p style="font-size: 18px;"><strong>Nombre de places:</strong> ${nbPlaces}</p>
      </div>

      <footer style="text-align: center; margin-top: 30px;">
        <p style="font-size: 18px; color: #444;">🎉 Profitez de votre soirée et amusez-vous bien ! 🎉</p>
      </footer>
    </div>
  `;

  const mailOptions = {
    from: 'eyaziri2@gmail.com',
    to: emailReceiver, // Utilisation de 'emailReceiver' au lieu de 'receiver'
    subject: '🎫 Merci pour votre achat de billet',
    html: htmlContent
  };

  transporter.sendMail(mailOptions, (error, info) => {
    if (error) {
      console.error('Erreur d’envoi :', error);
      res.status(500).json({ success: false, error: error.toString() });
    } else {
      console.log('Email envoyé :', info.response);
      res.status(200).json({ success: true, message: 'Email de remerciement envoyé ✔' });
    }
  });
});

module.exports = router;
