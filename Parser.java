public class Parser {
    // Atributos da classe
    private byte[] input;   
    private int current;     

    // Construtor
    public Parser(byte[] input) {
        this.input = input;
        this.current = 0;
    }

    // Método principal
    public void parse() {
        expr();
    }

    // --- Métodos Auxiliares ---
    private char peek() {
        if (current < input.length) {
            return (char) input[current];
        }
        return '\0';
    }

    private void match(char c) {
        if (c == peek()) {
            current++;
        } else {
            throw new Error("syntax error");
        }
    }
    // Este método representa a regra: expr -> digit oper
void expr() {
    digit(); // Primeiro, esperamos um dígito
    oper();  // Depois, esperamos um operador (ou o fim da expressão)
}
// Este método representa a regra: digit -> 0|1|...|9
void digit() {
    if (Character.isDigit(peek())) { // A função isDigit() checa se o caractere é um número
        System.out.println("push " + peek()); // Ação: Imprime "push" e o número
        match(peek()); // Consome o caractere (avança para o próximo)
    } else {
        throw new Error("syntax error"); // Se não for um dígito, lança um erro
    }
}
// Este método representa a regra: oper -> + digit oper | - digit oper | ε
void oper() {
    if (peek() == '+') { // Se o caractere atual for '+'
        match('+');        // Consome o '+'
        digit();           // Espera um dígito
        System.out.println("add"); // Ação: Imprime "add"
        oper();            // Chama a si mesmo para continuar a expressão
    } else if (peek() == '-') { // Se o caractere atual for '-'
        match('-');        // Consome o '-'
        digit();           // Espera um dígito
        System.out.println("sub"); // Ação: Imprime "sub"
        oper();            // Chama a si mesmo para continuar a expressão
    }
    // Se não for nem '+' nem '-', o método simplesmente termina (caso do ε, o vazio)
}
}