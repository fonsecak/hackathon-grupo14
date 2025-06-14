const db = require('../database/connection');

module.exports = {
  // ... outras funções

  getPalestranteById: async (req, res) => {
    const { id } = req.params;

    try {
      const palestrante = await db('palestrantes').where({ id }).first();

      if (!palestrante) {
        return res.status(404).json({ erro: 'Palestrante não encontrado.' });
      }

      res.json(palestrante);
    } catch (error) {
      console.error('Erro ao buscar palestrante:', error);
      res.status(500).json({ erro: 'Erro no servidor.' });
    }
  }
};
