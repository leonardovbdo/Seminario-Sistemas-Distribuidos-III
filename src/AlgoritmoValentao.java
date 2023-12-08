import java.util.Random;
import java.util.Scanner;

public class AlgoritmoValentao {

    Scanner entrada;
    Processo[] processos;
    int n;

    public AlgoritmoValentao() {
        entrada = new Scanner(System.in);
    }

    public void anel() {
        System.out.println("Insira o total de processos que terá o anel");
        n = entrada.nextInt();

        processos = new Processo[n];
        for (int i = 0; i < n; i++) {
            processos[i] = new Processo(i);
            System.out.println(processos[i].getId());
        }
    }

    public void performElection() {

        // sleep para parar a execução por 1 segundo
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // declara o processo inativo
        int idProcessoInativo = chooseRandomInactiveProcess();
        System.out.println("Processo de id " + processos[idProcessoInativo].getId() + " está inativo");

        // muda o status pra inativo
        processos[idProcessoInativo].setStatus("Inativo");

        // variavel de apoio
        int idInicial = 0;

        // loop infinito pra realizar os passos
        while (true) {
            boolean processosSuperiores = false;

            // itera por todos os processos
            for (int i = idInicial; i < n; i++) {
                if (processos[idInicial].getStatus().equals("ativo")) {
                    if (idInicial != i) {
                        System.out.println("Processo " + idInicial + " envia mensagem de Eleicao(" + idInicial
                                + ") para o processo " + i);
                        processosSuperiores = true;
                    }
                } else {
                    idInicial++;
                }
            }

            // checa por maiores processos
            if (processosSuperiores) {
                // usa o loop novamente para iterar sobre os processos superiores
                for (int i = idInicial; i < n; i++) {
                    if (processos[i].getStatus().equals("ativo")) {
                        if (idInicial != i) {
                            System.out.println("Processo " + i + " responde mensagem de Ok(" + i + ") para o processo " + idInicial);
                        }
                    }
                }
                idInicial++;
            } else {
                // pega o processo ativo de maior índice para declarar como coordenador
                int coordenador = processos[getMaxValue()].getId();

                // Exibe o processo coordenador
                System.out.println("Por fim o processo " + coordenador + " se torna o coordenador");

                for (int i = coordenador - 1; i >= 0; i--) {
                    if (processos[i].getStatus().equals("ativo")) {
                        System.out.println(
                                "Processo " + coordenador + " envia mensagem de Coordenador(" + coordenador + ") para o processo " + i);
                    }
                }

                System.out.println("Fim da eleição");
                break;
            }
        }

    }

    // Atribui uma variável aleatória para ser o processo inativo
    private int chooseRandomInactiveProcess() {
        Random random = new Random();
        int randomId;
        do {
            randomId = random.nextInt(n);
        } while (!processos[randomId].getStatus().equals("ativo"));
        return randomId;
    }

    // Método para pegar o processo de maior valor ativo
    public int getMaxValue() {
        int maxId = -99;
        int maxIdIndex = 0;
        for (int i = 0; i < processos.length; i++) {
            if (processos[i].getStatus().equals("ativo") && processos[i].getId() > maxId) {
                maxId = processos[i].getId();
                maxIdIndex = i;
            }
        }
        return maxIdIndex;
    }

    public static void main(String[] args) {

        // create instance of the BullyAlgoExample2 class
        AlgoritmoValentao algoritmoValentao = new AlgoritmoValentao();

        // call ring() and performElection() method
        algoritmoValentao.anel();
        algoritmoValentao.performElection();

    }

}