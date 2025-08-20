public abstract class Midia implements Playable {
    protected final String titulo;
    protected final String autor;
    protected final int duracaoSeg;

    protected Midia(String titulo, String autor, int duracaoSeg) {
        this.titulo = titulo;
        this.autor = autor;
        this.duracaoSeg = duracaoSeg;
    }

    public String getTitulo() { return titulo; }
    public String getAutor() { return autor; }
    public int getDuracaoSeg() { return duracaoSeg; }
}

