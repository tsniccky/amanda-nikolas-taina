import java.util.Scanner;

public class Main {
    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        PlaylistControlador controlador = new PlaylistControlador();
        controlador.carregarPlaylistsBin();

        while (true) {
            menu();
            int opcao = lerInt("Escolha: ");
            switch (opcao) {
                case 1: cadastrarUsuario(controlador);
                break;
                case 2: cadastrarMusica(controlador);
                break;
                case 3: controlador.listarCatalogo();
                break;
                case 4: criarPlaylist(controlador);
                break;
                case 5: adicionarItem(controlador);
                break;
                case 6: removerItem(controlador);
                break;
                case 7: listarPlaylist(controlador);
                break;
                case 0: System.out.println("Saindo..."); return;
                default: System.out.println("Opção inválida.");
            }
        }
    }

    private static void menu() {
        System.out.println("\n--- Sistema de Playlist ---");
        System.out.println("1. Cadastrar usuário");
        System.out.println("2. Cadastrar música");
        System.out.println("3. Listar catálogo");
        System.out.println("4. Criar playlist");
        System.out.println("5. Adicionar música à playlist");
        System.out.println("6. Remover música da playlist");
        System.out.println("7. Listar playlist");
        System.out.println("0. Sair");
    }

    private static void cadastrarUsuario(PlaylistControlador c) {
        System.out.print("Nome do usuário: ");
        String nome = sc.nextLine().trim();
        c.cadastrarUsuario(new Usuario(nome));
    }

    private static void cadastrarMusica(PlaylistControlador c) {
        System.out.print("Título da música: ");
        String titulo = sc.nextLine().trim();
        System.out.print("Artista: ");
        String artista = sc.nextLine().trim();
        int dur = lerInt("Duração (segundos): ");
        System.out.print("Gênero: ");
        String genero = sc.nextLine().trim();
        c.cadastrarMusica(new Musica(titulo, artista, dur, genero));
    }

    private static void criarPlaylist(PlaylistControlador c) {
        System.out.print("Nome da playlist: ");
        String nome = sc.nextLine().trim();
        System.out.print("Dono da playlist: ");
        String dono = sc.nextLine().trim();
        c.criarPlaylist(new Playlist(nome, dono));
    }

    private static void adicionarItem(PlaylistControlador c) {
        System.out.print("Qual playlist deseja adicionar: ");
        String pl = sc.nextLine().trim();
        System.out.print("Título da música: ");
        String mid = sc.nextLine().trim();
        c.adicionarItemNaPlaylist(pl, mid);
    }

    private static void removerItem(PlaylistControlador c) {
        System.out.print("Nome da playlist: ");
        String pl = sc.nextLine().trim();
        System.out.print("Título da música: ");
        String mid = sc.nextLine().trim();
        c.removerItemDaPlaylist(pl, mid);
    }

    private static void listarPlaylist(PlaylistControlador c) {
        System.out.print("Nome da playlist: ");
        String pl = sc.nextLine().trim();
        c.listarPlaylist(pl);
    }

    private static int lerInt(String msg) {
        while (true) {
            try {
                System.out.print(msg);
                return Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Digite um número válido.");
            }
        }
    }
}
