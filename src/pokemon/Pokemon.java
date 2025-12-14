package pokemon;

import java.util.Random;
import java.util.Scanner;
import javax.swing.JOptionPane;

public class Pokemon {

    private static Scanner read = new Scanner(System.in);
    private static Random r = new Random();
    private static Pokedex pokedex = new Pokedex();

    public static void main(String[] args) {
        mostBienvenida();
        menuPrincipal();
    }

    private static void mostBienvenida() {
        System.out.println("======================================");
        System.out.println("   SIMULADOR DE BATALLA POKEMON");
        System.out.println("======================================");
        System.out.println("Bienvenido entrenador!");
    }

    private static void menuPrincipal() {
        int op;
        do {
            System.out.println("====== MENU POKEMON ======");
            System.out.println("1. Iniciar nueva batalla");
            System.out.println("2. Ver Pokedex");
            System.out.println("3. Practica de ataques");
            System.out.println("4. Configuracion");
            System.out.println("5. Acerca del programa");
            System.out.println("6. Salir");
            System.out.println("Entrenador elija su opcion -> ");
            op = read.nextInt();

            switch (op) {
                case 1:
                    iniciarBatalla();
                    break;
                case 2:
                    mostrarPokedex();
                    break;
                case 3:
                    practicarAtaques();
                    break;
                case 4:
                    menuConfiguracion();
                    break;
                case 5:
                    mostrarAcercaDe();
                    break;
                case 6:
                    System.out.println("Hasta Luego entrenador!");
                    break;
                default:
                    System.out.println("Entrenador elija una opcion valida.");

            }

        } while (op != 6);
    }

    public static int validarEntero(int min, int max) {
        while (true) {
            String input = read.nextLine();

            boolean esNumero = true;
            for (int i = 0; i < input.length(); i++) {
                char c = input.charAt(i);
                if (!Character.isDigit(c) && !(i == 0 && c == '-')) {
                    esNumero = false;
                    break;
                }
            }

            if (esNumero && !input.isEmpty()) {
                int valor = Integer.parseInt(input);
                if (valor >= min && valor <= max) {
                    return valor;
                } else {
                    System.out.printf("Error: Debe ser entre %d y %d. Intente de nuevo: ", min, max);
                }
            } else {
                System.out.print("Error: Debe ingresar un número. Intente de nuevo: ");
            }
        }
    }

    private static void iniciarBatalla() {
        System.out.println("====== PREPARANDO BATALLA ======");
        Pokemons[] pokemones = pokedex.obPokemonesAle(2);
        System.out.println("Los Pokemon seleccionados son!");
        System.out.println("1. " + pokemones[0]);
        System.out.println("2. " + pokemones[1]);

        System.out.println("Que Pokemon quieres usar?: ");
        int eleccion = validarEntero(1, 2);

        Pokemons jugador = pokemones[eleccion - 1];
        Pokemons rival = (eleccion == 1) ? pokemones[1] : pokemones[0];

        System.out.println("Has elegido a " + jugador.getNombre() + "!");
        System.out.println("Tu rival usara a" + rival.getNombre() + "!");

        Batalla batalla = new Batalla(jugador, rival);
        batalla.iniciar();

        JOptionPane.showMessageDialog(null,
                "Batalla completada.\n"
                + "Ganador: " + batalla.obtenerGanador(),
                "Resultado",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private static void mostrarPokedex() {
        System.out.println("====== POKEDEX ======");
        System.out.println("\n--- Submenu Pokedex ---");
        System.out.println("1. Buscar por tipo");
        System.out.println("2. Pokemon mas fuerte");
        System.out.println("3. Volver al menu principal");
        System.out.print("Opcion: ");
        int op = validarEntero(1, 3);

        switch (op) {
            case 1:
                BuscarXTipo();
                break;
            case 2:
                pokemonMasFuerte();
                break;
        }
    }
    
    private static void BuscarXTipo(){
        System.out.println("\n Tipos disponibles: Fuego, Agua, Planta, Electrico");
        System.out.print("Ingrese tipo a buscar: ");
        String tipo = read.nextLine();
        pokedex.BuscarXTipo(tipo);
    }
    
    private static void pokemonMasFuerte(){
        Pokemons fuerte = pokedex.pokemonMasFuerte();
        System.out.println("\nEl Pokemon mas fuerte es: " + fuerte);
    }
    
    private static void practicarAtaques(){
        System.out.println("\n====== PRACTICA DE ATAQUES ======");
        System.out.println("Ingrese el numero de veces para practicar (max 10)");
        int veces = validarEntero(1, 10);
        int totDano = Recursividad(veces, 10);
        System.out.println("Dano total acuulado: " + totDano);
        
        System.out.println("\nProbando ataque basico.....");
        int dano1 = calcuDano(50);
        int dano2 = calcuDano(50, 1.5);
        
        System.out.println("Dano normal: " + dano1);
        System.out.println("Dano critico: " + dano2);
    }
    
    private static int Recursividad(int veces, int danoBase){
        if (veces <= 0) {
            return 0;
        }
        int variacion = r.nextInt(11) - 5;
        int danoActual = danoBase + variacion;
        
        System.out.println("Practica " + veces + ": " + danoActual + " de dano");
        return danoActual + Recursividad(veces - 1, danoBase);
    }
    
    
    public static int calcuDano(int poder){
        return poder + r.nextInt(10);
    }
    
    public static int calcuDano(int poder, double multiplicador){
        return (int)((poder  + r.nextInt(10)) * multiplicador);
    }
    
    private static void menuConfiguracion(){
        System.out.println("\n ====== CONFIGURACION ======");
        System.out.println("1. Cambiar Dificultad");
        System.out.println("2. Mostrar estadisticas");
        System.out.println("3. Reiniciar datos");
        System.out.println("4. Volver");
        System.out.print("Opcion: ");
        int op = validarEntero(1, 4);
        
        if (op == 1) {
            String[] opciones = {"Facil, Normal, Dificil"};
            String dificultad = (String)JOptionPane.showInputDialog(
            null,
            "Seleciione la dificultad: ",
            "Configuracion",
            JOptionPane.QUESTION_MESSAGE,
            null,
            opciones,
            opciones[1]
            );
            System.out.println("Dificultad establecida: " + dificultad);
        }
    }
    
    private static void mostrarAcercaDe(){
         String mensaje = "SIMULADOR DE BATALLA POKEMON\n" +
                        "Desarrollado para Proyecto Final\n" +
                        "Programación 1 - Diciembre 2025\n\n" +
                        "Características implementadas:\n" +
                        "- 4 clases diferentes\n" +
                        "- Métodos estáticos y no estáticos\n" +
                        "- Métodos recursivos\n" +
                        "- Sobrecarga de métodos\n" +
                        "- Arreglos unidimensionales y bidimensionales\n" +
                        "- GUI con JOptionPane\n" +
                        "- Validaciones robustas";
         
         JOptionPane.showMessageDialog(null, mensaje, "Acerca del Programa", 
                                     JOptionPane.INFORMATION_MESSAGE);
    }
}
