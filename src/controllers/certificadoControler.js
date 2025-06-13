const knex = require('./../database/connection'); // ajuste o caminho conforme sua estrutura

exports.getCertificado = async (req, res) => {
    const usuarioId = req.query.usuario_id;
    if (!usuarioId) {
         res.status(400).json({ error: 'Usuário não informado' });
         return; 
    }

    try {
        const resultado = await knex('certificados')
            .select('certificado_pdf')
            .where({ usuario_id: usuarioId })
            .first();

        if (!resultado) {
             res.status(404).json({ error: 'Certificado não encontrado' });
            return;
        }

        res.setHeader('Content-Type', 'application/pdf');
        res.send(resultado.certificado_pdf);
    } catch (error) {
        res.status(500).json({ error: 'Erro ao buscar certificado' });
    }
};