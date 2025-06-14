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

  $ch = curl_init('http://localhost:3000/login');
  curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
  curl_setopt($ch, CURLOPT_HTTPHEADER, array('Content-Type: application/json'));
  curl_setopt($ch, CURLOPT_POST, true);
  curl_setopt($ch, CURLOPT_POSTFIELDS, $json_data);

  $response = curl_exec($ch);
  $httpCode = curl_getinfo($ch, CURLINFO_HTTP_CODE);
  curl_close($ch);

  $resData = json_decode($response, true);

  if ($httpCode === 200 && isset($resData['success']) && $resData['success']) {
    $_SESSION['user'] = $resData['user'];
    header('Location: index.php'); // redireciona para "limpar" o POST
    exit;
  } else {
    $erro = $resData['message'] ?? 'Erro desconhecido';
  }
}
?>


<!DOCTYPE html>
<html lang="pt-br">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="../css/styles.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <!-- Font Awesome -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <!-- Bootstrap Icons CDN -->
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
    <title>AlfaExperience</title>
</head>

<body>
    

    <?php include 'navbar.php'; ?>

    <?php include 'eventos.php'; ?>

    <?php include 'footer.php'; ?>

    <?php if (!empty($erro)): ?>
<script>
  const myModal = new bootstrap.Modal(document.getElementById('loginModal'));
  myModal.show();
</script>
<?php endif; ?>
<?php include 'login-modal.php'; ?>

    
</body>

</html>