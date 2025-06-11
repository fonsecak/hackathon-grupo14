const express = require('express');
const router = express.Router();
const knex = require('../database/connection');
const bcrypt = require('bcrypt');

router.post('/', async (req, res) => {
  const { nome, sobrenome, cpf, email, senha, empresa } = req.body;

  // Verificação de campos obrigatórios
  if (!nome || !sobrenome || !cpf || !email || !senha || !empresa) {
    return res.status(400).json({ erro: 'Todos os campos são obrigatórios.' });
  }

  try {
    // Criptografar senha
    const saltRounds = 10;
    const hashedPassword = await bcrypt.hash(senha, saltRounds);

    // Inserir no banco
    await knex('participantes').insert({
      nome,
      sobrenome,
      cpf,
      email,
      senha: hashedPassword,
      empresa
    });

    res.status(201).json({ mensagem: 'Usuário cadastrado com sucesso!' });
  } catch (error) {
    console.error(error);
    res.status(500).json({ erro: 'Erro ao cadastrar usuário.' });
  }
});

module.exports = router;
