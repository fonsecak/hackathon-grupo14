const express = require('express');
const router = express.Router();
const db = require('../database/connection');
const bcrypt = require('bcrypt');

router.post('/', async (req, res) => {
  const { email, senha } = req.body;

  try {
    const user = await db('participantes').where({ email }).first();

    if (!user) {
      return res.status(401).json({ success: false, message: 'Usuário não encontrado' });
    }

    const senhaCorreta = await bcrypt.compare(senha, user.senha);

    if (!senhaCorreta) {
      return res.status(401).json({ success: false, message: 'Senha incorreta' });
    }

    return res.status(200).json({ success: true, message: 'Login bem-sucedido', user });
  } catch (err) {
    console.error(err);
    return res.status(500).json({ success: false, message: 'Erro no servidor' });
  }
});

module.exports = router;
