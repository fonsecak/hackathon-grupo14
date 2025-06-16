const express = require('express');
const router = express.Router();
const inscricaoController = require('../controllers/inscricaoController');
const autenticarToken = require('../middleware/auth');

router.post('/', autenticarToken, inscricaoController.criarInscricao);
router.delete('/', autenticarToken, inscricaoController.cancelarInscricao);
router.get('/verificar', inscricaoController.verificarInscricao);
router.get('/participante/:id_participante', inscricaoController.listarPorParticipante);

module.exports = router; 