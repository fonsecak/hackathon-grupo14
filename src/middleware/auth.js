const jwt = require('jsonwebtoken');

function autenticarToken(req, res, next) {
  const authHeader = req.headers['authorization'];
  const token = authHeader && authHeader.split(' ')[1]; // Espera: Bearer <token>

  

  if (!token) {    
    return res.status(401).json({ erro: 'Token não fornecido.' });
  }

  try {
    const usuario = jwt.verify(token, process.env.JWT_SECRET);
    
    // Verifica se o payload contém o campo email (necessário para o controlador de certificados)
    if (!usuario.email) {      
      return res.status(400).json({ erro: 'Token não contém informações de email do usuário.' });
    }

    req.usuario = usuario; // Anexa o payload decodificado ao req
    next();
  } catch (error) {        
    if (error.name === 'JsonWebTokenError') {
      return res.status(403).json({ erro: 'Token inválido.' });
    }
    if (error.name === 'TokenExpiredError') {
      return res.status(403).json({ erro: 'Token expirado.' });
    }
    return res.status(500).json({ erro: 'Erro interno na autenticação.' });
  }
}

module.exports = autenticarToken;