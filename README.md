#  Hackathon - Grupo 14  
##  Hotsite de Eventos UniALFA

Uma plataforma moderna para a organização e divulgação de eventos acadêmicos da UniALFA.

---

###  Integrantes do Grupo:

Marcos Kaiky Rodrigues Garcia - RA - 14610

Gabriel Fonseca Proença - RA - 14608

Brayan Adriano Flores Cristianini - RA - 14560

Guilherme Ananias Calixto Ribeiro - RA - 14652

Guilherme Augusto Cardoso Carrera - RA - 14651


###  Tecnologias Utilizadas

[![My Skills](https://skillicons.dev/icons?i=java,maven,php,js,nodejs,html,css,bootstrap,mysql,git,github)](https://skillicons.dev)


---


Prototipo de baixa fidelidade
Acessibilidade testada a font (escrita) e o background
Site para teste: https://webaim.org/resources/contrastchecker/

Site de do API de leitura de libras:
https://softwarepublico.gov.br/gitlab/groups/vlibras

Link do figma: 
https://www.figma.com/design/65wSlUl60gegzXIDbu8kP0/UX-Hackaton-Equipe-14?node-id=0-1&p=f&t=EC0DxPiX4YyB5Ioc-0


---

### Estrutura Back-Office



    src / main└── java/
        └── com/alfa/experience/
            ├── dao/
            │   ├── AlunoDao.java  --> (Descrição Breve sobre) !!Replicar ao Demais.!!
            │   ├── Dao.java
            │   ├── DaoInterface.java
            │   ├── EventoDao.java
            │   └── PalestranteDao.java
            │
            ├── gui/
            │   ├── GerEventoGui.java
            │   ├── GerPalestranteGui.java
            │   ├── GuiUtils.java
            │   ├── ListarAlunosGui.java
            │   └── TelaPrincipal.java
            │
            ├── model/
            │   ├── Aluno.java
            │   ├── Evento.java
            │   └── Palestrante.java
            │
            ├── service/
            │   ├── AlunoService.java
            │   ├── EventoService.java
            │   └── PalestranteService.java
            │
            └── Main.java
---

### Resources


           resources/
           ├── css/
           │ └── styles.css
           │
           ├── img/
           │ ├── eventoteste.png
           │ ├── eventoteste2.png
           │ └── logo.png
           │
           └── views/
           ├── cadastro.php
           ├── certificado.php
           ├── eventodetalhe.php
           ├── eventos.php
           ├── footer.php
           ├── gerar_pdf_certificado.php
           ├── index.php
           ├── login-modal.php
           ├── logout.php
           ├── meus_eventos.php
           └── navbar.php
---

### Src


    src/
    ├── controllers/
    │ ├── certificadoController.js
    │ ├── eventoController.js
    │ ├── inscricaoController.js
    │ ├── loginController.js
    │ ├── palestranteController.js
    │ └── usuarioController.js
    │
    ├── database/
    │ ├── migrations/
    │ └── connection.js
    │
    ├── middleware/
    │
    └── routes/
    ├── certificados.js
    ├── eventos.js
    ├── inscricoes.js
    ├── login.js
    ├── palestrantes.js
    └── usuarios.js

    ---

### Raíz do Projeto

  
    ├── .env
    ├── app.js
    ├── composer.json
    ├── composer.lock
    ├── index.php
    ├── knexfile.js
    ├── package-lock.json
    ├── package.json
    ├── README.md
    └── vendor/

    ---


Bibliotecas utilizadas neste programa:

Backend (Node.js / PHP)

- EXPRESS: Usada para criar APIs e servidores web, eke facilita o roteamento e o tratamento das requisições HTTP.
- KNEX: Ajuda a escrever comandos SQL usando JavaScript, permite que você interaja com bancos de dados relacionais de forma flexível e portátil
- CURL: Usada em PHP para fazer requisições HTTP para APIs externas.
- CORS Controla quais dominios podem acessar sua API.
- DOTENV: Gerencia variaveis de ambiente em arquivos .env.
- JWT: Cria tokens que representam usuarios logdos. Pode ser lido e verificado no backend sem armazenar sessão no servidor.
- BCRYPT: Usado para criptografar senhas.


Frontend (PHP/HTML + JS)

- TOASTR: Exibir notificações.
- ENDOCODEURICOMPONENT (ou encodeURL): Funçao do JS usada para codificar parametros de URL, tornando seguro enviar Strings com espaços, acentos, etc.

PHP

- COMPOSER: Gerenciador de pacotes do PHP.
- TCPDF: Biblioteca PHP para gerar arquivos PDF.

HTML/CSS 

- BOOTSTRAP: Responsividade, estilização.
- VLIBRAS: Acessibilidade.
