import java.util.ArrayList; // Importa a classe ArrayList, que será utilizada para armazenar os vértices e arestas
import java.util.Collections; // Importa a classe Collections para ordenar os vértices
import java.util.Comparator; // Importa a classe Comparator para comparar os vértices ao ordená-los
import java.util.LinkedList; // Importa a classe LinkedList para representar a lista de adjacência
import java.util.List; // Importa a interface List para manipulação de listas
import java.util.PriorityQueue; // Importa a classe PriorityQueue para a implementação do algoritmo de Dijkstra

// Define a classe GrafoDirecionado, que implementa a interface Cloneable
public class GrafoDirecionado implements Cloneable {
    // Declaração dos atributos da classe
    private ArrayList<Vertice> vertices; // Lista de vértices do grafo
    private ArrayList<Aresta> arestas; // Lista de arestas do grafo
    private int matrAdj[][]; // Matriz de adjacência para representar o grafo
    private int matrizInc[][]; // Matriz de incidência para representar o grafo
    private LinkedList<LinkedList<Vertice>> listaAdjacencia; // Lista de adjacência para representar o grafo

    // Construtor da classe GrafoDirecionado
    public GrafoDirecionado() {
        this.vertices = new ArrayList<Vertice>(); // Inicializa a lista de vértices como um ArrayList vazio
        this.arestas = new ArrayList<Aresta>(); // Inicializa a lista de arestas como um ArrayList vazio
    }

    // Método para adicionar um vértice ao grafo
    public void adicionarVertice(int dado) {
        Vertice verticeNovo = new Vertice(dado); // Cria um novo objeto Vertice com o valor 'dado'
        this.vertices.add(verticeNovo); // Adiciona o novo vértice à lista de vértices

        // Ordena a lista de vértices com base no valor do atributo 'dado' de cada vértice
        Collections.sort(vertices, Comparator.comparingInt(Vertice::getDado));
    }

    // Método para adicionar uma aresta ao grafo
    public void adicionarAresta(int peso, int dadoInicio, int dadoFim) {
        Vertice inicio = this.recuperarVertice(dadoInicio); // Recupera o vértice de início usando o valor 'dadoInicio'
        Vertice fim = this.recuperarVertice(dadoFim); // Recupera o vértice de fim usando o valor 'dadoFim'
        Aresta aresta = new Aresta(peso, inicio, fim); // Cria um novo objeto Aresta com o peso e os vértices de início e fim

        // Adiciona a aresta à lista de arestas de saída do vértice de início
        inicio.adicionarArestaSaida(aresta);
        // Adiciona a aresta à lista de arestas de entrada do vértice de fim
        fim.adicionarArestaEntrada(aresta);

        this.arestas.add(aresta); // Adiciona a aresta à lista de arestas do grafo
    }

    // Método para recuperar um vértice pelo seu valor 'dado'
    public Vertice recuperarVertice(int dado) {
        for (Vertice vertice : this.vertices) { // Percorre a lista de vértices
            if (vertice.getDado() == dado) { // Compara o valor 'dado' com o valor do vértice atual
                return vertice; // Retorna o vértice se encontrado
            }
        }
        return null; // Retorna null se não encontrar o vértice
    }

    // Método para gerar a matriz de adjacência
    public void gerarMatrizAdjacencia() {
        this.matrAdj = new int[vertices.size()][vertices.size()]; // Inicializa a matriz de adjacência com o tamanho da lista de vértices

        // Percorre a matriz de adjacência
        for (int i = 0; i < vertices.size(); i++) {
            for (int j = 0; j < vertices.size(); j++) {
                Vertice verticeI = vertices.get(i); // Recupera o vértice da linha i
                Vertice verticeJ = vertices.get(j); // Recupera o vértice da coluna j
                // Verifica se existe uma aresta entre verticeI e verticeJ
                if (temAresta(verticeI, verticeJ))
                    matrAdj[i][j] = 1; // Define 1 se existir uma aresta
                else
                    matrAdj[i][j] = 0; // Define 0 se não existir uma aresta
            }
        }
    }

    // Método para exibir a matriz de adjacência como string
    public String exibirMatrizAdjacencia() {
        StringBuilder str = new StringBuilder(); // StringBuilder para construir a string da matriz
        str.append("MATRIZ DE ADJACÊNCIA" + "\n"); // Adiciona o título da matriz

        // Percorre a matriz de adjacência e adiciona cada elemento à string
        for (int i = 0; i < vertices.size(); i++) {
            for (int j = 0; j < vertices.size(); j++) {
                str.append(matrAdj[i][j] + " ");
            }
            str.append("\n"); // Adiciona uma nova linha após cada linha da matriz
        }

        return str.toString(); // Retorna a string da matriz de adjacência
    }

