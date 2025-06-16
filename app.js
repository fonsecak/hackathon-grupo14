const express = require('express');
require('dotenv').config();
const eventosRoutes = require('./src/routes/eventos');
const usuariosRoutes = require('./src/routes/usuarios'); 
const loginRoutes = require('./src/routes/login');
const certificadoRoutes = require('./src/routes/certificados'); 
const palestrantesRoutes = require('./src/routes/palestrantes'); 
const inscricoesRoutes = require('./src/routes/inscricoes');
const path = require('path');
const app = express();
const cors = require('cors');


app.use(cors()); // necessário para aceitar requisições de outros domínios
app.use(express.urlencoded({ extended: true }));
app.use(express.json());

app.use('/api/eventos', eventosRoutes);
app.use('/api/usuarios', usuariosRoutes);
app.use('/api/login', loginRoutes);
app.use('/api/certificados', certificadoRoutes);
app.use('/api/palestrantes', palestrantesRoutes);
app.use('/api/inscricoes', inscricoesRoutes);

app.use('/img', express.static(path.join(__dirname, 'resources/img')));



app.listen(3000, () => {
  console.log('Servidor rodando na porta 3000');
});
