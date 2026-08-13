import java.util.ArrayList;
import java.util.List;

public class DigimonDB {

    private List<Digimon> digimons;

    public DigimonDB() {
        digimons = new ArrayList<>();
        cargarDigimons();
    }

    private void cargarDigimons() {
        DigiEvolucion adult =
                new DigiEvolucion("Adult", TipoEfecto.ATAQUE, 15, 30);

        DigiEvolucion mega =
                new DigiEvolucion("Mega", TipoEfecto.DEFENSA, 20, 30);

        DigiEvolucion ultimate =
                new DigiEvolucion("Ultimate", TipoEfecto.DANIO_RIVAL, 10, 30);

        digimons.add(new Digimon(
                "Agumon", Tipo.FUEGO, 70, 40, adult));

        digimons.add(new Digimon(
                "Gabumon", Tipo.AGUA, 65, 45, mega));

        digimons.add(new Digimon(
                "Palmon", Tipo.PLANTA, 60, 50, ultimate));

        digimons.add(new Digimon(
                "Tentomon", Tipo.ELECTRICO, 68, 42, adult));

        digimons.add(new Digimon(
                "Biyomon", Tipo.FUEGO, 64, 44, mega));

        digimons.add(new Digimon(
                "Gomamon", Tipo.AGUA, 62, 48, ultimate));

        digimons.add(new Digimon(
                "Floramon", Tipo.PLANTA, 61, 47, adult));

        digimons.add(new Digimon(
                "Elecmon", Tipo.ELECTRICO, 66, 43, mega));
    }

    public List<Digimon> getDigimon() {
        return digimons;
    }

    public Digimon getDigimon(int indice) {
        return digimons.get(indice);
    }
}