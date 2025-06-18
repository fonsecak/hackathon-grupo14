<?php
session_start();
$user = $_SESSION['user'] ?? null;
if (!$user) {
    header('Location: index.php');
    exit;
}
$id_participante = $user['id'];
$token = $user['token'] ?? null;

// Buscar inscrições do usuário
$inscricoesJson = @file_get_contents("http://localhost:3000/api/inscricoes/participante/$id_participante");
$inscricoes = json_decode($inscricoesJson, true);

// Buscar detalhes dos eventos
$eventos = [];
if (is_array($inscricoes)) {
    foreach ($inscricoes as $inscricao) {
        $eventoJson = @file_get_contents("http://localhost:3000/api/eventos/" . $inscricao['id_evento']);
        $evento = json_decode($eventoJson, true);
        if ($evento) {
            $eventos[] = $evento;
        }
    }
}
?>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Meus Eventos</title>
    <link rel="stylesheet" href="../css/styles.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
</head>
<body class="meus-eventos-bg">
<?php include 'navbar.php'; ?>
<main class="flex-grow-1">
    <div class="container py-5">
        <div class="row justify-content-center">
            <div class="col-12 col-lg-10">
                <div class="card meus-eventos-card shadow-lg border-0 rounded-4" style="max-width: 1200px; margin: 0 auto;">
                    <div class="card-body p-5">
                        <h2 class="mb-4 text-center section-title">Meus Eventos Inscritos</h2>
                        <?php if (empty($eventos)): ?>
                            <div class="alert alert-info text-center">Você ainda não está inscrito em nenhum evento.</div>
                        <?php else: ?>
                        <div class="table-responsive">
                            <table class="table meus-eventos-table align-middle mb-0">
                                <thead>
                                    <tr>
                                        <th>Evento</th>
                                        <th>Data</th>
                                        <th>Local</th>
                                        <th>Status</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <?php foreach ($eventos as $evento): ?>
                                        <tr>
                                            <td class="fw-bold text-primary" style="min-width:180px;">
                                                <a href="eventodetalhe.php?id=<?= $evento['id'] ?>" class="text-decoration-none" style="color: #022840;"><?= htmlspecialchars($evento['nome']) ?></a> 
                                            </td>
                                            <td><?= date('d/m/Y', strtotime($evento['data_hora_inicio'])) ?></td>
                                            <td><?= htmlspecialchars($evento['local']) ?></td>
                                            <td><span class="badge bg-success">Inscrito</span></td>
                                        </tr>
                                    <?php endforeach; ?>
                                </tbody>
                            </table>
                        </div>
                        <?php endif; ?>
                        <div class="text-center mt-4">
                            <a href="index.php" class="btn btn-lg btn-primary px-5">
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
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html> 