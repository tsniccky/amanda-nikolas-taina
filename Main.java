import java.util.Scanner;

public class Main {
    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        PlaylistController controller = new PlaylistController();
        controller.carregarPlaylistsBin();

        while (true) {
            menu();
            int op = lerInt("Escolha: ");
            switch (op) {
                case 1: cadastrarUsuario(controller); break;
                case 2: cadastrarMusica(controller); break;
                case 3: controller.listarCatalogo(); break;
                case 4: criarPlaylist(controller); break;
                case 5: adicionarItem(controller); break;
                case 6: removerItem(controller); break;
                case 7: listarPlaylist(controller); break;
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

    private static void cadastrarUsuario(PlaylistController c) {
        System.out.print("Nome do usuário: ");
        String nome = sc.nextLine().trim();
        c.cadastrarUsuario(new Usuario(nome));
    }

    private static void cadastrarMusica(PlaylistController c) {
        System.out.print("Título da música: ");
        String titulo = sc.nextLine().trim();
        System.out.print("Artista: ");
        String artista = sc.nextLine().trim();
        int dur = lerInt("Duração (segundos): ");
        System.out.print("Gênero: ");
        String genero = sc.nextLine().trim();
        c.cadastrarMusica(new Musica(titulo, artista, dur, genero));
    }

    private static void criarPlaylist(PlaylistController c) {
        System.out.print("Nome da playlist: ");
        String nome = sc.nextLine().trim();
        System.out.print("Dono da playlist: ");
        String dono = sc.nextLine().trim();
        c.criarPlaylist(new Playlist(nome, dono));
    }

    private static void adicionarItem(PlaylistController c) {
        System.out.print("Nome da playlist: ");
        String pl = sc.nextLine().trim();
        System.out.print("Título da música: ");
        String mid = sc.nextLine().trim();
        c.adicionarItemNaPlaylist(pl, mid);
    }

    private static void removerItem(PlaylistController c) {
        System.out.print("Nome da playlist: ");
        String pl = sc.nextLine().trim();
        System.out.print("Título da música: ");
        String mid = sc.nextLine().trim();
        c.removerItemDaPlaylist(pl, mid);
    }

    private static void listarPlaylist(PlaylistController c) {
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
