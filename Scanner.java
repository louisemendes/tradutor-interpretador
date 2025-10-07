
public class Scanner {
    private byte[] input;
    private int current;

    public Scanner(byte[] input) {
        this.input = input;
        this.current = 0;
    }

    // Os métodos peek() e advance() continuam os mesmos,
    // são meus auxiliares para olhar e avançar na entrada.
    private char peek() {
        if (current < input.length) {
            return (char) input[current];
        }
        return '\0';
    }

    private void advance() {
        if (current < input.length) {
            current++;
        }
    }
    
    // O método nextToken() foi o que eu refatorei para lidar com múltiplos dígitos.
    public Token nextToken() {
        // Decidi adicionar este loop para ignorar espaços em branco.
        // Assim, meu tradutor fica mais flexível e aceita entradas como "99 - 5".
        while (Character.isWhitespace(peek())) {
            advance();
        }

        char ch = peek();

        // Esta é a mudança principal: a lógica para números.
        // Verifico se o caractere atual é um dígito.
        if (Character.isDigit(ch)) {
            int value = 0;
            // Se for, inicio um loop para ler todos os dígitos seguintes que formam o número.
            while (Character.isDigit(peek())) {
                // Vou construindo o valor do número.
                value = value * 10 + Character.getNumericValue(peek());
                advance();
            }
            // Quando o loop acaba, retorno um Token do tipo NUM com o valor completo que montei.
            return new Token(Token.NUM, value);
        }

        // Para os operadores, a lógica é simples: se encontrar um,
        // avanço e retorno o Token correspondente.
        switch (ch) {
            case '+':
                advance();
                return new Token('+', 0); 
            case '-':
                advance();
                return new Token('-', 0);
        }

        // Se o caractere for nulo ('\0'), sei que cheguei ao fim da entrada.
        if (ch == '\0') {
            return new Token(Token.EOF, 0);
        }

        // Adicionei esta linha para tratar qualquer outro caractere que não seja válido.
        // Além de ser uma boa prática, isso resolve o erro de "missing return statement"
        // que o compilador Java apontaria.
        throw new Error("Caractere inválido: " + ch);
    }
}
