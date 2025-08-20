import java.io.Serializable;

public abstract class Pessoa implements Serializable {
    protected String nome;
    protected String id;

    public Pessoa(String nome, String id) {
        this.nome = nome;
        this.id = id;
    }

    public String getNome() { return nome; }
    public String getId() { return id; }
}