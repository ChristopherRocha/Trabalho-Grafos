using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Trabalho_Grafos
{
    internal class ListaAdjacencia
    {
        private List<(int destino, int peso)>[] lista;

        public ListaAdjacencia(int numVertices)
        {
            lista = new List<(int destino, int peso)>[numVertices];
            for (int i = 0; i < numVertices; i++)
            {
                lista[i] = new List<(int destino, int peso)>();
            }
        }

        public void AdicionarAresta(int origem, int destino, int peso)
        {
            lista[origem].Add((destino, peso));
        }

        public void Imprimir()
        {
            Console.WriteLine("Lista de Adjacência:");
            for (int i = 0; i < lista.Length; i++)
            {
                Console.Write($"Vértice {i}: ");
                foreach (var aresta in lista[i])
                {
                    Console.Write($"({aresta.destino}, {aresta.peso}) ");
                }
                Console.WriteLine();
            }
        }
    }
}
