import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import javax.annotation.processing.FilerException;

public class App {
    static Scanner sc;
    static GrafoDirecionado grafo;
    static Vertice vertice;

    public static void main(String[] args) throws Exception {
        sc = new Scanner(System.in);

        String nomeArq = "menuOpcoes";
        int opcao = -1;

        while (opcao != 0) {
            limparTela();
            opcao = menu(nomeArq);
            switch (opcao) {
                case 1:
                    limparTela();
                    parteUm();
                    break;

                case 2:
                    limparTela();
                    parteDois();
                    break;
            }
            pausa();
        }
        System.out.println("Sistema fechado!");
        sc.close();
    }

    public static void parteUm() {
        System.out.println("Criar grafo!");
        criarGrafoDirecionado();

        sc = new Scanner(System.in);
        String nomeArq = "opcoes1";
        int opcao = -1;
        limparTela();

        while (opcao != 0) {
            limparTela();
            opcao = menu(nomeArq);

            switch (opcao) {
                case 1:
                    limparTela();
                    escolherFormaDeRepresentacao();
                    break;
                case 2:
                    limparTela();
                    imprimirMelhorRepresentacao();
                    break;
            }
        }
    }

    public static void criarGrafoDirecionado() {
        System.out.println("Digite a quantidade de vértices: ");
        int vertices = sc.nextInt();
        System.out.println("Digite a quantidade de arestas: ");
        int arestas = sc.nextInt();

        grafo = new GrafoDirecionado();

        for (int i = 0; i < vertices; i++) {
            System.out.println("Digite os vértices: ");
            int vertice = sc.nextInt();

            grafo.adicionarVertice(vertice);
        }
        System.out.println("Vértices adicionados!");

        System.out.println("Conectar vértices");

        for (int i = 0; i < arestas; i++) {
            System.out.println("Digite o vertíce de origem: ");
            int verticeOrigem = sc.nextInt();
            System.out.println("Digite o vertíce de destino: ");
            int verticeDestino = sc.nextInt();
            System.out.println("Digite o peso da aresta: ");
            int peso = sc.nextInt();

            grafo.adicionarAresta(peso, verticeOrigem, verticeDestino);
            System.out.println("Aresta adicionada!");
        }
        pausa();
    }

    public static void escolherFormaDeRepresentacao() {
        sc = new Scanner(System.in);
        String nomeArq = "formasDeRepresentacao";
        int opcao = -1;
        limparTela();

        while (opcao != 0) {
            limparTela();
            opcao = menu(nomeArq);

            switch (opcao) {
                case 1:
                    grafo.gerarMatrizAdjacencia();
                    System.out.println(grafo.exibirMatrizAdjacencia());
                    pausa();
                    break;
                case 2:
                    grafo.gerarMatrizIncidencia();
                    System.out.println(grafo.exibirMatrizIncidencia());
                    pausa();
                    break;
                case 3:
                    grafo.gerarListaAdjacencia();
                    System.out.println(grafo.exibirListaAdjacencia());
                    pausa();
                    break;
            }
        }
    }

