import java.util.Random;

public class DigiEvolucion {

    private String nombre;
    private TipoEfecto tipoEfecto;
    private int valor;
    private int probabilidad;

    public DigiEvolucion(String nombre, TipoEfecto tipoEfecto,
                         int valor, int probabilidad) {
        this.nombre = nombre;
        this.tipoEfecto = tipoEfecto;
        this.valor = valor;
        this.probabilidad = probabilidad;
    }

    public boolean intentarActivacion() {
        Random random = new Random();
        int numero = random.nextInt(100);

        return numero < probabilidad;
    }

    public String getNombre() {
        return nombre;
    }

    public TipoEfecto getTipoEfecto() {
        return tipoEfecto;
    }

    public int getValor() {
        return valor;
    }

    public int getProbabilidad() {
        return probabilidad;
    }
}