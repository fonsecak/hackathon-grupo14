<?php
if (session_status() === PHP_SESSION_NONE) {
    session_start();
}
if (!isset($_GET['id'])) {
    die("Evento não especificado.");
}

// Debug: ver o conteúdo do usuário logado
// var_dump($_SESSION['user']); // Removido após debug

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

$id_participante = $_SESSION['user']['id'] ?? null;
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
<body >
    <?php include 'navbar.php'; ?> 
    <div class="evento-detalhe">  
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
                
                <button class="btn-inscricao" id="btnInscrever" style="display:none">
                    Inscrever-se Agora
                </button>
                <button class="btn-inscricao" id="btnCancelarInscricao" style="display:none">
                    Cancelar inscrição
                </button>
                
                <p class="garantia">✓ Certificado de participação incluso</p>
                
            </div>
        </div>
    </div>
</div> 
    <?php include 'footer.php'; ?>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/toastr.min.js"></script>
    <script>
        $(document).ready(function() {
            var id_participante = <?php echo json_encode($id_participante); ?>;
            var id_evento = <?php echo json_encode($id); ?>;
            var token = <?php echo json_encode($_SESSION['user']['token'] ?? null); ?>;

            function atualizarBotoesInscricao() {
                if (!id_participante) {
                    $('#btnInscrever').show();
                    $('#btnCancelarInscricao').hide();
                    return;
                }
                $.get('http://localhost:3000/api/inscricoes/verificar', {
                    id_participante: id_participante,
                    id_evento: id_evento
                }, function(res) {
                    if (res.inscrito) {
                        $('#btnInscrever').hide();
                        $('#btnCancelarInscricao').show();
                    } else {
                        $('#btnInscrever').show();
                        $('#btnCancelarInscricao').hide();
                    }
                });
            }

            atualizarBotoesInscricao();

            $('#btnInscrever').on('click', function(e) {
                e.preventDefault();
                <?php if (!isset($_SESSION['user'])): ?>
                    alert('Você precisa estar logado para se inscrever!');
                <?php else: ?>
                    var modal = new bootstrap.Modal(document.getElementById('modalInscricao'));
                    modal.show();
                <?php endif; ?>
            });

            // Intercepta o submit do formulário de inscrição
            $('#formInscricao').on('submit', function(e) {
                e.preventDefault();
                // Pega os dados do formulário
                var cpf = $('#cpf').val();
                var email = $('#email').val();
                if (!id_participante) {
                    alert('Usuário não identificado. Faça login novamente.');
                    return;
                }
                // Envia via AJAX para o backend
                $.ajax({
                    url: 'http://localhost:3000/api/inscricoes',
                    method: 'POST',
                    contentType: 'application/json',
                    headers: token ? { 'Authorization': 'Bearer ' + token } : {},
                    data: JSON.stringify({ id_participante: id_participante, id_evento: id_evento }),
                    success: function(res) {
                        $('#modalInscricao').modal('hide');
                        alert('Inscrição realizada com sucesso!');
                        atualizarBotoesInscricao();
                    },
                    error: function(xhr) {
                        if (xhr.status === 409) {
                            alert('Você já está inscrito neste evento!');
                        } else {
                            alert('Erro ao realizar inscrição. Tente novamente.');
                        }
                    }
                });
            });

            // Cancelar inscrição
            $('#btnCancelarInscricao').on('click', function(e) {
                e.preventDefault();
                if (confirm('Tem certeza que deseja cancelar sua inscrição?')) {
                    $.ajax({
                        url: 'http://localhost:3000/api/inscricoes',
                        method: 'DELETE',
                        contentType: 'application/json',
                        headers: token ? { 'Authorization': 'Bearer ' + token } : {},
                        data: JSON.stringify({ id_participante: id_participante, id_evento: id_evento }),
                        success: function(res) {
                            alert('Inscrição cancelada com sucesso!');
                            atualizarBotoesInscricao();
                        },
                        error: function() {
                            alert('Erro ao cancelar inscrição. Tente novamente.');
                        }
                    });
                }
            });
        });
    </script>

    <!-- Modal de Inscrição -->
    <div class="modal fade" id="modalInscricao" tabindex="-1" aria-labelledby="modalInscricaoLabel" aria-hidden="true">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title" id="modalInscricaoLabel">Inscrição no Evento</h5>
            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Fechar"></button>
          </div>
          <div class="modal-body">
            <form id="formInscricao">
              <div class="mb-3">
                <label for="cpf" class="form-label">CPF</label>
                <input type="text" class="form-control" id="cpf" name="cpf" maxlength="11" required placeholder="Digite seu CPF">
              </div>
              <div class="mb-3">
                <label for="email" class="form-label">Email</label>
                <input type="email" class="form-control" id="email" name="email" required placeholder="Digite seu email">
              </div>
            </form>
          </div>
          <div class="modal-footer p-0 border-0 bg-white">
            <div class="container-fluid px-4 pb-3 d-flex flex-row gap-2 justify-content-center">
              <button type="button" class="btn btn-secondary flex-fill" data-bs-dismiss="modal">Cancelar</button>
              <button type="submit" class="btn btn-primary flex-fill" form="formInscricao">Confirmar</button>
            </div>
          </div>
        </div>
      </div>
    </div>
</body>
    </html>