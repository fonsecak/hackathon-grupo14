// src/routes/usuarios.js
const express = require('express');
const router = express.Router();
const { cadastrarUsuario } = require('../controllers/usuarioController');

router.post('/', cadastrarUsuario);

module.exports = router;
