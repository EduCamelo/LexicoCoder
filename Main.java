import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
    public static void main(String args[]) throws FileNotFoundException {
        File arquivo = new File("C:\\Users\\vanpe\\OneDrive\\Área de Trabalho\\Pastas\\Programação\\compiladores/codigofonte.txt"); // pega arquivo.txt
        Analisador lexico = new Analisador();
        @SuppressWarnings("resource")
        Scanner input = new Scanner(arquivo);
        String Allargs = "";
        char letra;
        String argumentos = "";

        System.out.println("----------código fonte----------");

        while (input.hasNextLine()) { // LEMBRAR DAQUELE IF DE UMA LINHA !!!
            argumentos = input.nextLine();
            Allargs += argumentos; // todos os argumentos

            Allargs += " \n "; // o \n tem que ter esse espaço entre eles, para que não bugue quando for analisar o comentário

        }
        lexico.setArgs(Allargs);
        for (int i = 0; i < Allargs.length(); i++) {
            letra = Allargs.charAt(i);
            lexico.setPos(i);

            if (letra == ' ' || lexico.SimboloEspc(letra)) {
                if (lexico.SimboloEspc(letra)) { 
                    lexico.getTokens().add("SimboloEsp: "+letra);
                }
                else{
                    lexico.categorizar(); // categorizando o lexema
                }
            
            } else {
               lexico.setChar(letra); // incrementa todos os caracteres para formar o lexema
            }
        }
        lexico.lista();
        System.out.println(lexico.getArgs());
    }
}

/*
 * 
 */