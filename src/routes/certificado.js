const express = require('express');
const router = express.Router();
const { getCertificado } = require('../controllers/certificadoControler');

router.get('/certificado', getCertificado);

module.exports = router;