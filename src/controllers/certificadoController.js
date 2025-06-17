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
          'p.nome',
          'p.sobrenome'
        );

      res.json(certificados);
    } catch (error) {      
      res.status(500).json({ erro: 'Erro interno ao buscar certificados.' });
    }
  },

  async buscarCertificadoPorId(req, res) {
    const { id } = req.params;
    const email = req.usuario.email;

    try {
      const certificado = await knex('inscricoes as i')
        .join('participantes as p', 'i.id_participante', 'p.id')
        .join('eventos as e', 'i.id_evento', 'e.id')
        .where('i.id', id)
        .andWhere('p.email', email)
        .andWhere('i.status', 1)
        .select(
          'i.id',
          'e.nome as evento',
          'e.data_hora_inicio as data',
          'p.nome',
          'p.sobrenome'
        )
        .first();

      if (!certificado) {
        return res.status(404).json({ erro: 'Certificado não encontrado ou você não tem permissão para acessá-lo.' });
      }

      res.json(certificado);
    } catch (error) {
      res.status(500).json({ erro: 'Erro interno ao buscar certificado.' });
    }
  }
};

module.exports = certificadoController;