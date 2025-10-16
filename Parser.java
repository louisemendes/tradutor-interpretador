import java.util.ArrayList;
import java.util.List;

public class Parser {

    private Scanner scanner;      
    private Token lookahead;
    private List<String> commands;      

    // O construtor agora inicializa o scanner e já pega o primeiro token para análise.
    // Isso prepara o "lookahead" para o método parse().
    public Parser(byte[] input) {
        this.scanner = new Scanner(input);
        this.lookahead = scanner.nextToken(); // Pega o primeiro token para começar
        this.commands = new ArrayList<>();
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


    public List<String> parse() {
        expr();
        // Depois de processar a expressão, verifico se cheguei ao fim do arquivo.
        // Se não, significa que há caracteres sobrando que não fazem parte da expressão.
        if (lookahead.type != Token.EOF) {
            throw new Error("Erro de sintaxe: caracteres inesperados no final da expressão.");
        }
        return commands; 
    }
    
// O novo 'expr()'. Ele só se preocupa com SOMA e SUBTRAÇÃO.
    private void expr() {
        // Ele não lida mais com números diretamente, ele chama term() para isso.
        term();

        //O loop agora só reage a '+' e '-'.
        while (lookahead.type == '+' || lookahead.type == '-') {
            int op = lookahead.type;
            match(op); // Consome o operador ('+' ou '-')
            term();    // Lê o próximo termo (o número depois do operador)

            // Emite o comando pós-fixado correspondente à operação que encontramos.
            if (op == '+') {
                commands.add("add");
            } else if (op == '-') {
                commands.add("sub");
            }
        }
    }
        
    

    //O novo 'term()'. Ele só se preocupa com MULTIPLICAÇÃO e DIVISÃO.
    private void term() {
        // Ele chama factor() para pegar os números.
        factor();

        // O loop dele só reage a '*' e '/'.
        while (lookahead.type == '*' || lookahead.type == '/') {
            int op = lookahead.type;
            match(op);
            factor(); // Pede para o próximo fator ser resolvido.

            if (op == '*') {
                commands.add("mul");
            } else if (op == '/') {
                commands.add("div");
            }
        }
    }

    // O método factor() foi atualizado para lidar com o MENOS UNÁRIO (números negativos)
    // e também com parênteses.
    // Gramática: factor -> ('-') factor | NUMBER | '(' expr ')'
    private void factor() {

        //Verifica se o token é um sinal de menos.
        if (lookahead.type == '-') {
            // 1. Consome o token '-'
            match('-');
            // 2. Chama o próprio método 'factor' novamente para processar o que vem a seguir
            //    (seja um número, como em "-5", ou uma expressão, como em "-(2+3)").
            factor();
            // 3. Emite um novo comando para negar o resultado da última operação.
            commands.add("neg");
        }

        // Se o token for um número, acontece o que ja estava previsto antes.
        else if (lookahead.type == Token.NUM) {
            commands.add("push " + lookahead.value);
            match(Token.NUM);
        }
        //se o token for um parêntese de abertura...
        else if (lookahead.type == '(') {
            // 1. Consome o '('
            match('(');
            // 2. Chama o método principal 'expr()' para resolver TUDO que está dentro do parêntese.
            //    Esta é a chamada recursiva que faz a mágica acontecer.
            expr();
            // 3. Ao final, exige que haja um parêntese de fechamento.
            match(')');
          } 
        
        // Se não for nem um número nem um '(', é um erro.
         else {
            throw new Error("Erro de sintaxe: esperado um número, mas encontrei " + lookahead.type);
        }
    }


}