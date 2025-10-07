public class Token {
    // Constantes para definir os tipos de token
    // Usamos números altos para não confundir com os caracteres (como '+' e '-')
    public static final int NUM = 256; 
    public static final int EOF = 257; // EOF = End of File (Fim do Arquivo)

    public final int type;  // O tipo do token (NUM, '+', '-', EOF)
    public final int value; // O valor, caso o token seja um número

    public Token(int type, int value) {
        this.type = type;
        this.value = value;
    }

    @Override
    public String toString() {
        if (type == NUM) {
            return "Token(NUM, " + value + ")";
        }
        if (type == EOF) {
            return "Token(EOF)";
        }
        return "Token('" + (char)type + "')";
    }
}
