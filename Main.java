import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

//Dupla EVELLYN ALANE ALVES DE MELO BUONAFINA
//      EDUARDO CAMELO BARRETO DE SIQUEIRA

public class Main {
    public static void main(String args[]) throws FileNotFoundException {
        File arquivo = new File(
                "C:\\Users\\vanpe\\OneDrive\\Área de Trabalho\\Pastas\\Programação\\compiladores/codigofonte.txt"); // pega
                                                                                                                    // arquivo.txt
        Analisador lexico = new Analisador(); // chama o nosso analisador
        @SuppressWarnings("resource") // resolver o bug do input
        Scanner input = new Scanner(arquivo); // lê o arquivo
        String Allargs = ""; // Ficar com todos os argumentos
        char letra; // Vai pegar letra por letra do Allargs
        String argumentos = ""; // caso a gnt queira add algo

        while (input.hasNextLine()) { // add tudo oq tá no arquivo nos Allargs
            argumentos = input.nextLine();
            Allargs += argumentos;
            Allargs += " \n ";
        }

        lexico.setArgs(Allargs); // Coloca todos os argumentos no analisador
                                 // Caso tenha um comentário, eu preciso saber o tamanho

        for (int i = 0; i < Allargs.length(); i++) {// lê todos os Allargs
            letra = Allargs.charAt(i); // Pega letra por letra
            lexico.setPos(i); // setar o local de onde parou.

            if (letra == ' ' || lexico.SimboloEspc(letra)) { // vai até formar uma palavra
                if (lexico.SimboloEspc(letra)) { // Valida se é um simbEsp
                    lexico.getTokens().add("SimboloEsp: " + letra);
                } else {
                    lexico.categorizar(); // faz a categorização da palavra
                }

            } else if (letra == '"') {// valida as "
                lexico.setChar(letra);
                do {
                    i++;
                    letra = Allargs.charAt(i);
                    lexico.setChar(letra);
                } while (letra != '"');
                lexico.categorizar();
            } else {
                lexico.setChar(letra); // incrementa todos os caracteres para formar o lexema
            }
        }
        lexico.lista();
        System.out.println("");
        System.out.println("Todos os argumentos: ");
        System.out.println(lexico.getArgs());
    }
}

/*
 * 
 */