const express = require('express');
const router = express.Router();
const palestranteController = require('../controllers/palestranteController');

// ... outras rotas
router.get('/:id', palestranteController.getPalestranteById);

module.exports = router;
