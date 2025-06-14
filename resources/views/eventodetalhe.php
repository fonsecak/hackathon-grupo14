<?php
if (!isset($_GET['id'])) {
    die("Evento não especificado.");
}

$id = intval($_GET['id']);

// Busca o evento pelo ID
$eventoJson = file_get_contents("http://localhost:3000/api/eventos/$id");
$evento = json_decode($eventoJson, true);

// Verifica se o evento foi encontrado
if (!$evento) {
    die("Evento não encontrado.");
}

// Busca dados do palestrante
$idPalestrante = $evento['id_palestrantes'];
$palestranteJson = file_get_contents("http://localhost:3000/api/palestrantes/$idPalestrante");
$palestrante = json_decode($palestranteJson, true);
?>



<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Detalhes do Evento</title>
    <link rel="stylesheet" href="../css/styles.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <!-- Font Awesome -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/toastr.min.css">
    
</head>
<body class="evento-detalhe">

    <?php include 'navbar.php'; ?>
    <div class="container">
      

     <div class="evento-content">
            <div class="palestrante-section">
                <div class="palestrante-foto">
                    <img src="../img/<?= htmlspecialchars($palestrante['foto']) ?>" alt="Foto do palestrante" class="foto-perfil">
                </div>
                
                <div class="palestrante-info">
                    <h2><?= htmlspecialchars($palestrante['nome']) ?></h2>
                    <p class="cargo">Palestrante do evento</p>
                    
                    <div class="sobre-palestrante">
                        <h3>Sobre o Palestrante</h3>
                        <p><?= htmlspecialchars($palestrante['sobre']) ?></p>                        
                    </div>
                </div>
                <div class="evento-foto">
                    <img src="../img/<?= htmlspecialchars($evento['banner']) ?>" alt="Banner Evento" class="banner-evento" eight="200" width="300">
                </div>
            </div>

            <div class="evento-detalhes">                
                <div class="evento-info">
                    <h1><?= htmlspecialchars($evento['nome']) ?></h1>
                    <p class="descricao"><?= htmlspecialchars($evento['descricao']) ?></p>
                    <div class="info-item">
                        <strong>Objetivo:</strong> <?= htmlspecialchars($evento['objetivo']) ?>
                    </div>
                    <div class="info-item">
                        <strong>Público Alvo:</strong> <?= htmlspecialchars($evento['publico_alvo']) ?>
                    </div>
                    <div class="info-item">
                        <strong>Data:</strong> <?= date('d/m/Y', strtotime($evento['data_hora_inicio'])) ?>
                    </div>
                    <div class="info-item">
                        <strong>Horário:</strong> <?= date('H:i', strtotime($evento['data_hora_inicio'])) ?> às <?= date('H:i', strtotime($evento['data_hora_fim'])) ?>
                    </div>
                    <div class="info-item">
                        <strong>Local:</strong> <?= htmlspecialchars($evento['local']) ?>
                    </div>
                 
                </div>

               
            </div>

            <div class="inscricao-section">
                <div class="preco-info">                    
                    <span class="preco-promocional">R$: <?= htmlspecialchars($evento['valor_inscricao']) ?></span>
                    <span class="desconto">Vagas limitadas! <?= htmlspecialchars($evento['vagas_maxima']) ?> vagas</span>
                </div>
                
                <button class="btn-inscricao" >
                    Inscrever-se Agora
                </button>
                
                <p class="garantia">✓ Certificado de participação incluso</p>
                
            </div>
        </div>
    </div>

    <?php include 'footer.php'; ?>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</body>
    </html>