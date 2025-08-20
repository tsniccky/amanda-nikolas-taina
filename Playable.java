import java.io.Serializable;

public interface Playable extends Serializable {
    String getTitulo();
    String getAutor();
    int getDuracaoSeg();
}
