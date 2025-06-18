<?php
$eventosJson = file_get_contents("http://localhost:3000/api/eventos");
$eventos = json_decode($eventosJson, true);

if (session_status() === PHP_SESSION_NONE) {
    session_start();
}
$erro = '';

if ($_SERVER['REQUEST_METHOD'] === 'POST') {
  $email = $_POST['email'] ?? '';
  $senha = $_POST['senha'] ?? '';

  $data = array("email" => $email, "senha" => $senha);
  $json_data = json_encode($data);

  $ch = curl_init('http://localhost:3000/api/login');
  curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
  curl_setopt($ch, CURLOPT_HTTPHEADER, array('Content-Type: application/json'));
  curl_setopt($ch, CURLOPT_POST, true);
  curl_setopt($ch, CURLOPT_POSTFIELDS, $json_data);

  $response = curl_exec($ch);
  $httpCode = curl_getinfo($ch, CURLINFO_HTTP_CODE);
  curl_close($ch);

  $resData = json_decode($response, true);

  if ($httpCode === 200 && isset($resData['success']) && $resData['success']) {
    $userData = $resData['user'];
    $userData['token'] = $resData['token'] ?? null; // Adiciona o token ao array do usuário
    $_SESSION['user'] = $userData;
    header('Location: index.php'); // Redireciona diretamente para certificados
    exit;
  } else {
    $erro = $resData['message'] ?? 'Erro ao fazer login. Verifique suas credenciais.';
  }
}
?>


<!DOCTYPE html>
<html lang="pt-br">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="../css/styles.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Font Awesome -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <!-- Bootstrap Icons CDN -->
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/toastr.min.css">
    <title>AlfaExperience</title>
</head>

<body>    

    <?php include 'navbar.php'; ?>

    <?php include 'eventos.php'; ?>

    <?php include 'footer.php'; ?>

    
<div vw class="enabled">
  <div vw-access-button class="active"></div>
  <div vw-plugin-wrapper>
    <div class="vw-plugin-top-wrapper"></div>
  </div>
</div>

<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/toastr.min.js"></script>
<script src="https://vlibras.gov.br/app/vlibras-plugin.js"></script>
<script>
  new window.VLibras.Widget('https://vlibras.gov.br/app');
</script>

<script>
  // Configurações do Toastr
  toastr.options = {
    "closeButton": true,
    "progressBar": true,
    "positionClass": "toast-top-right",
    "timeOut": "3000"
  };

  <?php if (!empty($erro)): ?>
  $(document).ready(function() {
    toastr.error('<?= htmlspecialchars($erro) ?>');
    const myModal = new bootstrap.Modal(document.getElementById('loginModal'));
    myModal.show();
  });
  <?php endif; ?>
</script>

</body>

</html>