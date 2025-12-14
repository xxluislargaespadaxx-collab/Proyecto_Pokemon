package pokemon;

import java.util.Random;

public class Batalla {

    private Pokemons jugador;
    private Pokemons rival;
    private Random r;
    private String ganador;

    // Constructor
    public Batalla(Pokemons jugador, Pokemons rival) {
        this.jugador = jugador;
        this.rival = rival;
        this.r = new Random();
        this.ganador = null;
    }
    
    public void iniciar(){
        System.out.println("\n====== COMIENZA LA BATALLA! ======");
        System.out.println(jugador.getInfo());
        System.out.println("VS");
        System.out.println(rival.getInfo());
        System.out.println("====================================\n");
        
        while (jugador.puedeAtacar() && rival.puedeAtacar()) {
            turnoJugador();
            if (rival.estaDebilitado()) break;
            
            turnoRival();
            if (jugador.estaDebilitado()) break;
            
            mostrarEstado();
        }
        determinarGanador();
        mostrarResultado();
    }
    private void turnoJugador(){
        System.out.println("\n=== TU TURNO ===");
        System.out.println("Elige un ataque:");
        
        Ataque[] ataques = jugador.getAtaques();
        for (int i = 0; i < ataques.length; i++) {
            if (ataques[i] != null) {
                System.out.println((i+1) + ". " + ataques[i]);
            }
        }
        
        int eleccion;
        do {
            System.out.println("Seleccion (1-4): ");
            eleccion = Pokemon.validarEntero(1, 4) - 1;
            
        } while (ataques[eleccion] == null || !ataques[eleccion].tienePP());
        
        Ataque ataqueElegido = ataques[eleccion];
        int danio = calcuDano(jugador, rival, ataqueElegido);
        
        System.out.println(jugador.getNombre() + " usa" + ataqueElegido.getNombre() + "!");
        rival.recibirDanio(danio, ataqueElegido.getTipo());
        
        double probabilidad = r.nextDouble();
        if (probabilidad < 0.1) {
            System.out.println("Efecto adicional aplicado!");
        }
    }
    
    private void turnoRival(){
        System.out.println("\n=== TURNO DEL RIVAL ===");
        Ataque[] ataques = rival.getAtaques();
        int intentos = 0;
        int indiceAtaque;
        
        do {
            indiceAtaque = r.nextInt(ataques.length);
            intentos++;
        } while (ataques[indiceAtaque] == null || !ataques[indiceAtaque].tienePP() && intentos < 10);
        
        if (ataques[indiceAtaque] != null && ataques[indiceAtaque].tienePP()) {
            Ataque ataque = ataques[indiceAtaque];
            int danio = calcuDano(rival, jugador, ataque);
            
            System.out.println(rival.getNombre() + " usa" + ataque.getNombre() + "!");
            jugador.recibirDanio(danio, ataque.getTipo());
        } else {
            System.out.println(rival.getNombre() + " no puede atacar!");
        }
    }
    
    private int calcuDano(Pokemons atacante, Pokemons defensor, Ataque ataque) {
        int nivel = 50;
        int ataqueStat = atacante.getAtaque();
        int defensaStat = defensor.getDefensa();
        int poder = ataque.getPoder();
        
        double danio = (((2 * nivel / 5.0 + 2) * poder * ataqueStat / defensaStat) / 50.0) + 2;
        double variacion = 0.85 + (r.nextDouble() * 0.15);
        danio *= variacion;
        return (int) danio;
    }
    
    private void mostrarEstado(){
        System.out.println("\n=== ESTADO ACTUAL ===");
        System.out.println(jugador.getNombre() + ": " + jugador.getHp() + "/" + jugador.getHpMax() + " HP");
        System.out.println(rival.getNombre() + ": " + jugador.getHp() + "/" + jugador.getHpMax() + " HP");
        System.out.println("=====================\n");
    }
    
    private void determinarGanador(){
        if (jugador.estaDebilitado()) {
            ganador = rival.getNombre();
        } else {
            ganador = jugador.getNombre();
        }
    }
    
    private void mostrarResultado(){
        System.out.println("\n====== RESULTADO ======");
        System.out.println(jugador.getNombre() + ": " +
                (jugador.estaDebilitado() ? "DEBILITADO" : "VICTORIOSO"));
        System.out.println(rival.getNombre() + ": " +
                (rival.estaDebilitado() ? "DEBILITADO" : "VICTORIOSO"));
        System.out.println(ganador + " gana la batalla!");
        System.out.println("=======================\n");
    }
    
    public String obtenerGanador(){
        return ganador;
    }
}
