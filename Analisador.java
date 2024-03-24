import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/*  Sumario
 *  para identificar id: 253
 * 
 * 
 * 
 * 
 * 
 * 
 */

public class Analisador {

    private ArrayList<String> sysPalavras = new ArrayList<String>();
    private ArrayList<String> Op = new ArrayList<String>();
    private ArrayList<Character> Simb = new ArrayList<Character>();
    private ArrayList<String> tokens = new ArrayList<String>();
    private ArrayList<String> simbolos = new ArrayList<String>();

    private String aux[] = { "int", "float", "char", "boolean", "void", "if", "else", "for", "while", "scanf",
            "println", "return" };
    private String aux2[] = { "=", "+", "-", "*", "/", "%", "&&", "||", "!", "++", "--" };
    private String aux3[] = { ">", ">=", "<", "<=", "!=", "==" };

    private int pos;
    private int idCont;

    private String lexema = "";
    private String args = "";
    private Pattern id = Pattern.compile("[a-zA-Z][a-zA-Z0-9]*");

    public Analisador() {
        sysPalavras.addAll(Arrays.asList(aux));
        Op.addAll(Arrays.asList(aux2));
        Op.addAll(Arrays.asList(aux3));
        this.Simb.add('(');
        this.Simb.add(')');
        this.Simb.add('[');
        this.Simb.add(']');
        this.Simb.add('{');
        this.Simb.add('}');
        this.Simb.add(',');
        this.Simb.add(';');

    }

    public void teste() {
        System.out.println(sysPalavras);
    }

    public ArrayList<String> getSysPalavras() {
        return sysPalavras;
    }

    public void setSysPalavras(ArrayList<String> sysPalavras) {
        this.sysPalavras = sysPalavras;
    }

    public ArrayList<String> getOp() {
        return Op;
    }

    public void setOp(ArrayList<String> op) {
        Op = op;
    }

    public ArrayList<Character> getSimb() {
        return Simb;
    }

    public void setSimb(ArrayList<Character> simb) {
        Simb = simb;
    }

    public ArrayList<String> getTokens() {
        return tokens;
    }

    public void setTokens(ArrayList<String> tokens) {
        this.tokens = tokens;
    }

    public ArrayList<String> getSimbolos() {
        return simbolos;
    }

    public void setSimbolos(ArrayList<String> simbolos) {
        this.simbolos = simbolos;
    }

    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

    public int getIdCont() {
        return idCont;
    }

    public void setIdCont(int idCont) {
        this.idCont = idCont;
    }

    public String getLexema() {
        return lexema;
    }

    public void setLexema(String lexema) {
        this.lexema += lexema;
    }

    public void setChar(char letra) {
        this.lexema += letra;
    }

    public String getArgs() {
        return args;
    }

    public void setArgs(String args) {
        this.args = args;
    }

    public Pattern getId() {
        return id;
    }

    public void setId(Pattern id) {
        this.id = id;
    }

    // Criar situações

    public void categorizar() {
        if (this.lexema == "") {
            return;
        } else if (Operador(lexema)) {
            this.tokens.add("Operador: " + lexema);
            this.lexema = "";
        } else if (isNum(lexema)) {
            if (NumInt(lexema)) {
                this.tokens.add("Num_int: " + lexema);
            } else if (NumDouble(lexema)) {
                this.tokens.add("Num_double: " + lexema);
                this.lexema = "";
            }
        } else if (sysPalavras(lexema)) {
            this.tokens.add("Palavra do Sys: " + lexema);
            this.lexema = "";
        } else if (Ids(lexema)) {
            if (SinsID(lexema)) {
                for (int i = 0; i < simbolos.size(); i++) {
                    if (simbolos.get(i).equals(lexema)) {
                        i++;
                        this.tokens.add("id(" + i + "): " + lexema);
                    }
                }
                this.lexema = "";
            } else {
                idCont++;
                this.simbolos.add("Id(" + this.idCont + "): " + lexema);
                this.lexema = "";
            }
        } else if (texto(lexema)) {
            this.tokens.add("Texto: " + lexema);
            this.lexema = "";
        } else if (comentario(lexema)) {
            for (int i = this.pos; this.args.charAt(i) != '\n'; i++) {
                this.pos = i;
                this.lexema += this.args.charAt(i);
            }
            System.out.println("Comentário em linha: " + this.lexema);
            this.lexema = "";
        } else if (lexema.equals("\n")) {
            lexema = "";
            this.lexema = "";
        } else {
            System.out.println("LEXEMA NÃO RECONHECIDO: " + lexema);
            this.lexema = "";
        }
    }

    public void lista() {
        for (int i = 0; i < simbolos.size(); i++) {
            System.out.println(simbolos.get(i));
        }
        for (int i = 0; i < tokens.size(); i++) {
            System.out.println(tokens.get(i));
        }
    }

    private boolean Operador(String lexema) {
        return this.Op.contains(lexema);
    }

    public boolean SimboloEspc(char lexema) {
        if (this.Simb.contains(lexema)) {
            this.lexema = "";
            return true;
        }
        return false;
    }

    private boolean sysPalavras(String lexema) {
        return this.sysPalavras.contains(lexema);
    }

    public boolean NumInt(String lexema) {

        for (int i = 0; i < lexema.length(); i++) {
            char letra = lexema.charAt(i);
            if (!(letra >= '0' && letra <= '9') && letra != '.') {
                return false;
            }

        }
        return true;
    }

    public boolean NumDouble(String lexema) {
        boolean flag = false;
        for (int i = 0; i < lexema.length(); i++) {
            char letra = lexema.charAt(i);

            if (letra == '.' && lexema.length() - i > 0
                    && (lexema.charAt(i + 1) >= '0' && lexema.charAt(i + 1) <= '9')) {
                flag = true;
            }
            if (letra != '.' && (letra >= '0' && letra <= '9')) {
                flag = false;
            }

        }
        return flag;
    }

    private boolean isNum(String lexema) {
        if (lexema.isEmpty() || lexema.charAt(0) == '.' || lexema.charAt(lexema.length() - 1) == '.') {
        }
        int count = 0;
        for (int i = 0; i < lexema.length(); i++) {
            char analiseChar = lexema.charAt(i);
            if (analiseChar == '.') {
                count++;
                if (count == 2) {
                    return false;
                }
            }
            if (!(analiseChar >= '0' && analiseChar <= '9') && analiseChar != '.') {
                return false;
            }
        }
        return true;
    }

    private boolean Ids(String lexema) {
        Matcher matcher = id.matcher(this.lexema);
        return matcher.matches();
    }

    private boolean SinsID(String lexema) {
        return this.simbolos.contains(lexema);
    }

    private boolean texto(String lexema) {
        int aux = 0;
        char letra;
        for (int i = 0; i < lexema.length(); i++) {
            letra = lexema.charAt(i);
            if (i == 0 && letra != '"') {
                return false;
            } else if (letra == '"') {
                aux++;
                if (aux == 2) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean comentario(String lexema) {
        if (lexema.isEmpty()) { // caso padrão
            return false;
        }

        int aux = 0;

        for (int i = 0; i < lexema.length(); i++) {
            char analiseChar = lexema.charAt(i);

            if (analiseChar == '/') {

                aux += 1;

            } else if (analiseChar != '/') {
                aux -= 1;
            }

            if (aux == 2) {
                return true;
            }

        }

        return false;
    }
}
