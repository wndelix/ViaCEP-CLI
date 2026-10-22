package io.github.wndelix.Principal;

import java.util.List;
import java.util.Scanner;

public class Utils {
    private static final String CYAN = "\u001B[36m";
    private static final String GREEN = "\u001B[32m";
    private static final String YELLOW = "\u001B[33m";
    private static final String BOLD = "\u001B[1m";
    private static final String RESET = "\u001B[0m";

    private static void limparTela() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    private static void exibirBoasVindas() {
        System.out.println(CYAN + BOLD + """
            ██╗   ██╗██╗ █████╗  ██████╗███████╗██████╗      ██████╗██╗     ██╗
            ██║   ██║██║██╔══██╗██╔════╝██╔════╝██╔══██╗    ██╔════╝██║     ██║
            ██║   ██║██║███████║██║     █████╗  ██████╔╝    ██║     ██║     ██║
            ╚██  ██╔╝██║██╔══██║██║     ██╔══╝  ██╔═══╝     ██║     ██║     ██║
             ╚████╔╝ ██║██║  ██║╚██████╗███████╗██║         ╚██████╗███████╗██║
              ╚═══╝  ╚═╝╚═╝  ╚═╝ ╚═════╝╚══════╝╚═╝          ╚═════╝╚══════╝╚═╝
            """ + RESET);
    }

    public static void interfaceUser (Scanner sc) {
        boolean userContinue = true;

        while (userContinue) {
            limparTela();
            exibirBoasVindas();

            System.out.print(CYAN + """
                [1] CEP
                [2] Endereço
                [3] Sair
    
                """ + RESET);

            System.out.print("Selecione uma opção: ");

            if (!sc.hasNextInt()) {
                System.out.println(YELLOW + "Por favor, digite apenas números válidos (1, 2 ou 3)." + RESET);
                sc.next();
                System.out.print("Pressione " + GREEN + "ENTER" + RESET + " para continuar...");
                sc.nextLine();
                sc.nextLine();
                continue;
            }

            int userInput = sc.nextInt();
            sc.nextLine();

            switch (userInput) {
                case 1 -> {
                    pesquisaCEP(sc);
                }
                case 2 -> {
                    pesquisaEndereco(sc);
                }

                case 3 -> {
                    System.out.println(CYAN + "Saindo..." + RESET);
                    userContinue = false;
                }

                default -> {
                    System.out.println(YELLOW + "Opção inválida! Tente novamente." + RESET);
                }
            }
        }
    }

    private static void exibirEstados() {
        System.out.println(CYAN + """
         [01] Acre                  [15] Paraíba
         [02] Alagoas               [16] Paraná
         [03] Amapá                 [17] Pernambuco
         [04] Amazonas              [18] Piauí
         [05] Bahia                 [19] Rio de Janeiro
         [06] Ceará                 [20] Rio Grande do Norte
         [07] Distrito Federal      [21] Rio Grande do Sul
         [08] Espírito Santo        [22] Rondônia
         [09] Goiás                 [23] Roraima
         [10] Maranhão              [24] Santa Catarina
         [11] Mato Grosso           [25] São Paulo
         [12] Mato Grosso do Sul    [26] Sergipe
         [13] Minas Gerais          [27] Tocantins
         [14] Pará
        """ + RESET);
    }

    private static void pesquisaCEP(Scanner sc) {
        System.out.print("Digite o CEP: ");
        String userInputCEP = sc.nextLine();

        String cepTratado = (userInputCEP == null) ? "" : userInputCEP.trim().replace("-", "").replace(" ", "");

        if (cepTratado.isEmpty() || cepTratado.length() != 8) {
            System.out.println(YELLOW + "CEP Inválido!" + RESET);
            System.out.print("Pressione " + GREEN + "ENTER" + RESET + " para voltar ao menu...");
            sc.nextLine();
            return;
        }

        String url = String.format("https://viacep.com.br/ws/%s/json/", cepTratado);

        buscaCEP(url);
        System.out.print(RESET + "\nPressione " + GREEN + "ENTER" + RESET + " para voltar ao menu...");
        sc.nextLine();
    }

    private static void pesquisaEndereco(Scanner sc) {
        exibirEstados();
        System.out.print("Selecione um estado " + CYAN + "(1-27)" + RESET + ": ");

        if (!sc.hasNextInt()) {
            System.out.println(YELLOW + "Entrada inválida! Digite um número de 1 a 27." + RESET);
            sc.next();
            System.out.print("Pressione " + GREEN + "ENTER" + RESET + " para voltar ao menu...");
            sc.nextLine();
            sc.nextLine();
            return;
        }

        int userInputEstado = sc.nextInt();
        sc.nextLine();

        String[] ufs = {
                "AC", "AL", "AP", "AM", "BA", "CE", "DF", "ES", "GO",
                "MA", "MT", "MS", "MG", "PA", "PB", "PR", "PE", "PI",
                "RJ", "RN", "RS", "RO", "RR", "SC", "SP", "SE", "TO"
        };

        if (userInputEstado < 1 || userInputEstado > 27) {
            System.out.println(YELLOW + "Opção de estado inválida!" + RESET);
            System.out.print(RESET + "\nPressione " + GREEN + "ENTER" + RESET + " para voltar ao menu...");
            sc.nextLine();
            return;
        }

        String ufSelecionada = ufs[userInputEstado - 1];

        System.out.print("Digite o nome da cidade: ");
        String cidade = sc.nextLine();

        System.out.print("Digite o nome da rua/logradouro: ");
        String logradouro = sc.nextLine();

        String url = String.format("https://viacep.com.br/ws/%s/%s/%s/json/",
                ufSelecionada,
                cidade.replace(" ", "%20"),
                logradouro.replace(" ", "%20"));

        buscaEndereco(url);
        System.out.print("Pressione " + GREEN + "ENTER" + RESET + " para voltar ao menu...");
        sc.nextLine();
    }

    private static void buscaCEP(String CEP) {
        ViaCEPApi api = new ViaCEPApi();
        System.out.print("Requisitando dados da API...");
        CEPdto endereco = api.getResponseCEP(CEP);
        System.out.print("\r                                           \r");
        System.out.println("\nResultado da busca:" + CYAN);

        if (endereco != null && endereco.cep != null) {
            System.out.println(endereco.logradouro + ", " + endereco.bairro + ", " + endereco.localidade + "/" + endereco.uf + ", " + endereco.cep);
        }
        else {
            System.out.println(YELLOW + "CEP não encontrado." + RESET);
        }
    }

    private static void buscaEndereco(String Endereco) {
        ViaCEPApi api = new ViaCEPApi();
        System.out.print("Requisitando dados da API...");
        List<CEPdto> enderecos = api.getResponseEndereco(Endereco);
        System.out.print("\r                                                   \r");
        System.out.println("\nResultado(s) da busca:" + CYAN);
        if (enderecos != null && !enderecos.isEmpty()) {
            for (CEPdto endereco : enderecos) {
                System.out.println(endereco.logradouro + ", " + endereco.bairro + ", " + endereco.localidade + "/" + endereco.uf + ", " + endereco.cep);
            }
        }
        else {
            System.out.println(YELLOW + "Nenhum endereço encontrado." + RESET);
        }
        System.out.println(RESET);
    }
}
