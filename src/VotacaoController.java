import java.io.*;
import java.util.*;

public class VotacaoController {
    private Set<String> eleitoresQueVotaram = new HashSet<>();
    private Map<String, Candidato> candidatos = new HashMap<>();
    private Map<String, Integer> votos = new HashMap<>();

    public void cadastrarCandidato(Candidato candidato) {
        candidatos.put(candidato.getId(), candidato);
        votos.put(candidato.getId(), 0);
    }

    public void votar(Eleitor eleitor, String idCandidato) {
        if (eleitoresQueVotaram.contains(eleitor.getId())) {
            System.out.println("[Erro] Eleitor já votou!");
            return;
        }
        if (!votos.containsKey(idCandidato)) {
            System.out.println("[Erro] Candidato não encontrado!");
            return;
        }
        votos.put(idCandidato, votos.get(idCandidato) + 1);
        eleitoresQueVotaram.add(eleitor.getId());
        salvarBackup();
        salvarVotante(eleitor);
    }

    public void exibirResultadoFinal() {
        System.out.println("\nResultado Final:\n");
        String vencedorId = null;
        int maiorVoto = -1;
        for (String id : votos.keySet()) {
            Candidato c = candidatos.get(id);
            int qtd = votos.get(id);
            System.out.println("Candidato: " + c.getNome() + " (" + c.getChapa() + ") - " + qtd + " votos");
            if (qtd > maiorVoto) {
                maiorVoto = qtd;
                vencedorId = id;
            }
        }
        if (vencedorId != null) {
            Candidato vencedor = candidatos.get(vencedorId);
            System.out.println("\nVencedor: " + vencedor.getNome() + " (" + vencedor.getChapa() + ")\n");
        }
        salvarRelatorio();
    }

    private void salvarVotante(Eleitor e) {
        try (PrintWriter pw = new PrintWriter(new FileWriter("votantes.txt", true))) {
            pw.println(e.getId() + ";" + e.getNome());
        } catch (IOException ex) {
            System.out.println("[Erro] Ao salvar votante.");
        }
    }

    private void salvarBackup() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("votos.dat"))) {
            oos.writeObject(votos);
        } catch (IOException ex) {
            System.out.println("[Erro] Ao salvar backup.");
        }
    }

    private void salvarRelatorio() {
        try (PrintWriter pw = new PrintWriter("relatorio_final.txt")) {
            for (String id : votos.keySet()) {
                Candidato c = candidatos.get(id);
                pw.println("Candidato: " + c.getNome() + " (" + c.getChapa() + ") - " + votos.get(id) + " votos");
            }
        } catch (IOException ex) {
            System.out.println("[Erro] Ao salvar relatório.");
        }
    }
}