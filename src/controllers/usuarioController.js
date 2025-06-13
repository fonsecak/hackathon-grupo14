// src/controllers/usuarioController.js
const knex = require('../database/connection');
const bcrypt = require('bcrypt');

// Auxiliares
function isValidEmail(email) {
  return /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email);
}

function isValidCPF(cpf) {
  cpf = cpf.replace(/[^\d]+/g, '');

  if (cpf.length !== 11 || /^(\d)\1+$/.test(cpf)) return false;

  let soma = 0;
  for (let i = 0; i < 9; i++) {
    soma += parseInt(cpf.charAt(i)) * (10 - i);
  }

  let resto = 11 - (soma % 11);
  if (resto === 10 || resto === 11) resto = 0;
  if (resto !== parseInt(cpf.charAt(9))) return false;

  soma = 0;
  for (let i = 0; i < 10; i++) {
    soma += parseInt(cpf.charAt(i)) * (11 - i);
  }

  resto = 11 - (soma % 11);
  if (resto === 10 || resto === 11) resto = 0;
  if (resto !== parseInt(cpf.charAt(10))) return false;

  return true;
}



// Função principal exportada
async function cadastrarUsuario(req, res) {
  const { nome, sobrenome, cpf, email, senha, empresa } = req.body;  

  if (!nome || !sobrenome || !cpf || !email || !senha || !empresa) {
    return res.status(400).json({ erro: 'Todos os campos são obrigatórios.' });
  }

  if (!isValidEmail(email)) {
    return res.status(400).json({ erro: 'E-mail inválido.' });
  }

  if (!isValidCPF(cpf)) {
return res.status(400).json({ erro: 'CPF inválido. Use apenas números.' });
  }

  if (senha.length < 6) {
    return res.status(400).json({ erro: 'A senha deve ter pelo menos 6 caracteres.' });
  }
  

  try {
    // Verificar se já existe CPF
    const cpfExistente = await knex('participantes').where({ cpf }).first();
    if (cpfExistente) {
      return res.status(400).json({ erro: 'CPF já cadastrado.' });
    }

    // Verificar se já existe Email
    const emailExistente = await knex('participantes').where({ email }).first();
    if (emailExistente) {
      return res.status(400).json({ erro: 'E-mail já cadastrado.' });
    }

    const hashedPassword = await bcrypt.hash(senha, 10);

    await knex('participantes').insert({
      nome,
      sobrenome,
      cpf,
      email,
      senha: hashedPassword,
      empresa
    });

    
    return res.redirect('/');
  } catch (error) {
    console.error(error);
    res.status(500).json({ erro: 'Erro ao cadastrar usuário.' });
  }
}

module.exports = {
  cadastrarUsuario,
};
