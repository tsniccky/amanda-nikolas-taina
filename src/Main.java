public class Main {
    public static void main(String[] args) {
        VotacaoController controller = new VotacaoController();

        while (true) {
            System.out.println("\nSistema de Playlists Personalizadas!");
            System.out.println("1. Cadastrar músicas");
            System.out.println("2. Escolher música para tocar");
            System.out.println("3. Exibir músicas cadastradas");
            System.out.println("4. Exibir músicas já tocadas:");
            System.out.println("5. Sair");
            System.out.print("Escolha: ");
            int op = In.lerInt();

            if (op == 1) {
                System.out.print("Nome da música: ");
                String nome = In.lerString();
                System.out.print("Compositor: ");
                String compositor = In.lerString();
                System.out.print("Estilo de música: ");
                String musica = In.lerString();
                controller.cadastrarCandidato(new Candidato(nome, compositor, musica));
            } else if (op == 2) {
                System.out.print("Seu nome: ");
                String nome = In.lerString();
                System.out.print("Seu ID: ");
                String id = In.lerString();
                System.out.print("ID do candidato: ");
                String idCandidato = In.lerString();
                controller.votar(new Eleitor(nome, id), idCandidato);
            } else if (op == 3) {
                controller.exibirResultadoFinal();
            } else if (op == 4) {
                break;
            } else {
                System.out.println("[Erro] Opção inválida.");
            }
        }
    }
}