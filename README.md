# ViaCEP CLI

Aplicação de linha de comando em Java para consulta de endereços pela API pública do [ViaCEP](https://viacep.com.br/). Permite buscar um endereço a partir do CEP ou localizar CEPs a partir de estado, cidade e logradouro.

## Funcionalidades

- Consulta de endereço por CEP.
- Busca de CEPs por UF, cidade e logradouro.
- Interface de menu interativo no terminal.
- Execução em um único arquivo JAR, sem necessidade de instalar dependências manualmente.

## Requisitos

- **Java Development Kit (JDK) 17 ou superior**, necessário para compilar a partir do código-fonte e para executar o JAR. O projeto utiliza blocos de texto (*text blocks*) e a sintaxe `switch` com setas, disponíveis a partir do Java 17.
- **Apache Maven 3.6 ou superior**, necessário apenas para compilar a partir do código-fonte.
- **Conexão com a internet**, pois a aplicação consome a API do ViaCEP em tempo real.
- Terminal com suporte a códigos ANSI e UTF-8 para exibição correta das cores e dos caracteres acentuados.

## Instalação

### Opção 1: Baixar o JAR pronto (recomendado)

1. Acesse a página de [Releases](https://github.com/wndelix/ViaCEP-CLI/releases) do projeto.
2. Baixe o arquivo `viacep-v1.0.jar`.
3. Execute no terminal:

```bash
java -jar viacep-v1.0.jar
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

3. O arquivo executável será criado em `target/viacep-v1.0.jar`. Execute-o com:

```bash
java -jar target/viacep-v1.0.jar
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

## Estrutura do projeto

```
ViaCEP/
├── pom.xml
└── src/main/java/io/github/wndelix/Principal/
    ├── Principal.java     # Ponto de entrada da aplicação
    ├── Utils.java         # Interface de usuário e regras de entrada
    ├── ViaCEPApi.java     # Cliente HTTP e desserialização das respostas
    └── CEPdto.java        # Modelo de dados do endereço
```

## Tecnologias utilizadas

- Java 17
- `java.net.http.HttpClient` (cliente HTTP nativo)
- [Gson 2.14.0](https://github.com/google/gson) para serialização e desserialização JSON
- Maven com `maven-shade-plugin` para geração de JAR executável

## Limitações conhecidas

- Não há tratamento específico para respostas de erro da API. Quando o ViaCEP retorna um CEP inexistente com o corpo `{"erro": true}`, a aplicação pode exibir resultados incorretos ou vazios.
- Em caso de falha de conexão, a exceção é apenas registrada no console e a aplicação pode gerar `NullPointerException`.
- A busca por endereço não codifica caracteres especiais de forma completa, o que pode afetar nomes com caracteres fora do padrão ASCII.

## Contribuição

Contribuições são bem-vindas. Para mudanças relevantes, abra primeiro uma *issue* para discutir o que deseja alterar. Em seguida, envie um *pull request* com a descrição clara das modificações.

## Licença

Este projeto está disponível sob a licença MIT. Consulte o arquivo `LICENSE` para mais detalhes.
