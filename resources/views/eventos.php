<?php
// Busca todos os eventos
$allJson = file_get_contents("http://localhost:3000/api/eventos");
$eventos = json_decode($allJson, true);

// Busca apenas os destaques
$destaquesJson = file_get_contents("http://localhost:3000/api/eventos/destaques");
$destaques = json_decode($destaquesJson, true);

// Filtro via GET
$filtro = isset($_GET['filtro']) ? $_GET['filtro'] : null;

// Aplica o filtro se houver
if ($filtro) {
  $eventos = array_filter($eventos, function ($evento) use ($filtro) {
    return isset($evento['publico_alvo']) && $evento['publico_alvo'] === $filtro;
  });
}


?>
<body class="bg-light">

  <!-- Seção: Eventos em destaque -->
  <div class="container py-5">
    <h2 class="mb-4 section-title">Eventos em destaque.</h2>
    <div><br></div>
    <div class="row justify-content-center">
      <?php foreach ($destaques as $evento): ?>
        <div class="col-md-4 col-lg-4">
          <div class="card event-card shadow-sm">
            <img src="../img/<?php echo htmlspecialchars($evento['banner']); ?>" class="card-img-top" alt="Imagem do evento">
            <div class="card-body">
              <h5 class="card-title"><?php echo htmlspecialchars($evento['nome']); ?></h5>
              <p class="card-text text-muted">
                <?= date('d \d\e F, Y - H:i', strtotime($evento['data_hora_inicio'])) ?>
              </p>
              <p class="card-text"><i class="bi bi-geo-alt-fill"></i> <?= htmlspecialchars($evento['local']) ?></p>
              <p class="price">
                <?= ($evento['valor_inscricao'] == 0) ? 'Gratuito' : 'R$ ' . number_format($evento['valor_inscricao'], 2, ',', '.') ?>
              </p>
              <a href="eventodetalhe.php?id=<?= $evento['id'] ?>" class="btn w-100">Detalhes</a>
            </div>
          </div>
        </div>
      <?php endforeach; ?>
    </div>
  </div>

  <!-- Seção: Categorias dos cursos -->
  <div class="container py-5">
    <h2 class="mb-0 section-title">Explore nossos cursos.</h2>
    <div class="row g-3">
  <?php
  function renderBotaoCategoria($nome, $icone, $filtroAtual) {
    $ativo = ($filtroAtual === $nome) ? 'btn-primary text-white' : 'btn-light';
    $url = '?filtro=' . urlencode($nome);

    echo <<<HTML
    <div class="col-6 col-sm-4 col-md-3 col-lg-2">
      <a href="$url" class="btn $ativo w-100 categoria-btn">
        <i class="bi $icone fs-4 mb-1"></i>
        <div class="small">$nome</div>
      </a>
    </div>
    HTML;
  }

  // Botão "Todos"
  $classeTodos = $filtro ? 'btn-light' : 'btn-primary text-white';
  echo <<<HTML
  <div class="col-6 col-sm-4 col-md-3 col-lg-2">
    <a href="index.php" class="btn $classeTodos w-100 categoria-btn">
      <i class="bi bi-grid fs-4 mb-1"></i>
      <div class="small">Todos</div>
    </a>
  </div>
  HTML;

  // Demais categorias
  renderBotaoCategoria("Administração", "bi-briefcase", $filtro);
  renderBotaoCategoria("Ciências Contábeis", "bi-calculator", $filtro);
  renderBotaoCategoria("Direito", "bi-bank", $filtro);
  renderBotaoCategoria("Sistemas p/ Internet", "bi-code-slash", $filtro);
  renderBotaoCategoria("Pedagogia", "bi-book", $filtro);  
  ?>
</div>
  </div>

  <!-- Seção: Todos os eventos (ou filtrados) -->
  <div class="container py-5">
    <h2 class="mb-4 section-title">
      <?php if ($filtro): ?>
        Eventos para: <?= htmlspecialchars($filtro) ?>
      <?php else: ?>
        Todos os eventos
      <?php endif; ?>
    </h2>

    <div class="row justify-content-center">
      <?php if (empty($eventos)): ?>
        <p class="text-muted text-center">Nenhum evento encontrado para essa categoria.</p>
      <?php else: ?>
        <?php foreach ($eventos as $evento): ?>
          <div class="col-12 col-md-3 mb-4">
            <div class="card event-card shadow-sm">
              <img src="../img/<?php echo htmlspecialchars($evento['banner']); ?>" class="card-img-top" alt="Imagem do evento">
              <div class="card-body">
                <h5 class="card-title"><?= htmlspecialchars($evento['nome']) ?></h5>
                <p class="card-text text-muted">
                  <?= date('d \d\e F, Y - H:i', strtotime($evento['data_hora_inicio'])) ?>
                </p>
                <p class="card-text">
                  <i class="bi bi-geo-alt-fill"></i> <?= htmlspecialchars($evento['local']) ?>
                </p>
                <p class="price">
                  <?= $evento['valor_inscricao'] == 0 ? 'Gratuito' : 'R$ ' . number_format($evento['valor_inscricao'], 2, ',', '.') ?>
                </p>
                <a href="eventodetalhe.php?id=<?= $evento['id'] ?>" class="btn w-100">Detalhes</a>
              </div>
            </div>
          </div>
        <?php endforeach; ?>
      <?php endif; ?>
    </div>
  </div>

</body>

