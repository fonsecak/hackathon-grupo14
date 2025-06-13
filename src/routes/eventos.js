const express = require('express');
const router = express.Router();
const eventoController = require('../controllers/eventoController');

router.get('/', eventoController.index);
router.post('/', eventoController.store);
router.get('/destaques', eventoController.randomHighlights);

module.exports = router;
