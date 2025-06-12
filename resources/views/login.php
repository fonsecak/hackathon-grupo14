<?php
if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    $email = $_POST['email'];
    $senha = $_POST['senha'];

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

    if ($httpCode === 200 && $resData['success']) {
        session_start();
        $_SESSION['user'] = $resData['user'];
        header('Location: index.php');
        exit;
    } else {
        $erro = $resData['message'] ?? 'Erro desconhecido';
    }
}
?>