    public static void parteDois() throws CloneNotSupportedException, IOException {
        lerGrafoDIMACS();
        sc = new Scanner(System.in);
        String nomeArq = "opcoes2";
        int opcao = -1;
        limparTela();

        while (opcao != 0) {
            limparTela();
            opcao = menu(nomeArq);

            switch (opcao) {
                case 1:
                    imprimirMelhorRepresentacao();
                    break;
                case 2:
                    System.out.println("Digite o vértice de origem da aresta: ");
                    int origem = sc.nextInt();
                    Vertice verticeO = grafo.recuperarVertice(origem);
                    if (verticeO != null) {
                        System.out.println("Digite o vértice de destino da aresta: ");
                        int destino = sc.nextInt();
                        Vertice verticeD = grafo.recuperarVertice(destino);
                        sc.nextLine();
                        if (verticeD != null) {
                            System.out.println(grafo.imprimirArestasAdjacentes(verticeO, verticeD));
                            pausa();
                        } else {
                            System.out.println("Vértice não existe!");
                            pausa();
                        }
                    } else {
                        System.out.println("Vértice não existe!");
                        pausa();
                    }
                    break;
                case 3:
                    System.out.println("Digite o vértice:");
                    int verticeParaVerificarAdjacentes = sc.nextInt();
                    vertice = grafo.recuperarVertice(verticeParaVerificarAdjacentes);
                    if (vertice != null) {
                        System.out.println(grafo.imprimirVerticesAdjacentes(vertice));
                        sc.nextLine();
                        pausa();
                    } else {
                        System.out.println("O vértice não existe!");
                        pausa();
                    }
                    break;
                case 4:
                    System.out.println("Digite o vértice:");
                    int verticeParaVerificarArestasIncidentes = sc.nextInt();
                    vertice = grafo.recuperarVertice(verticeParaVerificarArestasIncidentes);

                    if (vertice != null) {
                        System.out.println(grafo.imprimirArestasIncidentes(vertice));
                        sc.nextLine();
                        pausa();
                    } else {
                        System.out.println("O vértice não existe!");
                        pausa();
                    }
                    break;
                case 5:
                    System.out.println("Digite a origem da aresta:");
                    int arestaOrigem = sc.nextInt();
                    System.out.println("Digite o destino da aresta:");
                    int arestaDestino = sc.nextInt();
                    grafo.imprimirVerticesIncidentes(arestaOrigem, arestaDestino);
                    sc.nextLine();
                    pausa();
                    break;
                case 6:
                    System.out.println("Digite o vértice:");
                    int verticeGrau = sc.nextInt();
                    vertice = grafo.recuperarVertice(verticeGrau);
                    if (vertice != null) {
                        System.out.println(grafo.imprimirGrauVertice(vertice));
                        sc.nextLine();
                        pausa();
                    } else {
                        System.out.println("O vértice não existe!");
                        pausa();
                    }
                    break;
                case 7:
                    System.out.println("Digite o 1º vértice:");
                    int vertice1 = sc.nextInt();
                    vertice = grafo.recuperarVertice(vertice1);
                    if (vertice != null) {
                        System.out.println("Digite o 2º vértice:");
                        int vertice2 = sc.nextInt();
                        Vertice verticeDestino = grafo.recuperarVertice(vertice2);
                        if (verticeDestino != null) {
                            grafo.verificarVerticesAdjacentes(vertice1, vertice2);
                            sc.nextLine();
                            pausa();
                        } else {
                            System.out.println("O vértice não existe");
                            pausa();
                        }
                    } else {
                        System.out.println("O vértice não existe");
                        pausa();
                    }
                    break;
                case 8:
                    System.out.println("Digite o vértice de origem da aresta: ");
                    int verticeOrigem = sc.nextInt();
                    vertice = grafo.recuperarVertice(verticeOrigem);
                    if (vertice != null) {
                        System.out.println("Digite o vértice de destino da aresta: ");
                        int verticeDestino = sc.nextInt();
                        Vertice verticeDest = grafo.recuperarVertice(verticeDestino);
                        if (verticeDest != null) {
                            System.out.println("Digite o novo peso da aresta: ");
                            int novoPeso = sc.nextInt();
                            System.out.println(grafo.substituirPesoAresta(vertice, verticeDest, novoPeso));
                            sc.nextLine();
                            pausa();
                        } else {
                            System.out.println("O vértice não existe!");
                            pausa();
                        }
                    } else {
                        System.out.println("O vértice não existe!");
                        pausa();
                    }
                    break;
                case 9:
                    System.out.println("Digite o 1º vértice que deseja trocar: ");
                    int v1 = sc.nextInt();
                    vertice = grafo.recuperarVertice(v1);

                    if (vertice != null) {
                        System.out.println("Digite o 2º vértice que deseja trocar: ");
                        int v2 = sc.nextInt();
                        Vertice verticeTroca2 = grafo.recuperarVertice(v2);

                        if (verticeTroca2 != null) {
                            grafo.trocarDoisVertices(vertice, verticeTroca2);
                            sc.nextLine();
                            pausa();
                        } else {
                            System.out.println("O vértice não existe!");
                            pausa();
                        }
                    } else {
                        System.out.println("O vértice não existe!");
                        pausa();
                    }

                    break;
                case 10:
                    System.out.println("Digite o vértice inicial:");
                    int verticeRaiz = sc.nextInt();
                    vertice = grafo.recuperarVertice(verticeRaiz);
                    if (vertice != null) {
                        grafo.buscaEmLargura(verticeRaiz);
                        sc.nextLine();
                        pausa();
                    } else {
                        System.out.println("O vértice não existe!");
                        pausa();
                    }
                    break;
                case 11:
                    System.out.println("Digite o vértice inicial: ");
                    int verticeInicial = sc.nextInt();
                    Vertice vertice = grafo.recuperarVertice(verticeInicial);
                    if (vertice != null) {
                        grafo.buscaEmProfundidade(verticeInicial);
                        sc.nextLine();
                        pausa();
                    } else {
                        System.out.println("O vértice não existe!");
                        pausa();
                    }
                    break;
                case 12:
                    System.out.println("Digite o vértice de inicio: ");
                    int verticeOrigemDijkstra = sc.nextInt();
                    vertice = grafo.recuperarVertice(verticeOrigemDijkstra);
                    if (vertice != null) {
                        System.out.println("Digite o vértice de destino: ");
                        int verticeDestinoDijkstra = sc.nextInt();
                        Vertice verticeDestDijstra = grafo.recuperarVertice(verticeDestinoDijkstra);
                        if (verticeDestDijstra != null) {
                            System.out.println(grafo.dijkstra(verticeOrigemDijkstra, verticeDestinoDijkstra));
                            sc.nextLine();
                            pausa();
                        } else {
                            System.out.println("O vértice não existe!");
                            pausa();
                        }
                    } else {
                        System.out.println("O vértice não existe!");
                        pausa();
                    }
                    break;
                case 13:
                    System.out.println(grafo.floydWarshall());
                    sc.nextLine();
                    pausa();
                    break;
            }
        }
    }

