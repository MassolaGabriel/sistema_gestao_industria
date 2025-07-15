package file;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class EscritaArquivo {

    public void salvarCSV(String relatorio) {
        String caminho = "src/file/relatorio.csv";
        File arquivo = new File(caminho);

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(arquivo))) {

            bw.write(relatorio);

        } catch (IOException e) {
            System.out.println("Erro ao escrever o arquivo.");
        }


    }//fim
}
