public class Candidato extends Pessoa implements Votavel {
    private String chapa;

    public Candidato(String nome, String id, String chapa) {
        super(nome, id);
        this.chapa = chapa;
    }

    public String getChapa() { return chapa; }
}