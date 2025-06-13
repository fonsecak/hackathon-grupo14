const express = require('express');
const eventosRoutes = require('./src/routes/eventos');
const usuariosRoutes = require('./src/routes/usuarios'); 
const loginRoutes = require('./src/routes/login');
const path = require('path');
const app = express();
const cors = require('cors');

app.use(cors()); // necessário para aceitar requisições de outros domínios
app.use(express.urlencoded({ extended: true }));
app.use(express.json());

app.use('/api/eventos', eventosRoutes);
app.use('/api/usuarios', usuariosRoutes);
app.use('/login', loginRoutes);

app.use('/img', express.static(path.join(__dirname, 'resources/img')));



app.listen(3000, () => {
  console.log('Servidor rodando na porta 3000');
});
