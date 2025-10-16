import java.util.List;
import java.util.Stack;

public class Interpreter {

    // O interpretador guarda a lista de comandos que o Parser gerou.
    private List<String> commands;

    // A ferramenta principal de trabalho é uma Pilha (Stack) de números inteiros.
    private Stack<Integer> stack;

    // O construtor recebe os comandos e inicializa a pilha.
    public Interpreter(List<String> commands) {
        this.commands = commands;
        this.stack = new Stack<>();
    }

    // Este é o método principal que executa a "mágica".
    public int run() {
        // Itera sobre cada comando da lista.
        for (String command : commands) {
            
            // Se o comando começa com "push", é um número.
            if (command.startsWith("push")) {
                // Quebramos a string "push 10" em ["push", "10"]
                String[] parts = command.split(" ");
                // Pegamos a segunda parte "10", convertemos para inteiro...
                int value = Integer.parseInt(parts[1]);
                // ...e a empilhamos.
                stack.push(value);
            } 
            // Para os operadores, a lógica é parecida.
            else if (command.equals("add")) {
                int b = stack.pop(); // Pega o segundo operando
                int a = stack.pop(); // Pega o primeiro operando
                stack.push(a + b);   // Empilha o resultado
            } 
            else if (command.equals("sub")) {
                int b = stack.pop();
                int a = stack.pop();
                stack.push(a - b);
            } 
            else if (command.equals("mul")) {
                int b = stack.pop();
                int a = stack.pop();
                stack.push(a * b);
            } 
            else if (command.equals("div")) {
                int b = stack.pop();
                int a = stack.pop();
                stack.push(a / b);
            } 
            else if (command.equals("neg")) {
                int a = stack.pop(); // Pega o único operando
                stack.push(-a);      // Empilha sua versão negada
            }
        }

        // No final de tudo, o resultado da expressão é o único número que sobrou na pilha.
        return stack.pop();
    }
}
