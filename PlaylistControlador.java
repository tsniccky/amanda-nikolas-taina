import java.io.*;
import java.util.*;

public class PlaylistControlador {
    private final Map<String, Musica> catalogo = new HashMap<>();
    private final Map<String, Usuario> usuarios = new HashMap<>();
    private final Map<String, Playlist> playlists = new HashMap<>();

    private static final String ARQ_CATALOGO = "catalogo.txt";
    private static final String ARQ_USUARIOS = "usuarios.txt";
    private static final String ARQ_PLAYLISTS = "playlists.dat";

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
        } catch (IOException e) { }
    }

    private void exportarCatalogoTxt() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(ARQ_CATALOGO))) {
            for (Musica m : catalogo.values()) {
                pw.printf("MUSICA;%s;%s;%d;%s\n", m.getTitulo(), m.getAutor(), m.getDuracaoSeg(), m.getGenero());
            }
        } catch (IOException e) { }
    }

    public void carregarPlaylistsBin() {
        File f = new File(ARQ_PLAYLISTS);
        if (!f.exists()) return;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f))) {
            Object obj = ois.readObject();
            if (obj instanceof Map) {
                playlists.clear();
                playlists.putAll((Map<String,Playlist>) obj);
            }
        } catch (Exception e) {
        }
    }

    private void salvarPlaylistsBin() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ARQ_PLAYLISTS))) {
            oos.writeObject(playlists);
        } catch (IOException e) {
        }
    }

    private static String formatarDuracao(int totalSeg) {
        int m = totalSeg / 60;
        int s = totalSeg % 60;
        return String.format("%dm %02ds", m, s);
    }
}
