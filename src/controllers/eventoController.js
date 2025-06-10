const db = require('../database/connection');

module.exports = {
  async index(req, res) {
    const eventos = await db('eventos').select('*');
    return res.json(eventos);
  },

  async store(req, res) {
    const { titulo, descricao, data, hora } = req.body;

    const [id] = await db('eventos').insert({ titulo, descricao, data, hora });

    return res.status(201).json({ id });
  }
};