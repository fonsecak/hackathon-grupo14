<?php
// Busca todos os eventos
$allJson    = file_get_contents("http://localhost:3000/api/eventos");
$eventos    = json_decode($allJson, true);

// Busca apenas os destaques (3 aleatórios)
$destaquesJson = file_get_contents("http://localhost:3000/api/eventos/destaques");
$destaques     = json_decode($destaquesJson, true);
?>

<body class="bg-light">

  <!-- Seção: Eventos em destaque -->
  <div class="container py-5">
    <h2 class="mb-4 text-left">Eventos em destaque</h2>
    <div><br></div>
      <div class="row justify-content-center">
          <?php foreach ($destaques as $evento): ?>
          <div class="col-md-4 col-lg-4">
            <div class="card event-card shadow-sm">
              <img src="../img/<?php echo htmlspecialchars($evento['banner']); ?>" class="card-img-top"  alt="Imagem do evento">
              <div class="card-body">
                <h5 class="card-title"><?php echo htmlspecialchars($evento['nome']); ?></h5>
                <p class="card-text text-muted">
                <?php
                echo date('d \d\e F, Y - H:i', strtotime($evento['data_hora_inicio']));
                ?>
                </p>
                <p class="card-text"><i class="bi bi-geo-alt-fill"></i> <?php echo htmlspecialchars($evento['local']); ?></p>
                <p class="price">
                <?php echo ($evento['valor_inscricao'] == 0) ? 'Gratuito' : 'R$ ' . number_format($evento['valor_inscricao'], 2, ',', '.'); ?>
                </p>
                <a href="#" class="btn w-100">Inscreva-se</a>
              </div>
            </div>
          </div>
                <?php endforeach; ?>
      </div>
  </div>

  

  <!-- Seção: Categorias dos cursos -->
   
  <div class="container py-5">
  <div class="d-flex justify-content-between align-items-center mb-4">
    <h2 class="mb-0">Explore nossos cursos.</h2>
  </div>

   <div><br><br><br></div>

  <div class="row g-3">
    <div class="col-6 col-sm-4 col-md-3 col-lg-2">
      <button class="btn btn-light w-100 categoria-btn">
        <i class="bi bi-briefcase fs-4 mb-1"></i>
        <div class="small">Administração</div>
      </button>
    </div>
    <div class="col-6 col-sm-4 col-md-3 col-lg-2">
      <button class="btn btn-light w-100 categoria-btn">
        <i class="bi bi-calculator fs-4 mb-1"></i>
        <div class="small">Ciências Contábeis</div>
      </button>
    </div>
    <div class="col-6 col-sm-4 col-md-3 col-lg-2">
      <button class="btn btn-light w-100 categoria-btn">
        <i class="bi bi-bank fs-4 mb-1"></i>
        <div class="small">Direito</div>
      </button>
    </div>
    <div class="col-6 col-sm-4 col-md-3 col-lg-2">
      <button class="btn btn-light w-100 categoria-btn">
        <i class="bi bi-code-slash fs-4 mb-1"></i>
        <div class="small">Sistemas p/ Internet</div>
      </button>
    </div>
    <div class="col-6 col-sm-4 col-md-3 col-lg-2">
      <button class="btn btn-light w-100 categoria-btn">
        <i class="bi bi-book fs-4 mb-1"></i>
        <div class="small">Pedagogia</div>
      </button>
    </div>
    <div class="col-6 col-sm-4 col-md-3 col-lg-2">
      <button class="btn btn-light w-100 categoria-btn">
        <i class="bi bi-heart-pulse fs-4 mb-1"></i>
        <div class="small">Psicologia</div>
      </button>
    </div>
  </div>
</div>



    <div><br><br><br><br></div>

    <!-- DIVISOR -->

    <div class="container">
  <div class="row justify-content-center">
    <?php foreach ($eventos as $evento): ?>
      <div class="col-6 col-md-3 mb-4">
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
            <a href="#" class="btn w-100">Inscreva-se</a>
          </div>
        </div>
      </div>
    <?php endforeach; ?>
  </div>
</div>
    </div>

  <!-- Bootstrap JS opcional -->
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>