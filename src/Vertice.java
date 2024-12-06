import java.util.ArrayList;

public class Vertice {
    private int dado;
    private ArrayList<Aresta> arestaEntrada;
    private ArrayList<Aresta> arestaSaida;

    public Vertice(int valor) {
        this.dado = valor;
        this.arestaEntrada = new ArrayList<Aresta>();
        this.arestaSaida = new ArrayList<Aresta>();
    }

    public void adicionarArestaEntrada(Aresta aresta) {
        this.arestaEntrada.add(aresta);
    }

    public void adicionarArestaSaida(Aresta aresta) {
        this.arestaSaida.add(aresta);
    }

    public int getDado() {
        return dado;
    }

    public void setDado(int dado) {
        this.dado = dado;
    }

    public ArrayList<Aresta> getArestaEntrada() {
        return arestaEntrada;
    }

    public void setArestaEntrada(ArrayList<Aresta> arestaEntrada) {
        this.arestaEntrada = arestaEntrada;
    }

    public ArrayList<Aresta> getArestaSaida() {
        return arestaSaida;
    }

    public void setArestaSaida(ArrayList<Aresta> arestaSaida) {
        this.arestaSaida = arestaSaida;
    }

}