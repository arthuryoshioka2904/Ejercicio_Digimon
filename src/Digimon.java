public class Digimon {

    private String nombre;
    private Tipo tipo;
    private int ataque;
    private int defensa;
    private DigiEvolucion digiEvolucion;

    public Digimon(String nombre, Tipo tipo, int ataque,
                   int defensa, DigiEvolucion digiEvolucion) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.ataque = ataque;
        this.defensa = defensa;
        this.digiEvolucion = digiEvolucion;
    }

    public String getNombre() {
        return nombre;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public int getAtaque() {
        return ataque;
    }

    public int getDefensa() {
        return defensa;
    }

    public DigiEvolucion getDigiEvolucion() {
        return digiEvolucion;
    }
}