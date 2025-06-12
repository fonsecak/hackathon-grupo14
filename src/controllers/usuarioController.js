const bcrypt = require('bcrypt');
const db = require('../database/connection');

module.exports = {
  async store(req, res) {
    const { nome, email, senha } = req.body;

    const senhaHash = await bcrypt.hash(senha, 10); 

    const [id] = await db('usuarios').insert({
      nome,
      email,
      senha: senhaHash
    });

    return res.status(201).json({ id });
  }
};

