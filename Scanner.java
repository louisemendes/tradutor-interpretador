public class Scanner {
    private byte[] input; // A expressão de entrada
    private int current;  // A posição de leitura

    // Construtor
    public Scanner(byte[] input) {
        this.input = input;
        this.current = 0;
    }

    // Método para "espiar" o caractere atual
    private char peek() {
        if (current < input.length) {
            return (char) input[current];
        }
        return '\0';
    }

    // Método para avançar para o próximo caractere
    private void advance() {
        char ch = peek();
        if (ch != '\0') {
            current++;
        }
    }

    // Método que o Parser vai chamar para pegar o próximo "token" (por enquanto, um caractere)
    public char nextToken() {
        char ch = peek();

        // Se for um dígito, retorna o dígito
        if (Character.isDigit(ch)) {
            advance();
            return ch;
        }

        // Se for um operador, retorna o operador
        switch (ch) {
            case '+':
            case '-':
                advance();
                return ch;
        }

        // Se não for nada que conhecemos, retorna o caractere nulo
        return '\0';
    }
}
