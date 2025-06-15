<?php
session_start();
$user = $_SESSION['user'] ?? null;
$token = $user['token'] ?? null;
$certificados = [];
$errorMessage = '';

// Faz a requisição com o token JWT
if ($token) {    
$url = "http://localhost:3000/api/certificados";
$ch = curl_init($url);
curl_setopt($ch, CURLOPT_HTTPHEADER, [
    "Authorization: Bearer $token"
]);
curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
$response = curl_exec($ch);
$httpcode = curl_getinfo($ch, CURLINFO_HTTP_CODE);
$error = curl_error($ch); // Captura erro cURL, se houver
curl_close($ch);

if ($httpcode !== 200) {
        $errorMessage = "Erro ao buscar certificados. Tente novamente mais tarde. (HTTP: $httpcode, Error: $error, Response: " . htmlspecialchars($response) . ")";
    } else {
        $certificados = json_decode($response, true) ?? [];
    }
 } else {
    $errorMessage = "Você precisa estar logado para visualizar e baixar seus certificados.";
}
?>

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
<?php include 'navbar.php'; ?>

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
                        <?php if ($errorMessage): ?>
                            <div class="alert alert-warning"><?php echo htmlspecialchars($errorMessage); ?></div>
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
                                    if (is_array($certificados) && count($certificados) > 0) {
                                        foreach ($certificados as $cert) {
                                            echo '<tr>
                                                <td>' . htmlspecialchars($cert['evento']) . '</td>
                                                <td>' . date('d/m/Y', strtotime($cert['data'])) . '</td>
                                                <td>
                                                    <a href="baixar_certificados.php?id=' . urlencode($cert['id']) . '" class="btn btn-primary btn-sm" target="_blank" aria-label="Baixar certificado">
                                                        <i class="fa fa-download"></i> Baixar
                                                    </a>
                                                </td>
                                            </tr>';
                                        }
                                    } else {
                                        echo '<tr><td colspan="3" class="text-center">Nenhum certificado disponível. Conclua um evento para receber um certificado.</td></tr>';
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