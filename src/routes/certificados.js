const express = require('express');
const router = express.Router();
const certificadoController = require('../controllers/certificadoController');
const autenticarToken = require('../middleware/auth');

router.get('/', autenticarToken, certificadoController.listarCertificados);
router.get('/:id', autenticarToken, certificadoController.buscarCertificadoPorId);

module.exports = router;