    // Método para gerar a matriz de incidência
    public void gerarMatrizIncidencia() {
        this.matrizInc = new int[vertices.size()][arestas.size()]; // Inicializa a matriz de incidência com o tamanho da lista de vértices e arestas

        // Percorre a lista de arestas
        for (int j = 0; j < arestas.size(); j++) {
            Aresta aresta = arestas.get(j); // Recupera a aresta atual
            Vertice inicio = aresta.getInicio(); // Recupera o vértice de início da aresta
            Vertice fim = aresta.getFim(); // Recupera o vértice de fim da aresta

            int indiceInicio = vertices.indexOf(inicio); // Recupera o índice do vértice de início
            int indiceFim = vertices.indexOf(fim); // Recupera o índice do vértice de fim

            matrizInc[indiceInicio][j] = -1; // Marca a saída da aresta com -1
            matrizInc[indiceFim][j] = 1; // Marca a entrada da aresta com 1
        }
    }

    // Método para exibir a matriz de incidência como string
    public String exibirMatrizIncidencia() {
        StringBuilder str = new StringBuilder(); // StringBuilder para construir a string da matriz
        str.append("MATRIZ DE INCIDÊNCIA" + "\n"); // Adiciona o título da matriz

        // Percorre a matriz de incidência e adiciona cada elemento à string
        for (int i = 0; i < vertices.size(); i++) {
            for (int j = 0; j < arestas.size(); j++) {
                str.append(matrizInc[i][j] + " ");
            }
            str.append("\n"); // Adiciona uma nova linha após cada linha da matriz
        }

        return str.toString(); // Retorna a string da matriz de incidência
    }

    // Método para gerar a lista de adjacência
    public void gerarListaAdjacencia() {
        this.listaAdjacencia = new LinkedList<>(); // Inicializa a lista de adjacência como uma LinkedList

        // Percorre a lista de vértices
        for (Vertice v : vertices) {
            LinkedList<Vertice> vizinhos = new LinkedList<>(); // Cria uma lista de vizinhos para o vértice atual

            // Percorre a lista de arestas
            for (Aresta a : arestas) {
                if (a.getInicio().equals(v)) // Verifica se a aresta inicia no vértice atual
                    vizinhos.add(a.getFim()); // Adiciona o vértice de fim à lista de vizinhos
            }
            listaAdjacencia.add(vizinhos); // Adiciona a lista de vizinhos à lista de adjacência
        }
    }

    // Método para exibir a lista de adjacência como string
    public String exibirListaAdjacencia() {
        StringBuilder str = new StringBuilder(); // StringBuilder para construir a string da lista
        str.append("LISTA DE ADJACÊNCIA\n"); // Adiciona o título da lista

        // Percorre a lista de adjacência
        for (int i = 0; i < listaAdjacencia.size(); i++) {
            str.append(vertices.get(i).getDado() + ": "); // Adiciona o valor do vértice atual à string
            LinkedList<Vertice> vizinhos = listaAdjacencia.get(i); // Recupera a lista de vizinhos do vértice atual

            // Percorre a lista de vizinhos e adiciona cada vizinho à string
            for (Vertice vizinho : vizinhos) {
                if (!vizinho.equals(vizinhos.get(vizinhos.size() - 1)))
                    str.append(vizinho.getDado() + " -> ");
                else
                    str.append(vizinho.getDado());
            }
            str.append("\n"); // Adiciona uma nova linha após cada lista de vizinhos
        }

        return str.toString(); // Retorna a string da lista de adjacência
    }

    // Método para verificar se existe uma aresta entre dois vértices
    public boolean temAresta(Vertice verticeI, Vertice verticeJ) {
        // Percorre a lista de arestas
        for (Aresta aresta : arestas) {
            // Verifica se a aresta inicia em verticeI e termina em verticeJ
            if (aresta.getInicio() == verticeI && aresta.getFim() == verticeJ) {
                return true; // Retorna true se existir a aresta
            }
        }
        return false; // Retorna false se não existir a aresta
    }

