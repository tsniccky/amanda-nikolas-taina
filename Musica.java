public class Musica extends Midia {
    private final String genero;

    public Musica(String titulo, String artista, int duracaoSeg, String genero) {
        super(titulo, artista, duracaoSeg);
        this.genero = genero;
    }

    public String getGenero() { return genero; }
}
