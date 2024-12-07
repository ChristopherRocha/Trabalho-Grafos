using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Trabalho_Grafos
{
    internal class MatrizIncidencia
    {
        private int[,] matriz;
        private int arestaAtual = 0;

        public MatrizIncidencia(int numVertices, int numArestas)
        {
            matriz = new int[numVertices, numArestas];
        }

        public void AdicionarAresta(int origem, int destino, int peso)
        {
            matriz[origem, arestaAtual] = peso;
            matriz[destino, arestaAtual] = peso;
            arestaAtual++;
        }

        public void Imprimir()
        {
            Console.WriteLine("Matriz de Incidência:");
            for (int i = 0; i < matriz.GetLength(0); i++)
            {
                for (int j = 0; j < matriz.GetLength(1); j++)
                {
                    Console.Write(matriz[i, j] + "\t");
                }
                Console.WriteLine();
            }
        }
    }
}
