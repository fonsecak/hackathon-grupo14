<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Meus Certificados</title>
    <link rel="stylesheet" href="../css/styles.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
</head>
<body>
<?php 
include 'navbar.php'; 
$usuario_id = isset($_SESSION['usuario_id']) ? $_SESSION['usuario_id'] : null;
?>
<main class="flex-grow-1">
    <div class="container my-5">
        <div class="row justify-content-center">
            <div class="col-12 col-md-8 col-lg-6">
                <div class="card shadow border-0">
                    <div class="card-body p-4">
                        <h2 class="mb-4 text-center">Meus Certificados</h2>
                        <p class="text-center mb-4">
                            Aqui você pode visualizar e baixar seus certificados de participação em eventos.
                        </p>
                        <?php if (!$usuario_id): ?>
                            <div class="alert alert-warning text-center">
                                Você precisa estar logado para visualizar e baixar seus certificados.
                            </div>
                        <?php else: ?>
                            <div class="table-responsive">
                                <table class="table table-striped align-middle">
                                    <thead>
                                        <tr>
                                            <th>Evento</th>
                                            <th>Data</th>
                                            <th>Certificado</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <?php
                                        // Exemplo de requisição para buscar certificados do usuário logado via Node.js
                                        $node_url = 'http://localhost:3000/api/certificado/certificado?usuario_id=' . urlencode($usuario_id);
                                        $response = @file_get_contents($node_url);
                                        if ($response === false) {
                                            echo '<tr><td colspan="3" class="text-center text-danger">Nenhum certificado disponível ou erro ao buscar certificados.</td></tr>';
                                        } else {
                                            // Supondo que o endpoint retorna um array JSON de certificados
                                            $certificados = json_decode($response, true);
                                            if (is_array($certificados) && count($certificados) > 0) {
                                                foreach ($certificados as $cert) {
                                                    echo '<tr>
                                                        <td>' . htmlspecialchars($cert['evento']) . '</td>
                                                        <td>' . htmlspecialchars($cert['data']) . '</td>
                                                        <td>
                                                            <a href="baixar_certificado.php?id=' . urlencode($cert['id']) . '" class="btn btn-primary btn-sm" target="_blank" aria-label="Baixar certificado">
                                                                <i class="fa fa-download"></i> Baixar
                                                            </a>
                                                        </td>
                                                    </tr>';
                                                }
                                            } else {
                                                echo '<tr><td colspan="3" class="text-center">Nenhum certificado disponível.</td></tr>';
                                            }
                                        }
                                        ?>
                                    </tbody>
                                </table>
                            </div>
                        <?php endif; ?>
                        <div class="text-center mt-4">
                            <a href="index.php" class="btn btn-secondary">
                                <i class="fa fa-arrow-left"></i> Voltar para Início
                            </a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</main>
<?php include 'footer.php'; ?>
</body>
</html>