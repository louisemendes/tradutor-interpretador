public class Parser {
    // os atributos mudaram.
    private Scanner scan;         // O Parser AGORA TEM um Scanner
    private char currentToken;   // E guarda o token (caractere) atual

    // O construtor foi atualizado para criar e usar o Scanner
    public Parser(byte[] input) {
        this.scan = new Scanner(input);    // Cria o nosso novo Scanner
        this.currentToken = scan.nextToken(); // Pega o primeiro token para começar
    }

    // Método auxiliar para pegar o próximo token do Scanner
    private void nextToken() {
        this.currentToken = scan.nextToken();
    }

    // O método match foi atualizado para usar o token atual
    private void match(char t) {
        if (currentToken == t) {
            nextToken(); // Pede ao Scanner pelo próximo
        } else {
            throw new Error("syntax error");
        }
    }

    // O método expr não muda
    void expr() {
        digit();
        oper();
    }

    // O método digit foi atualizado para usar o token atual
    void digit() {
        if (Character.isDigit(currentToken)) {
            System.out.println("push " + currentToken);
            match(currentToken);
        } else {
            throw new Error("syntax error");
        }
    }

    // O método oper foi atualizado para usar o token atual
    void oper() {
        if (currentToken == '+') {
            match('+');
            digit();
            System.out.println("add");
            oper();
        } else if (currentToken == '-') {
            match('-');
            digit();
            System.out.println("sub");
            oper();
        }
    }
    
    public void parse() {
        expr();
    }
}