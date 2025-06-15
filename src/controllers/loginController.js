const knex = require('../database/connection');
const bcrypt = require('bcrypt');
const jwt = require('jsonwebtoken');
const SECRET_KEY = process.env.JWT_SECRET || 'secreta';

async function loginUsuario(req, res) {
  const { email, senha } = req.body;

  try {
    const usuario = await knex('participantes').where({ email }).first();

    if (!usuario || !(await bcrypt.compare(senha, usuario.senha))) {
      return res.status(401).json({ success: false, message: 'Credenciais inválidas' });
    }

    const token = jwt.sign(
      { id: usuario.id, nome: usuario.nome, email: usuario.email },
      SECRET_KEY,
      { expiresIn: '2h' } // Padronize com o tempo da rota
    );

    return res.status(200).json({
      success: true,
      message: 'Login realizado com sucesso.',
      token,
      user: { // Corresponde à chave esperada pelo frontend
        id: usuario.id,
        nome: usuario.nome,
        email: usuario.email
      }
    });
  } catch (error) {    
    return res.status(500).json({ success: false, message: 'Erro ao fazer login.' });
  }
}

module.exports = { loginUsuario };