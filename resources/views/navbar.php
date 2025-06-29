<?php
if (session_status() === PHP_SESSION_NONE) {
    session_start();
}
?>
<body>
    <nav class="navbar navbar-expand-lg navbar-dark">
        <div class="container">
            <a class="navbar-brand" href="index.php">
                <img src="../img/logo.png" alt="Alfa Experience Logo" class="logo-img" width="60" height="60">
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
                        <a class="nav-link active" href="certificado.php"><i class="fas fa-award me-1"></i> Certificado</a>
                    </li>
                </ul>
                

                <?php if (!isset($_SESSION['user'])): ?>
                    <!-- Botão de Login -->
                    <button type="button" class="btn btn-primary ms-3" data-bs-toggle="modal" data-bs-target="#loginModal">
                        <i class="fas fa-sign-in-alt me-2"></i>Login
                    </button>
                    <!-- Link para Cadastro -->
                    <ul class="navbar-nav me-0 mb-2 mb-lg-0">
                        <li class="nav-item ms-3">
                            <a class="nav-link active" href="cadastro.php"><i class="fas fa-user-plus me-1"></i> Cadastrar</a>
                        </li>
                    </ul>
                <?php else: ?>
                    <!-- Se desejar, pode exibir algo como: -->
                    <ul class="navbar-nav ms-3">
                        <li class="nav-item">
                            <span class="navbar-text text-light">
                                Olá, <?= htmlspecialchars($_SESSION['user']['nome'] ?? 'User') ?>!
                            </span>
                        </li>
                        <li class="nav-item ms-3">
                            <a class="btn btn-outline-light" href="meus_eventos.php"><i class="fas fa-calendar-check me-1"></i> Meus Eventos</a>
                        </li>
                        <li class="nav-item ms-3">
                            <a class="btn btn-outline-light" href="logout.php"><i class="fas fa-sign-out-alt me-1"></i> Sair</a>
                        </li>
                    </ul>
                <?php endif; ?>
            </div>
        </div>
    </nav>

    <?php include 'login-modal.php'; ?>
</body>
</html>
