import java.io.Serializable;
import java.util.*;

public class Playlist implements Serializable {
    private final String nome;
    private final String dono;
    private final Set<String> itens = new LinkedHashSet<>();
    private final Map<String, Integer> popularidade = new HashMap<>();

    public Playlist(String nome, String dono) {
        this.nome = nome;
        this.dono = dono;
    }

    public String getNome() { return nome; }
    public String getDono() { return dono; }
    public Set<String> getItens() { return Collections.unmodifiableSet(itens); }

    public boolean adicionarItem(String titulo) {
        boolean added = itens.add(titulo);
        if (added) {
            popularidade.put(titulo, popularidade.getOrDefault(titulo, 0) + 1);
        }
        return added;
    }

    public boolean removerItem(String titulo) {
        return itens.remove(titulo);
    }

    public Map<String,Integer> getPopularidade() {
        return Collections.unmodifiableMap(popularidade);
    }
}
