import java.io.*;
import java.util.*;

public class PlaylistControlador {
    private final Map<String, Musica> catalogo = new LinkedHashMap<>();
    private final Map<String, Usuario> usuarios = new LinkedHashMap<>();
    private final Map<String, Playlist> playlists = new LinkedHashMap<>();

    private static final String ARQ_CATALOGO = "catalogo.txt";
    private static final String ARQ_USUARIOS = "usuarios.txt";
    private static final String ARQ_PLAYLISTS = "playlists.dat";

    public PlaylistControlador() {
        carregarUsuariosTxt();
        carregarCatalogoTxt();
        carregarPlaylistsBin();
    }

    public void cadastrarUsuario(Usuario u) {
        if (usuarios.containsKey(u.getNome())) {
            System.out.println("Usuário já existe.");
            return;
        }
        usuarios.put(u.getNome(), u);
        salvarUsuarioTxt(u);
    }

    public void cadastrarMusica(Musica m) {
        if (catalogo.containsKey(m.getTitulo())) {
            System.out.println("Já existe música com esse título.");
            return;
        }
        catalogo.put(m.getTitulo(), m);
        exportarCatalogoTxt();
    }

    public void criarPlaylist(Playlist pl) {
        if (!usuarios.containsKey(pl.getDono())) {
            System.out.println("Usuário não encontrado.");
            return;
        }
        if (playlists.containsKey(pl.getNome())) {
            System.out.println("Já existe playlist com esse nome.");
            return;
        }
        playlists.put(pl.getNome(), pl);
        salvarPlaylistsBin();
    }

    public void adicionarItemNaPlaylist(String playlistNome, String tituloMusica) {
        Playlist pl = playlists.get(playlistNome);
        if (pl == null) { System.out.println("Playlist não encontrada."); return; }
        if (!catalogo.containsKey(tituloMusica)) { System.out.println("Música não encontrada."); return; }
        if (pl.adicionarItem(tituloMusica)) {
            salvarPlaylistsBin();
            System.out.println("Adicionado!");
        } else {
            System.out.println("Já está na playlist.");
        }
    }

    public void removerItemDaPlaylist(String playlistNome, String tituloMusica) {
        Playlist pl = playlists.get(playlistNome);
        if (pl == null) { System.out.println("Playlist não encontrada."); return; }
        if (pl.removerItem(tituloMusica)) {
            salvarPlaylistsBin();
            System.out.println("Removido!");
        } else {
            System.out.println("Não estava na playlist.");
        }
    }

    public void listarPlaylist(String playlistNome) {
        Playlist pl = playlists.get(playlistNome);
        if (pl == null) { System.out.println("Playlist não encontrada."); return; }
        System.out.println("\nPlaylist: " + pl.getNome() + " (Dono: " + pl.getDono() + ")");
        int totalSeg = 0;
        int i = 1;
        for (String titulo : pl.getItens()) {
            Musica m = catalogo.get(titulo);
            if (m != null) {
                System.out.printf("%02d) %s - %s (%ds)\n", i++, m.getTitulo(), m.getAutor(), m.getDuracaoSeg());
                totalSeg += m.getDuracaoSeg();
            } else {
                System.out.printf("%02d) %s [Música não encontrada no catálogo]\n", i++, titulo);
            }
        }
        System.out.println("Duração total: " + formatarDuracao(totalSeg));
    }

    public void listarCatalogo() {
        System.out.println("\nCatálogo de músicas:");
        for (Musica m : catalogo.values()) {
            System.out.printf("%s - %s (%ds) | Gênero: %s\n",
                    m.getTitulo(), m.getAutor(), m.getDuracaoSeg(), m.getGenero());
        }
    }

    // ===== Persistência =====
    private void salvarUsuarioTxt(Usuario u) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(ARQ_USUARIOS, true))) {
            pw.println(u.getNome());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void carregarUsuariosTxt() {
        File f = new File(ARQ_USUARIOS);
        if (!f.exists()) return;
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                usuarios.put(linha, new Usuario(linha)); // precisa de construtor simples em Usuario
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void exportarCatalogoTxt() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(ARQ_CATALOGO))) {
            for (Musica m : catalogo.values()) {
                pw.printf("MUSICA;%s;%s;%d;%s\n", m.getTitulo(), m.getAutor(), m.getDuracaoSeg(), m.getGenero());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void carregarCatalogoTxt() {
        File f = new File(ARQ_CATALOGO);
        if (!f.exists()) return;
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] p = linha.split(";");
                if (p.length == 5 && p[0].equals("MUSICA")) {
                    String titulo = p[1];
                    String autor = p[2];
                    int duracao = Integer.parseInt(p[3]);
                    String genero = p[4];
                    catalogo.put(titulo, new Musica(titulo, autor, duracao, genero));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void carregarPlaylistsBin() {
        File f = new File(ARQ_PLAYLISTS);
        if (!f.exists()) return;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f))) {
            Object obj = ois.readObject();
            if (obj instanceof Map) {
                playlists.clear();
                playlists.putAll((Map<String, Playlist>) obj);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void salvarPlaylistsBin() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ARQ_PLAYLISTS))) {
            oos.writeObject(playlists);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String formatarDuracao(int totalSeg) {
        int m = totalSeg / 60;
        int s = totalSeg % 60;
        return String.format("%dm %02ds", m, s);
    }
}
