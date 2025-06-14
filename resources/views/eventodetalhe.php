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
                    <img src="" alt="Palestrante" class="foto-perfil">
                </div>
                
                <div class="palestrante-info">
                    <h2>Palestrante Teste</h2>
                    <p class="cargo">CEO & Fundador da TechInnovate</p>
                    
                    <div class="sobre-palestrante">
                        <h3>Sobre o Palestrante</h3>
                        <p>Descrição breve.</p>
                        
                    </div>
                </div>
            </div>

            <div class="evento-detalhes">
                
                <div class="evento-info">
                    <div class="info-item">
                        <strong>Data:</strong> 25 de Julho de 2025
                    </div>
                    <div class="info-item">
                        <strong>Horário:</strong> 19:00 às 21:00
                    </div>
                    <div class="info-item">
                        <strong>Local:</strong> Centro de Convenções São Paulo - Auditório Principal
                    </div>
                 
                </div>

               
            </div>

            <div class="inscricao-section">
                <div class="preco-info">
                    <span class="preco-original">R$ 150,00</span>
                    <span class="preco-promocional">R$ 99,00</span>
                    <span class="desconto">34% OFF - Promoção por tempo limitado!</span>
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