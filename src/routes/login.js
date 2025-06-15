const express = require('express');
const router = express.Router();
const { loginUsuario } = require('../controllers/loginController');

router.post('/', loginUsuario);

module.exports = router;