const knex = require('../database/connection');

const certificadoController = {
  async listarCertificados(req, res) {
    const email = req.usuario.email; // Garantido pelo middleware

    try {
      const certificados = await knex('inscricoes as i')
        .join('participantes as p', 'i.id_participante', 'p.id')
        .join('eventos as e', 'i.id_evento', 'e.id')
        .where('p.email', email)
        .andWhere('i.status', 1) // status 1 = concluído
        .select(
          'i.id', // ID da inscrição (pode usar para gerar certificado)
          'e.nome as evento',
          'e.data_hora_inicio as data',
        );

      res.json(certificados);
    } catch (error) {      
      res.status(500).json({ erro: 'Erro interno ao buscar certificados.' });
    }
  }
};

module.exports = certificadoController;