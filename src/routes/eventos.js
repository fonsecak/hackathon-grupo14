const express = require('express');
const router = express.Router();
const eventoController = require('../controllers/eventoController');

router.get('/', eventoController.index);
router.get('/destaques', eventoController.randomHighlights);
router.get('/:id', eventoController.getEventoById);

module.exports = router;