    // Método para verificar a densidade do grafo
    public double verificarDensidadeGrafo() {
        // Calcula a densidade do grafo como a razão entre o número de arestas e o número máximo possível de arestas
        return (arestas.size()) / ((vertices.size()) * (vertices.size() - 1));
    }

    // Método para imprimir as arestas adjacentes a uma aresta informada
    public String imprimirArestasAdjacentes(Vertice origem, Vertice destino) {
        StringBuilder str = new StringBuilder(); // StringBuilder para construir a string de saída
        String arestasAdjacentes = ""; // String para armazenar as arestas adjacentes

        // Percorre a lista de arestas
        for (Aresta a : arestas) {
            // Verifica se a aresta é diferente da aresta informada
            if (!(a.getInicio().getDado() == origem.getDado() && a.getFim().getDado() == destino.getDado())) {
                // Verifica se a aresta inicia no vértice de origem
                if (a.getInicio().getDado() == origem.getDado()) {
                    arestasAdjacentes += a.getInicio().getDado() + a.getFim().getDado(); // Adiciona a aresta à string de arestas adjacentes
                }
            }
        }

        // Verifica se não há arestas adjacentes
        if (arestasAdjacentes.equals("")) {
            str.append("Não existem arestas adjacentes a aresta " + origem + destino);
        } else {
            str.append("As arestas adjacentes a aresta " + origem + destino + " são: " + arestasAdjacentes);
        }

        return str.toString(); // Retorna a string de arestas adjacentes
    }

    // Método para imprimir os vértices adjacentes a um vértice informado
    public String imprimirVerticesAdjacentes(Vertice vertice) {
        StringBuilder str = new StringBuilder("Os vértices adjacentos do vértice " + vertice.getDado() + " são: ");

        // Percorre a lista de vértices
        for (Vertice v : vertices) {
            // Verifica se a lista de arestas de entrada do vértice atual é igual à lista de arestas de saída do vértice informado
            if (v.getArestaEntrada().equals(vertice.getArestaSaida()))
                str.append(v.getDado() + ", ");
        }

        return str.toString(); // Retorna a string de vértices adjacentes
    }

    // Método para imprimir as arestas incidentes a um vértice informado
    public String imprimirArestasIncidentes(Vertice vertice) {
        StringBuilder str = new StringBuilder("As arestas incidentes do vértice " + vertice.getDado() + " são:");

        // Percorre a lista de arestas
        for (Aresta a : arestas) {
            // Verifica se a aresta é incidente ao vértice informado
            if (a.getInicio().equals(vertice) || a.getFim().equals(vertice))
                str.append(a.getInicio().getDado() + a.getFim().getDado() + ", ");
        }

        return str.toString(); // Retorna a string de arestas incidentes
    }

    // Método para imprimir os vértices incidentes a uma aresta informada
    public String imprimirVerticesIncidentes(int arestaOrigem, int arestaDestino) {
        String verticesIncidentes = ""; // String para armazenar os vértices incidentes

        // Percorre a lista de vértices
        for (Vertice v : vertices) {
            // Percorre a lista de arestas de entrada do vértice atual
            for (Aresta a : v.getArestaEntrada()) {
                // Verifica se a aresta é incidente à aresta informada
                if (a.getInicio().getDado() == arestaOrigem || a.getFim().getDado() == arestaDestino) {
                    verticesIncidentes += v.getDado() + ", ";
                }
            }

            // Percorre a lista de arestas de saída do vértice atual
            for (Aresta a : v.getArestaSaida()) {
                // Verifica se a aresta é incidente à aresta informada
                if (a.getInicio().getDado() == arestaOrigem || a.getFim().getDado() == arestaDestino) {
                    verticesIncidentes += v.getDado() + ", ";
                }
            }
        }

        StringBuilder str = new StringBuilder();

        // Verifica se não há vértices incidentes
        if (verticesIncidentes == "") {
            str.append("Não existem vértices adjacentes a essa aresta");
        } else {
            str.append("Os vértices incidentes a aresta " + arestaOrigem + arestaDestino + " são: " + verticesIncidentes);
        }

        return str.toString(); // Retorna a string de vértices incidentes
    }

    // Método para imprimir o grau de um vértice informado
    public String imprimirGrauVertice(Vertice v) {
        int grauDeEntrada = v.getArestaEntrada().size(); // Calcula o grau de entrada do vértice
        int grauDeSaida = v.getArestaSaida().size(); // Calcula o grau de saída do vértice

        StringBuilder str = new StringBuilder("O grau de entrada do vértice " + v.getDado() + " é: " + grauDeEntrada
                + "\n" + "O grau de saída do vértice " + v.getDado() + " é: " + grauDeSaida);

        return str.toString(); // Retorna a string do grau do vértice
    }

