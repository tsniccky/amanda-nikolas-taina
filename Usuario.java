import java.io.Serializable;

public class Usuario implements Serializable {
    private final String nome;

    public Usuario(String nome) {
        this.nome = nome;
    }

    public String getNome() { return nome; }
}
