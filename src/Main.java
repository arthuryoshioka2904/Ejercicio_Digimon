import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        DigimonDB digimonDB = new DigimonDB();
        ControladorCombate controlador = new ControladorCombate(digimonDB);

        System.out.println("================================");
        System.out.println("       BATALLA DIGIMON");
        System.out.println("================================");

        System.out.print("Nombre del entrenador 1: ");
        String nombre1 = scanner.nextLine();

        System.out.print("Nombre del entrenador 2: ");
        String nombre2 = scanner.nextLine();

        Entrenador entrenador1 = new Entrenador(nombre1);
        Entrenador entrenador2 = new Entrenador(nombre2);

        controlador.registrarEntrenadores(entrenador1, entrenador2);
        seleccionarEquipo(scanner, entrenador1, digimonDB);
        seleccionarEquipo(scanner, entrenador2, digimonDB);
        jugarRondas(
            scanner,
            entrenador1,
            entrenador2,
            controlador
        );
        int ganadorFinal = controlador.obtenerGanadorFinal();

        System.out.println("================================");
        System.out.println("       RESULTADO FINAL");
        System.out.println("================================");

        System.out.println(
                entrenador1.getNombre() + ": "
                        + entrenador1.getRondasGanadas()
                        + " rondas ganadas"
        );

        System.out.println(
                entrenador2.getNombre() + ": "
                        + entrenador2.getRondasGanadas()
                        + " rondas ganadas"
        );

        if (ganadorFinal == 1) {
            System.out.println(
                    "Ganador de la batalla: "
                            + entrenador1.getNombre()
            );
        } else if (ganadorFinal == 2) {
            System.out.println(
                    "Ganador de la batalla: "
                            + entrenador2.getNombre()
            );
        } else {
            System.out.println("La batalla termino en empate.");
        }
        scanner.close();
    }
    private static void mostrarDigimons(DigimonDB digimonDB) {
    System.out.println("\nDigimon disponibles:");

    for (int i = 0; i < digimonDB.getDigimon().size(); i++) {
        Digimon digimon = digimonDB.getDigimon(i);

        System.out.println(
                (i + 1) + ". "
                + digimon.getNombre()
                + " | Tipo: " + digimon.getTipo()
                + " | Ataque: " + digimon.getAtaque()
                + " | Defensa: " + digimon.getDefensa()
             );
        }
    }
    private static void seleccionarEquipo(Scanner scanner,
                                      Entrenador entrenador,
                                      DigimonDB digimonDB) {

    System.out.println("\n" + entrenador.getNombre()
            + ", selecciona 4 Digimon:");

    int seleccionados = 0;

    for (; seleccionados < 4;) {

        mostrarDigimons(digimonDB);

        System.out.print("Selecciona el Digimon "
                + (seleccionados + 1) + ": ");

        int opcion = scanner.nextInt() - 1;

        if (opcion >= 0 && opcion < digimonDB.getDigimon().size()) {

            Digimon seleccionado = digimonDB.getDigimon(opcion);

            if (!digimonYaSeleccionado(entrenador, seleccionado)) {

                entrenador.agregarDigimon(seleccionado);
                seleccionados++;

                System.out.println(
                        seleccionado.getNombre()
                                + " agregado al equipo."
                );

            } else {
                System.out.println(
                        "Ese Digimon ya fue seleccionado. Elige otro."
                );
            }

        } else {
            System.out.println(
                    "Opcion invalida. Intenta nuevamente."
            );
        }
    }

    scanner.nextLine();
}
private static boolean digimonYaSeleccionado(
    Entrenador entrenador, Digimon digimon) {

    return entrenador.getDigimons().contains(digimon);
}
private static Digimon elegirDigimonRonda(
        Scanner scanner, Entrenador entrenador) {

    System.out.println("\nDigimon disponibles de "
            + entrenador.getNombre() + ":");

    for (int i = 0; i < entrenador.getDigimons().size(); i++) {
        Digimon digimon = entrenador.getDigimons().get(i);

        System.out.println(
                (i + 1) + ". "
                + digimon.getNombre()
                + " | Tipo: " + digimon.getTipo()
                + " | Ataque: " + digimon.getAtaque()
                + " | Defensa: " + digimon.getDefensa()
        );
    }

    int opcion = -1;

    for (; opcion < 0 || opcion >= entrenador.getDigimons().size();) {

        System.out.print("Selecciona un Digimon: ");
        opcion = scanner.nextInt() - 1;

        if (opcion < 0 || opcion >= entrenador.getDigimons().size()) {
            System.out.println("Opcion invalida. Intenta nuevamente.");
        }
    }

    return entrenador.seleccionarDigimon(opcion);
}
private static boolean elegirAccion(
        Scanner scanner, Entrenador entrenador, Digimon digimon) {

    int opcion = 0;

    for (; opcion != 1 && opcion != 2;) {

        System.out.println("\n" + entrenador.getNombre()
                + " usara a " + digimon.getNombre());

        System.out.println("1. Atacar");
        System.out.println("2. Usar DigiEvolucion");

        System.out.print("Selecciona una accion: ");
        opcion = scanner.nextInt();

        if (opcion != 1 && opcion != 2) {
            System.out.println("Opcion invalida. Intenta nuevamente.");
        }
    }

    return opcion == 2;
}
private static void jugarRondas(
        Scanner scanner,
        Entrenador entrenador1,
        Entrenador entrenador2,
        ControladorCombate controlador) {

    for (int ronda = 1; ronda <= 4; ronda++) {

        System.out.println("==============================");
        System.out.println("           RONDA " + ronda);
        System.out.println("==============================");

        Digimon digimon1 = elegirDigimonRonda(scanner, entrenador1);
        Digimon digimon2 = elegirDigimonRonda(scanner, entrenador2);

        boolean usarDigiEvolucion1 =
                elegirAccion(scanner, entrenador1, digimon1);

        boolean usarDigiEvolucion2 =
                elegirAccion(scanner, entrenador2, digimon2);

        int[] resultado = controlador.iniciarRonda(
                digimon1,
                digimon2,
                usarDigiEvolucion1,
                usarDigiEvolucion2
        );

        System.out.println("--- Resultado de la ronda ---");

        System.out.println(
                entrenador1.getNombre()
                        + " - " + digimon1.getNombre()
                        + " | Ataque total: " + resultado[0]
        );

        System.out.println(
                entrenador2.getNombre()
                        + " - " + digimon2.getNombre()
                        + " | Ataque total: " + resultado[1]
        );

        if (usarDigiEvolucion1) {
            if (resultado[3] == 1) {
                System.out.println(
                        "La DigiEvolucion de "
                                + digimon1.getNombre()
                                + " se activo."
                );
            } else {
                System.out.println(
                        "La DigiEvolucion de "
                                + digimon1.getNombre()
                                + " no se activo."
                );
            }
        }

        if (usarDigiEvolucion2) {
            if (resultado[4] == 1) {
                System.out.println(
                        "La DigiEvolucion de "
                                + digimon2.getNombre()
                                + " se activo."
                );
            } else {
                System.out.println(
                        "La DigiEvolucion de "
                                + digimon2.getNombre()
                                + " no se activo."
                );
            }
        }

        if (resultado[2] == 1) {
            System.out.println(
                    "Ganador de la ronda: "
                            + entrenador1.getNombre()
            );
        } else if (resultado[2] == 2) {
            System.out.println(
                    "Ganador de la ronda: "
                            + entrenador2.getNombre()
            );
        } else {
            System.out.println("La ronda termino en empate.");
        }

        System.out.println(
                "Marcador: "
                        + entrenador1.getNombre() + " "
                        + entrenador1.getRondasGanadas()
                        + " - "
                        + entrenador2.getRondasGanadas() + " "
                        + entrenador2.getNombre()
        );
    }
}
}