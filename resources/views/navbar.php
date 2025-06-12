<body>
    <nav class="navbar navbar-expand-lg navbar-dark">
        <div class="container">
            <a class="navbar-brand" href="index.php">
                <img src="../img/logo.png" alt="Alfa Experience Logo" class="logo-img" width="70" height="60">
            </a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse"
                data-bs-target="#navbarSupportedContent">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarSupportedContent">
                <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                    <li class="nav-item">
                        <a class="nav-link active" href="index.php"><i class="fas fa-home me-1"></i> Início</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link active" href="certificado.php"><i class="fas fa-certificate me-1"></i> Certificado</a>
                    </li>
                </ul>
                <form class="d-flex search-form">
                    <div class="input-group">
                        <input type="text" class="form-control search-input" placeholder="Pesquisar eventos...">
                        <button class="btn btn-outline-light" type="submit"><i class="fas fa-search"></i></button>
                    </div>
                </form>
                <button type="button" class="btn btn-primary ms-3" data-bs-toggle="modal" data-bs-target="#loginModal">
                    <i class="fas fa-sign-in-alt me-2"></i>Login</button>
                <ul class="navbar-nav me-0 mb-2 mb-lg-0">
                    <li class="nav-item ms-3">
                        <a class="nav-link active" href="cadastro.php"><i class="fas fa-certificate me-1"></i> Cadastrar</a>
                    </li>
                </ul>
            </div>
        </div>
    </nav>

    <?php include 'login-modal.php'; ?>
    
</body>
</html> <!-- Aqui está a tag de fechamento -->
