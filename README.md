# ViaCEP CLI

Aplicação de linha de comando em Java para consulta de endereços pela API pública do [ViaCEP](https://viacep.com.br/). Permite buscar um endereço a partir do CEP ou localizar CEPs a partir de estado, cidade e logradouro.

## Funcionalidades

- Consulta de endereço por CEP com 8 dígitos.
- Busca de CEPs por UF, cidade e logradouro, com exibição de todos os resultados encontrados.
- Tratamento de erros de rede, de timeout, de status HTTP inesperado e de respostas malformadas, com mensagens claras no terminal.
- Interface de menu interativo no terminal.
- Distribuição em um único arquivo JAR executável, sem necessidade de instalar dependências manualmente.

## Requisitos

- **Java Development Kit (JDK) 17 ou superior**, necessário para compilar a partir do código-fonte e para executar o JAR.
- **Apache Maven 3.6 ou superior**, necessário apenas para compilar a partir do código-fonte.
- **Conexão com a internet**, pois a aplicação consulta a API do ViaCEP em tempo real.
- Terminal com suporte a códigos ANSI e UTF-8 para exibição correta das cores e dos caracteres acentuados.

## Instalação

### Opção 1: Baixar o JAR pronto (recomendado)

1. Acesse a página de [Releases](https://github.com/wndelix/ViaCEP-CLI/releases) do projeto.
2. Baixe o arquivo `viacep-v1.1.jar`.
3. Execute no terminal:

```bash
java -jar viacep-v1.1.jar
```

### Opção 2: Compilar a partir do código-fonte

1. Clone o repositório:

```bash
git clone https://github.com/wndelix/ViaCEP-CLI.git
cd ViaCEP-CLI
```

2. Gere o pacote com o Maven:

```bash
mvn clean package
```

3. O arquivo executável será criado em `target/viacep-v1.1.jar`. Execute-o com:

```bash
java -jar target/viacep-v1.1.jar
```

## Uso

Ao iniciar a aplicação, será exibido o menu principal:

```
[1] CEP
[2] Endereço
[3] Sair
```

- **Opção 1 (CEP):** informe um CEP com 8 dígitos. Hífens e espaços são removidos automaticamente.
- **Opção 2 (Endereço):** selecione o estado pelo número correspondente, informe o nome da cidade e o nome do logradouro. A busca retorna todos os CEPs encontrados.
- **Opção 3 (Sair):** encerra a aplicação.

Quando a consulta falha por problema de rede, tempo esgotado ou resposta inesperada da API, a aplicação exibe uma mensagem explicativa e retorna ao menu.

## Estrutura do projeto

```
ViaCEP-CLI/
├── pom.xml
└── src/main/java/io/github/wndelix/Principal/
    ├── Principal.java     # Ponto de entrada da aplicação
    ├── Utils.java         # Interface de usuário e regras de entrada
    ├── ViaCEPApi.java     # Cliente HTTP, tratamento de erros e desserialização
    └── CEPdto.java        # Modelo de dados do endereço
```

## Tecnologias utilizadas

- Java 17
- `java.net.http.HttpClient` (cliente HTTP nativo, com timeouts configurados)
- [Gson 2.14.0](https://github.com/google/gson) para desserialização JSON
- Maven com `maven-shade-plugin` para geração de JAR executável

## Histórico de versões

### 1.1

- Tratamento de erros de rede, de timeout e de interrupção da requisição, com mensagens no terminal em vez de exceções não tratadas.
- Verificação do status HTTP da resposta antes da desserialização.
- Tratamento de respostas com JSON inválido ou inesperado.
- Timeouts configurados para conexão (10 s) e para a requisição (15 s).
- Mensagem de "nenhum endereço encontrado" destacada em amarelo.
- Remoção de código comentado e do método `buildUrl`, que não era utilizado.
- Ajuste no banner de boas-vindas.

### 1.0

- Primeira versão, com consulta por CEP e por endereço.

## Limitações conhecidas

- A busca por endereço substitui apenas espaços por `%20`. Nomes de cidade ou logradouro com outros caracteres especiais, como `&` ou `#`, ainda podem gerar uma consulta incorreta.
- A aplicação não possui testes automatizados.

## Contribuição

Contribuições são bem-vindas. Para mudanças relevantes, abra primeiro uma *issue* para discutir o que deseja alterar. Em seguida, envie um *pull request* com a descrição clara das modificações.

## Licença

Este projeto está licenciado sob a [Licença MIT](LICENSE). Copyright (c) 2026 Wendel Peterson.
