public class Main {
    public static void main(String[] args) throws Exception {
        // 1. Define a expressão de entrada que vamos traduzir
        String input = "99-5+10"; 

        // 2. Cria uma instância do nosso Parser, entregando a expressão a ele
        Parser p = new Parser(input.getBytes());

        // 3. Chama o método principal que inicia o processo de tradução
        p.parse();
    }
}