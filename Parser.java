public class Parser {

    // precisei mudar os atributos da classe para trabalhar com TOKENS, não mais com 'char'
    private Scanner scanner;      // O Parser continua tendo um Scanner
    private Token lookahead;      // Antigo 'currentToken', agora guarda um Token completo (tipo e valor)

    // O construtor agora inicializa o scanner e já pega o primeiro token para análise.
    // Isso prepara o "lookahead" para o método parse().
    public Parser(byte[] input) {
        this.scanner = new Scanner(input);
        this.lookahead = scanner.nextToken(); // Pega o primeiro token para começar
    }

    // Método que consome o token atual e avança para o próximo.
    // É como dizer: "Ok, já processei esse, me dê o próximo".
    private void consume() {
        this.lookahead = scanner.nextToken();
    }

    // O match foi atualizado. Agora ele compara o TIPO do token, não mais um caractere.
    private void match(int type) {
        if (lookahead.type == type) {
            consume(); // Se o tipo bate, consome o token e avança
        } else {
            // Se não, lança um erro de sintaxe mostrando o que esperava e o que encontrou.
            throw new Error("Erro de sintaxe. Esperava " + type + ", mas encontrou " + lookahead.type);
        }
    }

    // O método parse() é o ponto de entrada, ele chama a regra inicial da gramática.
    public void parse() {
        expr();
        // Depois de processar a expressão, verifico se cheguei ao fim do arquivo.
        // Se não, significa que há caracteres sobrando que não fazem parte da expressão.
        if (lookahead.type != Token.EOF) {
            throw new Error("Erro de sintaxe: caracteres inesperados no final da expressão.");
        }
    }
    
    // O método expr() foi refeito para lidar com a sequência de operações (ex: 99-5+10).
    // Ele implementa a regra: expr -> term (('+'|'-') term)*
    // Isso significa que ele lê um termo, e depois, ENQUANTO encontrar um '+' ou '-',
    // ele continua lendo os próximos termos e aplicando as operações.
    private void expr() {
        // Toda expressão começa com um termo (um número).
        term();

        // Loop para lidar com as operações de soma e subtração que vêm depois.
        while (lookahead.type == '+' || lookahead.type == '-' || lookahead.type == '*' || lookahead.type == '/') {
            int op = lookahead.type;
            match(op); // Consome o operador ('+' ou '-')
            term();    // Lê o próximo termo (o número depois do operador)

            // Emite o comando pós-fixado correspondente à operação que encontramos.
            if (op == '+') {
                System.out.println("add");
            } else if (op == '-') {
                System.out.println("sub");
            } else if (op == '*') {
                System.out.println("mul");
            } else if (op == '/') {
                System.out.println("div");
            }
        }
    }
        
    

    // O antigo método "digit()" agora é "term()", pois ele lida com um NÚMERO, não só um dígito.
    // A função dele é garantir que a próxima coisa na expressão seja um número.
    private void term() {
        // Verifico se o token atual é do tipo NUM
        if (lookahead.type == Token.NUM) {
            // Se for, emito o comando "push" com o VALOR do token, que agora pode ser > 9.
            System.out.println("push " + lookahead.value);
            match(Token.NUM); // Finalizo a leitura desse número e avanço.
        } else {
            throw new Error("Erro de sintaxe: esperado um número, mas encontrei " + lookahead.type);
        }
    }
}