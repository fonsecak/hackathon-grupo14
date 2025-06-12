<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Meus Certificados</title>
    <link rel="stylesheet" href="../css/styles.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
</head>
<body>
<?php include 'navbar.php'; ?>
<main class="flex-grow-1">
    <div class="container my-5">
        <div class="row justify-content-center">
            <div class="col-12 col-md-8 col-lg-6">
                <div class="card shadow border-0">
                    <div class="card-body p-4">
                        <h2 class="mb-4 text-center">Meus Certificados</h2>
                        <p class="text-center mb-4">
                            Aqui você pode visualizar e baixar seus certificados de participação em eventos.
                        </p>
                        <div class="table-responsive">
                            <table class="table table-striped align-middle">
                                <thead>
                                    <tr>
                                        <th>Evento</th>
                                        <th>Data</th>
                                        <th>Certificado</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <tr>
                                        <td>Semana Acadêmica de Tecnologia</td>
                                        <td>10/05/2025</td>
                                        <td>
                                            <a href="../certificados/semana-tecnologia.pdf" class="btn btn-primary btn-sm" target="_blank" aria-label="Baixar certificado Semana Acadêmica de Tecnologia">
                                                <i class="fa fa-download"></i> Baixar
                                            </a>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>Workshop de Inovação</td>
                                        <td>22/03/2025</td>
                                        <td>
                                            <a href="../certificados/workshop-inovacao.pdf" class="btn btn-primary btn-sm" target="_blank" aria-label="Baixar certificado Workshop de Inovação">
                                                <i class="fa fa-download"></i> Baixar
                                            </a>
                                        </td>
                                    </tr>
                                </tbody>
                            </table>
                        </div>
                        <div class="text-center mt-4">
                            <a href="index.php" class="btn btn-secondary">
                                <i class="fa fa-arrow-left"></i> Voltar para Início
                            </a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</main>
<?php include 'footer.php'; ?>
</body>
</html>