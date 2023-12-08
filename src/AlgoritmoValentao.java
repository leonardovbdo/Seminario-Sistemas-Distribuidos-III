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

        // show failed process
        int inactiveProcessId = chooseRandomInactiveProcess();
        System.out.println("Processo de id " + processos[inactiveProcessId].getId() + " está inativo");

        // change status to Inativo of the failed process
        processos[inactiveProcessId].setStatus("Inativo");

        // declare and initialize variables
        int idOfInitiator = 0;
        boolean overStatus = true;

        // use while loop to repeat steps
        while (overStatus) {
            boolean higherProcesses = false;

            // iterate all the processos
            for (int i = idOfInitiator + 1; i < n; i++) {
                if (processos[i].getStatus().equals("ativo")) {
                    System.out.println("Processo " + idOfInitiator + " envia mensagem de Eleicao(" + idOfInitiator
                            + ") para o processo " + i);
                    higherProcesses = true;

                    // check if the process is inativo, then skip this round
                    if (processos[i].getStatus().equals("Inativo")) {
                        continue;
                    }

                    // use for loop to again iterate processos
                    for (int j = i + 1; j < n; j++) {
                        if (processos[j].getStatus().equals("ativo")) {
                            System.out.println("Processo " + i + " envia mensagem de Eleicao(" + idOfInitiator
                                    + ") para o processo " + j);
                            higherProcesses = true;
                        }
                    }
                }
            }

            // check for higher process
            if (higherProcesses) {
                // use for loop to again iterate processos
                for (int i = idOfInitiator + 1; i < n; i++) {
                    if (processos[i].getStatus().equals("ativo")) {
                        System.out.println("Processo " + i + " responde mensagem de Ok(" + i + ") para o processo " + idOfInitiator);
                    }
                }
                // increment initiator id
                idOfInitiator++;
            } else {
                // get the last process from the processos that will become coordinator
                int coord = processos[getMaxValue()].getId();

                // show process that becomes the coordinator
                System.out.println("Por fim o processo " + coord + " se torna o coordenador");

                for (int i = coord - 1; i >= 0; i--) {
                    if (processos[i].getStatus().equals("ativo")) {
                        System.out.println(
                                "Processo " + coord + " envia mensagem de Coordenador(" + coord + ") para o processo " + i);
                    }
                }

                System.out.println("Fim da eleição");
                overStatus = false;
                break;
            }
        }

    }

    private int chooseRandomInactiveProcess() {
        Random random = new Random();
        int randomIndex;
        do {
            randomIndex = random.nextInt(n);
        } while (!processos[randomIndex].getStatus().equals("ativo"));
        return randomIndex;
    }

    public int getMaxValue() {
        int mxId = -99;
        int mxIdIndex = 0;
        for (int i = 0; i < processos.length; i++) {
            if (processos[i].getStatus() == "ativo" && processos[i].getId() > mxId) {
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