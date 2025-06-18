<?php
session_start();
require_once(__DIR__ . '/../../vendor/autoload.php');

if (!class_exists('TCPDF')) {
    die('A biblioteca TCPDF não está instalada corretamente. Por favor, execute "composer install" na raiz do projeto.');
}

$user = $_SESSION['user'] ?? null;
$token = $user['token'] ?? null;

if (!isset($_GET['id'])) {
    die('ID do certificado não fornecido');
}

$certificadoId = $_GET['id'];

if ($token) {
    $url = "http://localhost:3000/api/certificados/" . urlencode($certificadoId);
    $ch = curl_init($url);
    curl_setopt($ch, CURLOPT_HTTPHEADER, [
        "Authorization: Bearer $token"
    ]);
    curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
    $response = curl_exec($ch);
    $httpcode = curl_getinfo($ch, CURLINFO_HTTP_CODE);
    curl_close($ch);

    if ($httpcode === 200) {
        $certificado = json_decode($response, true);
        
        // Criar novo documento PDF
        $pdf = new TCPDF('L', 'mm', 'A4', true, 'UTF-8', false);

        // Configurar documento
        $pdf->SetCreator('ALFA EXPERIENCE');
        $pdf->SetAuthor('ALFA EXPERIENCE');
        $pdf->SetTitle('Certificado - ' . $certificado['nome'] . ' ' . $certificado['sobrenome']);

        // Remover cabeçalho e rodapé padrão
        $pdf->setPrintHeader(false);
        $pdf->setPrintFooter(false);

        // Configurações de margem
        $pdf->SetMargins(15, 15, 15);
        $pdf->SetAutoPageBreak(TRUE, 15);

        // Adiciona uma página
        $pdf->AddPage();

        // Define o conteúdo do certificado
        $css = file_get_contents(__DIR__ . '/../css/styles.css');
        $html = '<style>' . $css . '</style>';
        $html .= '
        <div class="certificado">
            <div class="certificado-inner">
                <div class="watermark">ALFA EXPERIENCE</div>
                <div class="logo">
                    <img src="' . __DIR__ . '/../../resources/img/logo.png" width="140">
                </div>
                <div class="titulo">Certificado de Participação</div>
                <div class="conteudo">
                    Certificamos que<br>
                    <strong>' . $certificado['nome'] . ' ' . $certificado['sobrenome'] . '</strong>
                    participou do evento<br>
                    <strong>' . $certificado['evento'] . '</strong>
                    realizado em<br>
                    <strong>' . date('d/m/Y', strtotime($certificado['data'])) . '</strong>
                </div>
                <div class="assinatura">
                    <p>Este certificado é válido e autenticado</p>
                </div>
            </div>
        </div>';

        // Gera o PDF
        $pdf->writeHTML($html, true, false, true, false, '');
        $pdf->Output('certificado.pdf', 'D');
    } else {
        die('Erro ao carregar o certificado');
    }
} else {
    die('Você precisa estar logado para baixar o certificado');
} 