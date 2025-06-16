const knex = require('../database/connection');

const inscricaoController = {
  async criarInscricao(req, res) {
    try {
      const { id_participante, id_evento } = req.body;
      if (!id_participante || !id_evento) {
        return res.status(400).json({ error: 'id_participante e id_evento são obrigatórios.' });
      }
      // Verifica se já existe inscrição
      const existe = await knex('inscricoes')
        .where({ id_participante, id_evento })
        .first();
      if (existe) {
        return res.status(409).json({ error: 'Já existe inscrição para este participante neste evento.' });
      }
      const [id] = await knex('inscricoes').insert({ id_participante, id_evento });
      return res.status(201).json({ success: true, id });
    } catch (error) {
      return res.status(500).json({ error: 'Erro ao criar inscrição.', details: error.message });
    }
  },
  async cancelarInscricao(req, res) {
    try {
      const { id_participante, id_evento } = req.body;
      if (!id_participante || !id_evento) {
        return res.status(400).json({ error: 'id_participante e id_evento são obrigatórios.' });
      }
      const deleted = await knex('inscricoes')
        .where({ id_participante, id_evento })
        .del();
      if (deleted) {
        return res.status(200).json({ success: true });
      } else {
        return res.status(404).json({ error: 'Inscrição não encontrada.' });
      }
    } catch (error) {
      return res.status(500).json({ error: 'Erro ao cancelar inscrição.', details: error.message });
    }
  },
  async verificarInscricao(req, res) {
    try {
      const { id_participante, id_evento } = req.query;
      if (!id_participante || !id_evento) {
        return res.status(400).json({ error: 'id_participante e id_evento são obrigatórios.' });
      }
      const existe = await knex('inscricoes')
        .where({ id_participante, id_evento })
        .first();
      return res.status(200).json({ inscrito: !!existe });
    } catch (error) {
      return res.status(500).json({ error: 'Erro ao verificar inscrição.', details: error.message });
    }
  }
};

module.exports = inscricaoController; 