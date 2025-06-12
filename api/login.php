
<?php
header('Content-Type: application/json');
$data = json_decode(file_get_contents('php://input'), true);

$email = $data['email'] ?? '';
$senha = $data['senha'] ?? '';

// Exemplo de validação (substitua pelo seu método real)
if ($email === 'admin@teste.com' && $senha === '123456') {
    echo json_encode(['success' => true]);
} else {
    echo json_encode(['success' => false, 'message' => 'E-mail ou senha incorretos.']);
}