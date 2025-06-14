const db = require('../database/connection');

module.exports = {
  index: async (req, res) => {
      try {
    const eventos = await db('eventos').select('*');
    res.json(eventos); 
  } catch (error) {
    console.error('Erro ao buscar eventos:', error);
    res.status(500).json({ error: 'Erro ao carregar eventos' });
  }
},

  store: async (req, res) => {
    const {
      nome,
      descricao,
      data_hora_inicio,
      data_hora_fim,
      local,
      valor_inscricao,
      publico_alvo,
      objetivo,
      banner,
      id_palestrante: palestrante,
      vagas_maxima
    } = req.body;    
  },

  randomHighlights: async (req, res) => {
  try {
    const destaques = await db('eventos').orderByRaw('RAND()').limit(3);
    res.json(destaques);
  } catch (error) {
    console.error('Erro ao buscar eventos aleatórios:', error);
    res.status(500).send('Erro ao carregar eventos em destaque');
  }
}
};