<?php
$sucesso = false;
$erro = null;

if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    $dados = [
        'nome' => $_POST['nome'],
        'sobrenome' => $_POST['sobrenome'],
        'cpf' => $_POST['cpf'],
        'email' => $_POST['email'],
        'senha' => $_POST['senha'],
        'empresa' => $_POST['empresa']
    ];

    $dados['cpf'] = preg_replace('/\D/', '', $dados['cpf']);


    $ch = curl_init('http://localhost:3000/api/usuarios');
    curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
    curl_setopt($ch, CURLOPT_POSTFIELDS, http_build_query($dados));
    curl_setopt($ch, CURLOPT_POST, true);
    curl_setopt($ch, CURLOPT_HTTPHEADER, ['Content-Type: application/x-www-form-urlencoded']);

    $resposta = curl_exec($ch);
    $status = curl_getinfo($ch, CURLINFO_HTTP_CODE);
      if (curl_errno($ch)) {
            $erro = 'Erro no cURL: ' . curl_error($ch);
        }
    curl_close($ch);

    $respostaArray = json_decode($resposta, true);

    if ($status === 201) {
        $sucesso = true;
    } else {
        $erro = $respostaArray['erro'] ?? 'Erro ao cadastrar usuário.';
    }
}
?>




<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Cadastrar</title>
    <link rel="stylesheet" href="../css/styles.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <!-- Font Awesome -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/toastr.min.css">
    
</head>
<body>
  <body class="cadastro-page">
    <?php if (isset($sucesso) && $sucesso): ?>
  <div class="alert alert-success">Cadastro realizado com sucesso!</div>
<?php elseif (isset($erro)): ?>
  <div class="alert alert-danger"><?= htmlspecialchars($erro) ?></div>
<?php endif; ?>

<?php include 'navbar.php'; ?>
<main class="flex-grow-1">
        <div class="container my-5">
            <div class="row justify-content-center">
                <div class="col-12 col-md-8 col-lg-6">
                    <div class="card shadow border-0">
                        <div class="card-body p-4">
                            <h2 class="mb-4 text-center">Cadastro de Usuário</h2>
                            <form method="post" action="">
                                <div class="mb-3">
                                    <label for="nome" class="form-label">Nome</label>
                                    <input type="text" class="form-control" id="nome" name="nome" placeholder="Digite seu nome" required>
<div class="invalid-feedback">Nome inválido.</div>

                                </div>
                                <div class="mb-3">
                                    <label for="sobrenome" class="form-label">Sobrenome</label>
                                    <input type="text" class="form-control" id="sobrenome" name="sobrenome" placeholder="Digite seu sobrenome" required>
                                </div>
                                <div class="mb-3">
                                    <label for="cpf" class="form-label">CPF</label>
                                <input type="text" class="form-control" id="cpf" name="cpf" placeholder="Digite seu CPF" required maxlength="11" inputmode="numeric" pattern="\d{11}">
                                </div>
                                <div class="mb-3">
                                    <label for="email" class="form-label">E-mail</label>
                                    <input type="email" class="form-control" id="email" name="email" placeholder="Digite seu e-mail" required>
                                </div>
                                <div class="mb-3">
                                    <label for="senha" class="form-label">Senha</label>
                                    <input type="password" class="form-control" id="senha" name="senha" placeholder="Digite sua senha" required>
                                </div>
                                <div class="mb-3">
                                    <label class="form-label" for="empresa">Empresa ou Universidade</label>
                                    <input type="text" class="form-control" id="empresa" name="empresa" placeholder="digite o nome da empresa ou universidade" required>
                                  
                                </div>
                                <button type="submit" class="btn w-100">Cadastrar</button>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </main>
<?php include 'footer.php'; ?>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/toastr.min.js"></script>

<script>
  // Configurações do Toastr
  toastr.options = {
    "closeButton": true,
    "progressBar": true,
    "positionClass": "toast-top-right",
    "timeOut": "3000"
  };

  // Função debounce para evitar execuções rápidas demais
  function debounce(func, delay = 1000) {
    let timer;
    return (...args) => {
      clearTimeout(timer);
      timer = setTimeout(() => func.apply(this, args), delay);
    };
  }

  document.addEventListener('DOMContentLoaded', () => {
    const campos = {
      nome: document.getElementById('nome'),
      sobrenome: document.getElementById('sobrenome'),
      cpf: document.getElementById('cpf'),
      email: document.getElementById('email'),
      senha: document.getElementById('senha'),
      empresa: document.getElementById('empresa')
    };

    // Exibir erro visual + toastr
    const mostrarErro = (mensagem, campo) => {
  if (!campo.classList.contains('is-invalid')) {
    toastr.clear();
    toastr.error(mensagem);
    campo.classList.add('is-invalid');
  }
};

const limparErro = (campo) => {
  if (campo.classList.contains('is-invalid')) {
    campo.classList.remove('is-invalid');
    toastr.clear();
  }
};


    // Validação dos campos com debounce
    campos.nome.addEventListener('input', debounce(() => {
      if (campos.nome.value.trim().length < 2) {
        mostrarErro('Nome deve ter ao menos 2 caracteres.', campos.nome);
      } else {
        limparErro(campos.nome);
      }
    }));

    campos.sobrenome.addEventListener('input', debounce(() => {
      if (campos.sobrenome.value.trim().length < 2) {
        mostrarErro('Sobrenome deve ter ao menos 2 caracteres.', campos.sobrenome);
      } else {
        limparErro(campos.sobrenome);
      }
    }));

    campos.cpf.addEventListener('input', debounce(() => {
  // Remove qualquer coisa que não for número
  let numeros = campos.cpf.value.replace(/\D/g, '').substring(0, 11);
  campos.cpf.value = numeros;

  if (numeros.length > 0 && numeros.length < 11) {
    mostrarErro('CPF incompleto. Digite os 11 números.', campos.cpf);
  } else if (numeros.length === 11) {
    limparErro(campos.cpf);
  }
}));

    campos.email.addEventListener('input', debounce(() => {
      const valor = campos.email.value;
      const regex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
      if (valor.length > 0 && !regex.test(valor)) {
        mostrarErro('E-mail inválido. Verifique se contém "@".', campos.email);
      } else {
        limparErro(campos.email);
      }
    }));

    campos.senha.addEventListener('input', debounce(() => {
      if (campos.senha.value.length > 0 && campos.senha.value.length < 6) {
        mostrarErro('Senha deve ter pelo menos 6 caracteres.', campos.senha);
      } else {
        limparErro(campos.senha);
      }
    }));

    campos.empresa.addEventListener('input', debounce(() => {
      if (campos.empresa.value.trim().length < 2) {
        mostrarErro('Informe o nome da empresa ou universidade.', campos.empresa);
      } else {
        limparErro(campos.empresa);
      }
    }));
  });
</script>



    </body>
    </html>