    // Método para verificar se dois vértices são adjacentes
    public String verificarVerticesAdjacentes(int v1, int v2) {
        Vertice vertice1 = recuperarVertice(v1); // Recupera o primeiro vértice
        Vertice vertice2 = recuperarVertice(v2); // Recupera o segundo vértice

        StringBuilder str = new StringBuilder();

        // Verifica se os vértices são adjacentes
        if (vertice1.getArestaEntrada().equals(vertice2.getArestaSaida())
                || vertice1.getArestaSaida().equals(vertice2.getArestaEntrada()))
            str.append("Os dois vértices são adjacentes");

        return str.toString(); // Retorna a string da verificação de adjacência
    }

    // Método para substituir o peso de uma aresta informada
    public String substituirPesoAresta(Vertice vertice1, Vertice vertice2, int novoPeso) {
        StringBuilder str = new StringBuilder();

        // Percorre a lista de arestas
        for (Aresta a : arestas) {
            // Verifica se a aresta é a aresta informada
            if (a.getInicio().equals(vertice1) && a.getFim().equals(vertice2)) {
                a.setPeso(novoPeso); // Define o novo peso da aresta
                str.append("A aresta " + vertice1.getDado() + vertice2.getDado() + " teve o valor alterado com sucesso!");
            } else {
                str.append("Aresta não encontra, não foi possível alterar o valor da aresta.");
            }
        }

        return str.toString(); // Retorna a string da substituição do peso da aresta
    }

    // Método para trocar dois vértices
    public void trocarDoisVertices(Vertice v1, Vertice v2) {
        List<Aresta> arestasEntradaTemp = v1.getArestaEntrada(); // Armazena temporariamente as arestas de entrada do primeiro vértice
        List<Aresta> arestasSaidaTemp = v1.getArestaSaida(); // Armazena temporariamente as arestas de saída do primeiro vértice

        v1.getArestaEntrada().clear(); // Limpa as arestas de entrada do primeiro vértice
        v1.getArestaSaida().clear(); // Limpa as arestas de saída do primeiro vértice

        v1.getArestaEntrada().addAll(v2.getArestaEntrada()); // Atribui as arestas de entrada do segundo vértice ao primeiro vértice
        v1.getArestaSaida().addAll(v2.getArestaSaida()); // Atribui as arestas de saída do segundo vértice ao primeiro vértice

        v2.getArestaEntrada().clear(); // Limpa as arestas de entrada do segundo vértice
        v2.getArestaSaida().clear(); // Limpa as arestas de saída do segundo vértice

        v2.getArestaEntrada().addAll(arestasEntradaTemp); // Atribui as arestas de entrada armazenadas temporariamente ao segundo vértice
        v2.getArestaSaida().addAll(arestasSaidaTemp); // Atribui as arestas de saída armazenadas temporariamente ao segundo vértice

        // Atualiza os vértices de início e fim das arestas do primeiro vértice
        for (Aresta a : v1.getArestaEntrada()) {
            a.setFim(v1);
        }

        for (Aresta a : v1.getArestaSaida()) {
            a.setInicio(v1);
        }

        // Atualiza os vértices de início e fim das arestas do segundo vértice
        for (Aresta a : v2.getArestaEntrada()) {
            a.setFim(v2);
        }

        for (Aresta a : v2.getArestaSaida()) {
            a.setInicio(v2);
        }
    }

    // Método de busca em largura (BFS)
    public void buscaEmLargura(int raiz) {
        ArrayList<Vertice> marcados = new ArrayList<Vertice>(); // Lista de vértices marcados
        ArrayList<Vertice> fila = new ArrayList<Vertice>(); // Fila de vértices para visitar

        Vertice atual = recuperarVertice(raiz); // Recupera o vértice raiz
        marcados.add(atual); // Marca o vértice atual
        System.out.print(atual.getDado()+" ");
        fila.add(atual); // Adiciona o vértice atual à fila
        while (fila.size() > 0) {
            Vertice visitado = fila.get(0); // Recupera o primeiro vértice da fila
            for (int i = 0; i < visitado.getArestaSaida().size(); i++) {
                Vertice proximo = visitado.getArestaSaida().get(i).getFim(); // Recupera o próximo vértice adjacente
                if (!marcados.contains(proximo)) { // Verifica se o vértice já foi marcado
                    marcados.add(proximo); // Marca o próximo vértice
                    System.out.print(proximo.getDado()+ " ");
                    fila.add(proximo); // Adiciona o próximo vértice à fila
                }
            }
            fila.remove(0); // Remove o vértice visitado da fila
        }
    }

