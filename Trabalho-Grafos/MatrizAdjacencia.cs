using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Trabalho_Grafos
{
    internal class MatrizAdjacencia
    {
        private int[,] matriz;

        public MatrizAdjacencia(int numVertices)
        {
            matriz = new int[numVertices, numVertices];
        }

        public void AdicionarAresta(int origem, int destino, int peso)
        {
            matriz[origem, destino] = peso;
            matriz[destino, origem] = peso;
        }

        public void Imprimir()
        {
            Console.WriteLine("Matriz de Adjacência:");
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
