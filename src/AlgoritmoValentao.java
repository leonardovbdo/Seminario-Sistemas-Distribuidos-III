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
        int inactiveProcessId = chooseRandomInactiveProcess();
        System.out.println("Processo de id " + processos[inactiveProcessId].getId() + " está inativo");

        // muda o status pra inativo
        processos[inactiveProcessId].setStatus("Inativo");

        // variavel de apoio
        int idOfInitiator = 0;

        // loop infinito pra realizar os passos
        while (true) {
            boolean higherProcesses = false;

            // itera por todos os processos
            for (int i = idOfInitiator; i < n; i++) {
                if (processos[idOfInitiator].getStatus().equals("ativo")) {
                    if (idOfInitiator != i) {
                        System.out.println("Processo " + idOfInitiator + " envia mensagem de Eleicao(" + idOfInitiator
                                + ") para o processo " + i);
                        higherProcesses = true;
                    }
                } else {
                    idOfInitiator++;
                }
            }

            // checa por maiores processos
            if (higherProcesses) {
                // usa o loop novamente para iterar sobre os processos superiores
                for (int i = idOfInitiator; i < n; i++) {
                    if (processos[i].getStatus().equals("ativo")) {
                        if (idOfInitiator != i) {
                            System.out.println("Processo " + i + " responde mensagem de Ok(" + i + ") para o processo " + idOfInitiator);
                        }
                    }
                }
                idOfInitiator++;
            } else {
                // pega o processo ativo de maior índice para declarar como coordenador
                int coord = processos[getMaxValue()].getId();

                // Exibe o processo coordenador
                System.out.println("Por fim o processo " + coord + " se torna o coordenador");

                for (int i = coord - 1; i >= 0; i--) {
                    if (processos[i].getStatus().equals("ativo")) {
                        System.out.println(
                                "Processo " + coord + " envia mensagem de Coordenador(" + coord + ") para o processo " + i);
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
        int randomIndex;
        do {
            randomIndex = random.nextInt(n);
        } while (!processos[randomIndex].getStatus().equals("ativo"));
        return randomIndex;
    }

    // Método para pegar o processo de maior valor ativo
    public int getMaxValue() {
        int mxId = -99;
        int mxIdIndex = 0;
        for (int i = 0; i < processos.length; i++) {
            if (processos[i].getStatus().equals("ativo") && processos[i].getId() > mxId) {
                mxId = processos[i].getId();
                mxIdIndex = i;
            }
        }
        return mxIdIndex;
    }

    public static void main(String[] args) {

        // create instance of the BullyAlgoExample2 class
        AlgoritmoValentao bully = new AlgoritmoValentao();

        // call ring() and performElection() method
        bully.anel();
        bully.performElection();

    }

}