    // Método de busca em profundidade (DFS)
    public void buscaEmProfundidade(int verticeInicial) {
        int tempo = 0;
        int[] tempoDescoberta = new int[vertices.size()]; // Array para armazenar o tempo de descoberta dos vértices
        int[] tempoTermino = new int[vertices.size()]; // Array para armazenar o tempo de término dos vértices
        Vertice[] predecessores = new Vertice[vertices.size()]; // Array para armazenar os predecessores dos vértices

        // Inicializa os arrays de descoberta, término e predecessores
        for (Vertice vertice : vertices) {
            tempoDescoberta[vertice.getDado()-1] = 0;
            tempoTermino[vertice.getDado()-1] = 0;
            predecessores[vertice.getDado()-1] = null;
        }

        Vertice verticeInicio = recuperarVertice(verticeInicial); // Recupera o vértice inicial
        if (verticeInicio != null) {
            tempo = buscaProfundidadeVisit(verticeInicio, tempo, tempoDescoberta, tempoTermino, predecessores); // Executa a busca em profundidade a partir do vértice inicial
        }

        // Verifica se algum vértice não foi descoberto e executa a busca em profundidade para esses vértices
        for (Vertice vertice : vertices) {
            if (tempoDescoberta[vertice.getDado()-1] == 0) {
                tempo = buscaProfundidadeVisit(vertice, tempo, tempoDescoberta, tempoTermino, predecessores);
            }   
        }

        // Imprime os tempos de descoberta e término de cada vértice
        for (int i = 0; i < vertices.size(); i++) {
            System.out.println("Vértice: " + i + ", Tempo de Descoberta: " + tempoDescoberta[i] + ", Tempo de Término: "
                    + tempoTermino[i]);
        }
    }

    // Método auxiliar para a busca em profundidade (DFS)
    private int buscaProfundidadeVisit(Vertice vertice, int tempo, int[] tempoDescoberta, int[] tempoTermino,
            Vertice[] predecessores) {
        tempo++;
        tempoDescoberta[vertice.getDado()] = tempo; // Define o tempo de descoberta do vértice

        List<Vertice> vizinhos = new ArrayList<>(); // Lista de vértices vizinhos
        // Adiciona os vértices adjacentes à lista de vizinhos
        for (Aresta aresta : vertice.getArestaSaida()) {
            vizinhos.add(aresta.getFim());
        }

        // Percorre a lista de vizinhos
        for (Vertice vizinho : vizinhos) {
            // Verifica se o vizinho ainda não foi descoberto
            if (tempoDescoberta[vizinho.getDado()-1] == 0) {
                predecessores[vizinho.getDado()-1] = vertice; // Define o vértice atual como predecessor do vizinho
                System.out.println(
                        "Visitar aresta {" + vertice.getDado() + ", " + vizinho.getDado() + "} - Aresta de árvore");

                tempo = buscaProfundidadeVisit(vizinho, tempo, tempoDescoberta, tempoTermino, predecessores); // Recursão para continuar a busca em profundidade
            } else if (tempoTermino[vizinho.getDado()-1] == 0 && !vizinho.equals(predecessores[vertice.getDado()-1])) {
                System.out.println(
                        "Visitar aresta {" + vertice.getDado() + ", " + vizinho.getDado() + "} - Aresta de retorno");
            }
        }
        tempo++;
        tempoTermino[vertice.getDado()] = tempo; // Define o tempo de término do vértice
        return tempo; // Retorna o tempo atualizado
    }

