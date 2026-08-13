import java.util.ArrayList;
import java.util.List;

public class Entrenador {

    private String nombre;
    private List<Digimon> digimons;
    private int rondasGanadas;

    public Entrenador(String nombre) {
        this.nombre = nombre;
        this.digimons = new ArrayList<>();
        this.rondasGanadas = 0;
    }

    public void agregarDigimon(Digimon digimon) {
        if (digimons.size() < 4) {
            digimons.add(digimon);
        }
    }

    public Digimon seleccionarDigimon(int indice) {
        return digimons.remove(indice);
    }

    public void incrementarRondas() {
        rondasGanadas++;
    }

    public String getNombre() {
        return nombre;
    }

    public List<Digimon> getDigimons() {
        return digimons;
    }

    public int getRondasGanadas() {
        return rondasGanadas;
    }
}