    public static void imprimirMelhorRepresentacao() {
        double result = grafo.verificarDensidadeGrafo();

        if (estaMaisProximoDeZero(result)) {
            System.out.println("Grafo esparso. Melhor forma de representação: Lista de Adjacência");
            grafo.gerarListaAdjacencia();
            System.out.println(grafo.exibirListaAdjacencia());
        } else {
            System.out.println(
                    "Grafo denso. Melhores formas de representação: Matriz de Adjacência e Matriz de Incidência");
            grafo.gerarMatrizAdjacencia();
            grafo.gerarMatrizIncidencia();
            System.out.println(grafo.exibirMatrizAdjacencia());
            System.out.println();
            System.out.println(grafo.exibirMatrizIncidencia());
        }
        pausa();
    }

    public static boolean estaMaisProximoDeZero(double valor) {
        double diferencaZero = Math.abs(valor - 0);
        double diferencaUm = Math.abs(valor - 1);

        return diferencaZero < diferencaUm;
    }

    public static void lerGrafoDIMACS() throws IOException {
        String nomeArq = "grafo";

        BufferedReader reader = new BufferedReader(new FileReader(nomeArq));
        try {
            String linha = reader.readLine();

            if (linha != null) {
                reader.readLine();
                grafo = new GrafoDirecionado();

                while ((linha = reader.readLine()) != null) {
                    String[] linhaAux = linha.split(" ");

                    int vertice = Integer.parseInt(linhaAux[0]);

                    if (grafo.recuperarVertice(vertice) == null)
                        grafo.adicionarVertice(vertice);
                }
                reader.close();

                reader = new BufferedReader(new FileReader(nomeArq));
                reader.readLine();

                while ((linha = reader.readLine()) != null) {
                    String[] linhaAux = linha.split(" ");

                    int verticeOrigem = Integer.parseInt(linhaAux[0]);
                    int verticeDestino = Integer.parseInt(linhaAux[1]);
                    int peso = Integer.parseInt(linhaAux[2]);

                    grafo.adicionarAresta(peso, verticeOrigem, verticeDestino);
                }
                System.out.println("Grafo lido com sucesso!");
            }
        } catch (FilerException f) {
            System.out.println("Erro ao ler o arquivo.");
            f.printStackTrace();
        } finally {
            reader.close();
        }

        pausa();
    }

    public static void limparTela() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    static void pausa() {
        System.out.println("Enter para continuar.");
        sc.nextLine();
    }

    public static int menu(String nomeArq) {
        limparTela();
        File arqMenu;
        Scanner leitor;
        int opcao = -1;
        try {
            arqMenu = new File(nomeArq);
            leitor = new Scanner(arqMenu, "UTF-8");
            System.out.println(leitor.nextLine());
            System.out.println("===============================");
            int contador = 1;

            while (leitor.hasNextLine()) {
                System.out.println(contador + " - " + leitor.nextLine());
                contador++;
            }
            System.out.println("0 - Sair");
            System.out.println("\nSua opção: ");
            opcao = Integer.parseInt(sc.nextLine());
            leitor.close();
        } catch (NumberFormatException e) {
            System.out.println("Digite apenas números.");
            e.printStackTrace();
        } catch (FileNotFoundException f) {
            System.out.println("Arquivo não encontrado, verifique se o nome do arquivo está correto.");
        }
        return opcao;
    }
}
