class Program
{
    static void Main(string[] args)
    {
        Console.WriteLine("Bem-vindo ao programa de criação de grafos!");

        // Coleta de informações do grafo
        Console.Write("Digite o número de vértices: ");
        int numVertices = int.Parse(Console.ReadLine());

        Console.Write("Digite o número de arestas: ");
        int numArestas = int.Parse(Console.ReadLine());

        Console.WriteLine("Escolha a representação do grafo:");
        Console.WriteLine("1 - Matriz de Adjacência");
        Console.WriteLine("2 - Lista de Adjacência");
        Console.WriteLine("3 - Matriz de Incidência");
        Console.Write("Sua escolha: ");
        int escolha = int.Parse(Console.ReadLine());

        Grafo grafo = null;

        switch (escolha)
        {
            case 1:
                grafo = new MatrizAdjacencia(numVertices);
                break;
            case 2:
                grafo = new ListaAdjacencia(numVertices);
                break;
            case 3:
                grafo = new MatrizIncidencia(numVertices, numArestas);
                break;
            default:
                Console.WriteLine("Opção inválida.");
                return;
        }

        // Inserção de arestas
        for (int i = 0; i < numArestas; i++)
        {
            Console.WriteLine($"Aresta {i + 1}:");
            Console.Write("Vértice de origem: ");
            int origem = int.Parse(Console.ReadLine());

            Console.Write("Vértice de destino: ");
            int destino = int.Parse(Console.ReadLine());

            Console.Write("Peso da aresta: ");
            int peso = int.Parse(Console.ReadLine());

            grafo.AdicionarAresta(origem, destino, peso);
        }

        // Impressão da representação do grafo
        grafo.Imprimir();
    }
}