    // Método para executar o algoritmo de Dijkstra
    public String dijkstra(int verticeOrigem, int verticeDestino) {
        int[] dist = new int[vertices.size()]; // Array para armazenar as distâncias dos vértices
        Vertice[] pred = new Vertice[vertices.size()]; // Array para armazenar os predecessores dos vértices
        boolean[] visitado = new boolean[vertices.size()]; // Array para marcar os vértices visitados

        // Inicializa os arrays de distâncias, predecessores e visitados
        for (int i = 0; i < vertices.size(); i++) {
            dist[i] = Integer.MAX_VALUE;
            pred[i] = null;
            visitado[i] = false;
        }

        dist[verticeOrigem-1] = 0; // Define a distância do vértice de origem como 0
        PriorityQueue<Vertice> queue = new PriorityQueue<>(Comparator.comparingInt(v -> dist[v.getDado()-1])); // Fila de prioridade para os vértices
        queue.add(recuperarVertice(verticeOrigem)); // Adiciona o vértice de origem à fila

        // Executa o algoritmo de Dijkstra
        while (!queue.isEmpty()) {
            Vertice v = queue.poll(); // Remove o vértice com a menor distância da fila
            visitado[v.getDado()-1] = true; // Marca o vértice como visitado

            // Percorre as arestas de saída do vértice
            for (Aresta aresta : v.getArestaSaida()) {
                Vertice w = aresta.getFim(); // Recupera o vértice de fim da aresta
                int pesoAresta = aresta.getPeso(); // Recupera o peso da aresta

                if (!visitado[w.getDado()-1]) { // Verifica se o vértice de fim não foi visitado
                    int novaDist = dist[v.getDado()-1] + pesoAresta; // Calcula a nova distância

                    if (novaDist < dist[w.getDado()-1]) { // Verifica se a nova distância é menor que a distância atual
                        dist[w.getDado()-1] = novaDist; // Atualiza a distância do vértice de fim
                        pred[w.getDado()-1] = v; // Define o vértice atual como predecessor do vértice de fim
                        queue.add(w); // Adiciona o vértice de fim à fila
                    }
                }
            }
        }

        LinkedList<Vertice> caminho = new LinkedList<>(); // Lista para armazenar o caminho mínimo

        // Recupera o caminho mínimo
        for (Vertice atual = recuperarVertice(verticeDestino); atual != null; atual = pred[atual.getDado()-1]) {
            caminho.addFirst(atual); // Adiciona o vértice ao início da lista
        }

        if (dist[verticeDestino] == Integer.MAX_VALUE) { // Verifica se não há caminho entre os vértices
            return "Não há caminho do vértice " + verticeOrigem + " para o vértice " + verticeDestino;
        }

        StringBuilder resultado = new StringBuilder(); // StringBuilder para construir a string de saída
        resultado.append("Caminho mínimo do vértice ").append(verticeOrigem).append(" para o vértice ")
                .append(verticeDestino).append(":\n");
        for (Vertice vertice : caminho) { // Percorre a lista de vértices do caminho mínimo
            resultado.append(vertice.getDado()).append(" ");
        }
        resultado.append("\nDistância total: ").append(dist[verticeDestino-1]);

        return resultado.toString(); // Retorna a string do caminho mínimo
    }

    // Método para executar o algoritmo de Floyd-Warshall
    public String floydWarshall() {
        int n = vertices.size(); // Número de vértices

        int[][] dist = new int[n][n]; // Matriz de distâncias

        // Inicializa a matriz de distâncias
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i == j) {
                    dist[i][j] = 0; // Define a distância para si mesmo como 0
                } else {
                    dist[i][j] = Integer.MAX_VALUE; // Define a distância inicial como infinito
                }
            }
        }

        // Inicializa as distâncias das arestas
        for (Aresta aresta : arestas) {
            int i = aresta.getInicio().getDado()-1;
            int j = aresta.getFim().getDado()-1;
            dist[i][j] = aresta.getPeso(); // Define a distância como o peso da aresta
        }

        // Executa o algoritmo de Floyd-Warshall
        for (int k = 0; k < n; k++) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    // Verifica se a distância pode ser melhorada
                    if (dist[i][k] != Integer.MAX_VALUE && dist[k][j] != Integer.MAX_VALUE
                            && dist[i][j] > dist[i][k] + dist[k][j]) {
                        dist[i][j] = dist[i][k] + dist[k][j]; // Atualiza a distância
                    }
                }
            }
        }

        StringBuilder resultado = new StringBuilder(); // StringBuilder para construir a string de saída
        resultado.append("Matriz de distâncias mínimas:\n");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (dist[i][j] == Integer.MAX_VALUE) {
                    resultado.append("INF "); // Adiciona 'INF' se a distância for infinita
                } else {
                    resultado.append(dist[i][j]).append(" ");
                }
            }
            resultado.append("\n"); // Adiciona uma nova linha após cada linha da matriz
        }

        return resultado.toString(); // Retorna a string da matriz de distâncias mínimas
    }
}
