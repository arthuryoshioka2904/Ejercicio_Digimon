public class ControladorCombate {

    private Entrenador entrenador1;
    private Entrenador entrenador2;
    
    private int efectoPendiente1;
    private TipoEfecto tipoEfectoPendiente1;
    private int duracionEfecto1;

    private int efectoPendiente2;
    private TipoEfecto tipoEfectoPendiente2;
    private int duracionEfecto2;

    public ControladorCombate(DigimonDB digimonDB) {
        this.efectoPendiente1 = 0;
        this.efectoPendiente2 = 0;
        this.duracionEfecto1 = 0;
        this.duracionEfecto2 = 0;
        this.tipoEfectoPendiente1 = null;
        this.tipoEfectoPendiente2 = null;
    }
    public void registrarEntrenadores(Entrenador entrenador1,
                                  Entrenador entrenador2) {
    this.entrenador1 = entrenador1;
    this.entrenador2 = entrenador2;
                                  }
    private int calcularModificadorTipo(Digimon atacante, Digimon defensor) {
    Tipo tipoAtacante = atacante.getTipo();
    Tipo tipoDefensor = defensor.getTipo();

    if ((tipoAtacante == Tipo.FUEGO && tipoDefensor == Tipo.PLANTA)
            || (tipoAtacante == Tipo.PLANTA && tipoDefensor == Tipo.AGUA)
            || (tipoAtacante == Tipo.AGUA && tipoDefensor == Tipo.FUEGO)
            || (tipoAtacante == Tipo.ELECTRICO && tipoDefensor == Tipo.AGUA)) {

        return 20;
    }

    if ((tipoAtacante == Tipo.PLANTA && tipoDefensor == Tipo.FUEGO)
            || (tipoAtacante == Tipo.AGUA && tipoDefensor == Tipo.PLANTA)
            || (tipoAtacante == Tipo.FUEGO && tipoDefensor == Tipo.AGUA)
            || (tipoAtacante == Tipo.AGUA && tipoDefensor == Tipo.ELECTRICO)) {

        return -10;
    }

    return 0;
    }
    private int calcularAtaqueTotal(Digimon atacante, Digimon defensor,
                                int efectoAtaque, int efectoDefensaRival,
                                int danioRival) {

    int modificadorTipo = calcularModificadorTipo(atacante, defensor);

    int ataqueTotal = atacante.getAtaque()
            + efectoAtaque
            + modificadorTipo
            - defensor.getDefensa()
            - efectoDefensaRival
            - danioRival;

        return ataqueTotal;
    }

    private int aplicarDigiEvolucion(Digimon digimon) {
        DigiEvolucion digiEvolucion = digimon.getDigiEvolucion();

        if (digiEvolucion.intentarActivacion()) {
            return digiEvolucion.getValor();
        }

        return 0;
    }
    private void guardarEfectoPendiente(int jugador, Digimon digimon, int valor) {
    TipoEfecto tipoEfecto = digimon.getDigiEvolucion().getTipoEfecto();

    if (jugador == 1) {
        efectoPendiente1 = valor;
        tipoEfectoPendiente1 = tipoEfecto;
        duracionEfecto1 = 1;
    } else if (jugador == 2) {
        efectoPendiente2 = valor;
        tipoEfectoPendiente2 = tipoEfecto;
        duracionEfecto2 = 1;
    }
    }
    private void actualizarDuracionEfectos() {
    if (duracionEfecto1 > 0) {
        duracionEfecto1--;

        if (duracionEfecto1 == 0) {
            efectoPendiente1 = 0;
            tipoEfectoPendiente1 = null;
        }
    }

    if (duracionEfecto2 > 0) {
        duracionEfecto2--;

        if (duracionEfecto2 == 0) {
            efectoPendiente2 = 0;
            tipoEfectoPendiente2 = null;
        }
    }
    }
    private int obtenerEfectoAtaque(int jugador) {
    if (jugador == 1 && tipoEfectoPendiente1 == TipoEfecto.ATAQUE) {
        return efectoPendiente1;
    }

    if (jugador == 2 && tipoEfectoPendiente2 == TipoEfecto.ATAQUE) {
        return efectoPendiente2;
    }

    return 0;
    }
    private int obtenerEfectoDefensa(int jugador) {
    if (jugador == 1 && tipoEfectoPendiente1 == TipoEfecto.DEFENSA) {
        return efectoPendiente1;
    }

    if (jugador == 2 && tipoEfectoPendiente2 == TipoEfecto.DEFENSA) {
        return efectoPendiente2;
    }

    return 0;
    }
    private int obtenerDanioRival(int jugador) {
    if (jugador == 1 && tipoEfectoPendiente1 == TipoEfecto.DANIO_RIVAL) {
        return efectoPendiente1;
    }

    if (jugador == 2 && tipoEfectoPendiente2 == TipoEfecto.DANIO_RIVAL) {
        return efectoPendiente2;
    }

    return 0;
    }
    private int[] calcularAtaquesRonda(Digimon digimon1, Digimon digimon2, int nuevoEfecto1, int nuevoEfecto2) {
        int nuevoAtaque1 = 0;
        int nuevaDefensa1 = 0;
        int nuevoDanio1 = 0;

        int nuevoAtaque2 = 0;
        int nuevaDefensa2 = 0;
        int nuevoDanio2 = 0;
    int ataque1 = calcularAtaqueTotal(
            digimon1,
            digimon2,
            obtenerEfectoAtaque(1) + nuevoAtaque1, 
            obtenerEfectoDefensa(2) + nuevaDefensa2,
            obtenerDanioRival(2) + nuevoDanio2
    );

    int ataque2 = calcularAtaqueTotal(
            digimon2,
            digimon1,
            obtenerEfectoAtaque(2) + nuevoAtaque2,
            obtenerEfectoDefensa(1) + nuevaDefensa1,
            obtenerDanioRival(1) + nuevoDanio1
    );

    return new int[]{ataque1, ataque2};
    }
    private int determinarGanadorRonda(int ataque1, int ataque2) {

    if (ataque1 > ataque2) {
        entrenador1.incrementarRondas();
        return 1;
    }

    if (ataque2 > ataque1) {
        entrenador2.incrementarRondas();
        return 2;
    }

    return 0;
    }
    public int[] iniciarRonda(Digimon digimon1, Digimon digimon2,
                          boolean usarDigiEvolucion1,
                          boolean usarDigiEvolucion2) {

    int nuevoEfecto1 = 0;
    int nuevoEfecto2 = 0;

    if (usarDigiEvolucion1) {
        nuevoEfecto1 = aplicarDigiEvolucion(digimon1);
    }

    if (usarDigiEvolucion2) {
        nuevoEfecto2 = aplicarDigiEvolucion(digimon2);
    }

    boolean activada1 = nuevoEfecto1 > 0;
    boolean activada2 = nuevoEfecto2 > 0;

    int[] ataques = calcularAtaquesRonda(
        digimon1,
        digimon2,
        nuevoEfecto1,
        nuevoEfecto2
    );

    int ganador = determinarGanadorRonda(
        ataques[0],
        ataques[1]
    );

    actualizarDuracionEfectos();

    if (activada1) {
    guardarEfectoPendiente(1, digimon1, nuevoEfecto1);
    }

    if (activada2) {
        guardarEfectoPendiente(2, digimon2, nuevoEfecto2);
    }

    return new int[]{
            ataques[0],
            ataques[1],
            ganador,
            activada1 ? 1 : 0,
            activada2 ? 1 : 0
    };
    }
    public int obtenerGanadorFinal() {
    if (entrenador1.getRondasGanadas() > entrenador2.getRondasGanadas()) {
        return 1;
    }

    if (entrenador2.getRondasGanadas() > entrenador1.getRondasGanadas()) {
        return 2;
    }

    return 0;
    }
}
