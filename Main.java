import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {
        // 1. Defina a expressão de entrada que você quer calcular.
        String input = "(10 + 5) * -2";

        // 2. Cria uma instância do nosso Parser, entregando a expressão a ele.
        Parser parser = new Parser(input.getBytes());

        // 3. Pede ao Parser para traduzir a expressão. Ele retorna uma lista de comandos.
        List<String> commands = parser.parse();
        
        // 4. Cria uma instância do nosso Interpretador, entregando a lista de comandos a ele.
        Interpreter interpreter = new Interpreter(commands);

        // 5. Pede ao Interpretador para executar os comandos e calcular o resultado.
        int result = interpreter.run();

        // 6. Imprime o resultado final!
        System.out.println("A expressão '" + input + "' resulta em: " + result);
    }
}